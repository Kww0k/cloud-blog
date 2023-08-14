create table article_collection
(
    username   varchar(255) not null comment '用户名',
    article_id bigint       not null,
    primary key (article_id, username)
)
    comment '收藏表' engine = InnoDB;

create table article_like
(
    username   varchar(255) not null comment '用户名',
    article_id bigint       not null comment '文章id',
    primary key (username, article_id)
)
    comment '点赞表' engine = InnoDB;

create table article_tag
(
    article_id bigint auto_increment comment '文章id',
    tag        varchar(255) default '0' not null comment '标签',
    primary key (article_id, tag)
)
    comment '文章标签关联表' engine = InnoDB;

create table cxc_article
(
    id          bigint auto_increment
        primary key,
    title       varchar(256)       null comment '标题',
    content     longtext           null comment '文章内容',
    summary     varchar(1024)      null comment '文章摘要',
    thumbnail   varchar(256)       null comment '缩略图',
    is_top      char   default '0' null comment '是否置顶（0否，1是）',
    status      char   default '1' null comment '状态（0已发布，1草稿）',
    view_count  bigint default 0   null comment '访问量',
    is_comment  char   default '1' null comment '是否允许评论 1是，0否',
    create_by   varchar(255)       null,
    create_time datetime           null,
    update_by   varchar(255)       null,
    update_time datetime           null,
    del_flag    int    default 0   null comment '删除标志（0代表未删除，1代表已删除）'
)
    comment '文章表' engine = InnoDB;

create table cxc_comment
(
    id          bigint auto_increment
        primary key,
    article_id  bigint            null comment '文章id',
    root_id     bigint default -1 null comment '根评论id',
    content     varchar(512)      null comment '评论内容',
    create_by   varchar(255)      null,
    create_time datetime          null,
    del_flag    int    default 0  null comment '删除标志（0代表未删除，1代表已删除）',
    comment_to  varchar(255)      null
)
    comment '评论表' engine = InnoDB;

create table cxc_tag
(
    id          bigint auto_increment
        primary key,
    name        varchar(128)  null comment '标签名',
    create_by   varchar(255)  null,
    create_time datetime      null,
    update_by   varchar(255)  null,
    update_time datetime      null,
    del_flag    int default 0 null comment '删除标志（0代表未删除，1代表已删除）',
    remark      varchar(500)  null comment '备注',
    parent_id   bigint        null
)
    comment '标签' engine = InnoDB;

