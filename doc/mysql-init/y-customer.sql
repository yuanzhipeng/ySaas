/*
 Navicat Premium Data Transfer

 Source Server         : exsi_5.7.24
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : 192.168.31.106:3306
 Source Schema         : y-customer

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 25/03/2023 23:54:58
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for company_info
-- ----------------------------
DROP TABLE IF EXISTS `company_info`;
CREATE TABLE `company_info` (
  `company_info_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '公司信息ID',
  `company_name` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '公司名称',
  `back_ID_card` varchar(1024) DEFAULT NULL COMMENT '法人身份证反面',
  `province_id` bigint(10) DEFAULT NULL COMMENT '省',
  `city_id` bigint(10) DEFAULT NULL COMMENT '市',
  `area_id` bigint(10) DEFAULT NULL COMMENT '区',
  `street_id` bigint(10) DEFAULT NULL COMMENT '街道id',
  `detail_address` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '详细地址',
  `contact_name` text COMMENT '联系人',
  `contact_phone` text COMMENT '联系方式',
  `copyright` varchar(255) CHARACTER SET utf8 DEFAULT NULL COMMENT '版权信息',
  `company_descript` text COMMENT '公司简介',
  `operator` varchar(45) CHARACTER SET utf8 DEFAULT NULL COMMENT '操作人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `company_type` tinyint(4) DEFAULT NULL COMMENT '商家类型 0、平台自营 1、第三方商家',
  `social_credit_code` varchar(50) DEFAULT NULL COMMENT '社会信用代码',
  `address` varchar(255) DEFAULT NULL COMMENT '住所',
  `legal_representative` varchar(150) DEFAULT NULL COMMENT '法定代表人',
  `registered_capital` decimal(12,2) DEFAULT NULL COMMENT '注册资本',
  `found_date` datetime DEFAULT NULL COMMENT ' 成立日期',
  `business_term_start` datetime DEFAULT NULL COMMENT '营业期限自',
  `business_term_end` datetime DEFAULT NULL COMMENT '营业期限至',
  `business_scope` varchar(1024) DEFAULT NULL COMMENT '经营范围',
  `business_licence` varchar(1024) DEFAULT NULL COMMENT '营业执照副本电子版',
  `front_ID_card` varchar(1024) DEFAULT NULL COMMENT '法人身份证正面',
  `company_code` varchar(32) DEFAULT NULL COMMENT '商家编号',
  `supplier_name` varchar(50) DEFAULT NULL COMMENT '商家名称',
  `apply_enter_time` datetime DEFAULT NULL COMMENT '入驻时间(第一次审核通过时间)',
  `store_type` tinyint(4) DEFAULT NULL COMMENT '商家类型0供应商，1商家',
  `company_source_type` tinyint(1) DEFAULT '0' COMMENT '商家来源类型 0:入驻 ',
  `delete_flag` tinyint(4) DEFAULT NULL COMMENT '是否删除 0 否  1 是',
  PRIMARY KEY (`company_info_id`),
  KEY `idx_company_type` (`company_type`)
) ENGINE=InnoDB AUTO_INCREMENT=12345679 DEFAULT CHARSET=utf8mb4 COMMENT='公司信息';

-- ----------------------------
-- Records of company_info
-- ----------------------------
BEGIN;
INSERT INTO `company_info` VALUES (-1, '公司名称1', '[{\"lastModified\":1506752290000,\"lastModifiedDate\":\"2017-09-30T06:18:10.000Z\",\"name\":\"122.jpg\",\"size\":33373,\"type\":\"image/jpeg\",\"uid\":\"rc-upload-1510902118991-8\",\"percent\":100,\"originFileObj\":{\"uid\":\"rc-upload-1510902118991-8\"},\"status\":\"done\",\"response\":[\"https://wanmi-b2b.oss-cn-shanghai.aliyuncs.com/201711171502368366\"],\"thumbUrl\":\"https://wanmi-b2b.oss-cn-shanghai.aliyuncs.com/201711171502368366\"}]', 2, NULL, NULL, NULL, NULL, '测试数据', NULL, NULL, NULL, '', '2017-12-01 10:35:49', '2017-11-25 10:09:08', NULL, NULL, '123456789012344', '12', 123.00, NULL, '2017-11-08 00:00:00', '2017-11-18 00:00:00', '什么都卖122233333', '[{\"lastModified\":1508839111000,\"lastModifiedDate\":\"2017-10-24T09:58:31.000Z\",\"name\":\"1024.png\",\"size\":51615,\"type\":\"image/png\",\"uid\":\"rc-upload-1511418994148-4\",\"percent\":100,\"originFileObj\":{\"uid\":\"rc-upload-1511418994148-4\"},\"status\":\"done\",\"response\":[\"https://wanmi-b2b.oss-cn-shanghai.aliyuncs.com/201711231436404678\"],\"thumbUrl\":\"https://wanmi-b2b.oss-cn-shanghai.aliyuncs.com/201711231436404678\"}]', '[{\"uid\":\"rc-upload-1504848759548-9\",\"status\":\"done\",\"url\":\"https://wanmi-b2b.oss-cn-shanghai.aliyuncs.com/201709081335501812\"}]', '00002', '测试公司', '2017-09-12 19:19:27', NULL, 0, 0);
COMMIT;

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `customer_id` varchar(32) NOT NULL COMMENT '会员标识UUID',
  `customer_account` varchar(20) NOT NULL COMMENT '会员登录账号|手机号',
  `customer_password` varchar(200) NOT NULL COMMENT '会员登录密码',
  `safe_level` tinyint(4) DEFAULT NULL COMMENT '密码安全等级：20危险 40低、60中、80高',
  `customer_salt_val` varchar(200) DEFAULT NULL COMMENT '盐值,用于密码加密',
  `check_state` tinyint(4) NOT NULL COMMENT '账户的审核状态 0:待审核 1:已审核通过 2:审核未通过',
  `check_time` datetime DEFAULT NULL COMMENT '账户的审核时间',
  `login_ip` varchar(32) DEFAULT '0.0.0.0' COMMENT '登录IP',
  `login_time` datetime DEFAULT NULL COMMENT '登录时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间|注册时间',
  `create_person` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_person` varchar(32) DEFAULT NULL COMMENT '修改人',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `delete_person` varchar(32) DEFAULT NULL COMMENT '删除人',
  `login_lock_time` datetime DEFAULT NULL COMMENT '登录锁定时间',
  `store_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '店铺Id',
  `company_info_id` bigint(20) DEFAULT '-1' COMMENT '公司Id',
  `delete_flag` tinyint(4) NOT NULL COMMENT '是否删除标志 0：否，1：是',
  PRIMARY KEY (`customer_id`),
  KEY `idx_customer_account` (`customer_account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户信息表';

-- ----------------------------
-- Records of customer
-- ----------------------------
BEGIN;
INSERT INTO `customer` VALUES ('1111', '13681441600', '123456', 0, '123456', 1, NULL, '0.0.0.0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, -1, -1, 0);
COMMIT;

-- ----------------------------
-- Table structure for customer_detail
-- ----------------------------
DROP TABLE IF EXISTS `customer_detail`;
CREATE TABLE `customer_detail` (
  `customer_detail_id` varchar(32) NOT NULL COMMENT '会员详细信息标识UUID',
  `customer_id` varchar(32) NOT NULL COMMENT '会员ID',
  `customer_name` varchar(128) DEFAULT NULL COMMENT '会员名称',
  `province_id` bigint(10) DEFAULT NULL COMMENT '省份',
  `city_id` bigint(10) DEFAULT NULL COMMENT '市',
  `area_id` bigint(10) DEFAULT NULL COMMENT '区',
  `street_id` bigint(10) DEFAULT NULL COMMENT '街道',
  `customer_address` varchar(225) DEFAULT NULL COMMENT '详细街道地址',
  `delete_flag` tinyint(4) DEFAULT NULL COMMENT '是否删除标志 0：否，1：是',
  `customer_status` tinyint(4) DEFAULT NULL COMMENT '账号状态 0：启用中  1：禁用中',
  `contact_name` varchar(128) DEFAULT NULL COMMENT '联系人名字',
  `contact_phone` varchar(20) DEFAULT NULL COMMENT '联系方式',
  `employee_id` varchar(32) DEFAULT NULL COMMENT '负责业务员',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_person` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_person` varchar(32) DEFAULT NULL COMMENT '修改人',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `delete_person` varchar(32) DEFAULT NULL COMMENT '删除人',
  `reject_reason` varchar(256) DEFAULT NULL COMMENT '审核驳回原因',
  `forbid_reason` varchar(256) DEFAULT NULL COMMENT '禁用原因',
  `is_distributor` tinyint(2) DEFAULT '0' COMMENT '是否为分销员 0：否 1：是',
  `birth_day` date DEFAULT NULL COMMENT '生日',
  `gender` tinyint(1) DEFAULT NULL COMMENT '0女，1男，2保密',
  `is_employee` tinyint(1) DEFAULT '0' COMMENT '是否为员工 0：否 1：是',
  `store_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '店铺Id',
  `company_info_id` bigint(20) DEFAULT '-1' COMMENT '公司Id',
  PRIMARY KEY (`customer_detail_id`),
  KEY `idx_customer_id` (`customer_id`),
  KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='客户详细信息';

-- ----------------------------
-- Records of customer_detail
-- ----------------------------
BEGIN;
INSERT INTO `customer_detail` VALUES ('1111', '1111', 'hebi', NULL, NULL, NULL, NULL, NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NULL, NULL, 0, -1, -1);
COMMIT;

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `employee_id` varchar(32) NOT NULL COMMENT '员工信息ID',
  `employee_name` varchar(45) DEFAULT NULL COMMENT '员工姓名',
  `employee_mobile` varchar(20) DEFAULT NULL COMMENT '员工手机号',
  `role_ids` varchar(255) DEFAULT NULL COMMENT '员工角色集合',
  `is_employee` tinyint(4) NOT NULL COMMENT '是否业务员 0：是   1：否',
  `account_name` varchar(45) NOT NULL COMMENT '账户名',
  `account_password` varchar(45) NOT NULL COMMENT '账户密码',
  `employee_salt_val` varchar(200) DEFAULT NULL COMMENT '盐值,用于密码加密',
  `account_state` tinyint(4) NOT NULL COMMENT '账户状态  0：启用   1：禁用  2：离职',
  `account_disable_reason` varchar(255) DEFAULT NULL COMMENT '账号禁用原因',
  `third_id` varchar(32) DEFAULT NULL COMMENT '第三方店铺ID',
  `customer_id` varchar(32) DEFAULT NULL COMMENT '会员编号',
  `del_flag` tinyint(4) NOT NULL COMMENT '是否删除标志 0：否，1：是',
  `login_error_time` tinyint(4) DEFAULT NULL COMMENT '错误登录次数',
  `login_lock_time` datetime DEFAULT NULL COMMENT '登录锁定时间',
  `login_time` datetime DEFAULT NULL COMMENT '登录时间',
  `company_info_id` bigint(11) DEFAULT NULL COMMENT '公司信息ID',
  `is_master_account` tinyint(4) DEFAULT NULL COMMENT '是否主账号 0、否 1、是',
  `account_type` tinyint(1) DEFAULT NULL COMMENT '账号类型 0 b2b账号 1 s2b平台端账号 2 商家端账号 3供应商端账号',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `job_no` varchar(32) DEFAULT NULL COMMENT '工号',
  `position` varchar(32) DEFAULT NULL COMMENT '职位',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `sex` tinyint(1) DEFAULT '0' COMMENT '性别，0：保密，1：男，2：女',
  `become_member` tinyint(1) DEFAULT '0' COMMENT '是否激活会员账号，0：否，1：是',
  `heir_employee_id` varchar(32) DEFAULT NULL COMMENT '交接人员工ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_person` varchar(32) DEFAULT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_person` varchar(32) DEFAULT NULL COMMENT '修改人',
  `delete_time` datetime DEFAULT NULL COMMENT '删除时间',
  `delete_person` varchar(32) DEFAULT NULL COMMENT '删除人',
  `department_ids` varchar(500) DEFAULT NULL COMMENT '所属部门集合',
  `manage_department_ids` varchar(500) DEFAULT NULL COMMENT '管理部门集合',
  PRIMARY KEY (`employee_id`),
  UNIQUE KEY `employee_id_UNIQUE` (`employee_id`),
  KEY `idx_company_info_id` (`company_info_id`),
  KEY `idx_account_name` (`account_name`),
  KEY `idx_account_type` (`is_employee`,`del_flag`,`account_type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='员工信息表';

-- ----------------------------
-- Records of employee
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for store
-- ----------------------------
DROP TABLE IF EXISTS `store`;
CREATE TABLE `store` (
  `store_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '店铺主键',
  `company_info_id` bigint(11) DEFAULT NULL COMMENT '公司信息ID',
  `freight_template_type` tinyint(1) DEFAULT '0' COMMENT '使用的运费模板类别(0:店铺运费,1:单品运费)',
  `contract_start_date` datetime DEFAULT NULL COMMENT '签约开始日期',
  `contract_end_date` datetime DEFAULT NULL COMMENT '签约结束日期',
  `store_name` varchar(150) DEFAULT NULL COMMENT '店铺名称',
  `store_logo` varchar(255) DEFAULT NULL COMMENT '店铺logo',
  `store_sign` varchar(255) DEFAULT NULL COMMENT '店铺店招图片',
  `supplier_name` varchar(50) DEFAULT NULL COMMENT '商家名称',
  `company_type` tinyint(4) DEFAULT NULL COMMENT '商家类型 0、平台自营 1、第三方商家',
  `contact_person` varchar(150) DEFAULT NULL COMMENT '联系人',
  `contact_mobile` varchar(20) DEFAULT NULL COMMENT '联系方式',
  `contact_email` varchar(50) DEFAULT NULL COMMENT '联系邮箱',
  `province_id` bigint(10) DEFAULT NULL COMMENT '省',
  `city_id` bigint(10) DEFAULT NULL COMMENT '市',
  `area_id` bigint(10) DEFAULT NULL COMMENT '区',
  `street_id` bigint(10) DEFAULT NULL COMMENT '街道id',
  `address_detail` varchar(255) DEFAULT NULL COMMENT '详细地址',
  `account_day` varchar(50) DEFAULT NULL COMMENT '结算日',
  `audit_state` tinyint(4) DEFAULT NULL COMMENT '审核状态 0、待审核 1、已审核 2、审核未通过',
  `audit_reason` varchar(255) DEFAULT NULL COMMENT '审核未通过原因',
  `store_closed_reason` varchar(255) DEFAULT NULL COMMENT '店铺关店原因',
  `store_state` tinyint(4) DEFAULT NULL COMMENT '店铺状态 0、开启 1、关店',
  `del_flag` tinyint(4) DEFAULT NULL COMMENT '是否删除 0 否  1 是',
  `apply_enter_time` datetime DEFAULT NULL COMMENT '申请入驻时间',
  `small_program_code` varchar(250) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '店铺小程序码',
  `store_type` tinyint(4) DEFAULT NULL COMMENT '商家类型0供应商，1商家',
  `store_pinyin_name` varchar(500) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '店铺拼音名称',
  `supplier_pinyin_name` varchar(500) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '供应商拼音名称',
  `company_source_type` tinyint(1) DEFAULT '0' COMMENT '商家来源类型 0:商城入驻 1:linkMall初始化',
  PRIMARY KEY (`store_id`),
  KEY `idx_store_type` (`store_type`),
  KEY `idx_company_info_id` (`company_info_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12345679 DEFAULT CHARSET=utf8 COMMENT='店铺';

-- ----------------------------
-- Records of store
-- ----------------------------
BEGIN;
INSERT INTO `store` VALUES (-1, -1, 0, '2017-06-14 15:00:57', '2039-04-01 15:00:59', '有家私人店', 'https://hebi-b2b.oss-cn-shanghai.aliyuncs.com/201711291654190971', NULL, NULL, 1, 'system_admin', '13681441600', 'system@sybx.cc', 110000, 110100, 110101, NULL, '18号', NULL, 1, '0未通过', '', 1, 0, '2017-10-10 15:22:25', NULL, 1, NULL, NULL, 0);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
