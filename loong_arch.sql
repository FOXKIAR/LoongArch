CREATE DATABASE loong_arch;
\c loong_arch;

CREATE TABLE "user"
(
    id INT PRIMARY KEY,
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
    ADD FOREIGN KEY (user_id) REFERENCES "user" (id);

COMMENT ON TABLE "user" IS '用户表';
COMMENT ON COLUMN "user"."id" IS '用户ID';
COMMENT ON COLUMN "user"."name" IS '用户名';
COMMENT ON COLUMN "user"."account" IS '登录账号';
COMMENT ON COLUMN "user"."password" IS '登录密码，应存储为加密值';
COMMENT ON COLUMN "user"."permission" IS '权限，4位二进制 每一位代表一个权限';
COMMENT ON COLUMN "user"."email" IS '邮箱账号';
COMMENT ON COLUMN "user"."phone" IS '电话号码';

COMMENT ON TABLE patrol IS '检测记录表';
COMMENT ON COLUMN patrol.record_date IS '记录日期';
COMMENT ON COLUMN patrol.user_id IS '记录人ID，对应 user.id';
COMMENT ON COLUMN patrol.user_name IS '记录人姓名，对应 user.id';
COMMENT ON COLUMN patrol.is_normal IS '设备是否正常运行';
COMMENT ON COLUMN patrol.comment IS '设备非正常运行时的备注';

