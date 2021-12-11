package com.lowt.codepenlowt.vo;

import lombok.Data;

import java.io.Serializable;


@Data
public class LoginInfoVO implements Serializable {
    private String userName;
    private String password;
    private String imageCode;
}
