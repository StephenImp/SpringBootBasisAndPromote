package com.cn.config;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018\9\12 0012.
 * 存放数据库配置信息.
 * 存放mapper.xml信息。
 */
public class Configuration {

    private String jdbcDriver;
    private String jdbcUrl;
    private String jdbcUsername;
    private String jdbcPassword;

    /**
     * 扫描的mapper.xml信息。
     */
    private Map<String,MapperStatement> mapperStatements = new HashMap<>();


    public String getJdbcDriver() {
        return jdbcDriver;
    }

    public void setJdbcDriver(String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getJdbcUsername() {
        return jdbcUsername;
    }

    public void setJdbcUsername(String jdbcUsername) {
        this.jdbcUsername = jdbcUsername;
    }

    public String getJdbcPassword() {
        return jdbcPassword;
    }

    public void setJdbcPassword(String jdbcPassword) {
        this.jdbcPassword = jdbcPassword;
    }

    public Map<String, MapperStatement> getMapperStatements() {
        return mapperStatements;
    }

    public void setMapperStatements(Map<String, MapperStatement> mapperStatements) {
        this.mapperStatements = mapperStatements;
    }
}
