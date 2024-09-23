package com.itwang.controller.client;

import cn.dev33.satoken.stp.StpUtil;
import com.itwang.common.result.CommonResult;
import com.itwang.request.AiChatConversationCreateRequest;
import com.itwang.response.AiChatConversationRespVO;
import com.itwang.service.IAiChatConversationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/list-by-roleId")
    @Operation(summary = "获取我的聊天列表")
    public CommonResult<List<AiChatConversationRespVO>> getMyConversationList(@RequestParam(required = false) Long chatRoleId){
        return CommonResult.success(aiChatConversationService.getMyConversationList(chatRoleId));
    }

    @GetMapping("/get-current")
    @Operation(summary = "获取当前对话")
    public CommonResult<AiChatConversationRespVO> getCurrentConversation(@RequestParam Long id){
        return CommonResult.success(aiChatConversationService.getCurrentConversation(id));
    }

    @GetMapping("/get-default-chat-conversation-id")
    @Operation(summary = "获取默认的对话id")
    public CommonResult<Long> getDefaultChatConversationId(){
        return CommonResult.success(aiChatConversationService.getDefaultChatConversationIdWithoutRole(StpUtil.getLoginIdAsLong()));
    }



}
