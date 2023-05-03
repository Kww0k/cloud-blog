package com.cxcacm.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cxcacm.article.entity.ResponseResult;
import com.cxcacm.article.entity.Tag;


/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2023-05-03 16:06:56
 */
public interface TagService extends IService<Tag> {

    ResponseResult getTagList();
}
