package com.lowt.codepenlowt.controller;


import com.lowt.codepenlowt.bean.UserIdBean;
import com.lowt.codepenlowt.entity.TableUser;
import com.lowt.codepenlowt.service.TableUserService;
import com.lowt.codepenlowt.utils.JWTUtils;
import com.lowt.codepenlowt.utils.R;
import com.lowt.codepenlowt.vo.LoginInfoVO;
import com.lowt.codepenlowt.vo.UserInfoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * 控制器
 * </p>
 *
 * @author LOW_TASTE
 * @since 2021-12-04
 */
@CrossOrigin
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    TableUserService tableUserService;

    @PostMapping("login")
    public R login(HttpServletRequest request, @RequestBody LoginInfoVO loginInfoVO) {
//        if (!request.getSession().getAttribute(SessionConstant.IMAGE_CODE).equals(loginInfoVO.getImageCode())) {
//            System.out.println(request.getSession().getAttribute(SessionConstant.IMAGE_CODE) + "@@");
//            return R.error("验证码错误");
//        }
        UserInfoVO userInfoVO = new UserInfoVO();
        String token = "REFUSE";
        try {
            TableUser tableUser = tableUserService.login(loginInfoVO);
            BeanUtils.copyProperties(tableUser, userInfoVO);
            Map<String, String> playLoad = new HashMap<>();
            playLoad.put("id", tableUser.getUserId().toString());
            playLoad.put("name", tableUser.getUserName());
            token = JWTUtils.getToken(playLoad);
        } catch (Exception e) {
            return R.error(500, "登陆失败");
        }
        return Objects.requireNonNull(R.ok().put("data", userInfoVO)).put("token", token);
    }

    // 校验 JR303 加上 前端检查
    @PostMapping("register")
    public R register(@RequestBody TableUser tableUser) {
        TableUser tableUserSuccess = new TableUser();
        try {
            tableUserSuccess = tableUserService.register(tableUser);
        } catch (Exception e) {
            return Objects.requireNonNull(R.error().put("state", false)).put("msg", "用户名占用");
        }
        return R.ok().put("data", tableUserSuccess);
    }

    // 找回密码
    @PostMapping("findBackPwd")
    public R findBackPwd(@RequestBody TableUser tableUser) {
        try {
            tableUserService.findBackPwd(tableUser);
            return R.ok().put("state", true);
        } catch (Exception e) {
            return R.error("信息有误");
        }
    }

    @GetMapping("userInfo")
    @Cacheable(value = "userInfo", key = "#userId")
    public R getUserInfo(Long userId) {
        TableUser tableUser = tableUserService.getById(userId);
        if (tableUser == null) {
            return R.error("无用户信息");
        }
        UserInfoVO userInfoVO = new UserInfoVO();
        BeanUtils.copyProperties(tableUser, userInfoVO);
        System.out.println(userInfoVO);
        return R.ok().put("data", userInfoVO);
    }

    // 更新
    @PostMapping("update")
    @Caching(evict = @CacheEvict(cacheNames = "userInfo", key = "#tableUser.userId"))
    public R updateUserInfo(@RequestBody TableUser tableUser) {
        try {
            tableUserService.updateUserInfo(tableUser);
        } catch (RuntimeException e) {
            return R.error();
        }
        return R.ok().put("state", true);
    }

    
    @PostMapping("delUser")
    public R delUser(@RequestBody UserIdBean userIdBean) {
        try {
            tableUserService.removeByIds(Arrays.asList(userIdBean.getUserIds()));
            return R.ok("删除userId" + Arrays.toString(userIdBean.getUserIds()));
        } catch (Exception e) {
            return R.error();
        }
    }
}

