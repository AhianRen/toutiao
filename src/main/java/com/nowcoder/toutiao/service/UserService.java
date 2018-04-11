package com.nowcoder.toutiao.service;

import com.nowcoder.toutiao.dao.LoginTicketDAO;
import com.nowcoder.toutiao.dao.UserDAO;
import com.nowcoder.toutiao.model.LoginTicket;
import com.nowcoder.toutiao.model.User;
import com.nowcoder.toutiao.utils.ToutiaoUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private LoginTicketDAO loginTicketDAO;

    public User getUser(int id){
        return userDAO.selectUserById(id);
    }

    public Map<String,Object> register(String userName, String password) {
        Map<String ,Object> map = new HashMap<>();
        if(StringUtils.isBlank(userName)){
            map.put("msgname","用户名不能为空");
            return map;
        }
        if (StringUtils.isBlank(password)){
            map.put("msgpwd","密码不能为空");
            return map;
        }
        User user = userDAO.selectUserByUserName(userName);
        if(user!= null){
            map.put("msgname","用户名已存在");
            return map;
        }
        user = new User();
        user.setName(userName);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        user.setPassword(ToutiaoUtils.MD5(password+user.getSalt()));
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png",new Random().nextInt(1000)));
        userDAO.addUser(user);
        String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;
    }


    public Map<String,Object> login(String userName, String password) {
        Map<String,Object> map = new HashMap<String,Object>();
        if(StringUtils.isBlank(userName)){
            map.put("msgname","用户名不能为空");
            return map;
        }
        if(StringUtils.isBlank(password)){
            map.put("msgpwd","密码不能为空");
            return map;
        }
        User user = userDAO.selectUserByUserName(userName);
        if(user == null){
            map.put("msgname","用户名不存在");
            return map;
        }
        if(!ToutiaoUtils.MD5(password+user.getSalt()).equals(user.getPassword())){
            map.put("msgpwd","密码错误");
            return map;
        }

        String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;
    }

    private String addLoginTicket(int userId) {
        LoginTicket loginTicket = new LoginTicket();
        Date date = new Date();
        date.setTime(new Date().getTime()+1000*3600*24);
        loginTicket.setExpired(date);
        loginTicket.setStatus(0);
        loginTicket.setTicket(UUID.randomUUID().toString().replace("-",""));
        loginTicket.setUserId(userId);
        loginTicketDAO.addTicket(loginTicket);
        return loginTicket.getTicket();
    }
}
