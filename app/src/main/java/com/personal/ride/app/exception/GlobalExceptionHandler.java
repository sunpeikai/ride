package com.personal.ride.app.exception;

import com.personal.ride.app.config.SYSConstant;
import com.personal.ride.app.response.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理器
 *
 * @author sunpeikai
 */
@Slf4j
@RestControllerAdvice
@Component
public class GlobalExceptionHandler {
    /**
     * 请求方式不支持
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public R handleException(HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage(), e);
        return R.fail("不支持' " + e.getMethod() + "'请求");
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public R notFount(RuntimeException e, HttpServletRequest request) {
        log.info(" 请求:[" + request.getServletPath() + "] 出现异常");
        log.error("运行时异常:", e);
        return R.fail(SYSConstant.GLOBAL_EXCEPTION_MESSAGE);
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public R handleException(Exception e, HttpServletRequest request) {
        log.info(" 请求:[" + request.getServletPath() + "] 出现异常");
        log.error(e.getMessage(), e);
        return R.fail(SYSConstant.GLOBAL_EXCEPTION_MESSAGE);
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public R businessException(BusinessException e) {
        log.error(e.getMessage(), e);
        return R.fail(e.getStatus(), e.getStatusDesc());
    }

    /**
     * 参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R handleException(MethodArgumentNotValidException e) {
        String errorMsg = e.getBindingResult().getAllErrors().get(0).getDefaultMessage().replace("{}", "不能为空");
        log.debug("参数校验异常:" + errorMsg);
        return R.fail(errorMsg);
    }

}
