package com.wxmblog.base.common.aspect;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import com.wxmblog.base.common.annotation.ForeignTable;
import com.wxmblog.base.common.utils.ViolationUtils;
import com.wxmblog.base.common.enums.BaseExceptionEnum;
import com.wxmblog.base.common.exception.JrsfException;
import com.wxmblog.base.common.web.domain.R;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-12-29 10:33
 **/
@Aspect
@Component(value = "IViolationAspect")
public class ViolationAspect {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    // 定义一个切入点
    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)" +
            "||@annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void operation() {

    }

    // 前置通知
    @Before(value = "operation()")
    public void before(JoinPoint jp) {
        Object[] objects = jp.getArgs();
        if (objects != null && objects.length > 0) {
            Object object = objects[0];
            ViolationUtils.violation(object);

            //自定义业务校验
            violationService(object);

        }
    }

    private void violationService(Object object) {
        Field[] fieldIds = ClassUtil.getDeclaredFields(object.getClass());
        for (Field fieldId : fieldIds) {

            //外键表
            ForeignTable foreignTable = fieldId.getAnnotation(ForeignTable.class);
            if (foreignTable != null) {
                String tableName = foreignTable.value();
                String message = foreignTable.message();
                fieldId.setAccessible(true);
                Integer fieldValue = null;
                try {
                    Object objectId = fieldId.get(object);
                    if (objectId != null) {
                        fieldValue = (Integer) objectId;
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (ObjectUtil.isNotNull(fieldValue)) {
                    String totalQuery = "select count(1) from " + tableName + " tb where tb.del_flag=0 and tb.id=:id";
                    HashMap<String, Object> param = new HashMap<>();
                    param.put("id", fieldValue);
                    Long total = namedParameterJdbcTemplate.queryForObject(totalQuery, param, Long.class);
                    if (total == 0) {
                        throw new JrsfException(BaseExceptionEnum.Foreign_Value_Not_Exist).setMsg(message);
                    }
                }
            }
        }
    }

    // 后置通知
    @After(value = "operation()")
    public void after(JoinPoint jp) {
    }

    // 返回通知
    @AfterReturning(value = "operation()", returning = "result")
    public void afterReturning(JoinPoint jp, Object result) {
    }

    // 返回通知
    @AfterReturning(value = "operation()", returning = "result")
    public void afterReturning(JoinPoint jp, R result) {
        if (result.getCode() == 200) {
        }
    }
}
