package com.itwang.service.impl;

import cn.hutool.core.util.StrUtil;
import com.itwang.ai_model.DeepSeek.DeepSeekChatModel;
import com.itwang.common.constants.AiConstants;
import com.itwang.common.result.CommonResult;
import com.itwang.common.utils.AiUtils;
import com.itwang.common.utils.BeanUtils;
import com.itwang.dao.entity.AiChatMessage;
import com.itwang.dao.entity.AiChatModel;
import com.itwang.request.AiChatMessageSendReq;
import com.itwang.response.AiChatMessageSendResponse;
import com.itwang.service.AiChatMessageService;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.beans.Introspector;
import java.util.Map;


@Service
public class AiChatMessageServiceImpl implements AiChatMessageService {

    @Resource
    private Map<String, ChatModel> chatModelMap;

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
        String modelType = sendReqVO.getModelType();
        ChatModel chatModel = chatModelMap.get(modelType);
        chatModel = chatModel == null ? chatModelMap.get(DEFAULT_MODEL) : chatModel;
        ChatOptions chatOptions = AiUtils.buildChatOptions(AiConstants.AiPlatform.getByModel(modelType), sendReqVO.getModelName(), 0.5F, 1000);
        return chatModel.stream(buildPrompt(sendReqVO.getContent(), chatOptions)).map(chunk->{
            String content = chunk.getResult() != null ? chunk.getResult().getOutput().getContent() : null;
            content = StrUtil.nullToDefault(content, "");
//            return CommonResult.success(new AiChatMessageSendResponse().setSend(BeanUtils.toBean(sendReqVO, AiChatMessageSendResponse.class)));
            return null;
        });
    }

    private Prompt buildPrompt(String content, ChatOptions chatOptions){
        return new Prompt(content, chatOptions);
    }


    private AiChatMessage createChatMessage(Long conversationId, Long replyId, AiChatModel aiChatModel, Long userId,
                                            Long roleId, MessageType messageType, String content, Boolean useContext){

        return null;
    }
}
