package com.itwang.service.impl;

import com.itwang.dao.entity.AiChatConversation;
import com.itwang.dao.entity.AiChatRole;
import com.itwang.dao.mapper.AiChatConversationMapper;
import com.itwang.dao.mapper.AiChatModelMapper;
import com.itwang.dao.mapper.AiChatRoleMapper;
import com.itwang.request.AiChatConversationCreateRequest;
import com.itwang.service.IAiChatConversationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

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
//        chatRole != null && chatRole.getModelId() != null ?

        return 0L;
    }
}
