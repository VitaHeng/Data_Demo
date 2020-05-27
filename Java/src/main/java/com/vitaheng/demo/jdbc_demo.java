package com.vitaheng.demo;

import java.sql.*;

public class jdbc_demo {
    public static void main(String[] args) {
        Connection cn = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            cn = DriverManager.getConnection("jdbc:mysql:///jdbc?serverTimezone=UTC", "root", "159159");
            st = cn.createStatement();
            String sql = "select * from users;";
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



    }
}
