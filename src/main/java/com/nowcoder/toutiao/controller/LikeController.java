package com.nowcoder.toutiao.controller;

import com.nowcoder.toutiao.model.EntityType;
import com.nowcoder.toutiao.model.HostHolder;
import com.nowcoder.toutiao.model.User;
import com.nowcoder.toutiao.service.LikeService;
import com.nowcoder.toutiao.service.NewsService;
import com.nowcoder.toutiao.utils.ToutiaoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class LikeController {
    @Autowired
    private LikeService likeService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private NewsService newsService;

    @RequestMapping(path = {"/like"},method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String Like(@RequestParam("newsId") int newsId){
        User user = hostHolder.getUser();
        long count = likeService.like(user.getId(), EntityType.ENTITY_NEWS,newsId);
        newsService.updateLikeCount(newsId,(int)count);
        return ToutiaoUtils.getJSONString(0,String.valueOf(count));
    }

    @RequestMapping(path = {"/dislike"},method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String disLike(@RequestParam("newsId") int newsId){
        User user = hostHolder.getUser();
        long count = likeService.disLike(user.getId(),EntityType.ENTITY_NEWS,newsId);
        newsService.updateLikeCount(newsId,(int)count);
        return ToutiaoUtils.getJSONString(0,String.valueOf(count));
    }
}
