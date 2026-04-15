CREATE DATABASE IF NOT EXISTS support_datahub;
USE support_datahub;

CREATE TABLE IF NOT EXISTS sd_user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(32),
    account VARCHAR(32),
    passwordHash VARCHAR(255),
    phone VARCHAR(16),
    email VARCHAR(32),
    is_admin BOOLEAN
);