package com.cxcacm.auth.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "oauth_user")
public class User {
    @Id
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String sex;
    private String email;
    private Date createTime;
    private Date updateTime;
    private Integer delFlag;
    private String url;
}
