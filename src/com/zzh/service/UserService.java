package com.zzh.service;

import com.zzh.dao.UserDao;
import com.zzh.entity.User;

import java.util.ArrayList;

/**
 * @author zzh
 * @description
 * @date
 */
public class UserService {
    private UserDao userDao = new UserDao();

    //验证账号和密码
    public boolean checkNameAndPassword(User user) {
        ArrayList<User> list = userDao.checkNameAndPassword(user);
        if (list.size() == 1) {
            return true;
        }
        //存在相同的账号和密码
        //账号密码不正确
        return false;
    }
}
