package com.cxcacm.community.service.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;

import java.util.Date;

/**
 * @Author: Silvery
 * @Date: 2023/6/12 22:35
 */
public class CommunityInfoVo {
    private Long id;
    //标题
    private String title;
    //文章内容
    private String content;
    //缩略图
    private String thumbnail;

    private String createBy;

}
