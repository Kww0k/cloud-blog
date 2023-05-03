package com.cxcacm.file.entity;


import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("files")
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

}
