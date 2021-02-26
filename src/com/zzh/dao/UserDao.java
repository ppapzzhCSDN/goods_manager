package com.zzh.dao;


import com.zzh.entity.User;
import com.zzh.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author zzh
 * @description
 * @date
 */
public class UserDao {
    public ArrayList<User> checkNameAndPassword(User user) {
        ArrayList<User> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement prep = null;
        ResultSet rs = null;
        try {
            conn = DbUtil.getConn();
            String sql = "select id,name,password from user where name = ? and password = ?";
            prep = conn.prepareStatement(sql);
            prep.setString(1,user.getName());
            prep.setString(2,user.getPassword());
            rs = prep.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String password = rs.getString("password");
                User user0 = new User();
                user0.setId(id);
                user0.setName(name);
                user0.setPassword(password);
                list.add(user0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.close(rs, prep, conn);
            return list;
        }
    }
}