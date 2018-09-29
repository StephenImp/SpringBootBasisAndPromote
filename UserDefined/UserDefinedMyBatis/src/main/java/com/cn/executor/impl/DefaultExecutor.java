package com.cn.executor.impl;

import com.cn.config.Configuration;
import com.cn.config.MapperStatement;
import com.cn.executor.Executor;
import com.cn.jdbc.DbUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018\9\12 0012.
 *两个功能
 * 1.使用jdbc得到数据
 * 2.利用反射吧数据映射到实体类中（暂未实现）
 */
public class DefaultExecutor implements Executor{

    private Configuration conf;

    public DefaultExecutor(Configuration conf) {
        this.conf = conf;
    }

    @Override
    public <E> List<E> query(MapperStatement ms, Object parameter) {

        List<E> ret = new ArrayList<>();

        DbUtils dbUtils = new DbUtils(conf);
        Connection connection = dbUtils.getConnection();

        Object[] params = {parameter};

        System.out.println(ms.getSql());
        System.out.println(ms.getResultType());

        ResultSet result = dbUtils.runSelectSql(ms.getSql(), params);

        List<E> resultList = new ArrayList<>();

        try {
            while (result.next()){
                ResultSetMetaData md = result.getMetaData();

                Map rowData = new HashMap();
                int columnCount = md.getColumnCount();

                for (int i = 1; i <= columnCount; i++) {
                    rowData.put(md.getColumnName(i), result.getObject(i));
                }

                //这里是应该拿到数据，映射到实体类中去
                //这里其实是应该反射实体类中的数据
                resultList.add((E) rowData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            dbUtils.close(connection);
        }
        return resultList;
    }
}
