package com.cxcacm.article.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxcacm.article.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * 标签(Tag)表数据库访问层
 *
 * @author makejava
 * @since 2023-05-03 16:06:56
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {
}
