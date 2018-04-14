package com.nowcoder.toutiao.model;

import org.springframework.stereotype.Component;

@Component
public class HostHold {
    private static ThreadLocal<User> users = new ThreadLocal<User>();

    public void setUsers(User user){
        users.set(user);
    }

    public User getUser(){
        return users.get();
    }
    public void clear(){
        users.remove();
    }
}
