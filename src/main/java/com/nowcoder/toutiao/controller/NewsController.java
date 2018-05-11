package com.nowcoder.toutiao.controller;

import com.nowcoder.toutiao.model.*;
import com.nowcoder.toutiao.service.CommentService;
import com.nowcoder.toutiao.service.LikeService;
import com.nowcoder.toutiao.service.NewsService;
import com.nowcoder.toutiao.service.UserService;
import com.nowcoder.toutiao.utils.ToutiaoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class NewsController {
    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    private NewsService newsService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private LikeService likeService;

    @RequestMapping(path = {"/addComment"},method = {RequestMethod.POST})
    public String addComment(@RequestParam("newsId") int newsId,
                            @RequestParam("content") String content){
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCreatedDate(new Date());
        comment.setStatus(0);
        comment.setUserId(hostHolder.getUser().getId());
        comment.setEntityId(newsId);
        comment.setEntityType(EntityType.ENTITY_NEWS);

        commentService.addComment(comment);

        int commentCount = commentService.getCommentCount(newsId, EntityType.ENTITY_NEWS);
        newsService.updateCommentCount(newsId,commentCount);
        return "redirect:/news/" + newsId;

    }

    @RequestMapping(path = {"/user/addNews/"},method = {RequestMethod.POST})
    @ResponseBody
    public String addNews(@RequestParam("image") String image,
                          @RequestParam("title") String title,
                          @RequestParam("link") String link){
        try {
            News news = new News();
            news.setImage(image);
            news.setTitle(title);
            news.setLink(link);
            news.setCreatedDate(new Date());
            if(hostHolder.getUser() != null){
                news.setUserId(hostHolder.getUser().getId());
            }else {
                news.setUserId(1);
            }
            newsService.addNews(news);
            return ToutiaoUtils.getJSONString(0,"咨询发布成功");
        } catch (Exception e) {
            logger.error("资讯添加失败"+ e.getMessage());
            return ToutiaoUtils.getJSONString(1,"资讯发布失败");
        }
    }

    @RequestMapping(path = {"/image"},method = {RequestMethod.GET})
    @ResponseBody
    public void getImage(@RequestParam("name") String imageName,
                         HttpServletResponse response){

        try {
            response.setContentType("image/jpeg");
            StreamUtils.copy(new FileInputStream(new File(ToutiaoUtils.IMAGE_DIR + imageName)),response.getOutputStream());
        } catch (IOException e) {
            logger.error("读取图片错误" + imageName + e.getMessage());
        }

    }



    @RequestMapping(path = {"/news/{newsId}"},method = {RequestMethod.GET,RequestMethod.POST})
    public String newsDetail (@PathVariable("newsId") int newsId, Model model){
        News news = newsService.getById(newsId);
        if (news != null){
            List<Comment> comments = commentService.getCommentsByEntity(newsId, EntityType.ENTITY_NEWS);
            List<ViewObject> commentVOs = new ArrayList<ViewObject>();
            int localUserId = hostHolder.getUser() != null ? hostHolder.getUser().getId():0;
            if(localUserId == 0){
               model.addAttribute("like",0);
            }else {
                model.addAttribute("like",likeService.getLikeStatus(localUserId, EntityType.ENTITY_NEWS,news.getId()));
            }
            for (Comment comment : comments){
                ViewObject vo = new ViewObject();
                vo.set("comment",comment);
                vo.set("user",userService.getUser(comment.getUserId()));
                commentVOs.add(vo);
            }
            model.addAttribute("comments",commentVOs);
        }
        model.addAttribute(news);
        model.addAttribute("owner", userService.getUser(news.getUserId()));
        return "detail";
    }


    @RequestMapping(path = {"/uploadImage/"},method = {RequestMethod.POST})
    @ResponseBody
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = newsService.saveImage(file);
            if (fileUrl == null) {
                return ToutiaoUtils.getJSONString(1, "图片上传失败");
            }
            return ToutiaoUtils.getJSONString(0,fileUrl);
        } catch (IOException e) {
            logger.error("图片上传失败" + e.getMessage());
            return ToutiaoUtils.getJSONString(1,"图片上传失败");
        }

    }




}
