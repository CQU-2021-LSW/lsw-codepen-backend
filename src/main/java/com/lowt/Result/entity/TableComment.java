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
public class TableComment implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "comment_id", type = IdType.AUTO)
      private Long commentId;

    private Long userId;

    private String commentText;

    private Integer topicId;

    private Long preCommentId;

    private LocalDateTime commentCreateTime;


}
