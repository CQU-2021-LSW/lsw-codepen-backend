package com.lowt.codepenlowt.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 
 * </p>
 *
 * @author LOW_TASTE
 * @since 2021-12-04
 */
@Data
  @EqualsAndHashCode(callSuper = false)
  @Accessors(chain = true)
public class TableComment implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "comment_id", type = IdType.AUTO)
    private Long commentId;

    private Long userId;

    private String commentText;

    private Long likes;

    private Integer topicId;

    private Long preCommentId;

    @TableField(fill = FieldFill.INSERT)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime commentCreateTime;

}
