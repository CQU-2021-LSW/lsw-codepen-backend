package com.lowt.codepenlowt.vo;

import com.lowt.codepenlowt.entity.TableComment;
import lombok.Data;

import java.io.Serializable;

@Data
public class CommentVO extends TableComment implements Serializable{
    String userName;
}
