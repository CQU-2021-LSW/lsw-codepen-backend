package com.lowt.codepenlowt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lowt.codepenlowt.entity.TableUser;
import com.lowt.codepenlowt.mapper.TableUserMapper;
import com.lowt.codepenlowt.service.TableUserService;
import com.lowt.codepenlowt.vo.LoginInfoVO;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author LOW_TASTE
 * @since 2021-12-04
 */
@Service
public class TableUserServiceImpl extends ServiceImpl<TableUserMapper, TableUser> implements TableUserService {

    @Override
    public TableUser login(LoginInfoVO loginInfoVO) {
        QueryWrapper<TableUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", loginInfoVO.getUserName())
                .eq("password", loginInfoVO.getPassword());
        TableUser tableUser = baseMapper.selectOne(queryWrapper);
        if (tableUser != null) {
            if (tableUser.getUserName().equals(loginInfoVO.getUserName()) && tableUser.getPassword().equals(loginInfoVO.getPassword())) {
                return tableUser;
            } else {
                throw new RuntimeException("认证失败");
            }
        } else {
            throw new RuntimeException("认证失败");
        }
    }

    @Override
    public TableUser register(TableUser tableUser) {
        QueryWrapper<TableUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", tableUser.getUserName())
                .or()
                .eq("user_phone", tableUser.getUserPhone());
        // 用户名占用
        if (baseMapper.selectOne(queryWrapper) != null) {
            throw new RuntimeException("用户名占用");
        } else {
            baseMapper.insert(tableUser);
        }
        return tableUser;
    }

    @Override
    public void findBackPwd(TableUser tableUser) {
        QueryWrapper<TableUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_name", tableUser.getUserName())
                .eq("user_phone", tableUser.getUserPhone());
        TableUser dbResult = baseMapper.selectOne(queryWrapper);
        // 查到符合条件的用户 可以修改
        if (dbResult != null) {
            // 结果更换并存入
            dbResult.setPassword(tableUser.getPassword());
            baseMapper.updateById(dbResult);
        } else {
            // 查不到用户
            throw new RuntimeException("ERROR");
        }
    }

    @Override
    public void updateUserInfo(TableUser tableUser) {
        QueryWrapper<TableUser> queryWrapper = new QueryWrapper<>();
        // 不是这个id
        queryWrapper.ne("user_id", tableUser.getUserId());
        // 用户名和电话有一个已经存在就不能改
        queryWrapper.and(tableUserQueryWrapper -> {
            tableUserQueryWrapper.eq("user_name", tableUser.getUserName())
                    .or()
                    .eq("user_phone", tableUser.getUserPhone());
        });
        if (baseMapper.selectOne(queryWrapper) != null) {
            throw new RuntimeException("用户名或号码重复(_　_)zZ");
        }
        baseMapper.updateById(tableUser);
    }

    @Override
    public void updateUserPhoto(String userPhotoUrl, Long userId) {
        QueryWrapper<TableUser> queryWrapper = new QueryWrapper<>();
        TableUser tableUser = baseMapper.selectById(userId);
        tableUser.setUserPhoto(userPhotoUrl);
        baseMapper.updateById(tableUser);
    }
}
