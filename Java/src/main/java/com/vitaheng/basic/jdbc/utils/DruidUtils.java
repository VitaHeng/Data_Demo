package com.vitaheng.basic.jdbc.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DruidUtils {
    private DruidUtils() {}
    private static DataSource ds;

    static {
        Properties ps = new Properties();
        try {
            ps.load(new FileReader("E:\\study\\Demo\\Data_Demo\\Java\\src\\main\\resources\\config.properties"));
            ds = DruidDataSourceFactory.createDataSource(ps);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DataSource getDataSource() {
        return ds;
    }

    public static Connection getConnection() {
        try {
            return ds.getConnection();
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void release(Connection conn, Statement stat, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
                rs = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stat != null) {
                    stat.close();
                    stat = null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (conn != null) {
                        conn.close();
                        conn = null;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void release(Connection conn, Statement stat) {
        try {
            if (stat != null) {
                stat.close();
                stat = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
