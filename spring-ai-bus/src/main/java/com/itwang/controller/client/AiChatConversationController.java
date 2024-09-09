package com.itwang.controller.client;

import cn.dev33.satoken.stp.StpUtil;
import com.itwang.common.result.CommonResult;
import com.itwang.request.AiChatConversationCreateRequest;
import com.itwang.request.AiChatMessageSendReq;
import com.itwang.response.AiChatConversationRespVO;
import com.itwang.response.AiChatMessageSendResponse;
import com.itwang.service.IAiChatConversationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * AI 聊天对话表 前端控制器
 * </p>
 *
 * @author lmz
 * @since 2024-09-08
 */
@Tag(name = "用户聊天")
@RestController
@RequestMapping("/ai/chat/conversation")
public class AiChatConversationController {

    @Resource
    private IAiChatConversationService aiChatConversationService;

    @Operation(summary = "创建我的聊天")
    @PostMapping("/create-my")
    public CommonResult<Long> createMyConversation(@Valid @RequestBody AiChatConversationCreateRequest createRequest){
        Long loginId = StpUtil.getLoginIdAsLong();
        return CommonResult.success(aiChatConversationService.createChatConversationMy(createRequest, loginId));
    }

    /**
     * 此操作发生在用户点击了一个模版进来之后
     * 寻找该模版下面发生的对话
     * @return
     */
    @GetMapping("/list-my")
    @Operation(summary = "获取我的聊天列表")
    public CommonResult<List<AiChatConversationRespVO>> getMyConversationList(@RequestParam Long chatRoleId){
        return CommonResult.success(aiChatConversationService.getMyConversationList(chatRoleId));
    }

}
