package com.cxcacm.user.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "oauth_user")
public class User {
    @Id
    private Long id;

    private String username;

    private String password;

    private String nickname;

    private String sex;

    private String email;
    private String createBy;
    @CreatedDate
    private Date createTime;

    private String updateBy;
    @LastModifiedDate
    private Date updateTime;

    private Integer delFlag;

    private String url;
}
