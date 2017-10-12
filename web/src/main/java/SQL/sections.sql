CREATE TABLE `sections` (
  `id`       INT(10)    NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50)   NOT NULL DEFAULT '',
  `name_cn` VARCHAR(50)   NOT NULL DEFAULT '',
  `value` VARCHAR(50)   NOT NULL DEFAULT '',
  `url` VARCHAR(50)   NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY name (`value`)
);

INSERT INTO `sections` (id, name, name_cn, value, url)
  VALUE (NULL, 'Base_System', '本站系统', '0', 'bbsboa?sec=0');

INSERT INTO `sections` (id, name, name_cn, value, url)
  VALUE (NULL, 'Nanjing_University', '南京大学', '1', 'bbsboa?sec=1');