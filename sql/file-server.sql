create table cxc_files
(
    id          bigint auto_increment comment 'Id

'
        primary key,
    name        varchar(255)  null comment '文件名称',
    type        varchar(255)  null comment '文件类型',
    size        bigint        null comment '文件大小',
    url         varchar(255)  null comment '链接',
    create_by   varchar(255)  null,
    create_time datetime      null,
    update_by   varchar(255)  null,
    update_time datetime      null,
    del_flag    int default 0 null comment '删除标志（0代表未删除，1代表已删除）'
)
    engine = InnoDB;

