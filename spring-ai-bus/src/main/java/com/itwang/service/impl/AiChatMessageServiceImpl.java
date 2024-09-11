package com.itwang.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.itwang.ai_model.DeepSeek.DeepSeekChatModel;
import com.itwang.common.constants.AiConstants;
import com.itwang.common.result.CommonResult;
import com.itwang.common.utils.AiUtils;
import com.itwang.common.utils.BeanUtils;
import com.itwang.dao.entity.AiChatConversation;
import com.itwang.dao.entity.AiChatMessage;
import com.itwang.dao.entity.AiChatModel;
import com.itwang.dao.mapper.AiChatMessageMapper;
import com.itwang.request.AiChatMessageSendReq;
import com.itwang.response.AiChatMessageSendResponse;
import com.itwang.service.AiChatMessageService;
import com.itwang.service.IAiApiKeyService;
import com.itwang.service.IAiChatConversationService;
import com.itwang.service.IAiChatModelService;
import jakarta.annotation.Resource;
import org.slf4j.MDC;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.StreamingChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.beans.Introspector;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class AiChatMessageServiceImpl implements AiChatMessageService {

    @Resource
    private IAiApiKeyService aiApiKeyService;

    @Resource
    private IAiChatConversationService aiChatConversationService;

    @Resource
    private AiChatMessageMapper aiChatMessageMapper;

    @Resource
    private IAiChatModelService chatModelService;

    /**
     * 兜底模型
     */
    private static final String DEFAULT_MODEL = Introspector.decapitalize(DeepSeekChatModel.class.getSimpleName());

    @Override
    public AiChatMessageSendResponse sendMessage(AiChatMessageSendReq sendReq, Long userId) {
        return null;
    }

    @Override
    public Flux<CommonResult<AiChatMessageSendResponse>> sendChatMessageStream(AiChatMessageSendReq sendReqVO, Long userId) {
        // 校验当前是否存在该用户的对话
        AiChatConversation chatConversation = aiChatConversationService.validateConversation(sendReqVO.getConversationId());
        if(!ObjUtil.equal(chatConversation.getUserId(), userId)){
            throw new RuntimeException("当前用户没有权限访问该对话");
        }

        List<AiChatMessage> chatMessageList = sendReqVO.getUseContext() ? aiChatMessageMapper.getChatMessageList(chatConversation.getId()) : Collections.emptyList();
        // 获取相关的模型
        AiChatModel aiChatModel = chatModelService.validateChatModel(chatConversation.getModelId());
        StreamingChatModel chatModel = aiApiKeyService.getChatModel(aiChatModel.getKeyId());

        AiChatMessage userMessage = createChatMessage(chatConversation.getId(), null, aiChatModel, userId, chatConversation.getRoleId(), MessageType.USER, sendReqVO.getContent(), sendReqVO.getUseContext());
        AiChatMessage assistantMessage = createChatMessage(chatConversation.getId(), userMessage.getId(), aiChatModel,
                userId, chatConversation.getRoleId(), MessageType.ASSISTANT, "", sendReqVO.getUseContext());

        Prompt prompt = buildPrompt(chatConversation, chatMessageList, aiChatModel, sendReqVO);
        String traceId = MDC.get("traceId");
        StringBuffer contentBuffer = new StringBuffer();
        return chatModel.stream(prompt).map(chunk->{
            String content = chunk.getResult() != null ? chunk.getResult().getOutput().getContent() : null;
            content = StrUtil.nullToDefault(content, "");
            contentBuffer.append(content);
            AiChatMessageSendResponse.Message responseMsg = BeanUtils.toBean(assistantMessage, AiChatMessageSendResponse.Message.class);
            responseMsg.setContent(content);
//            .send(BeanUtils.toBean(userMessage, AiChatMessageSendResponse.Message.class))
            return CommonResult.success(AiChatMessageSendResponse.builder()
                    .receive(responseMsg).build());
        }).doOnComplete(()->{
            MDC.put("traceId", traceId);
            aiChatMessageMapper.updateById(AiChatMessage.builder().id(assistantMessage.getId()).content(contentBuffer.toString()).build());
        }).doOnError(throwable->{
            aiChatMessageMapper.updateById(AiChatMessage.builder().id(assistantMessage.getId()).content(throwable.getMessage()).build());
        });
    }

    private Prompt buildPrompt(AiChatConversation chatConversation, List<AiChatMessage> messages,
                               AiChatModel aiChatModel, AiChatMessageSendReq aiChatMessageSendReq) {
        List<Message> chatMessages = new ArrayList<>();
        if(StrUtil.isNotEmpty(chatConversation.getSystemMessage())){
            chatMessages.add(new SystemMessage(chatConversation.getSystemMessage()));
        }
        // 对其他信息进行转化
        List<AiChatMessage> contextChatMessage = getContextChatMessage(chatConversation, aiChatMessageSendReq, messages);
        contextChatMessage.forEach(message->{
            chatMessages.add(AiUtils.buildMessage(message.getType(), message.getContent()));
        });
        chatMessages.add(new UserMessage(aiChatMessageSendReq.getContent()));
        AiConstants.AiPlatformEnum aiPlatformEnum = AiConstants.AiPlatformEnum.getAiPlatformEnum(aiChatModel.getPlatform());
        ChatOptions chatOptions = AiUtils.buildChatOptions(aiPlatformEnum, aiChatModel.getModel(), aiChatModel.getTemperature().floatValue(), aiChatModel.getMaxTokens());
        return new Prompt(chatMessages, chatOptions);

    }

    /**
     * 构造聊天上下文
     * @param chatConversation
     * @param aiChatMessageSendReq
     * @param aiChatMessages
     * @return
     */
    private List<AiChatMessage> getContextChatMessage(AiChatConversation chatConversation, AiChatMessageSendReq aiChatMessageSendReq, List<AiChatMessage> aiChatMessages){
        if(chatConversation.getMaxContexts() == null || ObjUtil.notEqual(aiChatMessageSendReq.getUseContext(), true)){
            return Collections.emptyList();
        }

        List<AiChatMessage> contextChatMessages = new ArrayList<>(chatConversation.getMaxContexts() * 2);
        for(int index = aiChatMessages.size() - 1; index >= 0; index--){
            AiChatMessage assistantChatMessage = CollUtil.get(aiChatMessages, index);
            if(assistantChatMessage == null || assistantChatMessage.getReplyId() == null){
                continue;
            }
            AiChatMessage userMessage = CollUtil.get(aiChatMessages, index - 1);
            if(userMessage == null || ObjUtil.notEqual(assistantChatMessage.getReplyId(), userMessage.getId()) || StrUtil.isEmpty(assistantChatMessage.getContent())){
                continue;
            }
            contextChatMessages.add(assistantChatMessage);
            contextChatMessages.add(userMessage);
            if(contextChatMessages.size() >= chatConversation.getMaxContexts() * 2){
                break;
            }
        }
        Collections.reverse(contextChatMessages);
        return contextChatMessages;
    }

    private AiChatMessage createChatMessage(Long conversationId, Long replyId,
                                            AiChatModel model, Long userId, Long roleId,
                                            MessageType messageType, String content, Boolean useContext){
        AiChatMessage chatMessage = AiChatMessage.builder().conversationId(conversationId)
                .replyId(replyId)
                .model(model.getModel())
                .modelId(model.getId())
                .userId(userId)
                .roleId(roleId)
                .type(messageType.getValue())
                .content(content)
                .useContext(useContext)
                .build();
        aiChatMessageMapper.insert(chatMessage);
        return chatMessage;
    }



}
