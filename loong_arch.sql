CREATE DATABASE loong_arch;
\c loong_arch;
SET search_path = "public";

CREATE TABLE person
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(8) NOT NULL,
    account VARCHAR(16) NOT NULL,
    password VARCHAR(32) NOT NULL,
    permission INT NOT NULL DEFAULT 0,
    email VARCHAR(32),
    phone VARCHAR(11)
);

CREATE TABLE patrol(
    record_date DATE PRIMARY KEY DEFAULT CURRENT_DATE,
    user_id INT,
    user_name VARCHAR(8) NOT NULL,
    is_normal BOOLEAN NOT NULL,
    comment TEXT DEFAULT '略'
);

ALTER TABLE patrol
    ADD FOREIGN KEY (user_id) REFERENCES "person" (id);

COMMENT ON TABLE "person" IS '用户表';
COMMENT ON COLUMN "person"."id" IS '用户ID';
COMMENT ON COLUMN "person"."name" IS '用户名';
COMMENT ON COLUMN "person"."account" IS '登录账号';
COMMENT ON COLUMN "person"."password" IS '登录密码，应存储为加密值';
COMMENT ON COLUMN "person"."permission" IS '权限，3位二进制 每一位代表一个权限';
COMMENT ON COLUMN "person"."email" IS '邮箱账号';
COMMENT ON COLUMN "person"."phone" IS '电话号码';

COMMENT ON TABLE patrol IS '检测记录表';
COMMENT ON COLUMN patrol.record_date IS '记录日期';
COMMENT ON COLUMN patrol.user_id IS '记录人ID，对应 person.id';
COMMENT ON COLUMN patrol.user_name IS '记录人姓名，对应 person.id';
COMMENT ON COLUMN patrol.is_normal IS '设备是否正常运行';
COMMENT ON COLUMN patrol.comment IS '设备非正常运行时的备注';

