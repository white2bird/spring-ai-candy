package com.itwang.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <p>
 * AI API 密钥表
 * </p>
 *
 * @author lmz
 * @since 2024-09-10
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("ai_api_key")
public class AiApiKey implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 平台
     */
    @TableField("platform")
    private String platform;

    /**
     * 密钥
     */
    @TableField("api_key")
    private String apiKey;

    /**
     * 自定义 API 地址
     */
    @TableField("url")
    private String url;

    /**
     * 状态
     */
    @TableField("status")
    private Integer status;
}
