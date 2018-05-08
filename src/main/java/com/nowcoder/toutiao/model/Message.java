package com.nowcoder.toutiao.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class Message {
    int id;
    int fromId;
    int toId;
    String content;
    Date createdDate;
    int hasRead;
    String conversationId;


}
