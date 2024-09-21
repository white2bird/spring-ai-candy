package com.itwang.response;

import lombok.Data;

/**
 * {
 * "type": "text",
 * "name": "其他要求：",
 * "placeholder": "例如，分段",
 * "min": 3,
 * "max": 1000,
 * "required": true
 * }
 *
 * @author yiming@micous.com
 * @date 2024/9/20 18:04
 */
@Data
public class FormItemResponse {

    private Long id;

    private String type;

    private String name;

    private String placeholder;

    private Integer min;

    private Integer max;

    private Boolean required;
}
