package com.han.community.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.han.community.dto.AccessTokenDTO;
import com.han.community.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubUtil {

    private final String ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token";

    private final String GET_GITHUB_USER_URL = "https://api.github.com/user?access_token=";

    /**
     * 获取access_token
     */
    public String getAccessToken(AccessTokenDTO accessTokenDTO) throws IOException {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        JSONObject jsonObject = new JSONObject();
        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url(ACCESS_TOKEN_URL)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String req = response.body().string();
            String[] split = req.split("&");
            String s = split[0];
            String[] split1 = s.split("=");
            String access_token = split1[1];
            System.out.println(access_token);
            return access_token;
        }
    }

    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(GET_GITHUB_USER_URL + accessToken)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            GithubUser user = JSON.parseObject(string, GithubUser.class);
            System.out.println(user);
            return user;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
