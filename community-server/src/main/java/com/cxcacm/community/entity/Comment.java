package com.cxcacm.community.entity;

import java.util.Date;

import java.io.Serializable;

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
 * @since 2023-06-02 22:18:51
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("cxc_comment")
public class Comment  {
    @TableId
    private Long id;
    //论文id
    private Long communityId;
    //根评论id
    private Long rootId;
    //评论内容
    private String content;
    
    private String createBy;
    
    private Date createTime;
    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;
    
    private String commentTo;
    @TableField(exist = false)
    private String nickname;
    @TableField(exist = false)
    private String url;


}
