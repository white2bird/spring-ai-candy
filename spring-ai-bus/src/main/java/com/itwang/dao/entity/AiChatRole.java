package com.itwang.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * AI 聊天角色表
 * </p>
 *
 * @author lmz
 * @since 2024-09-09
 */
@Getter
@Setter
@TableName("ai_chat_role")
public class AiChatRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 角色名称
     */
    @TableField("name")
    private String name;

    /**
     * 头像
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 角色描述
     */
    @TableField("description")
    private String description;

    /**
     * 状态
     */
    @TableField("status")
    private Byte status;

    /**
     * 角色排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 用户编号
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 是否公开
     */
    @TableField("public_status")
    private Boolean publicStatus;

    /**
     * 角色类别
     */
    @TableField("category")
    private String category;

    /**
     * 模型编号
     */
    @TableField("model_id")
    private Long modelId;

    /**
     * 角色上下文
     */
    @TableField("system_message")
    private String systemMessage;
}
