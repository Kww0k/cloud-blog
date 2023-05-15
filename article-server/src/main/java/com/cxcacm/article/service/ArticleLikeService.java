package com.cxcacm.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cxcacm.article.controller.dto.GiveLikeDto;
import com.cxcacm.article.entity.ArticleLike;
import com.cxcacm.article.entity.ResponseResult;


/**
 * 点赞表(ArticleLike)表服务接口
 *
 * @author makejava
 * @since 2023-05-15 17:42:17
 */
public interface ArticleLikeService extends IService<ArticleLike> {

    ResponseResult giveLike(GiveLikeDto giveLikeDto);

    ResponseResult deleteLike(GiveLikeDto giveLikeDto);

    ResponseResult getLikeList(String username);
}
