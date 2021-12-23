package com.lowt.Result.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author LOW_TASTE
 * @since 2021-12-23
 */
@Data
  @EqualsAndHashCode(callSuper = false)
  @Accessors(chain = true)
public class TableUserLiked implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "liked_id", type = IdType.AUTO)
      private Long likedId;

    private Long likedCommentId;

      /**
     * 点赞用户id
     */
      private Long likedPostId;

      /**
     * 点赞状态，0取消，1点赞。默认为1
     */
      private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}
