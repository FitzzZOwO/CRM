package com.wenjing.crm.settings.dao;


import com.wenjing.crm.settings.domain.User;

import java.util.Map;

public interface UserDao {

    User login(Map<String, Object> map);
}
