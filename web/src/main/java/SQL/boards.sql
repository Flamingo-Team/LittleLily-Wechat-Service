CREATE TABLE `boards` (
  `id`       INT(10)    NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50)   NOT NULL DEFAULT '',
  `name_cn` VARCHAR(500)   NOT NULL DEFAULT '',
  `section_name` VARCHAR(50)   NOT NULL DEFAULT '',
  `section_value` VARCHAR(50)   NOT NULL DEFAULT '',
  `url` VARCHAR(50)   NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY name (`name`)
);

INSERT INTO `boards` (id, name, name_cn, section_name, section_value, url)
  VALUE (NULL, 'D_Computer', '计算机系', 'Nanjing_University', '1', 'bbsdoc?board=D_Computer');

INSERT INTO `boards` (id, name, name_cn, section_name, section_value, url)
  VALUE (NULL, 'D_EE', '电子科学与工程学院', 'Nanjing_University', '1', 'bbsdoc?board=D_EE');
