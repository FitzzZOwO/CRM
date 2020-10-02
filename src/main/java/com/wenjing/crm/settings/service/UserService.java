package com.wenjing.crm.settings.service;

import com.wenjing.crm.settings.domain.User;
import com.wenjing.crm.exception.LoginException;

public interface UserService {

    User login(String loginAct, String loginPwd, String ip) throws LoginException;
}
