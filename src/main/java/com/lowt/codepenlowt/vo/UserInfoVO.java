package com.lowt.codepenlowt.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UserInfoVO implements Serializable {

    private Long userId;

    private String userName;

    private String userPhone;

    private Integer userGender;

    private String userMotto;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime userCreateTime;

    private String userAddr;

    private String userEmail;

    private String userPhoto;
}
