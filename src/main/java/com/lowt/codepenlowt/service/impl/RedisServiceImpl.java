package com.lowt.codepenlowt.service.impl;

import com.lowt.codepenlowt.dto.LikedCountDTO;
import com.lowt.codepenlowt.entity.TableUserLiked;
import com.lowt.codepenlowt.service.RedisService;
import com.lowt.codepenlowt.service.TableUserLikedService;
import com.lowt.codepenlowt.utils.LikedStatusEnum;
import com.lowt.codepenlowt.utils.RedisKeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    TableUserLikedService tableUserLikedService;

    @Override
    public void saveLiked2Redis(Long likedCommentId, Long likedPostId) {
        String key = RedisKeyUtils.getLikedKey(likedCommentId, likedPostId);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER_LIKED, key, LikedStatusEnum.LIKE.getCode());
    }

    @Override
    public void unlikeFromRedis(Long likedCommentId, Long likedPostId) {
        String key = RedisKeyUtils.getLikedKey(likedCommentId, likedPostId);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER_LIKED, key, LikedStatusEnum.UNLIKE.getCode());
    }

    @Override
    public void deleteLikedFromRedis(Long likedCommentId, Long likedPostId) {
        String key = RedisKeyUtils.getLikedKey(likedCommentId, likedPostId);
        redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED, key);
    }

    @Override
    public void incrementLikedCount(Long likedUserId) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT, likedUserId, 1);
    }

    @Override
    public void decrementLikedCount(Long likedUserId) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT, likedUserId, -1);
    }

    @Override
    public List<TableUserLiked> getLikedDataFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_USER_LIKED, ScanOptions.NONE);
        List<TableUserLiked> list = new ArrayList<>();
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> entry = cursor.next();
            String key = (String) entry.getKey();
            //分离出 likedCommentId，likedPostId
            String[] split = key.split("::");
            Long likedCommentId = Long.valueOf(split[0]);
            Long likedPostId = Long.valueOf(split[1]);
            Integer value = (Integer) entry.getValue();
            //组装成 UserLike对象
            TableUserLiked userLike = new TableUserLiked(likedCommentId, likedPostId, value);
            list.add(userLike);
            //存到 list 后从 Redis 中删除
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED, key);
        }
        return list;
    }

    @Override
    public Long getLikedCountRedisByLikedCommentId(Long likedCommentId) {
        Long count = 0L;
        List<LikedCountDTO> likedCountFromRedis = getLikedCountFromRedis();
        if (likedCountFromRedis == null) {
            return count;
        }
        for (LikedCountDTO dto : likedCountFromRedis) {
            if (dto.getLikedCommentId().equals(likedCommentId) && dto.getStatus() == 1) {
                count++;
            }
        }
        return count;
    }

    @Override
    public List<LikedCountDTO> getLikedCountFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_USER_LIKED, ScanOptions.NONE);
        List<LikedCountDTO> list = new ArrayList<>();
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> map = cursor.next();
            //将点赞情况存储在 LikedCountDTO
            String key = (String) map.getKey();
            LikedCountDTO dto = new LikedCountDTO(key, (Integer) map.getValue());
            list.add(dto);
        }
        return list;
    }

    @Override
    public Integer getLikedFromRedis(Long likedCommentId, Long likedPostId) {
        Integer status = 0;
        String key = RedisKeyUtils.getLikedKey(likedCommentId, likedPostId);
        try {
            status = redisTemplate.opsForHash().get(RedisKeyUtils.MAP_KEY_USER_LIKED, key) == null ? -1 : (Integer) redisTemplate.opsForHash().get(RedisKeyUtils.MAP_KEY_USER_LIKED, key);
            System.out.println(status);
            return status;
        } catch (Exception e) {

        }
        return -1;
    }
}



