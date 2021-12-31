package com.lowt.codepenlowt.controller;


import com.lowt.codepenlowt.entity.TableComment;
import com.lowt.codepenlowt.entity.TableUserLiked;
import com.lowt.codepenlowt.service.RedisService;
import com.lowt.codepenlowt.service.TableCommentService;
import com.lowt.codepenlowt.service.TableUserLikedService;
import com.lowt.codepenlowt.service.TableUserService;
import com.lowt.codepenlowt.utils.R;
import com.lowt.codepenlowt.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
            List<CommentVO> commentVOS = tableCommentService.getCommentList(null, userId);
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


    @PostMapping("delComment")
    public R delComment(@RequestBody Long commentId) {
        try {
            tableCommentService.removeById(commentId);
            return R.ok();
        } catch (Exception e) {
            return R.error(500, "删除失败");
        }
    }


    @GetMapping("getSubComment")
    public R getSubComment(@RequestParam Long commentId, @RequestParam(name = "userId", required = false) Long userId) {
        try {
            return R.ok().put("data", tableCommentService.getCommentList(commentId, userId));
        } catch (Exception e) {
            return R.error(500, " 获取失败");
        }
    }
}

