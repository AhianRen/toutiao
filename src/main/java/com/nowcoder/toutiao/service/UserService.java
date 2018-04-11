package com.nowcoder.toutiao.service;

import com.nowcoder.toutiao.dao.UserDAO;
import com.nowcoder.toutiao.model.User;
import com.nowcoder.toutiao.utils.ToutiaoUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

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

        return map;
    }



}
