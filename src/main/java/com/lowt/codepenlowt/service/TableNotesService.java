package com.lowt.codepenlowt.service;

import com.lowt.codepenlowt.entity.TableNotes;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author LOW_TASTE
 * @since 2021-12-04
 */
@Service
@EnableCaching
public interface TableNotesService extends IService<TableNotes> {

    ArrayList<TableNotes> getByUserId(Long userId);
}
