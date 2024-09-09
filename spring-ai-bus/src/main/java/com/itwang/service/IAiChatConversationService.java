package com.itwang.service;

import com.itwang.dao.entity.AiChatConversation;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itwang.request.AiChatConversationCreateRequest;
import com.itwang.response.AiChatConversationRespVO;

import java.util.List;

/**
 * <p>
 * AI 聊天对话表 服务类
 * </p>
 *
 * @author lmz
 * @since 2024-09-08
 */
public interface IAiChatConversationService extends IService<AiChatConversation> {

    Long createChatConversationMy(AiChatConversationCreateRequest createReqVO, Long userId);

    List<AiChatConversationRespVO> getMyConversationList(Long chatRoleId);
}
