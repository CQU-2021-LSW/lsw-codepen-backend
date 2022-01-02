package com.lowt.codepenlowt.controller;


import com.lowt.codepenlowt.bean.LibIdBean;
import com.lowt.codepenlowt.entity.TableFrontendLib;
import com.lowt.codepenlowt.service.TableFrontendLibService;
import com.lowt.codepenlowt.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * <p>
 * 前端控制器
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
    public R getLibs() {
        try {
            return R.ok().put("data", tableFrontendLibService.list());
        } catch (Exception e) {
            return R.error();
        }
    }

    @PostMapping("updateLib")
    public R updateLib(@RequestBody TableFrontendLib tableFrontendLib) {
        try {
            tableFrontendLibService.updateById(tableFrontendLib);
            return R.ok();
        } catch (Exception e) {
            return R.error();
        }
    }


    @PostMapping("delLib")
    public R deleteLib(@RequestBody LibIdBean libIdBean) {
        try {
            tableFrontendLibService.removeByIds(Arrays.asList(libIdBean.getLibIds()));
            return R.ok("删除libId" + Arrays.toString(libIdBean.getLibIds()));
        } catch (Exception e) {
            return R.error();
        }
    }

    @PostMapping("insertLib")
    public R insertLib(@RequestBody TableFrontendLib tableFrontendLib) {
        try {
            tableFrontendLibService.save(tableFrontendLib);
            return R.ok();
        } catch (Exception e) {
            return R.error();
        }
    }

}

