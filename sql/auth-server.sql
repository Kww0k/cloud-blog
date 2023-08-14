create table oauth_user
(
    id          bigint auto_increment comment '用户id'
        primary key,
    username    varchar(255)                                                          null comment '用户名
',
    password    varchar(255)                                                          null comment '密码
',
    nickname    varchar(255) default '新用户胖子'                                     null comment '昵称',
    sex         varchar(2)   default '男'                                             null comment '性别',
    email       varchar(255)                                                          null comment '邮箱',
    create_time datetime                                                              null,
    update_time datetime                                                              null,
    del_flag    int          default 0                                                null comment '删除标志（0代表未删除，1代表已删除）',
    url         varchar(255) default 'https://img1.imgtp.com/2023/05/22/wPNvY0Zz.jpg' null comment '头像地址',
    constraint uni_email
        unique (email) comment '密码的唯一索引',
    constraint uni_name
        unique (username) comment '用户名的唯一索引'
)
    comment '用户信息表' engine = InnoDB;

-- comment on index uni_email not supported: 密码的唯一索引

-- comment on index uni_name not supported: 用户名的唯一索引

