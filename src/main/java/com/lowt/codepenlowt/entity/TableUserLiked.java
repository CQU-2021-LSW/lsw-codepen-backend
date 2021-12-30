package com.lowt.codepenlowt.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author LOW_TASTE
 * @since 2021-12-23
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class TableUserLiked implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "liked_id", type = IdType.AUTO)
    private Long likedId;
    /**
     * 被点赞评论id
     */
    private Long likedCommentId;

    /**
     * 点赞用户id
     */
    private Long likedPostId;

    /**
     * 点赞状态，0取消，1点赞。默认为1
     */
    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;


    public TableUserLiked(Long likedCommentId, Long likedPostId, Integer value) {
        this.likedPostId = likedPostId;
        this.likedCommentId = likedCommentId;
        this.status = value;
    }
}
