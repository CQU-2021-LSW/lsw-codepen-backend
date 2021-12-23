package com.lowt.codepenlowt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lowt.codepenlowt.entity.TableUserLiked;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author LOW_TASTE
 * @since 2021-12-23
 */
@Service
public interface TableUserLikedService extends IService<TableUserLiked> {
    /**
     * 根据被点赞评论的id查询点赞列表（即查询都谁给这个人点赞过）
     *
     * @param likedCommentId 被点赞评论的id
     * @param pageable
     * @return
     */
    List<TableUserLiked> getLikedListByLikedCommentId(Long likedCommentId);

    /**
     * 根据点赞人的id查询点赞列表（即查询这个人都给谁点赞过）
     *
     * @param likedPostId 点赞人的id
     * @param pageable
     * @return
     */
    List<TableUserLiked> getLikedListByLikedPostId(Long likedPostId);

    /**
     * 通过被点赞和点赞人id查询是否存在点赞记录
     *
     * @param likedCommentId
     * @param likedPostId
     * @return
     */
    TableUserLiked getByLikedCommentIdAndLikedPostId(Long likedCommentId, Long likedPostId);

    /**
     * 将Redis里的点赞数据存入数据库中
     */
    void transLikedFromRedis2DB();

    /**
     * 将Redis中的点赞数量数据存入数据库
     */
    void transLikedCountFromRedis2DB();

}
