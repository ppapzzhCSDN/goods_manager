package com.zzh.utils;

import java.sql.*;

/**
 * @author zzh
 * @description
 * @date
 */
public class DbUtil {
    public static final String DRIVER = "com.mysql.jdbc.Driver";
    public static final String URL = "jdbc:mysql://localhost:3306/goods_manager?useSSL=false";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "root";

    public static Connection getConn(){
        Connection conn =null;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
    public static  void close(ResultSet rs, PreparedStatement prep,Connection conn){
        try {
            if (rs!=null){
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (prep!=null){
                prep.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn!=null){
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void close(PreparedStatement prep, Connection conn) {
        try {
            if (prep != null) {
                prep.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
