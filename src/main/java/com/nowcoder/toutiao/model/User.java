package com.nowcoder.toutiao.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by nowcoder on 2016/6/26.
 */
@Getter
@Setter
public class User {
    private int id;
    private String name;
    private String password;
    private String salt;
    private String headUrl;

    public User() {

    }

    public User(String name) {
        this.name = name;
        this.password = "";
        this.salt = "";
        this.headUrl = "";
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", headUrl='" + headUrl + '\'' +
                '}';
    }
}
