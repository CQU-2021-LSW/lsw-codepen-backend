package com.lowt.codepenlowt.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum ErrorEnum {
    CRATE_IMAGE_ERROR("CRATE_IMAGE_ERROR");
    
    private String key;
}
    