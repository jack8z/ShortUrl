CREATE TABLE `short_urls` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(1024) NOT NULL COMMENT '长网址',
  `title` varchar(100) DEFAULT NULL COMMENT '网址标题',
  `memo` varchar(255) DEFAULT NULL COMMENT '备注',
  `flag` int(11) unsigned zerofill NOT NULL COMMENT '网址分类',
  `short_url` varchar(1024) DEFAULT NULL COMMENT '短网址',
  `access_times` bigint(20) unsigned zerofill NOT NULL COMMENT '短网址的访问次数',
  `created` datetime NOT NULL COMMENT '短网址的生成时间',
  `modified` datetime NOT NULL COMMENT '短网址的修改时间，access_times加1时，修改此时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `short_url_UNIQUE` (`short_url`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
