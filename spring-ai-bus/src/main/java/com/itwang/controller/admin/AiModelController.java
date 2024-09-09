package com.itwang.controller.admin;

import cn.dev33.satoken.annotation.SaCheckRole;
import com.itwang.common.result.CommonResult;
import com.itwang.request.AiChatModelSaveRequest;
import com.itwang.service.IAiChatModelService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yiming@micous.com
 * @date 2024/9/9 11:29
 */
@RestController
@RequestMapping("/admin/ai_model")
public class AiModelController {

    @Resource
    private IAiChatModelService aiChatModelService;

//    @SaCheckRole("admin")
    @PostMapping("/create")
    public CommonResult<Long> addChatModel(AiChatModelSaveRequest saveRequest) {
        return CommonResult.success(aiChatModelService.createChatModel(saveRequest));
    }
}
