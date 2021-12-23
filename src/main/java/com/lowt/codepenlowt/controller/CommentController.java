package com.lowt.codepenlowt.controller;


import com.lowt.codepenlowt.entity.TableComment;
import com.lowt.codepenlowt.entity.TableUser;
import com.lowt.codepenlowt.entity.TableUserLiked;
import com.lowt.codepenlowt.service.RedisService;
import com.lowt.codepenlowt.service.TableCommentService;
import com.lowt.codepenlowt.service.TableUserLikedService;
import com.lowt.codepenlowt.service.TableUserService;
import com.lowt.codepenlowt.utils.R;
import com.lowt.codepenlowt.vo.CommentVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
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

    @Autowired
    TableUserLikedService tableUserLikedService;

    @Autowired
    RedisService redisService;

    @PostMapping("uploadComment")
    public R uploadComment(@RequestBody TableComment tableComment) {
        try {
            tableCommentService.save(tableComment);
            return R.ok();
        } catch (Exception e) {
            return R.error();
        }
    }

    @GetMapping("getCommentList")
    public R getCommentList(@RequestParam(name = "userId", required = false) Long userId) {
        try {
            List<TableComment> tableComments = tableCommentService.list();
            List<TableUser> tableUsers = tableUserService.list();
            List<CommentVO> commentVOS = new ArrayList<>();

            for (TableComment tableComment : tableComments) {
                CommentVO commentVO = new CommentVO();
                BeanUtils.copyProperties(tableComment, commentVO);

                // 匹配姓名
                for (TableUser tableUser : tableUsers) {
                    if (tableUser.getUserId().equals(tableComment.getUserId())) {
                        commentVO.setUserName(tableUser.getUserName());
                    }
                }
                // 匹配点赞数量
                // Redis中的数量
                Long likeCount = redisService.getLikedCountRedisByLikedCommentId(tableComment.getCommentId());
                // Mysql中的数量
                likeCount += tableUserLikedService.getLikedListByLikedCommentId(tableComment.getCommentId()).size();
                commentVO.setLikeCount(likeCount);
                if (userId != null) {
                    Integer status = 0;
                    // Redis和数据库
                    status = redisService.getLikedFromRedis(tableComment.getCommentId(), userId);
                    // 如果Redis没有
                    if (status == -1) {
                        TableUserLiked byLikedCommentIdAndLikedPostId = tableUserLikedService.getByLikedCommentIdAndLikedPostId(tableComment.getCommentId(), userId);
                        if (byLikedCommentIdAndLikedPostId == null) {
                            status = 0;
                        } else {
                            status = byLikedCommentIdAndLikedPostId.getStatus();
                        }
                    }
                    commentVO.setLiked(status);
                }
                commentVOS.add(commentVO);
            }
            return R.ok().put("data", commentVOS);
        } catch (Exception e) {
            return R.error();
        }
    }

    @PostMapping("doLike")
    public R doLike(@RequestBody TableUserLiked tableUserLiked) {
        // 数据库里存在
        if (tableUserLikedService.getByLikedCommentIdAndLikedPostId(tableUserLiked.getLikedCommentId(), tableUserLiked.getLikedPostId()) != null) {
            // 已经 like 啥也不干
            if (tableUserLikedService.getByLikedCommentIdAndLikedPostId(tableUserLiked.getLikedCommentId(), tableUserLiked.getLikedPostId()).getStatus() == 1) {
                return R.ok("already liked");
            }
        }
        // 没有 like 或者数据库里无记录
        try {
            // 保存到 redis 中定时任务更新
            redisService.saveLiked2Redis(tableUserLiked.getLikedCommentId(), tableUserLiked.getLikedPostId());
            return R.ok("success");
        } catch (Exception e) {
            System.out.println("err");
        }
        return R.error();
    }


    @PostMapping("cancelLike")
    public R cancelLike(@RequestBody TableUserLiked tableUserLiked) {
        try {
            // 保存到 redis 中定时任务更新
            redisService.unlikeFromRedis(tableUserLiked.getLikedCommentId(), tableUserLiked.getLikedPostId());
            return R.ok("success");
        } catch (Exception e) {
            System.out.println("err");
        }
        return R.error();
    }

}

