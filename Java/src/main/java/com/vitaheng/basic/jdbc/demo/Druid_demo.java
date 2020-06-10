package com.vitaheng.basic.jdbc.demo;

import com.vitaheng.basic.jdbc.utils.DruidUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Druid_demo {
    public static void main(String[] args) {
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        try {
            //3
            //从数据库连接池中获取连接对象.
            //方式一: 先获取数据库连接池 --> 获取连接对象
            /*DataSource ds = DruidUtils.getDataSource();
            conn = ds.getConnection();*/

            //方式二: 直接从数据库连接池中获取连接对象.
            conn = DruidUtils.getConnection();

            //4
            stat = conn.createStatement();
            //5
            String sql = "select * from users;";
            rs = stat.executeQuery(sql);
            //6
            while(rs.next()) {  //判断结果集中是否有数据
                int uid = rs.getInt("uid");
                String username = rs.getString("username");
                String password = rs.getString("password");
                System.out.println(uid + "..." + username + "..." + password);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //7
            DruidUtils.release(conn,stat,rs);
            /*
                关于conn.close();的说:
                    如果conn是我们自己手动创建的, 调用close()就是销毁连接对象.
                    如果conn是我们从数据库连接池中获取的, 调用close()就是: 自动归还连接对象到数据库连接池.
             */
        }
    }



}
