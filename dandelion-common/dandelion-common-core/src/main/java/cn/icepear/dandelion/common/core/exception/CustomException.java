package cn.icepear.dandelion.common.core.exception;

/**
 * @author rim-wood
 * @description 自定义异常
 * @date Created on 2019-09-02.
 */
public class CustomException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CustomException(String message) {
        super(message);
    }

    public CustomException(Throwable cause) {
        super(cause);
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
