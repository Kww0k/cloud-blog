package com.cxcacm.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cxcacm.user.entity.User;
import org.apache.ibatis.annotations.Mapper;


/**
 * 用户信息表(User)表数据库访问层
 *
 * @author makejava
 * @since 2023-05-21 00:34:03
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
