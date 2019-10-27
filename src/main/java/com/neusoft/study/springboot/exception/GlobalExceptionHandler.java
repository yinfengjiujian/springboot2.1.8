package com.neusoft.study.springboot.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.neusoft.study.springboot.common.CommonResult;
import com.neusoft.study.springboot.exception.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.StringJoiner;

/**
 * <p>Title: com.neusoft.study.springboot.exception</p>
 * <p>Company:东软集团(neusoft)</p>
 * <p>Copyright:Copyright(c)</p>
 * User: Administrator
 * Date: 2019/10/4 16:10
 * Description: 全局异常处理器
 */
@Slf4j
@RestControllerAdvice("com.neusoft.study.springboot")
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public CommonResult<Void> handleBusinessException(BusinessException be) {
        log.error(be.getErrorMsg(), be);
        return CommonResult.errorResult(be.getErrorCode(), be.getErrorMsg());
    }

    @ExceptionHandler(BindException.class)
    public CommonResult<Void> handleBindException(BindException e) {
        log.error(e.getMessage(), e);
        return CommonResult.errorResult(ErrorEnum.PARAM_FAIL_CODE.getCode().toString(), e.getMessage());
    }

    @ExceptionHandler(InvalidFormatException.class)
    public CommonResult<Void> invalidFormatException(BindException e) {
        log.error(e.getMessage(), e);
        return CommonResult.errorResult(ErrorEnum.PARAM_FAIL_CODE.getCode().toString(), e.getMessage());
    }

    /**
     * 方法参数校验
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResult<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        // 按需重新封装需要返回的错误信息 解析原错误信息，封装后返回，此处返回非法的字段名称error.getField()，原始值error.getRejectedValue()，错误信息
        StringJoiner sj = new StringJoiner(";");
        e.getBindingResult().getFieldErrors().forEach(x -> sj.add(x.getDefaultMessage()));
        return CommonResult.errorResult(ErrorEnum.PARAM_FAIL_CODE.getCode().toString(), ErrorEnum.PARAM_FAIL_CODE.getMessage() + sj.toString());
    }

    /**
     * ValidationException
     */
    @ExceptionHandler(ValidationException.class)
    public CommonResult<Void> handleValidationException(ValidationException e) {
        log.error(e.getMessage(), e);
        return CommonResult.errorResult(ErrorEnum.VALIDATION_FAIL_CODE.getCode().toString(), e.getCause().getMessage());
    }

    /**
     * ConstraintViolationException
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public CommonResult<Void> handleConstraintViolationException(ConstraintViolationException e) {
        log.error(e.getMessage(), e);
        StringJoiner sj = new StringJoiner(";");
        e.getConstraintViolations().forEach(x -> sj.add(x.getMessageTemplate()));
        return CommonResult.errorResult(ErrorEnum.PARAM_FAIL_CODE.getCode().toString(), sj.toString());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public CommonResult<Void> handlerNoFoundException(Exception e) {
        log.error(e.getMessage(), e);
        return CommonResult.errorResult(String.valueOf(HttpStatus.NOT_FOUND.value()), "路径不存在，请检查路径是否正确");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public CommonResult<Void> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage(), e);
        return CommonResult.errorResult(ErrorEnum.METHOD_NOT_SUPPORTED.getCode().toString(), "不支持'" + e.getMethod() + "'请求方法");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public CommonResult<Void> handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        return CommonResult.errorResult(ErrorEnum.DUPLICATE_KEY_CODE.getCode().toString(), ErrorEnum.DUPLICATE_KEY_CODE.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public CommonResult<Void> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return CommonResult.errorResult(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), "系统繁忙,请稍后再试");
    }

}
