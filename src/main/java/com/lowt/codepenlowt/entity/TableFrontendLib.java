package com.lowt.codepenlowt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2021-12-07
 */
@Data
  @EqualsAndHashCode(callSuper = false)
  @Accessors(chain = true)
public class TableFrontendLib implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "lib_id", type = IdType.AUTO)
      private Long libId;

    private String libContent;

    private String libName;


}
