package com.nowcoder.toutiao;

import com.nowcoder.toutiao.dao.NewsDAO;
import com.nowcoder.toutiao.dao.UserDAO;
import com.nowcoder.toutiao.model.News;
import com.nowcoder.toutiao.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
//@Sql("/init-schema.sql")
public class InitDatabaseTests {
    @Autowired
    UserDAO userDAO;
    @Autowired
    NewsDAO newsDAO;

    Random random = new Random();
    @Test
    public void initData(){
       for (int i = 0; i < 10; i++){
//            User user = new User();
//            user.setName(String.format("User%d",i));
//            user.setPassword("");
//            user.setSalt("");
//            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
//
//          userDAO.addUser(user);
           News news = new News();
           news.setCommentCount(i);
           Date date = new Date();
           date.setTime(date.getTime() + 1000*3600*5*i);
           news.setCreatedDate(date);
           news.setImage(String.format("http://images.nowcoder.com/head/%dm.png", random.nextInt(1000)));
           news.setLikeCount(i+1);
           news.setUserId(i+1);
           news.setTitle(String.format("TITLE{%d}", i));
           news.setLink(String.format("http://www.nowcoder.com/%d.html", i));
           newsDAO.addNews(news);
       }
    }

    @Test
    public void testData(){
        User user = new User();
        user.setName(String.format("User%d",7));
        user.setPassword("7");
        user.setSalt("7");
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
        //userDAO.addUser(user);
      //  System.out.println(userDAO.selectUserById(10));
        System.out.println("===================");
        System.out.println(newsDAO.selectByUserIdAndOffset(1,0,4));
        System.out.println("===================");
    }




}
