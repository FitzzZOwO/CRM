package com.wenjing.crm.settings.service.impl;

import com.wenjing.crm.settings.dao.UserDao;
import com.wenjing.crm.settings.domain.User;
import com.wenjing.crm.exception.LoginException;
import com.wenjing.crm.settings.service.UserService;
import com.wenjing.crm.util.DateTimeUtil;
import com.wenjing.crm.util.SqlSessionUtil;

import java.util.HashMap;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private UserDao userDao = SqlSessionUtil.getSqlSession().getMapper(UserDao.class);

    @Override
    public User login(String loginAct, String loginPwd, String ip) throws LoginException {

        Map<String, Object> map = new HashMap<>();
        map.put("loginAct", loginAct);
        map.put("loginPwd", loginPwd);

        User user = userDao.login(map);

        if (user == null) {

            throw new LoginException("账号密码错误");

        }

        //如果程序能够成功的执行到该行，说明账号密码正确
        //需要继续向下验证其他3项信息

        //验证失效时间
        String expireTime = user.getExpireTime();
        String currentTime = DateTimeUtil.getSysTime();
        if (expireTime.compareTo(currentTime) < 0) {

            throw new LoginException("账号已失效");

        }

        //判断锁定状态
        String lockState = user.getLockState();
        if ("0".equals(lockState)) {

            throw new LoginException("账号已锁定");

        }

        //判断ip地址
        String allowIps = user.getAllowIps();

        if (!allowIps.contains(ip)) {

            throw new LoginException("ip地址受限");

        }


        return user;

    }
}
