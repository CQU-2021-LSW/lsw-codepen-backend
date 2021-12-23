package com.lowt.codepenlowt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lowt.codepenlowt.entity.TableUserLiked;
import com.lowt.codepenlowt.mapper.TableUserLikedMapper;
import com.lowt.codepenlowt.service.RedisService;
import com.lowt.codepenlowt.service.TableUserLikedService;
import com.lowt.codepenlowt.utils.LikedStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * 主要是对数据库的操作
 * </p>
 *
 * @author LOW_TASTE
 * @since 2021-12-23
 */
@Service
public class TableUserLikedServiceImpl extends ServiceImpl<TableUserLikedMapper, TableUserLiked> implements TableUserLikedService {

    @Autowired
    RedisService redisService;

    @Override
    public List<TableUserLiked> getLikedListByLikedCommentId(Long likedCommentId) {
        QueryWrapper<TableUserLiked> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("liked_comment_id", likedCommentId)
                .eq("status", LikedStatusEnum.LIKE.getCode());
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<TableUserLiked> getLikedListByLikedPostId(Long likedPostId) {
        QueryWrapper<TableUserLiked> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("liked_post_id", likedPostId)
                .eq("status", LikedStatusEnum.LIKE.getCode());
        return baseMapper.selectList(queryWrapper);
    }


    @Override
    public TableUserLiked getByLikedCommentIdAndLikedPostId(Long likedCommentId, Long likedPostId) {
        QueryWrapper<TableUserLiked> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("liked_post_id", likedPostId)
                .eq("liked_comment_id", likedCommentId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    @Transactional
    public void transLikedFromRedis2DB() {
        List<TableUserLiked> list = redisService.getLikedDataFromRedis();
        for (TableUserLiked like : list) {
            TableUserLiked ul = getByLikedCommentIdAndLikedPostId(like.getLikedCommentId(), like.getLikedPostId());
            if (ul == null) {
                //没有记录，直接存入
                baseMapper.insert(like);
            } else {
                // 有记录，需要更新
                ul.setStatus(like.getStatus());
                baseMapper.updateById(ul);
            }
        }
    }

    @Override
    @Transactional
    public void transLikedCountFromRedis2DB() {
        System.out.println("更新点赞数量操作");
    }
}
