package com.lowt.codepenlowt.task;

import com.lowt.codepenlowt.service.TableUserLikedService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class LikeTask extends QuartzJobBean {

    @Autowired
    TableUserLikedService tableUserLikedService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //将 Redis 里的点赞信息同步到数据库里
        System.out.println("DI -- DI --");
        tableUserLikedService.transLikedFromRedis2DB();
//            tableUserLikedService.transLikedCountFromRedis2DB();
    }
}