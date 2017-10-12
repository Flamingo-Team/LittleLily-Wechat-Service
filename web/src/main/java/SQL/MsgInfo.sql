-- auto Generated on 2017-09-22 11:46:02 
-- DROP TABLE IF EXISTS `msg_info`; 
CREATE TABLE `msg_info`(
    `open_id` VARCHAR (255) UNIQUE NOT NULL  COMMENT 'openId',
    `form_id` VARCHAR (255) NOT NULL DEFAULT '' COMMENT 'formId',
    `count` INT (11) NOT NULL DEFAULT -1 COMMENT 'count',
    `insert_time` DATETIME NOT NULL DEFAULT '1000-01-01 00:00:00' COMMENT 'insertTime',
    PRIMARY KEY (`open_id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '`msg_info`';
