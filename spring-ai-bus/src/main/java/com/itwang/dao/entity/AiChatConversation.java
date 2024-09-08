package com.itwang.dao.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * AI 聊天对话表
 * </p>
 *
 * @author lmz
 * @since 2024-09-08
 */
@Getter
@Setter
@TableName("ai_chat_conversation")
public class AiChatConversation implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 对话编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户编号
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 聊天角色
     */
    @TableField("role_id")
    private Long roleId;

    /**
     * 对话标题
     */
    @TableField("title")
    private String title;

    /**
     * 模型编号
     */
    @TableField("model_id")
    private Long modelId;

    /**
     * 模型标识
     */
    @TableField("model")
    private String model;

    /**
     * 是否置顶
     */
    @TableField("pinned")
    private Boolean pinned;

    /**
     * 置顶时间
     */
    @TableField("pinned_time")
    private LocalDateTime pinnedTime;

    /**
     * 角色设定
     */
    @TableField("system_message")
    private String systemMessage;

    /**
     * 温度参数
     */
    @TableField("temperature")
    private Double temperature;

    /**
     * 单条回复的最大 Token 数量
     */
    @TableField("max_tokens")
    private Integer maxTokens;

    /**
     * 上下文的最大 Message 数量
     */
    @TableField("max_contexts")
    private Integer maxContexts;

    /**
     * 创建人
     */
    @TableField("creator")
    private String creator;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    @TableField("updater")
    private String updater;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 删除标识
     */
    @TableField("delete_time_stamp")
    private Long deleteTimeStamp;
}
