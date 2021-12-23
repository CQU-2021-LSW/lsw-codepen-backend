package com.lowt.codepenlowt.dto;


import com.lowt.codepenlowt.entity.TableUserLiked;

/**
 * 继承 TableUserLiked
 * 构造函数接受 redis 里的 Key 自动解析成两个 Id 和 status
 */
public class LikedCountDTO extends TableUserLiked {
    public LikedCountDTO(String key, Integer value) {
        super();
        String[] keys = key.split("::");
        this.setLikedCommentId(Long.parseLong(keys[0]));
        this.setLikedPostId(Long.parseLong(keys[1]));
        this.setStatus(value);
    }
}
