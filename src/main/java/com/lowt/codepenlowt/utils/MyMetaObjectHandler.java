package com.lowt.codepenlowt.utils;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 对数据库每条记录的创建时间和更新时间自动进行填充
 **/
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    /**
     * 插入时的填充策略
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "userCreateTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "noteCreateTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "commentCreateTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
    }

    /**
     * 更新时的填充策略
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        this.strictInsertFill(metaObject, "noteUpdateTime", LocalDateTime.class, LocalDateTime.now());
    }
}
