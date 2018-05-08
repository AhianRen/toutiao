package com.nowcoder.toutiao.controller;

import com.nowcoder.toutiao.model.Message;
import com.nowcoder.toutiao.model.User;
import com.nowcoder.toutiao.model.ViewObject;
import com.nowcoder.toutiao.service.MessageService;
import com.nowcoder.toutiao.service.UserService;
import com.nowcoder.toutiao.utils.ToutiaoUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;

    @RequestMapping(path = {"/msg/detail"},method = {RequestMethod.GET})
    public String conversationDetail(Model model,@RequestParam("conversationId") String conversationId){

        List<Message> conversationList = messageService.getConversationDetail(conversationId, 0, 10);

            List<ViewObject> messages = new ArrayList<>();
            for (Message message:conversationList){
                ViewObject vo = new ViewObject();
                vo.set("message",message);
                User user = userService.getUser(message.getFromId());
                if (user == null){
                    continue;
                }
                vo.set("headUrl",user.getHeadUrl());
                vo.set("userName",user.getName());
                messages.add(vo);
            }
            model.addAttribute("messages",messages);
        return "letterDetail";
    }




    @RequestMapping(path = {"/msg/addMessage"},method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public String addMessage(@Param("fromId") int fromId, @Param("toId") int toId, @Param("content") String content){

        Message message = new Message();
        message.setFromId(fromId);
        message.setToId(toId);
        message.setContent(content);
        message.setCreatedDate(new Date());
        message.setConversationId(fromId<toId?String.format("%d_%d",fromId,toId):String.format("%d_%d",toId,fromId));

        messageService.addMessage(message);
        return ToutiaoUtils.getJSONString(message.getId());
    }

}
