package com.cxcacm.user.entity;

import java.util.Date;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 用户信息表(User)表实体类
 *
 * @author makejava
 * @since 2023-05-21 00:34:04
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("oauth_user")
public class User  {
    //用户id
    @TableId
    private Long id;
    //用户名
    private String username;
    //密码
    private String password;
    //昵称
    private String nickname;
    //性别
    private String sex;
    //邮箱
    private String email;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;
    //头像地址
    private String url;



}
