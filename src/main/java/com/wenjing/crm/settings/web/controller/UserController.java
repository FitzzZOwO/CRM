package com.wenjing.crm.settings.web.controller;

import com.wenjing.crm.settings.domain.User;
import com.wenjing.crm.settings.service.UserService;
import com.wenjing.crm.settings.service.impl.UserServiceImpl;
import com.wenjing.crm.utils.MD5Util;
import com.wenjing.crm.utils.PrintJson;
import com.wenjing.crm.utils.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UserController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String s = request.getServletPath();
        if ("/settings/user/login.do".equals(s)) {
            login(request, response);
        } else if ("/settings/user/xxx.do".equals(s)) {
            //xxx(request, response);
        }
    }


    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("进入到登录验证");
        String loginAct = request.getParameter("loginAct");
        String loginPwd = request.getParameter("loginPwd");
        //密码加密存储
        loginPwd= MD5Util.getMD5(loginPwd);
        String ip = request.getRemoteAddr();
        System.out.println("===========================当前请求ip:" + ip);
        UserService us = (UserService) ServiceFactory.getService(new UserServiceImpl());
        try {
            User user = us.login(loginAct, loginPwd, ip);
            /*
            程序到此没有抛出异常，一定返回user
            登陆成功,返回格式：
                data
                    {"success":true}
             */
            request.getSession().setAttribute("user", user);
            //转换为json格式的工具类
            PrintJson.printJsonFlag(response, true);
        } catch (Exception e) {
            e.printStackTrace();
            /*
            登陆失败返回格式：
                data
                    {"success":false,"msg":? }

             */
            String msg = e.getMessage();
            /*
                返回多项的Json时，两种解决方案：
                       1.放入map
                       2.创建一个Vo来存取
                   当展示的信息将来会被大量使用，建议创建Vo类，否则用map
             */
            Map<String, Object> map = new HashMap<>();
            map.put("success", false);
            map.put("msg", msg);
            //转换为json格式的工具类
            PrintJson.printJsonObj(response, map);
        }


    }

}
