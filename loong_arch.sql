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

COMMENT ON TABLE "person" IS '用户表';
COMMENT ON COLUMN "person"."id" IS '用户ID';
COMMENT ON COLUMN "person"."name" IS '用户名';
COMMENT ON COLUMN "person"."account" IS '登录账号';
COMMENT ON COLUMN "person"."password" IS '登录密码，应存储为加密值';
COMMENT ON COLUMN "person"."permission" IS '权限，3位二进制 每一位代表一个权限';
COMMENT ON COLUMN "person"."email" IS '邮箱账号';
COMMENT ON COLUMN "person"."phone" IS '电话号码';

CREATE TABLE patrol(
    record_date DATE PRIMARY KEY DEFAULT CURRENT_DATE,
    person_id INT NOT NULL,
    person_name VARCHAR(8) NOT NULL,
    is_normal BOOLEAN NOT NULL,
    comment TEXT DEFAULT '略'
);

ALTER TABLE patrol
    ADD FOREIGN KEY (person_id) REFERENCES "person" (id);

COMMENT ON TABLE patrol IS '检测记录表';
COMMENT ON COLUMN patrol.record_date IS '记录日期';
COMMENT ON COLUMN patrol.person_id IS '记录人ID，对应 person.id';
COMMENT ON COLUMN patrol.person_name IS '记录人姓名，对应 person.id';
COMMENT ON COLUMN patrol.is_normal IS '设备是否正常运行';
COMMENT ON COLUMN patrol.comment IS '设备非正常运行时的备注';


CREATE TABLE request_log(
    occurrence_time TIME PRIMARY KEY DEFAULT CURRENT_TIME,
    method VARCHAR(8) NOT NULL,
    path VARCHAR(16) NOT NULL,
    response VARCHAR(8) NOT NULL,
    failure_reason VARCHAR(16) NOT NULL
);

COMMENT ON TABLE request_log IS '请求日志';
COMMENT ON COLUMN request_log.occurrence_time IS '发生时间';
COMMENT ON COLUMN request_log.method IS '请求方式';
COMMENT ON COLUMN request_log.path IS '接口路径';
COMMENT ON COLUMN request_log.response IS '响应结果';
COMMENT ON COLUMN request_log.failure_reason IS '失败原因';


CREATE TABLE system_log(
    occurrence_time TIME PRIMARY KEY DEFAULT CURRENT_TIME,
    level VARCHAR(8) NOT NULL,
    message VARCHAR(256) NOT NULL,
    reason VARCHAR(16) NOT NULL
);

COMMENT ON TABLE system_log IS '系统日志';
COMMENT ON COLUMN system_log.occurrence_time IS '发生时间';
COMMENT ON COLUMN system_log.level IS '级别';
COMMENT ON COLUMN system_log.message IS '日志信息';
COMMENT ON COLUMN system_log.reason IS '可能是什么原因导致的';



