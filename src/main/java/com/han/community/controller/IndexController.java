package com.han.community.controller;

import com.han.community.mapper.UserMapper;
import com.han.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/")
    public String index(HttpServletRequest request){

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if ("token".equals(cookie.getName())){
                User user = userMapper.getUserByToken(cookie.getValue());
                if (user != null){
                    //写入session
                    request.getSession().setAttribute("user",user);//将user信息放入session中
                }
                break;
            }
        }
        return "index";
    }
}
