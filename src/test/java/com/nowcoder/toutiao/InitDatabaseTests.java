package com.nowcoder.toutiao;

import com.nowcoder.toutiao.dao.CommentDAO;
import com.nowcoder.toutiao.dao.LoginTicketDAO;
import com.nowcoder.toutiao.dao.NewsDAO;
import com.nowcoder.toutiao.dao.UserDAO;
import com.nowcoder.toutiao.model.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
//@Sql("/init-schema.sql")
public class InitDatabaseTests {
    @Autowired
    UserDAO userDAO;
    @Autowired
    NewsDAO newsDAO;
    @Autowired
    LoginTicketDAO loginTicketDAO;
    @Autowired
    CommentDAO commentDAO;



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

    @Test
    public void testLoginTicket(){
        LoginTicket loginTicket = new LoginTicket();
        Date date = new Date();
        date.setTime(new Date().getTime()+1000*3600*24);
        loginTicket.setExpired(date);
        loginTicket.setStatus(0);
        loginTicket.setTicket(UUID.randomUUID().toString().replace("-",""));
        loginTicket.setUserId(1);
        loginTicketDAO.addTicket(loginTicket);
        loginTicketDAO.updateStatus(loginTicket.getTicket(),2);

        Assert.assertEquals(1,loginTicketDAO.selectByTicket(loginTicket.getTicket()).getUserId());
        Assert.assertEquals(2,loginTicketDAO.selectByTicket(loginTicket.getTicket()).getStatus());
    }

    @Test
    public void testCommentDAO(){
        /*
        for (int i = 0;i < 10;i++) {
            Comment comment = new Comment();
            comment.setUserId(i+1);
            comment.setContent("来自" + (i+1) + "的评论");
            Date date = new Date();
            date.setTime(new Date().getTime()+1000*3600*i);
            comment.setCreatedDate(date);
            comment.setStatus(0);
            comment.setEntityType(EntityType.ENTITY_NEWS);
            comment.setEntityId(i+1);
            commentDAO.addComment(comment);
        }
        */
        System.out.println("===============================================");
        System.out.println(commentDAO.getCommentCount(1,EntityType.ENTITY_NEWS));
        System.out.println(commentDAO.selectComment(2,EntityType.ENTITY_NEWS));
    }

    @Test
    public void testRedis(){
        Jedis jedis = new Jedis();
       // jedis.lpush("list1","xxx","xxx","yyy","zzz");
        List l = jedis.brpop(10,"list1");
        System.out.println(l.toString());
    }
}
