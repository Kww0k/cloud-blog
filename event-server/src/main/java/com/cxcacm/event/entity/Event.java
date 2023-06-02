package com.cxcacm.event.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (Event)表实体类
 *
 * @author makejava
 * @since 2023-05-23 11:23:17
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("cxc_event")
public class Event  {
    @TableId
    private Long id;
    private String content;
    private String labelName;
    private String url;
    private String introduction;
}
