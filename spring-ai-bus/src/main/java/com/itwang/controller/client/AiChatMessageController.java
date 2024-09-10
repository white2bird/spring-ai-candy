package com.itwang.controller.client;

import cn.dev33.satoken.stp.StpUtil;
import com.itwang.common.result.CommonResult;
import com.itwang.request.AiChatMessageSendReq;
import com.itwang.response.AiChatMessageSendResponse;
import com.itwang.service.AiChatMessageService;
import com.itwang.service.IAiChatMessageService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

/**
 * <p>
 * AI 聊天消息表 前端控制器
 * </p>
 *
 * @author lmz
 * @since 2024-09-08
 */
@Controller
@RequestMapping("/ai/chat/message")
public class AiChatMessageController {

    @Resource
    private AiChatMessageService chatMessageService;

    @Operation(summary = "发送消息（流式）", description = "流式返回，响应较快")
    @PostMapping(value = "/send-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<CommonResult<AiChatMessageSendResponse>> sendChatMessageStream(@Valid @RequestBody AiChatMessageSendReq sendReqVO) {
        return chatMessageService.sendChatMessageStream(sendReqVO, StpUtil.getLoginIdAsLong());
    }

}
