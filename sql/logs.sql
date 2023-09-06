CREATE DATABASE `logs`;

USE `logs`;

DROP TABLE IF EXISTS `logs_subject`;
CREATE TABLE `logs_subject` (
	`logs_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
	`description` VARCHAR(128) DEFAULT NULL COMMENT '操作描述',
	`username` VARCHAR(128) DEFAULT NULL COMMENT '操作用户',
	`start_time` VARCHAR(128) DEFAULT NULL COMMENT '操作时间',
	`spend_time` VARCHAR(128) DEFAULT NULL COMMENT '消耗时间',
	`url` VARCHAR(128) DEFAULT NULL COMMENT 'URL',
	`method_id` INT DEFAULT NULL COMMENT '请求类型表id',
	`ip` VARCHAR(128) DEFAULT NULL COMMENT 'IP地址',
	`parameter` TEXT DEFAULT NULL COMMENT '请求参数',
	`result` TEXT DEFAULT NULL COMMENT '请求返回的结果',	
	`device` TEXT DEFAULT NULL COMMENT '请求设备信息',
	`type_id` INT DEFAULT NULL COMMENT '日志类型表id',
	`error_id` INT DEFAULT NULL COMMENT '异常表id',
	PRIMARY KEY (`logs_id`)
) ENGINE=INNODB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT '日志数据表';


DROP TABLE IF EXISTS `logs_error`;
CREATE TABLE `logs_error`(
	`error_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
	`exception_name` VARCHAR(128) DEFAULT NULL COMMENT '异常名',
	`exception_msg` TEXT DEFAULT NULL COMMENT '异常信息',
	PRIMARY KEY(`error_id`)
) ENGINE=INNODB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT '异常日志表';


DROP TABLE IF EXISTS `method_type`;
CREATE TABLE `method_type` (
	`method_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
	`method` VARCHAR(64) UNIQUE NOT NULL COMMENT '请求类型名',
	PRIMARY KEY (`method_id`)
) ENGINE=INNODB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT '请求类型表';

INSERT INTO `method_type`(`method_id`, `method`) VALUES(1, 'get'),(2, 'post'),(3, 'put'),(4, 'delete');


DROP TABLE IF EXISTS `log_type`;
CREATE TABLE `log_type` (
	`type_id` INT(11) NOT NULL AUTO_INCREMENT COMMENT '主键id',
	`type` VARCHAR(64) UNIQUE NOT NULL COMMENT '日志类型',
	PRIMARY KEY (`type_id`)
) ENGINE=INNODB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT '日志类型表';

INSERT INTO `log_type`(`type_id`, `type`) VALUES(1, 'error'),(2, 'warning'),(3, 'info'),(4, 'debug');