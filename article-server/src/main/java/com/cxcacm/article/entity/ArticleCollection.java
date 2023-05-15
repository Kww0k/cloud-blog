package com.cxcacm.article.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
    @TableId
    private Long articleId;




}
