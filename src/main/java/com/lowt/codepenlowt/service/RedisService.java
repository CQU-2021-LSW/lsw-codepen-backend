package com.lowt.codepenlowt.service;

import com.lowt.codepenlowt.dto.LikedCountDTO;
import com.lowt.codepenlowt.entity.TableUserLiked;

import java.util.List;

public interface RedisService {
    /**
     * 点赞。状态为1
     *
     * @param likedCommentId
     * @param likedPostId
     */
    void saveLiked2Redis(Long likedCommentId, Long likedPostId);

    /**
     * 取消点赞。将状态改变为0
     *
     * @param likedCommentId
     * @param likedPostId
     */
    void unlikeFromRedis(Long likedCommentId, Long likedPostId);

    /**
     * 从Redis中删除一条点赞数据
     *
     * @param likedCommentId
     * @param likedPostId
     */
    void deleteLikedFromRedis(Long likedCommentId, Long likedPostId);

    /**
     * 该用户的点赞数加1
     *
     * @param likedUserId
     */
    void incrementLikedCount(Long likedUserId);

    /**
     * 该用户的点赞数减1
     *
     * @param likedUserId
     */
    void decrementLikedCount(Long likedUserId);

    /**
     * 获取Redis中存储的所有点赞数据
     *
     * @return
     */
    List<TableUserLiked> getLikedDataFromRedis();

    /**
     * 获取Redis中存储的某个LikedCommentId点赞数据
     */
    Long getLikedCountRedisByLikedCommentId(Long likedCommentId);


    /**
     * 获取Redis中存储的所有点赞数量
     *
     * @return
     */
    List<LikedCountDTO> getLikedCountFromRedis();

    /**
     * 获取Redis中存储的点赞与否
     *
     * @return
     */
    Integer getLikedFromRedis(Long likedCommentId, Long likedPostId);
}
