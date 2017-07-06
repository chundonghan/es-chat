

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `chat_account`
-- ----------------------------
DROP TABLE IF EXISTS `chat_account`;
CREATE TABLE `chat_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(20) DEFAULT '',
  `avatar` varchar(200) DEFAULT '',
  `account` varchar(20) NOT NULL DEFAULT '',
  `password` varchar(32) NOT NULL DEFAULT '',
  `create_timestamp` int(11) DEFAULT '0',
  `login_timestamp` int(11) DEFAULT '0',
  `enable_flag` char(1) DEFAULT '1' COMMENT '0 删除',
  `phone` char(11) DEFAULT '',
  `gender` smallint(2) DEFAULT '1' COMMENT '1:M 0:F',
  `auth_token` varchar(40) DEFAULT '',
  `token_expire` int(11) DEFAULT '0',
  `salt` char(6) DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `chat_contact`
-- ----------------------------
DROP TABLE IF EXISTS `chat_contact`;
CREATE TABLE `chat_contact` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `account_id` bigint(20) NOT NULL,
  `contact_account_id` bigint(20) NOT NULL,
  `create_timestamp` int(11) DEFAULT '0',
  `update_timestamp` int(11) DEFAULT '0',
  `enable_flag` char(1) DEFAULT '1' COMMENT '0 删除 2黑名单',
  `validate_msg` varchar(50) DEFAULT '' COMMENT '好友验证信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `chat_group`
-- ----------------------------
DROP TABLE IF EXISTS `chat_group`;
CREATE TABLE `chat_group` (
  `id` varchar(20) NOT NULL,
  `group_name` varchar(40) DEFAULT '',
  `create_timestamp` int(11) DEFAULT '0',
  `enable_flag` char(1) DEFAULT '1',
  `update_timestamp` int(11) DEFAULT '0',
  `group_host` varchar(20) NOT NULL COMMENT 'account',
  `group_avatar` varchar(200) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `chat_group_account`
-- ----------------------------
DROP TABLE IF EXISTS `chat_group_account`;
CREATE TABLE `chat_group_account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `group_id` varchar(20) NOT NULL,
  `account` varchar(20) NOT NULL,
  `create_timestamp` int(11) NOT NULL DEFAULT '0',
  `enable_flag` char(1) NOT NULL DEFAULT '1',
  `update_timestamp` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `chat_group_message`
-- ----------------------------
DROP TABLE IF EXISTS `chat_group_message`;
CREATE TABLE `chat_group_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `group_id` varchar(20) NOT NULL,
  `account` varchar(20) NOT NULL,
  `msg` varchar(2000) NOT NULL,
  `create_timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `enable_flag` char(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `chat_message`
-- ----------------------------
DROP TABLE IF EXISTS `chat_message`;
CREATE TABLE `chat_message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `from_account` varchar(20) NOT NULL,
  `to_account` varchar(20) NOT NULL,
  `msg` varchar(2000) NOT NULL,
  `create_timestamp` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `from_enable_flag` char(1) DEFAULT '1' COMMENT '1 甲方有效',
  `to_enable_flag` char(1) DEFAULT '1' COMMENT '乙方 有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=utf8;

