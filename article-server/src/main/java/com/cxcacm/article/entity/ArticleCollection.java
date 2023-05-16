package com.cxcacm.article.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 收藏表(ArticleCollection)表实体类
 *
 * @author makejava
 * @since 2023-05-15 17:42:10
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("article_collection")
public class ArticleCollection  {
    //用户名@TableId
    private String username;
    //@TableId
    private Long articleId;




}
