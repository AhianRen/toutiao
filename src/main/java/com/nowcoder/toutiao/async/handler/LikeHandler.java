package com.nowcoder.toutiao.async.handler;

import com.nowcoder.toutiao.async.EventHandler;
import com.nowcoder.toutiao.async.EventModel;
import com.nowcoder.toutiao.async.EventType;
import com.nowcoder.toutiao.model.Message;
import com.nowcoder.toutiao.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
@Component
public class LikeHandler implements EventHandler{

    @Autowired
    private MessageService messageService;
    @Override
    public void doHandler(EventModel eventModel) {
        Message message = new Message();
        message.setFromId(2);//2:系统消息
        int toId = eventModel.getEntityOwnerId();
        message.setToId(toId);
        message.setCreatedDate(new Date());
        message.setConversationId(2<toId?String.format("%d_%d",2,toId):String.format("%d_%d",toId,2));
        message.setContent(eventModel.getExts(String.valueOf(eventModel.getActorId())) + "赞了你的资讯:http://127.0.0.1:8080/news/" + eventModel.getEntityId());
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportType() {
        return Arrays.asList(EventType.LIKE);
    }
}
