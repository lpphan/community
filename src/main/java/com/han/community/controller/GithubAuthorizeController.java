package com.han.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * github跳转登录
 */
@Controller
public class GithubAuthorizeController {
    /**
     * callback获取登录code
     */
    public String callBack(@RequestParam(name = "code")String code, @RequestParam(name = "status")String status, Model model){
        return "index";
    }
}
