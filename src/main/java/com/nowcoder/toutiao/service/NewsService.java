package com.nowcoder.toutiao.service;

import com.nowcoder.toutiao.dao.NewsDAO;
import com.nowcoder.toutiao.model.News;
import com.nowcoder.toutiao.utils.ToutiaoUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class NewsService {
    @Autowired
    private NewsDAO newsDAO;


    public List<News> getLatestNews(int userId,int offset,int limit){
        return newsDAO.selectByUserIdAndOffset(userId,offset,limit);
    }

    public String saveImage(MultipartFile file) throws IOException {
        int lastIndex = file.getOriginalFilename().lastIndexOf(".");
        if (lastIndex < 0){
            return null;
        }
        String fileExt = file.getOriginalFilename().substring(lastIndex + 1).toLowerCase();
        if(!ToutiaoUtils.isFileAllowed(fileExt)){
            return null;
        }
        String fileName = UUID.randomUUID().toString().replace("-","") + "." + fileExt;
        Files.copy(file.getInputStream(),new File(ToutiaoUtils.IMAGE_DIR + fileName).toPath(),
                StandardCopyOption.REPLACE_EXISTING);
        return ToutiaoUtils.TOUTIAO_DOMAIN + "image?name=" + fileName;
    }

    public News getById(int newsId) {
        return newsDAO.getById(newsId);
    }

    public int addNews(News news){
        return newsDAO.addNews(news);
    }

    public int updateCommentCount(int newsId,int count){
        return newsDAO.updateCommentCount(newsId,count);
    }

    public void updateLikeCount(int newsId, int count) {
        newsDAO.updateLikeCount(newsId,count);
    }
}
