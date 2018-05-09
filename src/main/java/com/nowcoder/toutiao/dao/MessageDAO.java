package com.nowcoder.toutiao.dao;

import com.nowcoder.toutiao.model.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MessageDAO {
    int addMessage(Message message);

    List<Message> getConversationDetail(@Param("conversationId") String conversationId, @Param("offset") int offset, @Param("limit") int limit);

    List<Message> getConversationList(@Param("userId") int userId,@Param("offset") int offset, @Param("limit") int limit);

    int getUnreadCount(@Param("userId") int userId, @Param("conversationId") String conversationId);
}
