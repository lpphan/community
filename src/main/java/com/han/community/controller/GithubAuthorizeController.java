package com.han.community.controller;

import com.han.community.dto.AccessTokenDTO;
import com.han.community.util.GithubUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

/**
 * github跳转登录
 */
@Controller
public class GithubAuthorizeController {
    private final String CLIENT_ID = "2263f7d8276928e147d0";
    private final String CLIENT_SECRET = "2abacbb12febb6e407a812811d8b30237e7b018a";
    private final String CALLBACK_URL = "http://localhost:8887/callback";

    @Autowired
    private GithubUtil githubUtil;

    /**
     * callback获取登录code
     */
    public String callBack(@RequestParam(name = "code")String code, @RequestParam(name = "status")String status, Model model) throws IOException {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(CLIENT_ID);
        accessTokenDTO.setClient_secret(CLIENT_SECRET);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(CALLBACK_URL);
        accessTokenDTO.setState(status);
        String accessToken = githubUtil.getAccessToken(accessTokenDTO);//得到access_token

        return "index";
    }
}
