package com.han.community.util;

import com.alibaba.fastjson.JSONObject;
import com.han.community.dto.AccessTokenDTO;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubUtil {

    private final String ACCESS_TOKEN_URL = "https://github.com/login/oauth/access_token";

    /**
     * 获取access_token
     */
    public String getAccessToken(AccessTokenDTO accessTokenDTO) throws IOException {
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        JSONObject jsonObject = new JSONObject();
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(ACCESS_TOKEN_URL)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String req = response.body().string();
            System.out.println(req);
            return response.body().string();
        }
        return "";
    }


}
