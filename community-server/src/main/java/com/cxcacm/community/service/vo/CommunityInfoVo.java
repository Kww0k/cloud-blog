package com.cxcacm.community.service.vo;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Silvery
 * @Date: 2023/6/12 22:35
 */
@Data
public class CommunityInfoVo {
    private Long id;
    //标题
    private String title;
    //文章内容
    private String content;
    //缩略图
    private String thumbnail;

    private String createBy;

    private Date createTime;

}
