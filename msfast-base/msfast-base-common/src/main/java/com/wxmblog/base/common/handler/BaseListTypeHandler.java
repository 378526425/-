package com.wxmblog.base.common.handler;

import com.alibaba.fastjson.JSON;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.lang.reflect.ParameterizedType;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class BaseListTypeHandler<T> extends BaseTypeHandler<List<T>> {


    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, List<T> list, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, JSON.toJSONString(list));
    }

    @Override
    public List<T> getNullableResult(ResultSet resultSet, String s) throws SQLException {
        Class<T> class1 = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        List<T> list = JSON.parseArray(resultSet.getString(s), class1);
        return list;
    }

    @Override
    public List<T> getNullableResult(ResultSet resultSet, int i) throws SQLException {
        Class<T> class1 = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        List<T> list = JSON.parseArray(resultSet.getString(i), class1);
        return list;
    }

    @Override
    public List<T> getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        Class<T> class1 = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        List<T> list = JSON.parseArray(callableStatement.getString(i), class1);
        return list;
    }
}
