package com.wxmblog.base.file.aspect;

import com.wxmblog.base.common.utils.TokenUtils;
import com.wxmblog.base.common.web.domain.R;
import com.wxmblog.base.file.service.MsfFileService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
 * @Author wanglei
 * @Description  将临时文件修改为持久化文件 避免被清楚
 * @Date 17:23 2022/11/13
 * @Param
 * @return
 **/
@Aspect
@Component
public class FileAspect {

    @Autowired
    private MsfFileService fileService;

    // 定义一个切入点
    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)" +
            "||@annotation(org.springframework.web.bind.annotation.PutMapping)" +
            "||@annotation(com.wxmblog.base.file.annotation.FileSaveService)")
    public void saveFile() {

    }

    // 前置通知
    @Before(value = "saveFile()")
    public void before(JoinPoint jp) {

        Object[] objects = jp.getArgs();
        if (objects != null && objects.length > 0) {
            Object object = objects[0];
            deleteDiscardFile(object);
        }
    }

    void deleteDiscardFile(Object object) {
        fileService.deleteSaveFile(object, TokenUtils.getOwnerId());
    }

    // 后置通知
    @After(value = "saveFile()")
    public void after(JoinPoint jp) {
    }

    // 返回通知
    @AfterReturning(value = "saveFile()", returning = "result")
    public void afterReturning(JoinPoint jp, Object result) {
    }

    // 返回通知
    @AfterReturning(value = "saveFile()", returning = "result")
    public void afterReturning(JoinPoint jp, R result) {
        if (result.getCode() == 200) {
            Object[] objects = jp.getArgs();
            if (objects != null && objects.length > 0) {
                Object object = objects[0];
                fileService.changeTempFile(object);
            }
        }
    }

}
