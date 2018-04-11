package com.nowcoder.toutiao.dao;

import com.nowcoder.toutiao.model.LoginTicket;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LoginTicketDAO {
    int addTicket(LoginTicket loginTicket);
    void updateStatus(@Param("ticket") String ticket,
                      @Param("status") int status);
    LoginTicket selectByTicket(String ticket);

}
