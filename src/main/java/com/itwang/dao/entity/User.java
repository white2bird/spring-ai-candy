package com.itwang.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author lmz
 * @since 2024-09-08
 */
@Getter
@Setter
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名称
     */
    private String nickname;

    /**
     * 密码
     */
    private String password;

    /**
     * 密码的盐值
     */
    private String salt;

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
    private Short type;

    /**
     * 用户状态是否封禁
     */
    private Byte status;

    /**
     * 删除标识
     */
    private Long deleteTimeStamp;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
