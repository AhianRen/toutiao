package com.nowcoder.toutiao.controller;

import com.nowcoder.toutiao.service.NewsService;
import com.nowcoder.toutiao.utils.ToutiaoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class NewsController {
    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    private NewsService newsService;
    @RequestMapping(path = {"/uploadImage/"},method = {RequestMethod.POST})
    @ResponseBody
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            if (newsService.saveImage(file) == null) {
                return ToutiaoUtils.getJSONString(1, "图片上传失败");
            }
            return ToutiaoUtils.getJSONString(0, "图片上传成功");
        } catch (IOException e) {
            logger.error("图片上传失败" + e.getMessage());
            return ToutiaoUtils.getJSONString(1,"图片上传失败");
        }

    }


}
