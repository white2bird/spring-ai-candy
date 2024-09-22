package com.itwang.controller.client;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaIgnore;
import com.itwang.response.RoleTabResponse;
import com.itwang.service.IAiChatRoleTypeService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 角色主分类 前端控制器
 * </p>
 *
 * @author lmz
 * @since 2024-09-22
 */
@RestController
@RequestMapping("/aiChatRoleType")
public class AiChatRoleTypeController {

    @Resource
    private IAiChatRoleTypeService aiChatRoleTypeService;

    @SaIgnore
    @RequestMapping("/get-tabs-items")
    public List<RoleTabResponse> getTabsItems(){
        return aiChatRoleTypeService.getTabsItems();
    }
}
