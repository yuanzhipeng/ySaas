/*
 Navicat Premium Data Transfer

 Source Server         : exsi_5.7.24
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : 192.168.31.106:3306
 Source Schema         : y-setting

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 25/03/2023 23:55:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for base_config
-- ----------------------------
DROP TABLE IF EXISTS `base_config`;
CREATE TABLE `base_config` (
  `base_config_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '基本设置ID',
  `pc_website` varchar(128) DEFAULT NULL COMMENT 'PC端商城网址',
  `mobile_website` varchar(128) DEFAULT NULL COMMENT '移动端商城网址',
  `pc_logo` text COMMENT 'PC商城logo',
  `pc_banner` text COMMENT 'PC商城banner,最多可添加5个,多个图片间以"|"隔开',
  `mobile_banner` text COMMENT '移动商城banner,最多可添加5个,多个图片间以"|"隔开',
  `pc_main_banner` text COMMENT 'PC商城首页banner,最多可添加5个,多个图片间以"|"隔开',
  `pc_ico` text COMMENT '网页ico',
  `pc_title` varchar(128) DEFAULT NULL COMMENT 'pc商城标题',
  `supplier_website` varchar(128) DEFAULT NULL COMMENT '商家后台登录网址',
  `register_content` mediumtext COMMENT '会员注册协议',
  `store_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '店铺Id',
  PRIMARY KEY (`base_config_id`)
) ENGINE=InnoDB AUTO_INCREMENT=108 DEFAULT CHARSET=utf8 COMMENT='基本设置';

-- ----------------------------
-- Records of base_config
-- ----------------------------
BEGIN;
INSERT INTO `base_config` VALUES (34, 'https://hebi.kstore.shop/site', 'https://hebi.kstore.shop/mobile/', '', '[{\"uid\":\"rc-upload-1542184276900-14\",\"status\":\"done\",\"url\":\"https://ySaas-b2b.oss-cn-shanghai.aliyuncs.com/201811141632252680\"}]', NULL, NULL, '', NULL, 'https://hebi.kstore.shop/supplier/', '<p style=\"white-space: normal; text-align: left;\">尊敬的用户，欢迎您注册成为本网站用户。在注册前请您仔细阅读如下服务条款：</p><p style=\"white-space: normal;\">本服务协议双方为本网站与本网站用户，本服务协议具有合同效力。</p><p style=\"white-space: normal;\">您确认本服务协议后，本服务协议即在您和本网站之间产生法律效力。请您务必在注册之前认真阅读全部服务协议内容，如有任何疑问，可向本网站咨询。</p><p style=\"white-space: normal;\">无论您事实上是否在注册之前认真阅读了本服务协议，只要您点击协议正本下方的&quot;注册&quot;按钮并按照本网站注册程序成功注册为用户，您的行为仍然表示您同意并签署了本服务协议。</p><p style=\"white-space: normal;\">1．本网站服务条款的确认和接纳</p><p style=\"white-space: normal;\">本网站各项服务的所有权和运作权归本网站拥有。</p><p style=\"white-space: normal;\">2．用户必须：</p><p style=\"white-space: normal;\">(1)自行配备上网的所需设备， 包括个人电脑、调制解调器或其他必备上网装置。</p><p style=\"white-space: normal;\">(2)自行负担个人上网所支付的与此服务有关的电话费用、 网络费用。</p><p style=\"white-space: normal;\">3．用户在本网站上交易平台上不得发布下列违法信息：</p><p style=\"white-space: normal;\">(1)反对宪法所确定的基本原则的；</p><p style=\"white-space: normal;\">(2).危害国家安全，泄露国家秘密，颠覆国家政权，破坏国家统一的；</p><p style=\"white-space: normal;\">(3).损害国家荣誉和利益的；</p><p style=\"white-space: normal;\">(4).煽动民族仇恨、民族歧视，破坏民族团结的；</p><p style=\"white-space: normal;\">(5).破坏国家宗教政策，宣扬邪教和封建迷信的；</p><p style=\"white-space: normal;\">(6).散布谣言，扰乱社会秩序，破坏社会稳定的；</p><p style=\"white-space: normal;\">(7).散布淫秽、色情、赌博、暴力、凶杀、恐怖或者教唆犯罪的；</p><p style=\"white-space: normal;\">(8).侮辱或者诽谤他人，侵害他人合法权益的；</p><p style=\"white-space: normal;\">(9).含有法律、行政法规禁止的其他内容的。</p><p style=\"white-space: normal;\">4． 有关个人资料</p><p style=\"white-space: normal;\">用户同意：</p><p style=\"white-space: normal;\">(1) 提供及时、详尽及准确的个人资料。</p><p style=\"white-space: normal;\">(2).同意接收来自本网站的信息。</p><p style=\"white-space: normal;\">(3) 不断更新注册资料，符合及时、详尽准确的要求。所有原始键入的资料将引用为注册资料。</p><p style=\"white-space: normal;\">(4)本网站不公开用户的姓名、地址、电子邮箱和笔名，以下情况除外：</p><p style=\"white-space: normal;\">（a）用户授权本网站透露这些信息。</p><p style=\"white-space: normal;\">（b）相应的法律及程序要求本网站提供用户的个人资料。如果用户提供的资料包含有不正确的信息，本网站保留结束用户使用本网站信息服务资格的权利。</p><p style=\"white-space: normal;\">5. 用户在注册时应当选择稳定性及安全性相对较好的电子邮箱，并且同意接受并阅读本网站发往用户的各类电子邮件。如用户未及时从自己的电子邮箱接受电子邮件或因用户电子邮箱或用户电子邮件接收及阅读程序本身的问题使电子邮件无法正常接收或阅读的，只要本网站成功发送了电子邮件，应当视为用户已经接收到相关的电子邮件。电子邮件在发信服务器上所记录的发出时间视为送达时间。</p><p style=\"white-space: normal;\">6． 服务条款的修改</p><p style=\"white-space: normal;\">本网站有权在必要时修改服务条款，本网站服务条款一旦发生变动，将会在重要页面上提示修改内容。如果不同意所改动的内容，用户可以主动取消获得的本网站信息服务。如果用户继续享用本网站信息服务，则视为接受服务条款的变动。本网站保留随时修改或中断服务而不需通知用户的权利。本网站行使修改或中断服务的权利，不需对用户或第三方负责。</p><p style=\"white-space: normal;\">7． 用户隐私制度</p><p style=\"white-space: normal;\">尊重用户个人隐私是本网站的一项基本政策。所以，本网站一定不会在未经合法用户授权时公开、编辑或透露其注册资料及保存在本网站中的非公开内容，除非有法律许可要求或本网站在诚信的基础上认为透露这些信息在以下四种情况是必要的：</p><p style=\"white-space: normal;\">(1) 遵守有关法律规定，遵从本网站合法服务程序。</p><p style=\"white-space: normal;\">(2) 保持维护本网站的商标所有权。</p><p style=\"white-space: normal;\">(3) 在紧急情况下竭力维护用户个人和社会大众的隐私安全。</p><p style=\"white-space: normal;\">(4)符合其他相关的要求。</p><p style=\"white-space: normal;\">本网站保留发布会员人口分析资询的权利。</p><p style=\"white-space: normal;\">8．用户的帐号、密码和安全性</p><p style=\"white-space: normal;\">你一旦注册成功成为用户，你将得到一个密码和帐号。如果你不保管好自己的帐号和密码安全，将负全部责任。另外，每个用户都要对其帐户中的所有活动和事件负全责。你可随时根据指示改变你的密码，也可以结束旧的帐户重开一个新帐户。用户同意若发现任何非法使用用户帐号或安全漏洞的情况，请立即通告本网站。</p><p style=\"white-space: normal;\">9. 物流配送</p><p style=\"white-space: normal;\">因如下情况造成订单延迟或无法配送等，本站不承担延迟配送的责任：</p><p style=\"white-space: normal;\">（1）用户提供的信息错误、地址不详细等原因导致的；</p><p style=\"white-space: normal;\">（2）货物送达后无人签收，导致无法配送或延迟配送的；</p><p style=\"white-space: normal;\">（3）情势变更因素导致的；</p><p style=\"white-space: normal;\">（4）不可抗力因素导致的，例如：自然灾害、交通戒严、突发战争等。</p><p style=\"white-space: normal;\">10． 拒绝提供担保</p><p style=\"white-space: normal;\">用户明确同意信息服务的使用由用户个人承担风险。 本网站不担保服务不会受中断，对服务的及时性，安全性，出错发生都不作担保，但会在能力范围内，避免出错。</p><p style=\"white-space: normal;\">11．有限责任</p><p style=\"white-space: normal;\">本网站对任何直接、间接、偶然、特殊及继起的损害不负责任，这些损害来自：不正当使用本网站服务，或用户传送的信息不符合规定等。这些行为都有可能导致本网站形象受损，所以本网站事先提出这种损害的可能性，同时会尽量避免这种损害的发生。</p><p style=\"white-space: normal;\">12．信息的储存及限制</p><p style=\"white-space: normal;\">本网站有判定用户的行为是否符合本网站服务条款的要求和精神的权利，如果用户违背本网站服务条款的规定，本网站有权中断其服务的帐号。</p><p style=\"white-space: normal;\">13．用户管理</p><p style=\"white-space: normal;\">用户必须遵循：</p><p style=\"white-space: normal;\">(1) 使用信息服务不作非法用途。</p><p style=\"white-space: normal;\">(2) 不干扰或混乱网络服务。</p><p style=\"white-space: normal;\">(3) 遵守所有使用服务的网络协议、规定、程序和惯例。用户的行为准则是以因特网法规，政策、程序和惯例为根据的。</p><p style=\"white-space: normal;\">用户同意保障和维护本网站全体成员的利益，负责支付由用户使用超出服务范围引起的律师费用，违反服务条款的损害补偿费用，其它人使用用户的电脑、帐号和其它知识产权的追索费。</p><p style=\"white-space: normal;\">15．结束服务</p><p style=\"white-space: normal;\">用户或本网站可随时根据实际情况中断一项或多项服务。本网站不需对任何个人或第三方负责而随时中断服务。用户若反对任何服务条款的建议或对后来的条款修改有异议，或对本网站服务不满，用户可以行使如下权利：</p><p style=\"white-space: normal;\">(1) 不再使用本网站信息服务。</p><p style=\"white-space: normal;\">(2) 通知本网站停止对该用户的服务。</p><p style=\"white-space: normal;\">结束用户服务后，用户使用本网站服务的权利马上中止。从那时起，用户没有权利，本网站也没有义务传送任何未处理的信息或未完成的服务给用户或第三方。</p><p style=\"white-space: normal;\">16．通告</p><p style=\"white-space: normal;\">所有发给用户的通告都可通过重要页面的公告或电子邮件或常规的信件传送。服务条款的修改、服务变更、或其它重要事件的通告都会以此形式进行。</p><p style=\"white-space: normal;\">17．信息内容的所有权</p><p style=\"white-space: normal;\">本网站定义的信息内容包括：文字、软件、声音、相片、录象、图表；在广告中全部内容；本网站为用户提供的其它信息。所有这些内容受版权、商标、标签和其它财产所有权法律的保护。所以，用户只能在本网站和广告商授权下才能使用这些内容，而不能擅自复制、再造这些内容、或创造与内容有关的派生产品。</p><p style=\"white-space: normal;\">18．法律</p><p style=\"white-space: normal;\">本网站信息服务条款要与中华人民共和国的法律解释一致。用户和本网站一致同意服从本网站所在地有管辖权的法院管辖。如发生本网站服务条款与中华人民共和国法律相抵触时，则这些条款将完全按法律规定重新解释，而其它条款则依旧保持对用户的约束力。</p><p style=\"white-space: normal; text-align: center;\"><br/></p>', -1);
COMMIT;

-- ----------------------------
-- Table structure for system_config
-- ----------------------------
DROP TABLE IF EXISTS `system_config`;
CREATE TABLE `system_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT ' 编号',
  `config_key` varchar(255) NOT NULL COMMENT '键',
  `config_type` varchar(255) NOT NULL COMMENT '类型',
  `config_name` varchar(255) NOT NULL COMMENT '名称',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `status` tinyint(1) DEFAULT NULL COMMENT '状态,0:未启用1:已启用',
  `context` longtext COMMENT '配置内容，如JSON内容',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` tinyint(4) DEFAULT NULL COMMENT '删除标识,0:未删除1:已删除',
  `store_id` bigint(20) NOT NULL DEFAULT '-1' COMMENT '店铺Id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `idx_config_key` (`config_key`),
  KEY `idx_config_type` (`config_type`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='系统配置表';

-- ----------------------------
-- Records of system_config
-- ----------------------------
BEGIN;
INSERT INTO `system_config` VALUES (1, 's2b_audit', 'saas_domain', 'Saas化', 'saas化主域名', 1, '.saas.sybx.com', '2018-09-25 11:41:40', '2020-01-15 19:52:18', 0, -1);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
