create table t_news (
	id varchar(19) not null,
	address varchar(200),
	create_time datetime,
	description varchar(200),
	news_time datetime,
	title varchar(200),
	primary key (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE `go_item_t` (
  `item_id` varchar(19) COLLATE utf8_bin NOT NULL,
  `goods_name` varchar(500) COLLATE utf8_bin DEFAULT NULL,
  `brand_name` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `word` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `gkey` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`item_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;