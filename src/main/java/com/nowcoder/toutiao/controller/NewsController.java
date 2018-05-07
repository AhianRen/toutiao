package com.nowcoder.toutiao.controller;

import com.nowcoder.toutiao.model.HostHolder;
import com.nowcoder.toutiao.model.News;
import com.nowcoder.toutiao.service.NewsService;
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
import java.util.Date;

@Controller
public class NewsController {
    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    private NewsService newsService;
    @Autowired
    private HostHolder hostHolder;

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
        model.addAttribute(news);
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
