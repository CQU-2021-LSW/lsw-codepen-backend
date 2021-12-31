package com.lowt.codepenlowt.vo;

import com.lowt.codepenlowt.entity.TableComment;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class CommentVO extends TableComment implements Serializable {

    private String userPhoto;

    private String userName;

    private Long likeCount;

    private Integer liked;

    private Long subCommentNum = 0L;

}
