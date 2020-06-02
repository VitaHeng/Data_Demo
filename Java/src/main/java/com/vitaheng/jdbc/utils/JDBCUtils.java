package com.vitaheng.jdbc.utils;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class JDBCUtils {
    private JDBCUtils() {}

    private static String driverClass;
    private static String url;
    private static String username;
    private static String password;

    private static void readConfig() {
        Properties properties=new Properties();
        try {
            properties.load(new FileReader("E:\\study\\Demo\\Data_Demo\\Java\\src\\main\\resources\\config.properties"));
            driverClass = properties.getProperty("driverClass");
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    static {
        readConfig();
        try {
            Class.forName(driverClass);
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
       try {
            return DriverManager.getConnection(url,username,password);
       }catch (SQLException e) {
           e.printStackTrace();
       }
       return null;
    }

    public static void release(Connection cn, Statement st, ResultSet rs) {
        try {
            if (rs!=null) {
                rs.close();
                rs=null;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                if (st!=null) {
                    st.close();
                    st=null;
                }
            }catch (SQLException e) {
                e.printStackTrace();
            }finally {
                try {
                    if (cn!=null) {
                        cn.close();
                        cn=null;
                    }
                }catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void release(Connection stat,Statement conn) {
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
