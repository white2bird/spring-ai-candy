package com.itwang.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yiming@micous.com
 * @date 2024/9/4 18:28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public final class ServiceException extends RuntimeException{

    /**
     * 业务错误码
     *
     * @see
     */
    private Integer code;
    /**
     * 错误提示
     */
    private String message;

    /**
     * 空构造方法，避免反序列化问题
     */
    public ServiceException() {
    }

    public ServiceException(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMsg();
    }

    public ServiceException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public ServiceException setCode(Integer code) {
        this.code = code;
        return this;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public ServiceException setMessage(String message) {
        this.message = message;
        return this;
    }
}
