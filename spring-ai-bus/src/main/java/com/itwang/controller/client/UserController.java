package com.itwang.controller.client;

import com.itwang.common.result.CommonResult;
import com.itwang.request.UserLoginRequest;
import com.itwang.request.UserRegisterRequest;
import com.itwang.response.client.ClientLoginRes;
import com.itwang.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@Tag(name = "UserController", description = "用户管理")
@RequestMapping("/user")
@RestController
public class UserController {

    @Resource
    private IUserService userService;

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public CommonResult<ClientLoginRes> userLogin(@RequestBody @Valid UserLoginRequest userLoginRequest){
        return CommonResult.success(userService.userLogin(userLoginRequest));
    }

    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public CommonResult<Boolean> userRegister(@RequestBody @Valid UserRegisterRequest userRegisterRequest){
        return CommonResult.success(userService.createUser(userRegisterRequest));
    }

    @Operation(summary = "用户登出")
    @GetMapping("/logout")
    public CommonResult<Void> userLogout(){
        userService.logout();
        return CommonResult.success(null);
    }
}
