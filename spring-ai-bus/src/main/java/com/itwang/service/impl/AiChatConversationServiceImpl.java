package com.itwang.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itwang.converter.AiChatConversationConverter;
import com.itwang.dao.entity.AiChatConversation;
import com.itwang.dao.entity.AiChatModel;
import com.itwang.dao.entity.AiChatRole;
import com.itwang.dao.mapper.AiChatConversationMapper;
import com.itwang.dao.mapper.AiChatModelMapper;
import com.itwang.dao.mapper.AiChatRoleMapper;
import com.itwang.request.AiChatConversationCreateRequest;
import com.itwang.response.AiChatConversationRespVO;
import com.itwang.service.IAiChatConversationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itwang.service.IAiChatModelService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * AI 聊天对话表 服务实现类
 * </p>
 *
 * @author lmz
 * @since 2024-09-08
 */
@Service
public class AiChatConversationServiceImpl extends ServiceImpl<AiChatConversationMapper, AiChatConversation> implements IAiChatConversationService {


    @Resource
    private AiChatRoleMapper aiChatRoleMapper;

    @Resource
    private AiChatModelMapper aiChatModelMapper;

    @Resource
    private IAiChatModelService aiChatModelService;

    @Resource
    private AiChatConversationConverter aiChatConversationConverter;

    /**
     * 根据模型创建对话
     * @param createReqVO
     * @param userId
     * @return
     */
    @Override
    public Long createChatConversationMy(AiChatConversationCreateRequest createReqVO, Long userId) {
        AiChatRole chatRole = createReqVO.getRoleId() != null ? aiChatRoleMapper.selectById(createReqVO.getRoleId()) : null;
        // 角色可以默认优先指定大模型 选择一个大模型
        AiChatModel chatModel = chatRole != null && chatRole.getModelId() != null ? aiChatModelMapper.selectByIdWithUseful(chatRole.getModelId()) : aiChatModelService.getDefaultChatModel();
        Assert.notNull(chatModel, "未找到可用的模型");
        AiChatConversation aiChatConversation = AiChatConversation.builder().userId(userId).pinned(false).modelId(chatModel.getId()).model(chatModel.getModel())
                .temperature(chatModel.getTemperature()).maxTokens(chatModel.getMaxTokens())
                .maxContexts(chatModel.getMaxContexts()).build();
        if(chatRole != null){
            aiChatConversation.setTitle(chatRole.getName());
            aiChatConversation.setRoleId(chatRole.getId());
            aiChatConversation.setSystemMessage(chatRole.getSystemMessage());
        }else{
            aiChatConversation.setTitle(AiChatConversation.TITLE_DEFAULT);
        }
        this.save(aiChatConversation);
        return aiChatConversation.getId();
    }

    @Override
    public List<AiChatConversationRespVO> getMyConversationList(Long chatRoleId) {
        // TODO 分页
        List<AiChatConversation> result = this.list(new LambdaQueryWrapper<AiChatConversation>().eq(AiChatConversation::getRoleId, chatRoleId));
        return aiChatConversationConverter.convertList(result);
    }
}
