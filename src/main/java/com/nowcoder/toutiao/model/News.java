package com.nowcoder.toutiao.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by rainday on 16/6/30.
 */
@Getter
@Setter
public class News {

  private int id;

  private String title;

  private String link;

  private String image;

  private int likeCount;

  private int commentCount;

  private Date createdDate;

  private int userId;

  @Override
  public String toString() {
    return "News{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", userId=" + userId +
            '}';
  }
}