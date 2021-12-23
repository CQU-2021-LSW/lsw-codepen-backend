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
public class TableNotes implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "note_id", type = IdType.AUTO)
      private Long noteId;

    private Long userId;

    private String noteName;

    private String htmlCode;

    private String jsCode;

    private String cssCode;

    private LocalDateTime noteCreateTime;


}
