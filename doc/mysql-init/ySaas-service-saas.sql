/*
 Navicat Premium Data Transfer

 Source Server         : exsi_5.7.24
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : 192.168.31.106:3306
 Source Schema         : ySaas-service-saas

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 29/03/2023 14:37:42
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for domain_store_rela
-- ----------------------------
DROP TABLE IF EXISTS `domain_store_rela`;
CREATE TABLE `domain_store_rela` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `pc_domain` varchar(32) DEFAULT NULL COMMENT 'PC端域名',
  `h5_domain` varchar(32) DEFAULT NULL COMMENT 'h5端域名',
  `store_id` bigint(20) DEFAULT NULL COMMENT '店铺Id',
  `company_info_id` int(11) DEFAULT NULL COMMENT '公司信息ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_person` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `update_person` varchar(32) DEFAULT NULL COMMENT '修改人',
  `del_flag` tinyint(4) DEFAULT '0' COMMENT '是否删除标志 0：否，1：是；默认0',
  `init_pc_domain` varchar(32) DEFAULT NULL COMMENT '初始化pc域名',
  `init_h5_domain` varchar(32) DEFAULT NULL COMMENT '初始化h5域名',
  `pem_file` varchar(200) DEFAULT NULL COMMENT 'pem证书路径',
  `key_file` varchar(200) DEFAULT NULL COMMENT 'key证书路径',
  `is_pc_full_domain` tinyint(1) DEFAULT NULL COMMENT 'pc是否使用自定义全域名 0：否，1：是',
  `is_h5_full_domain` varchar(200) DEFAULT NULL COMMENT 'h5是否使用自定义全域名 0：否，1：是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COMMENT='域名映射表';

-- ----------------------------
-- Records of domain_store_rela
-- ----------------------------
BEGIN;
INSERT INTO `domain_store_rela` VALUES (1, 'local.saas.sybx.com', 'local.saas.sybx.com', -1, -1, '2023-03-24 23:40:00', 'system_admin', '2023-03-24 23:40:00', 'system_admin', 0, '-1', '-1', NULL, NULL, 1, '1');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
