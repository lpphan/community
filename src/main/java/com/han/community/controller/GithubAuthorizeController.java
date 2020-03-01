package com.han.community.controller;

import com.han.community.dto.AccessTokenDTO;
import com.han.community.dto.GithubUser;
import com.han.community.mapper.UserMapper;
import com.han.community.model.User;
import com.han.community.util.GithubUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

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
    @Autowired
    private UserMapper userMapper;

    /**
     * callback获取登录code
     */
    @RequestMapping("/callback")
    public String callBack(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state,
                           HttpServletResponse response,Model model) throws IOException {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(CLIENT_ID);
        accessTokenDTO.setClient_secret(CLIENT_SECRET);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(CALLBACK_URL);
        accessTokenDTO.setState(state);
        String accessToken = githubUtil.getAccessToken(accessTokenDTO);//得到access_token
        GithubUser githubUser = githubUtil.getUser(accessToken);
        //判断是否登录成功
        if (githubUser != null) {
            User user = new User();
            String token = UUID.randomUUID().toString();//将token作为session
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(githubUser.getId().toString());
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            int insert = userMapper.insert(user);
            if (insert > 0){
                System.out.println("成功保存：" + user);
            }
            //成功
            response.addCookie(new Cookie("token",token));//写入cookie
            //request.getSession().setAttribute("user",githubUser);//将user信息放入session中
            return "redirect:/";//重定向到首页
        }else {
            //登录失败
            return "redirect:/";//重定向到首页
        }
    }
}
