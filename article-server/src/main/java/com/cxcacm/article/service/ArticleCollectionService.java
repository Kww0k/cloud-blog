package com.cxcacm.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cxcacm.article.controller.dto.GiveLikeDto;
import com.cxcacm.article.entity.ArticleCollection;
import com.cxcacm.article.entity.ResponseResult;


/**
 * 收藏表(ArticleCollection)表服务接口
 *
 * @author makejava
 * @since 2023-05-15 17:42:10
 */
public interface ArticleCollectionService extends IService<ArticleCollection> {

    ResponseResult giveCollection(GiveLikeDto giveLikeDto);

    ResponseResult deleteCollection(GiveLikeDto giveLikeDto);

    ResponseResult getCollectionList(String username);
}
