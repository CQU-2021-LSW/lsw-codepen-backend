package com.lowt.codepenlowt.service;

import com.lowt.codepenlowt.entity.TableFrontendLib;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LOW_TASTE
 * @since 2021-12-07
 */
@Service
@EnableCaching
public interface TableFrontendLibService extends IService<TableFrontendLib> {

}
