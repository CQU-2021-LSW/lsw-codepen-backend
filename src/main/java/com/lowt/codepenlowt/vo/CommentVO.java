package com.lowt.codepenlowt.vo;

import com.lowt.codepenlowt.entity.TableComment;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class CommentVO extends TableComment implements Serializable {

    String userName;

    Long likeCount;

    Integer liked;
    
}
