package com.cxcacm.article.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 点赞表(ArticleLike)表实体类
 *
 * @author makejava
 * @since 2023-05-15 17:42:17
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("article_like")
public class ArticleLike  {
    //用户名@TableId
    private String username;
    //文章id@TableId
    private Long articleId;




}
