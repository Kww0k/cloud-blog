package com.cxcacm.user.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "oauth_user")
public class User {
    @Id
    private Long id;

    private String username;

    private String password;

    private String nickname;
}
