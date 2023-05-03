package com.cxcacm.file.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxcacm.file.entity.Files;
import org.apache.ibatis.annotations.Mapper;


/**
 * (Files)表数据库访问层
 *
 * @author makejava
 * @since 2023-05-03 00:36:11
 */
@Mapper
public interface FilesMapper extends BaseMapper<Files> {

}
