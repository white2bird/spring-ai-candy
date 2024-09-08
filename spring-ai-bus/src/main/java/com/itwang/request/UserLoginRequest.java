package com.itwang.request;


import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserLoginRequest {

    /**
     * 用户名称
     */
    @NotEmpty(message = "用户名称不能为空")
    private String nickname;

    /**
     * 密码
     */
    @NotEmpty(message = "用户密码不能为空")
    private String password;
}
