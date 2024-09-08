package com.itwang.request;


import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserRegisterRequest {

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

    /**
     * 头像
     */
    private String avatar;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 0:普通用户 1:管理员用户
     */
    private Integer type;

}
