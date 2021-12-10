package com.lowt.codepenlowt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lowt.codepenlowt.entity.TableNotes;
import com.lowt.codepenlowt.mapper.TableNotesMapper;
import com.lowt.codepenlowt.service.TableNotesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LOW_TASTE
 * @since 2021-12-04
 */
@Service
public class TableNotesServiceImpl extends ServiceImpl<TableNotesMapper, TableNotes> implements TableNotesService {
    @Override
    public ArrayList<TableNotes> getByUserId(Long userId) {
        QueryWrapper<TableNotes> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        return (ArrayList<TableNotes>) baseMapper.selectList(queryWrapper);
    }
}
