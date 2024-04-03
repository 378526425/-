package com.wxmblog.base.common.aop;

import com.wxmblog.base.common.enums.BaseExceptionEnum;
import com.wxmblog.base.common.exception.JrsfException;
import com.wxmblog.base.common.exception.OptimisticLockerException;
import com.wxmblog.base.common.web.domain.R;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: msfast
 * @description: 统一处理异常操作
 * @author: Mr.Wang
 * @create: 2022-06-12 10:18
 **/
@Slf4j
@ResponseBody
@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleValidException(MethodArgumentNotValidException e) {
        log.error("数据校验出现问题{},异常类型: {}", e.getMessage(), e.getClass());
        BindingResult bindingResult = e.getBindingResult();
        Map<String, String> errorMap = new HashMap<>();
        bindingResult.getFieldErrors().forEach((fieldError -> {
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        }));

        String msg = errorMap.values().toArray()[0].toString();
        return R.fail(BaseExceptionEnum.VALID_EXCEPTION.getCode(), msg, errorMap);
    }

    @ExceptionHandler(value = NoSuchBeanDefinitionException.class)
    public R handleNoSuchException(NoSuchBeanDefinitionException e) {
        log.error("没有实现相关接口,接口名：{}", e.getBeanType());
        return R.fail(BaseExceptionEnum.NO_SERVICE_AVAILABLE_EXCEPTION.getCode(), BaseExceptionEnum.NO_SERVICE_AVAILABLE_EXCEPTION.getMessage(), e.getBeanType());
    }

    @ExceptionHandler(value = JrsfException.class)
    public R handleJrsfException(JrsfException e) {
        log.error("业务处理异常:", e);
        return R.fail(e.getCode(), e.getMessage(), e.getData());
    }

    @ExceptionHandler(value = MalformedJwtException.class)
    public R handleMalformedJwtException(MalformedJwtException e) {
        log.error("token 格式错误：{}", e.getMessage());
        return R.fail(BaseExceptionEnum.TOKEN_FORMAT_EXCEPTION.getCode(), BaseExceptionEnum.TOKEN_FORMAT_EXCEPTION.getMessage());
    }

    @ExceptionHandler(value = SignatureException.class)
    public R handleSignatureException(SignatureException e) {
        log.error("token 非法：{}", e.getMessage());
        return R.fail(BaseExceptionEnum.TOKEN_ILLEGAL_EXCEPTION.getCode(), BaseExceptionEnum.TOKEN_ILLEGAL_EXCEPTION.getMessage());
    }

    @ExceptionHandler(value = ExpiredJwtException.class)
    public R handleExpiredJwtException(ExpiredJwtException e) {
        log.error("token 过期：{}", e.getMessage());
        return R.fail(BaseExceptionEnum.TOKEN_EXPIRED_EXCEPTION.getCode(), BaseExceptionEnum.TOKEN_EXPIRED_EXCEPTION.getMessage());
    }

    @ExceptionHandler(value = OptimisticLockerException.class)
    public R handleOptimisticLockerException(OptimisticLockerException e) {
        log.error("乐观锁更新失败：{}", e.getMessage());
        return R.fail(BaseExceptionEnum.OPTIMISTICLOCKER_EXCEPTION.getCode(), BaseExceptionEnum.OPTIMISTICLOCKER_EXCEPTION.getMessage());
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public R handleConversionException(HttpMessageNotReadableException e) {
        log.error("请求内容字段格式转换错误：{}", e.getMessage());
        return R.fail(BaseExceptionEnum.CONVERSION_EXCEPTION.getCode(), BaseExceptionEnum.CONVERSION_EXCEPTION.getMessage());
    }

    @ExceptionHandler(value = Throwable.class)
    public R handleException(Throwable throwable) {
        log.error("错误:", throwable);
        return R.fail(BaseExceptionEnum.UNKNOWN_EXCEPTION.getCode(), BaseExceptionEnum.UNKNOWN_EXCEPTION.getMessage(), throwable.getMessage());
    }

}
