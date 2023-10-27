package com.wxmblog.base.common.handler;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.lang.reflect.ParameterizedType;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-12-09 15:11
 **/

public class BaseObjectTypeHandler<T> extends BaseTypeHandler<T> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, T t, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, JSON.toJSONString(t));
    }

    @Override
    public T getNullableResult(ResultSet resultSet, String s) throws SQLException {
        Class<T> class1 = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        T t = JSONObject.parseObject(resultSet.getString(s), class1);
        return t;
    }

    @Override
    public T getNullableResult(ResultSet resultSet, int i) throws SQLException {
        Class<T> class1 = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        T t = JSONObject.parseObject(resultSet.getString(i), class1);
        return t;
    }

    @Override
    public T getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        Class<T> class1 = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        T t = JSONObject.parseObject(callableStatement.getString(i), class1);
        return t;
    }
}
