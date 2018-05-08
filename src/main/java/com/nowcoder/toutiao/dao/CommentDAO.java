package com.nowcoder.toutiao.dao;

import com.nowcoder.toutiao.model.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentDAO {
    int addComment(Comment comment);
    int getCommentCount(@Param("entityId") int entityId, @Param("entityType") int entityType);
    List<Comment> selectComment(@Param("entityId")int entityId,@Param("entityType")int entityType);
}
