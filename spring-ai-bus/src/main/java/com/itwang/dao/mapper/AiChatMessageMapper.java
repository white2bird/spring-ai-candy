package com.itwang.dao.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itwang.dao.entity.AiChatMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * AI 聊天消息表 Mapper 接口
 * </p>
 *
 * @author lmz
 * @since 2024-09-08
 */
public interface AiChatMessageMapper extends BaseMapper<AiChatMessage> {

    default List<AiChatMessage> getChatMessageList(Long conversationId) {
        return this.selectList(new LambdaQueryWrapper<AiChatMessage>()
                .eq(AiChatMessage::getConversationId, conversationId));
    }

}
