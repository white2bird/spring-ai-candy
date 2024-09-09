package com.itwang.converter;

import com.itwang.dao.entity.AiChatModel;
import com.itwang.request.AiChatModelSaveRequest;
import org.mapstruct.Mapper;

/**
 * @author yiming@micous.com
 * @date 2024/9/9 11:26
 */
@Mapper(componentModel = "spring")
public interface AiModelConverter {

    AiChatModel convert(AiChatModelSaveRequest aiChatModel);
}
