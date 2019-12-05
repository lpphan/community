package com.han.community.controller;

import com.han.community.dto.AccessTokenDTO;
import com.han.community.dto.GithubUser;
import com.han.community.util.GithubUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

/**
 * github跳转登录
 */
@Controller
public class GithubAuthorizeController {

    @Value("${github.accesstoken.client_id}")
    private String CLIENT_ID;
    @Value("${github.accesstoken.client_secret}")
    private String CLIENT_SECRET;
    @Value("${github.accesstoken.redirect_uri}")
    private String CALLBACK_URL;

    @Autowired
    private GithubUtil githubUtil;

    /**
     * callback获取登录code
     */
    @RequestMapping("/callback")
    public String callBack(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state, Model model) throws IOException {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(CLIENT_ID);
        accessTokenDTO.setClient_secret(CLIENT_SECRET);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(CALLBACK_URL);
        accessTokenDTO.setState(state);
        String accessToken = githubUtil.getAccessToken(accessTokenDTO);//得到access_token
        GithubUser user = githubUtil.getUser(accessToken);
        System.out.println(user);
        return "index";
    }
}
