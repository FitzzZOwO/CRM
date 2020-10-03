package com.wenjing.crm.settings.service;

import com.wenjing.crm.settings.domain.User;
import com.wenjing.crm.exception.LoginException;

import java.util.List;

public interface UserService {

    User login(String loginAct, String loginPwd, String ip) throws LoginException;

    List<User> getUserList();
}
