package com.lowt.codepenlowt.controller;


import com.lowt.codepenlowt.entity.TableUser;
import com.lowt.codepenlowt.service.TableUserService;
import com.lowt.codepenlowt.utils.JWTUtils;
import com.lowt.codepenlowt.utils.R;
import com.lowt.codepenlowt.utils.SessionConstant;
import com.lowt.codepenlowt.vo.LoginInfoVO;
import com.lowt.codepenlowt.vo.UserInfo;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 *  控制器
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
    public R login(HttpServletRequest request, @RequestBody LoginInfoVO loginInfoVO){
        if (!request.getSession().getAttribute(SessionConstant.IMAGE_CODE).equals(loginInfoVO.getImageCode())){
            System.out.println(request.getSession().getAttribute(SessionConstant.IMAGE_CODE)+"@@");
            return R.error("验证码错误");
        }
        Map<String,Object> map = new HashMap<>();
        TableUser tableUser = new TableUser();
        try {
            tableUser = tableUserService.login(loginInfoVO);
//            System.out.println(tableUser);
            Map<String,String> playLoad = new HashMap<>();
            playLoad.put("id", tableUser.getUserId().toString());
            playLoad.put("name", tableUser.getUserName());
            String token = JWTUtils.getToken(playLoad);
            map.put("state",true);
            map.put("msg","认证成功");
            map.put("token",token);
        } catch (Exception e){
            map.put("state",false);
            map.put("msg",e.getMessage());
        }
        return R.ok(map).put("userId",tableUser.getUserId());
    }

    // 校验 JR303 加上 前端检查
    @PostMapping("register")
    public R register(@RequestBody TableUser tableUser){
        try {
            tableUserService.register(tableUser);
        } catch (Exception e){
            return Objects.requireNonNull(R.error().put("state", false)).put("msg","用户名占用");
        }
        return R.ok().put("state",true);
    }

    // 找回密码
    @PostMapping("findBackPwd")
    public R findBackPwd(@RequestBody TableUser tableUser){
        try {
            tableUserService.findBackPwd(tableUser);
            return R.ok().put("state",true);
        } catch (Exception e) {
            return R.error("信息有误");
        }
    }

    @GetMapping("userInfo")
    @Cacheable(cacheNames = "userInfo")
    public R getUserInfo(Long userId){
        TableUser tableUser = tableUserService.getById(userId);
        if (tableUser == null){
            return R.error("无用户信息");
        }
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(tableUser,userInfo);
        System.out.println(userInfo);
        return R.ok().put("data",userInfo);
    }

    // 更新
    @CachePut("userInfo")
    @PostMapping("update")
    public R updateUserInfo(@RequestBody TableUser tableUser){
        try {
            tableUserService.updateUserInfo(tableUser);
        } catch (RuntimeException e) {
            return R.error();
        }
        return R.ok().put("state",true);
    }
}

