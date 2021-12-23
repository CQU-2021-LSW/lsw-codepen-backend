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
public class TableUser implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "user_id", type = IdType.AUTO)
      private Long userId;

    private String userName;

    private String password;

    private String userPhone;

    private Integer userGender;

    private String userMotto;

    private LocalDateTime userCreateTime;

    private String userAddr;

    private String userEmail;

    private LocalDateTime userUpdateTime;

    private String userPhoto;


}
