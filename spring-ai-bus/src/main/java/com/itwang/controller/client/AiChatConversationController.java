package com.itwang.controller.client;

import com.itwang.common.result.CommonResult;
import com.itwang.request.AiChatMessageSendReq;
import com.itwang.response.AiChatMessageSendResponse;
import com.itwang.service.IAiChatConversationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

/**
 * <p>
 * AI 聊天对话表 前端控制器
 * </p>
 *
 * @author lmz
 * @since 2024-09-08
 */
@Tag(name = "用户聊天")
@Controller
@RequestMapping("/aiChatConversation")
public class AiChatConversationController {

    @Resource
    private IAiChatConversationService aiChatConversationService;

    public CommonResult<AiChatMessageSendResponse> sendMessage(@Valid @RequestBody AiChatMessageSendReq sendReq){
        return null;
    }


}
