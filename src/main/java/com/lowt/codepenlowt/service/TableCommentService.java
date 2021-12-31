package com.lowt.codepenlowt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lowt.codepenlowt.entity.TableComment;
import com.lowt.codepenlowt.vo.CommentVO;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author LOW_TASTE
 * @since 2021-12-04
 */
@Service
@EnableCaching
public interface TableCommentService extends IService<TableComment> {
/*    List<CommentVO> getSubComment(Long commentId, Long userId);

    List<CommentVO> getPreCommentList(Long userId);*/

    List<CommentVO> getCommentList(Long commentId, Long userId);
}
