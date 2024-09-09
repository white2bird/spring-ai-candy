package com.itwang.converter;

import com.itwang.dao.entity.AiChatConversation;
import com.itwang.response.AiChatConversationRespVO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AiChatConversationConverter {


    AiChatConversationRespVO convert(AiChatConversation conversation);


    List<AiChatConversationRespVO> convertList(List<AiChatConversation> list);
}
