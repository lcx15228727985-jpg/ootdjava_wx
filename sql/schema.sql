-- 数据库脚本
CREATE DATABASE IF NOT EXISTS smart_fitting_room DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE smart_fitting_room;

-- 用户表
CREATE TABLE IF NOT EXISTS `user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `openid` VARCHAR(128) DEFAULT NULL COMMENT '微信openid',
    `nickname` VARCHAR(64) DEFAULT NULL COMMENT '昵称',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_openid` (`openid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 服装表
CREATE TABLE IF NOT EXISTS `garment` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `category` TINYINT NOT NULL COMMENT '分类: 0-上装 1-下装 2-裙装',
    `image_url` VARCHAR(512) DEFAULT NULL COMMENT '图片URL',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='服装表';

-- 试穿任务表
CREATE TABLE IF NOT EXISTS `try_on_task` (
    `id` VARCHAR(32) NOT NULL COMMENT '主键(UUID)',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `garment_id` BIGINT DEFAULT NULL COMMENT '服装ID',
    `model_image_url` VARCHAR(512) DEFAULT NULL COMMENT '模特图片URL',
    `status` TINYINT NOT NULL DEFAULT '0' COMMENT '状态: 0-处理中 1-成功 2-失败',
    `result_image_url` VARCHAR(512) DEFAULT NULL COMMENT '结果图片URL',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='试穿任务表';