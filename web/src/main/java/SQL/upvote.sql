CREATE TABLE `upvote` (
  `id`       INT(10)    NOT NULL AUTO_INCREMENT,
  `topic_id` VARCHAR(100)   NOT NULL DEFAULT '',
  `floor_id` VARCHAR(50)   NOT NULL DEFAULT '',
  `webchat_id` char(50) NOT NULL DEFAULT '',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
);

