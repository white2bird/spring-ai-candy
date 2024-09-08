package com.itwang.service;

import com.itwang.common.result.CommonResult;
import com.itwang.request.AiChatMessageSendReq;
import com.itwang.response.AiChatMessageSendResponse;
import reactor.core.publisher.Flux;

/**
 * @author yiming@micous.com
 * @date 2024/9/4 19:04
 */
public interface AiChatMessageService {


    AiChatMessageSendResponse sendMessage(AiChatMessageSendReq sendReq, Long userId);


    Flux<CommonResult<AiChatMessageSendResponse>> sendChatMessageStream(AiChatMessageSendReq sendReqVO, Long userId);


}
