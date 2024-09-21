package com.itwang.controller.client;

import com.itwang.common.result.CommonResult;
import com.itwang.dao.entity.ModelPreviewFormItem;
import com.itwang.response.FormItemResponse;
import com.itwang.service.IModelPreviewFormItemService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lmz
 * @since 2024-09-20
 */
@RestController
@RequestMapping("/formItem")
public class ModelPreviewFormItemController {

    @Resource
    private IModelPreviewFormItemService modelPreviewFormItemService;


    @Operation(summary = "根据角色id获取表单项信息")
    @GetMapping(value = "/getFormItemListByRoleId")
    public CommonResult<List<FormItemResponse>> getFormItemList(@RequestParam Long roleId)
    {
        return CommonResult.success(modelPreviewFormItemService.getModelPreviewFormItemList(roleId));
    }

}
