package com.itwang.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * AI 聊天模型表
 * </p>
 *
 * @author lmz
 * @since 2024-09-08
 */
@Getter
@Setter
@TableName("ai_chat_model")
public class AiChatModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * API 秘钥编号
     */
    @TableField("key_id")
    private Long keyId;

    /**
     * 模型名字
     */
    @TableField("name")
    private String name;

    /**
     * 模型标识
     */
    @TableField("model")
    private String model;

    /**
     * 模型平台
     */
    @TableField("platform")
    private String platform;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 状态
     */
    @TableField("status")
    private Byte status;

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
     * 创建者
     */
    @TableField("creator")
    private String creator;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新者
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
