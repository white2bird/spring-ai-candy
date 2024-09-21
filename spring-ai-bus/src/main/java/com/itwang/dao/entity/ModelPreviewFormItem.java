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
 * 
 * </p>
 *
 * @author lmz
 * @since 2024-09-20
 */
@Getter
@Setter
@TableName("ModelPreviewFormItem")
public class ModelPreviewFormItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 模型角色id
     */
    @TableField("role_id")
    private Long roleId;

    /**
     * formItem类型，限制于text file等
     */
    @TableField("type")
    private String type;

    /**
     * 列展示的名字，到时候前端传参数会用这个
     */
    @TableField("name")
    private String name;

    /**
     * 提示语
     */
    @TableField("placeHolder")
    private String placeholder;

    /**
     * 最大最小限制
     */
    @TableField("max")
    private Integer max;

    /**
     * 最大最小限制
     */
    @TableField("min")
    private Integer min;

    /**
     * 是否要求前端是否是必填项目
     */
    @TableField("required")
    private Boolean required;
}
