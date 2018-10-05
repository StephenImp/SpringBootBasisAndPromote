package com.cn.jdbc;

import com.cn.config.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.jsp.jstl.sql.Result;
import javax.servlet.jsp.jstl.sql.ResultSupport;

public class DbUtils {

    private Configuration conf;

    public DbUtils(Configuration conf) {
        this.conf = conf;
    }

    private  String driver = conf.getJdbcDriver();
    private  String url = conf.getJdbcUrl();
    private  String username = conf.getJdbcUsername();
    private  String password = conf.getJdbcPassword();

    public  Connection getConnection() {

        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }


    public  ResultSet runSelectSql(String sql, Object[] params) {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Result result = null;
        conn = getConnection();
        try {
            pstm = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                pstm.setObject(i + 1, params[i]);
            }
            rs = pstm.executeQuery();
            //result = ResultSupport.toResult(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(pstm);
            close(conn);
        }
        //return result;
        return rs;
    }

    public  Result runSelectSql2(String sql) {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        Result result = null;
        conn = getConnection();
        try {
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            result = ResultSupport.toResult(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(rs);
            close(pstm);
            close(conn);
        }
        return result;
    }

    public  boolean runUpdateSql(String sql, Object[] params) {
        Connection conn = null;
        PreparedStatement pstm = null;
        conn = getConnection();
        try {
            pstm = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                pstm.setObject(i + 1, params[i]);
            }
            pstm.executeUpdate();
            return true;
        } catch (SQLException e) {

            e.printStackTrace();
            return false;
        } finally {
            close(pstm);
            close(conn);
        }
    }

    public  void close(PreparedStatement pstmt) {
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public  void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public  void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public  void commit(Connection con) {
        try {
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  void rollback(Connection con) {
        try {
            con.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
