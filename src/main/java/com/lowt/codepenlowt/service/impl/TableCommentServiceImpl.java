package com.lowt.codepenlowt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lowt.codepenlowt.entity.TableComment;
import com.lowt.codepenlowt.entity.TableUser;
import com.lowt.codepenlowt.entity.TableUserLiked;
import com.lowt.codepenlowt.mapper.TableCommentMapper;
import com.lowt.codepenlowt.service.RedisService;
import com.lowt.codepenlowt.service.TableCommentService;
import com.lowt.codepenlowt.service.TableUserLikedService;
import com.lowt.codepenlowt.service.TableUserService;
import com.lowt.codepenlowt.vo.CommentVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

/*    @Override
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
    }*/

    /**
     * @param commentId 0值查询所有父Id, 其他值为子Id
     * @param userId    判断 userId 是否 liked
     * @return
     */
    @Override
    public List<CommentVO> getCommentList(Long commentId, Long userId) {
        if (commentId == null) {
            commentId = 0L;
        }
        final List<TableComment> tableComments = baseMapper.selectList(null);
        List<CommentVO> commentVOS = tableComment2VO(tableComments, userId);

        Long finalCommentId = commentId;
        return commentVOS.stream().filter(commentVO -> {
            return commentVO.getPreCommentId().equals(finalCommentId);
        }).collect(Collectors.toList());
    }

    public List<CommentVO> tableComment2VO(List<TableComment> tableComments, Long userId) {
        List<TableUser> tableUsers = tableUserService.list();
        List<CommentVO> commentVOS = new ArrayList<>();

        // comment有关用户信息的拼接
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
        // n^2 复杂度
        for (CommentVO commentVO : commentVOS) {
            commentVO.setSubCommentNum((long) getSubCommentNum(commentVO, commentVOS).size());
        }
        return commentVOS;
    }

    private List<CommentVO> getSubCommentNum(CommentVO commentVO, List<CommentVO> commentVOList) {
        return commentVOList.stream().filter(commentVO1 -> {
            return commentVO1.getPreCommentId().equals(commentVO.getCommentId());
        }).collect(Collectors.toList());
    }

}

