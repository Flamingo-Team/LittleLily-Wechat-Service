-- auto Generated on 2017-09-03 09:18:02 
-- DROP TABLE IF EXISTS `auth_info`; 
CREATE TABLE `auth_info`(
    `open_id` VARCHAR (255) UNIQUE NOT NULL  COMMENT 'openId',
    `nick_name` VARCHAR (255) NOT NULL DEFAULT '' COMMENT 'nickName',
    `gender` VARCHAR (4) NOT NULL DEFAULT '' COMMENT 'gender',
    `city` VARCHAR (50) NOT NULL DEFAULT '' COMMENT 'city',
    `province` VARCHAR (50) NOT NULL DEFAULT '' COMMENT 'province',
    `country` VARCHAR (50) NOT NULL DEFAULT '' COMMENT 'country',
    `avatar_url` VARCHAR (255) NOT NULL DEFAULT '' COMMENT 'avatarUrl',
    `union_id` VARCHAR (50) NOT NULL DEFAULT '' COMMENT 'unionId',
    `language` VARCHAR (20) NOT NULL DEFAULT '' COMMENT 'language',
    PRIMARY KEY (`open_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '`auth_info`';

INSERT INTO `auth_info` (open_id, nick_name, gender, city, province, country, avatar_url, union_id, language)
    VALUE ('test', 'lxl', '1', 'Nanjing', 'JiangSu', 'China', 'url:', 'unionId', 'ZH_cn');
