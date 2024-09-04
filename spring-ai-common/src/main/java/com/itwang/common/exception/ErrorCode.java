package com.itwang.common.exception;

import lombok.Data;

/**
 * @author yiming@micous.com
 * @date 2024/9/4 18:24
 */
@Data
public class ErrorCode {

    /**
     * 错误码
     */
    private final Integer code;
    /**
     * 错误提示
     */
    private final String msg;

    public ErrorCode(Integer code, String message) {
        this.code = code;
        this.msg = message;
    }
}
