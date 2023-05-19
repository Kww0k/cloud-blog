package com.cxcacm.article.entity;

import java.util.Date;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 评论表(Comment)表实体类
 *
 * @author makejava
 * @since 2023-05-19 19:23:56
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("cxc_comment")
public class Comment  {
    @TableId
    private Long id;
    //文章id
    private Long articleId;
    //根评论id
    private Long rootId;
    //评论内容
    private String content;
    @TableField(fill = FieldFill.INSERT)
    private String createBy;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;



}
