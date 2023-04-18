package com.cxcacm.commons.entity;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (User)表实体类
 *
 * @author makejava
 * @since 2023-04-18 17:30:05
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("oauth_user")
public class User  {
    @TableId
    private Long id;
    private String username;
    private String password;
}
