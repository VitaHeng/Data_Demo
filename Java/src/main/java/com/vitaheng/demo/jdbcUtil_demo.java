package com.vitaheng.demo;

import com.vitaheng.utils.C3P0Utils;
import com.vitaheng.utils.DruidUtils;
import com.vitaheng.utils.JDBCUtils;

import javax.sql.DataSource;
import java.sql.*;

public class jdbcUtil_demo {
    public static void main(String[] args) {
        Connection cn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
//            cn = JDBCUtils.getConnection();
            cn = DruidUtils.getConnection();
//            cn = C3P0Utils.getConnection();
            st = cn.createStatement();
            String sql = "select * from users";
            rs = st.executeQuery(sql);

            while (rs.next()){
                int uid = rs.getInt("uid");
                String username = rs.getString("username");
                String password = rs.getString("password");
                System.out.println(uid+"--"+username+"--"+password);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
//            JDBCUtils.release(cn,st,rs);
            DruidUtils.release(cn,st,rs);
//            C3P0Utils.release(cn,st,rs);
        }
    }
}
