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
     * 头像 默认可null
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
     * 角色排序 暂时不需要
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 用户编号 暂时不需要用户自己建立
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 是否公开 默认公开
     */
    @TableField("public_status")
    private Boolean publicStatus;

    /**
     * 角色类别 默认为自定义
     */
    @TableField("category")
    private String category;

    /**
     * 模型编号 角色可以指定默认模型 如果没有指定默认模型
     */
    @TableField("model_id")
    private Long modelId;


    @TableField("role_type_id")
    private Long roleTypeId;

    /**
     * 角色上下文 一段描述 你是谁谁谁，你应该怎么样
     */
    @TableField("system_message")
    private String systemMessage;
}
