create table cxc_event
(
    id           bigint auto_increment
        primary key,
    content      text          null,
    label_name   varchar(255)  null,
    url          varchar(1000) null,
    introduction varchar(255)  null
)
    engine = InnoDB;

