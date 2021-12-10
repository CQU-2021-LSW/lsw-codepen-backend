package com.lowt.codepenlowt.controller;


import com.lowt.codepenlowt.entity.TableComment;
import com.lowt.codepenlowt.entity.TableUser;
import com.lowt.codepenlowt.service.TableCommentService;
import com.lowt.codepenlowt.service.TableUserService;
import com.lowt.codepenlowt.service.impl.TableCommentServiceImpl;
import com.lowt.codepenlowt.utils.R;
import com.lowt.codepenlowt.vo.CommentVO;
import com.mysql.cj.xdevapi.Table;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LOW_TASTE
 * @since 2021-12-04
 */
@CrossOrigin
@RestController
@RequestMapping("comments")
public class CommentController {
    @Autowired
    TableCommentService tableCommentService;
    @Autowired
    TableUserService tableUserService;
    @PostMapping("uploadComment")
    public R uploadComment(@RequestBody TableComment tableComment){
        try {
            tableCommentService.save(tableComment);
            return R.ok();
        } catch (Exception e){
            return R.error();
        }
    }

    @GetMapping("getCommentList")
    public R getCommentList(){
        try {
            List<TableComment> tableComments = tableCommentService.list();
            List<TableUser> tableUsers = tableUserService.list();
            List<CommentVO> commentVOS = new ArrayList<>();
            for (TableComment tableComment : tableComments){
                CommentVO commentVO = new CommentVO();
                BeanUtils.copyProperties(tableComment,commentVO);
                for (TableUser tableUser : tableUsers){
                    if (tableUser.getUserId().equals(tableComment.getUserId())){
                        commentVO.setUserName(tableUser.getUserName());
                    }
                }
                commentVOS.add(commentVO);
            }
            return R.ok().put("data",commentVOS);
        } catch (Exception e){
            return R.error();
        }
    }
}

