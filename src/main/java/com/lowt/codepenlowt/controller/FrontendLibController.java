package com.lowt.codepenlowt.controller;


import com.lowt.codepenlowt.service.TableFrontendLibService;
import com.lowt.codepenlowt.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LOW_TASTE
 * @since 2021-12-07
 */
@CrossOrigin
@RestController
@RequestMapping("libs")
public class FrontendLibController {

    @Autowired
    TableFrontendLibService tableFrontendLibService;

    @GetMapping("getLibs")
    public R getLibs(){
        try {
            return R.ok().put("data",tableFrontendLibService.list());
        }
        catch (Exception e){
            return R.error();
        }
    }
}

