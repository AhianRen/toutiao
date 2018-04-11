package com.nowcoder.toutiao.dao;

import com.nowcoder.toutiao.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDAO {

//    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
//            ") values (#{name},#{password},#{salt},#{headUrl})"})
    int addUser(User user);

     void updatePassword(User user);

     User selectUserById (int id);

    User selectUserByUserName(String userName);


}
