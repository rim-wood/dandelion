/*
Navicat MySQL Data Transfer

Source Server         : 46-3307
Source Server Version : 50722
Source Host           : 192.168.110.46:3307
Source Database       : cityplatform_uaa

Target Server Type    : MYSQL
Target Server Version : 50722
File Encoding         : 65001

Date: 2019-05-30 10:56:11
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
  `dept_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '机构-部门 id',
  `dept_name` varchar(100) NOT NULL COMMENT '机构名称',
  `parent_id` int(11) NOT NULL COMMENT '父类编号',
  `dept_type` varchar(10) DEFAULT NULL COMMENT '机构类型(HOSPITAL-医院、STATION-血站、GOVERMENT-卫健委)',
  `sort_order` int(5) NOT NULL COMMENT '排序',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updator` varchar(50) DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识(0-正常，1-删除)',
  PRIMARY KEY (`dept_id`),
  UNIQUE KEY `name` (`dept_name`) USING BTREE COMMENT '名称唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='机构部门表';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dept_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept_role`;
CREATE TABLE `sys_dept_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '机构-角色映射表',
  `dept_id` int(11) NOT NULL COMMENT '机构编号',
  `role_id` int(11) NOT NULL COMMENT '角色编号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `dept_role` (`dept_id`,`role_id`) USING BTREE COMMENT '机构-角色唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='机构-角色映射表';

-- ----------------------------
-- Records of sys_dept_role
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` int(64) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `value` varchar(100) NOT NULL COMMENT '数据值',
  `label` varchar(100) NOT NULL COMMENT '标签名',
  `type` varchar(100) NOT NULL COMMENT '类型',
  `description` varchar(100) NOT NULL COMMENT '描述',
  `sort` int(10) NOT NULL COMMENT '排序（升序）',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注信息',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',
  PRIMARY KEY (`id`),
  KEY `sys_dict_value` (`value`),
  KEY `sys_dict_label` (`label`),
  KEY `sys_dict_del_flag` (`del_flag`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='字典表';

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
INSERT INTO `sys_dict` VALUES ('1', '9', '异常', 'log_type', '日志异常', '1', '2018-07-09 06:16:14', '2018-11-24 07:25:11', '日志异常', '0');
INSERT INTO `sys_dict` VALUES ('2', '0', '正常', 'log_type', '正常', '0', '2018-07-09 06:15:40', '2018-11-24 07:25:14', '正常', '0');

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `type` char(1) DEFAULT '1' COMMENT '日志类型',
  `title` varchar(255) DEFAULT '' COMMENT '日志标题',
  `service_id` varchar(32) DEFAULT NULL COMMENT '服务ID',
  `creator` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `remote_addr` varchar(255) DEFAULT NULL COMMENT '操作IP地址',
  `user_agent` varchar(1000) DEFAULT NULL COMMENT '用户代理',
  `request_uri` varchar(255) DEFAULT NULL COMMENT '请求URI',
  `method` varchar(10) DEFAULT NULL COMMENT '操作方式',
  `params` text COMMENT '操作提交的数据',
  `time` mediumtext COMMENT '执行时间',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标记',
  `exception` text COMMENT '异常信息',
  PRIMARY KEY (`id`),
  KEY `sys_log_create_by` (`creator`),
  KEY `sys_log_request_uri` (`request_uri`),
  KEY `sys_log_type` (`type`),
  KEY `sys_log_create_date` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='日志表';

-- ----------------------------
-- Records of sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '菜单id',
  `parent_id` int(11) NOT NULL COMMENT '父菜单ID，一级菜单为0',
  `menu_name` varchar(100) NOT NULL COMMENT '菜单名称',
  `menu_type` int(2) NOT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `path` varchar(500) DEFAULT NULL COMMENT '前端菜单路径',
  `component` varchar(255) DEFAULT NULL COMMENT '前端组件',
  `icon` varchar(50) CHARACTER SET utf8 DEFAULT NULL COMMENT '菜单图标',
  `permission` varchar(255) DEFAULT NULL COMMENT '权限标识(多个用逗号分隔，如：user_list,user_create)',
  `sort_order` int(11) DEFAULT NULL COMMENT '排序',
  `keep_alive` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0-开启，1- 关闭',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updator` varchar(50) DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识(0-正常，1-删除)',
  PRIMARY KEY (`menu_id`),
  UNIQUE KEY `name_path` (`path`,`menu_name`) USING BTREE COMMENT '菜单唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统菜单表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------

-- ----------------------------
-- Table structure for sys_oauth_client_details
-- ----------------------------
DROP TABLE IF EXISTS `sys_oauth_client_details`;
CREATE TABLE `sys_oauth_client_details` (
  `client_id` varchar(32) NOT NULL COMMENT '客户端id',
  `resource_ids` varchar(256) DEFAULT NULL,
  `client_secret` varchar(256) DEFAULT NULL COMMENT '客户端安全码',
  `scope` varchar(256) DEFAULT NULL COMMENT '限制客户端的访问范围',
  `authorized_grant_types` varchar(256) DEFAULT NULL COMMENT '授权类型password,refresh_token,authorization_code,client_credentials',
  `web_server_redirect_uri` varchar(256) DEFAULT NULL,
  `authorities` varchar(256) DEFAULT NULL COMMENT '此客户端可以使用的权限',
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=DYNAMIC COMMENT='终端信息表';

-- ----------------------------
-- Records of sys_oauth_client_details
-- ----------------------------
INSERT INTO `sys_oauth_client_details` VALUES ('app', null, 'app', 'server', 'password,refresh_token', null, null, null, null, null, 'true');
INSERT INTO `sys_oauth_client_details` VALUES ('daemon', null, 'daemon', 'server', 'password,refresh_token', null, null, null, null, null, 'true');
INSERT INTO `sys_oauth_client_details` VALUES ('gen', null, 'gen', 'server', 'password,refresh_token', null, null, null, null, null, 'true');
INSERT INTO `sys_oauth_client_details` VALUES ('hos', '', 'hos', 'server', 'password,refresh_token,authorization_code,client_credentials', 'http://localhost:8001/sso/login', null, null, null, null, 'true');
INSERT INTO `sys_oauth_client_details` VALUES ('test', null, 'test', 'server', 'password,refresh_token', null, null, null, null, null, 'true');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '角色id',
  `role_name` varchar(100) NOT NULL COMMENT '角色名称',
  `role_code` varchar(100) NOT NULL COMMENT '角色代码',
  `role_desc` varchar(255) NOT NULL COMMENT '角色描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updator` varchar(50) DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识(0-正常，1-删除)',
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `name` (`role_name`) USING BTREE COMMENT '角色名称唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统角色表';

-- ----------------------------
-- Records of sys_role
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` int(11) NOT NULL COMMENT '角色-菜单id',
  `role_id` int(11) NOT NULL COMMENT '角色id',
  `menu_id` int(11) NOT NULL COMMENT '菜单id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_menu` (`role_id`,`menu_id`) USING BTREE COMMENT '角色菜单唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL,
  `user_name` varchar(60) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL,
  `salt` varchar(20) DEFAULT NULL COMMENT '盐',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  `dept_id` int(11) NOT NULL,
  `lock_flag` tinyint(1) DEFAULT '0' COMMENT '锁定状态0-正常，1-锁定',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `creator` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updator` varchar(50) DEFAULT NULL COMMENT '更新人',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识(0-正常，1-删除)',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name` (`user_name`) USING BTREE COMMENT '用户名唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'rim', '$2a$10$RpFJjxYiXdEsAGnWp/8fsOetMuOON96Ntk/Ym2M/RKRyU0GZseaDC', 'rim', null, null, null, '1', '0', null, null, '2019-05-28 15:43:02', null, '0');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `role_user` (`role_id`,`user_id`) USING BTREE COMMENT '角色用户唯一索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
