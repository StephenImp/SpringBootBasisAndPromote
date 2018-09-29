/*
Navicat MySQL Data Transfer

Source Server         : 203
Source Server Version : 50719
Source Host           : 192.168.0.203:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2018-04-12 15:20:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for roles
-- ----------------------------
DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `r_id` int(11) NOT NULL AUTO_INCREMENT,
  `r_name` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `r_flag` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`r_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of roles
-- ----------------------------
INSERT INTO `roles` VALUES ('0', 'ROLE_ADMIN', '管理员');
INSERT INTO `roles` VALUES ('1', 'ROLE_SUPER_ADMIN', '超级管理员');
INSERT INTO `roles` VALUES ('2', 'ROLE_USER', '用户');

-- ----------------------------
-- Table structure for t_logger_infos
-- ----------------------------
DROP TABLE IF EXISTS `t_logger_infos`;
CREATE TABLE `t_logger_infos` (
  `ali_id` int(11) NOT NULL AUTO_INCREMENT,
  `ali_client_ip` varchar(30) DEFAULT NULL COMMENT '客户端请求IP地址',
  `ali_uri` varchar(100) DEFAULT NULL COMMENT '日志请求地址',
  `ali_type` varchar(50) DEFAULT NULL COMMENT '终端请求方式,普通请求,ajax请求',
  `ali_method` varchar(10) DEFAULT NULL COMMENT '请求方式method,post,get等',
  `ali_param_data` longtext COMMENT '请求参数内容,json',
  `ali_session_id` varchar(100) DEFAULT NULL COMMENT '请求接口唯一session标识',
  `ali_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '请求时间',
  `ali_returm_time` varchar(50) DEFAULT NULL COMMENT '接口返回时间',
  `ali_return_data` longtext COMMENT '接口返回数据json',
  `ali_http_status_code` varchar(10) DEFAULT NULL COMMENT '请求时httpStatusCode代码，如：200,400,404等',
  `ali_time_consuming` int(8) DEFAULT '0' COMMENT '请求耗时（毫秒单位）',
  PRIMARY KEY (`ali_id`)
) ENGINE=InnoDB AUTO_INCREMENT=106132 DEFAULT CHARSET=utf8 COMMENT='请求日志信息表';

-- ----------------------------
-- Records of t_logger_infos
-- ----------------------------
INSERT INTO `t_logger_infos` VALUES ('106119', '0:0:0:0:0:0:0:1', '/logger/login', null, 'GET', '{\"name\":[\"wpw\"]}', '7A566607D2C7C244653AF2C2E6836460', '2018-04-10 15:07:47', '1523344069869', '{\"msg\":\"用户：wpw，登录成功。\"}', '200', '3');
INSERT INTO `t_logger_infos` VALUES ('106120', '0:0:0:0:0:0:0:1', '/1.jpg', null, 'GET', '{}', '65C67C9BAB27D9F539913E63F37E5AA2', '2018-04-10 15:12:36', '1523344358709', 'null', '200', '15');
INSERT INTO `t_logger_infos` VALUES ('106121', '0:0:0:0:0:0:0:1', '/1.jpg', null, 'GET', '{}', '65C67C9BAB27D9F539913E63F37E5AA2', '2018-04-10 15:16:09', '1523344572613', 'null', '304', '32');
INSERT INTO `t_logger_infos` VALUES ('106122', '0:0:0:0:0:0:0:1', '/1.jpg', null, 'GET', '{}', '65C67C9BAB27D9F539913E63F37E5AA2', '2018-04-10 15:16:11', '1523344574124', 'null', '304', '2');
INSERT INTO `t_logger_infos` VALUES ('106123', '0:0:0:0:0:0:0:1', '/1.jpg', null, 'GET', '{}', '65C67C9BAB27D9F539913E63F37E5AA2', '2018-04-10 15:16:28', '1523344590878', 'null', '304', '5');
INSERT INTO `t_logger_infos` VALUES ('106124', '0:0:0:0:0:0:0:1', '/testStatic/resources/1.jpg', null, 'GET', '{}', '65C67C9BAB27D9F539913E63F37E5AA2', '2018-04-10 15:16:40', '1523344603522', 'null', '200', '39');
INSERT INTO `t_logger_infos` VALUES ('106125', '0:0:0:0:0:0:0:1', '/testStatic/resources/img/1.jpg', null, 'GET', '{}', '65C67C9BAB27D9F539913E63F37E5AA2', '2018-04-10 15:18:07', '1523344690382', 'null', '404', '3');
INSERT INTO `t_logger_infos` VALUES ('106126', '0:0:0:0:0:0:0:1', '/error', null, 'GET', '{}', '65C67C9BAB27D9F539913E63F37E5AA2', '2018-04-10 15:18:07', '1523344690516', 'null', '404', '70');
INSERT INTO `t_logger_infos` VALUES ('106127', '0:0:0:0:0:0:0:1', '/logger/name=cl', null, 'GET', '{}', '65C67C9BAB27D9F539913E63F37E5AA2', '2018-04-10 15:44:12', '1523346255573', 'null', '404', '1');
INSERT INTO `t_logger_infos` VALUES ('106128', '0:0:0:0:0:0:0:1', '/logger/name=cl', null, 'GET', '{}', '65C67C9BAB27D9F539913E63F37E5AA2', '2018-04-10 15:44:16', '1523346259068', 'null', '404', '2');
INSERT INTO `t_logger_infos` VALUES ('106129', '0:0:0:0:0:0:0:1', '/logger/name=cl', null, 'GET', '{}', '65C67C9BAB27D9F539913E63F37E5AA2', '2018-04-10 15:45:23', '1523346326234', 'null', '404', '3');
INSERT INTO `t_logger_infos` VALUES ('106130', '0:0:0:0:0:0:0:1', '/logger/name=cl', null, 'GET', '{}', '65C67C9BAB27D9F539913E63F37E5AA2', '2018-04-10 15:45:25', '1523346328308', 'null', '404', '2');
INSERT INTO `t_logger_infos` VALUES ('106131', '0:0:0:0:0:0:0:1', '/logger/name=cl', null, 'GET', '{}', '65C67C9BAB27D9F539913E63F37E5AA2', '2018-04-10 16:21:10', '1523348473006', 'null', '404', '2');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `t_id` int(11) NOT NULL AUTO_INCREMENT,
  `t_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `t_age` int(255) DEFAULT NULL,
  `t_address` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `t_pwd` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`t_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES ('5', 'zw', '18', 'gh', '111');
INSERT INTO `t_user` VALUES ('6', '测试', '21', '测试地址', null);

-- ----------------------------
-- Table structure for user_roles
-- ----------------------------
DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles` (
  `ur_id` int(11) NOT NULL AUTO_INCREMENT,
  `ur_user_id` int(11) DEFAULT NULL,
  `ur_role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`ur_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of user_roles
-- ----------------------------
INSERT INTO `user_roles` VALUES ('1', '1', '1');
INSERT INTO `user_roles` VALUES ('2', '2', '2');

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `u_id` int(11) NOT NULL AUTO_INCREMENT,
  `u_username` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `u_password` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`u_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users` VALUES ('1', 'admin', '$2a$10$TxEPU2iCQOK8yPlbIjOB5eHDo2dH1637iwaykjgJcifg4fGEDV3YG');
