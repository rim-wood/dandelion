package cn.icepear.dandelion.common.core.exception;

import cn.icepear.dandelion.common.core.constant.CommonConstants;
import cn.icepear.dandelion.common.core.utils.R;
import cn.icepear.dandelion.common.core.utils.ThrowableUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

/**
 * @author rimwood
 * @date 2019-04-15
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 全局异常.
     *
     * @param e the e
     * @return R
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R exception(Exception e) {
        // 打印堆栈信息
        log.error("全局异常信息 ex={}", ThrowableUtil.getStackTrace(e));
        return R.error(CommonConstants.FAIL,"未知异常，请联系管理员",e.getMessage());
    }

    /**
     * 处理 接口无权访问异常AccessDeniedException
     * @param e
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public R handleAccessDeniedException(AccessDeniedException e){
        // 打印堆栈信息
        log.error("鉴权无效信息 ex={}", ThrowableUtil.getStackTrace(e));
        return R.error(CommonConstants.FAIL,"功能无权使用，请联系管理员",e.getMessage());
    }

    /**
     * 校验参数 Exception
     *
     * @param exception
     * @return R
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R bodyValidExceptionHandler(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        // 打印错误信息
        log.error("参数错误：ex={}",fieldErrors.get(0).getDefaultMessage());
        return  R.error(CommonConstants.FAIL,"参数错误",fieldErrors.get(0).getDefaultMessage());
    }

    /**
     * 其他参数校验 Exception
     *
     * @param exception
     * @return R
     */
    @ExceptionHandler(CheckedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R checkedExceptionHandler(CheckedException exception) {
        // 打印错误信息
        log.error("参数错误：ex={}",ThrowableUtil.getStackTrace(exception));
        return  R.error(CommonConstants.FAIL,exception.getMessage(),exception);
    }

    /**
     * http请求方法不正确 Exception
     *
     * @param exception
     * @return R
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public R methodNotAllowedExceptionHandler(HttpRequestMethodNotSupportedException exception) {
        // 打印错误信息
        log.error("请求方式错误：ex={}",ThrowableUtil.getStackTrace(exception));
        return  R.error(CommonConstants.FAIL,"http请求方式不正确",exception.getMessage());
    }

    /**
     * http请求方法不正确 Exception
     *
     * @param exception
     * @return R
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R missingRequestParameterExceptionHandler(MissingServletRequestParameterException exception) {
        // 打印错误信息
        log.error("缺少请求参数：ex={}",ThrowableUtil.getStackTrace(exception));
        return  R.error(CommonConstants.FAIL,"缺少请求参数",exception.getMessage());
    }
}
