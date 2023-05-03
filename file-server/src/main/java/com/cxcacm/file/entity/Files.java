package com.cxcacm.file.entity;


import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (Files)表实体类
 *
 * @author makejava
 * @since 2023-05-03 00:36:12
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("cxc_files")
public class Files  {
    //Id

    @TableId(type = IdType.AUTO)
    private Long id;
    //文件名称
    private String name;
    //文件类型
    private String type;
    //文件大小
    private Long size;
    //链接
    private String url;
    @TableField(fill = FieldFill.INSERT)
    private String createBy;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;
}
