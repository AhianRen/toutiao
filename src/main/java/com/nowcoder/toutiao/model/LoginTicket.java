package com.nowcoder.toutiao.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class LoginTicket {
    private int id;
    private int userId;
    private Date expired;
    private int status;// 0有效，1无效
    private String ticket;

}
