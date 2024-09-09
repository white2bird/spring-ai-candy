package com.itwang.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 * AI 聊天消息表
 * </p>
 *
 * @author lmz
 * @since 2024-09-08
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("ai_chat_message")
public class AiChatMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消息编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 对话编号
     */
    @TableField("conversation_id")
    private Long conversationId;

    /**
     * 回复编号
     */
    @TableField("reply_id")
    private Long replyId;

    /**
     * 用户编号
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 角色编号
     */
    @TableField("role_id")
    private Long roleId;

    /**
     * 消息类型
     */
    @TableField("type")
    private String type;

    /**
     * 模型标识
     */
    @TableField("model")
    private String model;

    /**
     * 模型编号
     */
    @TableField("model_id")
    private Long modelId;

    /**
     * 消息内容
     */
    @TableField("content")
    private String content;

    /**
     * 是否携带上下文
     */
    @TableField("use_context")
    private Boolean useContext;

    /**
     * 创建人
     */
    @TableField("creator")
    private String creator;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    @TableField("updater")
    private String updater;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 是否删除
     */
    @TableField("deleted")
    private Boolean deleted;

    /**
     * 租户编号
     */
    @TableField("tenant_id")
    private Long tenantId;
}
