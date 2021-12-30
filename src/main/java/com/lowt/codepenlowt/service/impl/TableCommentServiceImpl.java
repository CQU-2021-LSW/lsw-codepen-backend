package com.lowt.codepenlowt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lowt.codepenlowt.entity.TableComment;
import com.lowt.codepenlowt.entity.TableUser;
import com.lowt.codepenlowt.entity.TableUserLiked;
import com.lowt.codepenlowt.mapper.TableCommentMapper;
import com.lowt.codepenlowt.service.RedisService;
import com.lowt.codepenlowt.service.TableCommentService;
import com.lowt.codepenlowt.service.TableUserLikedService;
import com.lowt.codepenlowt.service.TableUserService;
import com.lowt.codepenlowt.utils.R;
import com.lowt.codepenlowt.vo.CommentVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author LOW_TASTE
 * @since 2021-12-04
 */
@Service
public class TableCommentServiceImpl extends ServiceImpl<TableCommentMapper, TableComment> implements TableCommentService {

    @Autowired
    TableUserService tableUserService;

    @Autowired
    TableUserLikedService tableUserLikedService;

    @Autowired
    RedisService redisService;

    @Override
    public List<CommentVO> getSubComment(Long commentId, Long userId) {
        QueryWrapper<TableComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pre_comment_id", commentId);
        return tableComment2VO(baseMapper.selectList(queryWrapper), userId);
    }

    @Override
    public List<CommentVO> getPreCommentList(Long userId) {
        QueryWrapper<TableComment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pre_comment_id", 0);
        return tableComment2VO(baseMapper.selectList(queryWrapper), userId);
    }


    public List<CommentVO> tableComment2VO(List<TableComment> tableComments, Long userId) {
        List<TableUser> tableUsers = tableUserService.list();
        List<CommentVO> commentVOS = new ArrayList<>();
        for (TableComment tableComment : tableComments) {
            CommentVO commentVO = new CommentVO();
            BeanUtils.copyProperties(tableComment, commentVO);

            // 匹配姓名
            for (TableUser tableUser : tableUsers) {
                if (tableUser.getUserId().equals(tableComment.getUserId())) {
                    commentVO.setUserName(tableUser.getUserName());
                    commentVO.setUserPhoto(tableUser.getUserPhoto());
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
        return commentVOS;
    }

}

