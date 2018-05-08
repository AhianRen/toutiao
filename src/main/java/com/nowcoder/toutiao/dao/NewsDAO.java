package com.nowcoder.toutiao.dao;

import com.nowcoder.toutiao.model.News;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NewsDAO {

    int addNews(News news);
    List<News> selectByUserIdAndOffset(@Param("userId") int id,@Param("offset") int offset,@Param("limit") int limit);

    News getById(int newsId);

    int updateCommentCount(@Param("newsId") int newsId, @Param("count") int count);

}
