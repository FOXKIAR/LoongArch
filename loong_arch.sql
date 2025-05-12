CREATE DATABASE loong_arch;
USE loong_arch;

CREATE TABLE user(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(8) NOT NULL COMMENT '姓名',
    account VARCHAR(16) NOT NULL COMMENT '登录账号',
    password VARCHAR(32) NOT NULL COMMENT '登录密码的MD5值',
    permission INT NOT NULL DEFAULT 0 COMMENT '权限认证，存储为4位二进制。每一位分别对应拥有 POST、DELETE、PUT、GET 权限',
    email VARCHAR(32) COMMENT '邮箱',
    phone VARCHAR(11) COMMENT '电话号码'
) COMMENT '用户表';

CREATE TABLE patrol(
    record_date DATE PRIMARY KEY DEFAULT CURRENT_DATE() COMMENT '记录日期',
    user_id INT COMMENT '检测人ID',
    user_name VARCHAR(8) NOT NULL COMMENT '检测人姓名',
    is_normal BOOLEAN NOT NULL COMMENT '设备是否正常运行',
    comment TEXT DEFAULT '略' COMMENT '设备非正常运行时的备注'
) COMMENT '检测记录';

ALTER TABLE patrol
    ADD FOREIGN KEY (user_id) REFERENCES user(id);
