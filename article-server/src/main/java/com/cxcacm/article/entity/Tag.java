package com.cxcacm.article.entity;

import java.util.Date;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 标签(Tag)表实体类
 *
 * @author makejava
 * @since 2023-05-03 16:06:56
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("cxc_tag")
public class Tag  {
    @TableId
    private Long id;

    //标签名
    private String name;

    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;
    
    private Long parentId;



}
