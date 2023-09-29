create table sys_user
(
    user_id     bigint                          not null comment '用户ID'
        primary key,
    dept_id     bigint                          null comment '部门ID',
    user_name   varchar(30)                     not null comment '用户账号',
    nick_name   varchar(30)                     not null comment '用户昵称',
    user_type   varchar(10)  default 'sys_user' null comment '用户类型（sys_user系统用户）',
    email       varchar(50)  default ''         null comment '用户邮箱',
    phonenumber varchar(11)  default ''         null comment '手机号码',
    sex         char         default '0'        null comment '用户性别（0男 1女 2未知）',
    avatar      varchar(100) default ''         null comment '头像地址',
    password    varchar(100) default ''         null comment '密码',
    status      char         default '0'        null comment '帐号状态（0正常 1停用）',
    del_flag    char         default '0'        null comment '删除标志（0代表存在 2代表删除）',
    login_ip    varchar(128) default ''         null comment '最后登录IP',
    login_date  datetime                        null comment '最后登录时间',
    create_by   varchar(64)  default ''         null comment '创建者',
    create_time datetime                        null comment '创建时间',
    update_by   varchar(64)  default ''         null comment '更新者',
    update_time datetime                        null comment '更新时间',
    remark      varchar(500)                    null comment '备注'
)
    comment '用户信息表';

INSERT INTO `ry-vue`.sys_user (user_id, dept_id, user_name, nick_name, user_type, email, phonenumber, sex, avatar, password, status, del_flag, login_ip, login_date, create_by, create_time, update_by, update_time, remark) VALUES (1, 103, 'admin', '疯狂的狮子Li', 'sys_user', 'crazyLionLi@163.com', '15888888888', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', '2023-09-28 13:42:24', 'admin', '2023-09-28 13:42:24', '', null, '管理员');
INSERT INTO `ry-vue`.sys_user (user_id, dept_id, user_name, nick_name, user_type, email, phonenumber, sex, avatar, password, status, del_flag, login_ip, login_date, create_by, create_time, update_by, update_time, remark) VALUES (2, 103, '张翼德', '疯狂的狮子Li', 'sys_user', 'crazyLionLi@163.com', '15888888888', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', '2023-09-28 13:42:24', 'admin', '2023-09-28 13:42:24', '', null, '管理员');
INSERT INTO `ry-vue`.sys_user (user_id, dept_id, user_name, nick_name, user_type, email, phonenumber, sex, avatar, password, status, del_flag, login_ip, login_date, create_by, create_time, update_by, update_time, remark) VALUES (3, 103, '貂蝉', '疯狂的狮子Li', 'sys_user', 'crazyLionLi@163.com', '15888888888', '0', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', '2023-09-28 13:42:24', 'admin', '2023-09-28 13:42:24', '', null, '管理员');
INSERT INTO `ry-vue`.sys_user (user_id, dept_id, user_name, nick_name, user_type, email, phonenumber, sex, avatar, password, status, del_flag, login_ip, login_date, create_by, create_time, update_by, update_time, remark) VALUES (4, 105, '吕布', '疯狂的狮子Li', 'sys_user', 'crazyLionLi@qq.com', '15666666666', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', '2023-09-28 13:42:24', 'admin', '2023-09-28 13:42:24', '', null, '测试员');
