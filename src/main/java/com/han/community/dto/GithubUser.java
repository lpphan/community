package com.han.community.dto;

import lombok.Data;

@Data
public class GithubUser {
    //id
    private Long id;
    //用户名
    private String name;
    //个人简介
    private String bio;
}
