# myserver

#### 项目介绍
个人留言网站

#### 软件架构
软件架构说明


#### 安装教程

1. xxxx
2. xxxx
3. xxxx

#### 使用说明

DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `f_id` int(32) NOT NULL AUTO_INCREMENT,
  `f_no` varchar(255) DEFAULT NULL,
  `f_name` varchar(255) DEFAULT NULL,
  `f_sex` varchar(255) DEFAULT NULL,
  `f_password` varchar(255) DEFAULT NULL,
  `f_birth` varchar(255) DEFAULT NULL,
  `f_email` varchar(255) DEFAULT NULL,
  `f_code` varchar(255) DEFAULT NULL,
  `f_word` varchar(255) DEFAULT NULL,
  `f_state` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`f_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


DROP TABLE IF EXISTS `tb_login`;
CREATE TABLE `tb_login` (
  `f_id` int(32) NOT NULL AUTO_INCREMENT,
  `f_devices` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `f_time` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `f_ip` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`f_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


DROP TABLE IF EXISTS `tb_message`;
CREATE TABLE `tb_message` (
  `f_id` int(32) NOT NULL AUTO_INCREMENT,
  `f_no` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `f_name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `f_time` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `f_text` longtext COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`f_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

#### 参与贡献

1. Fork 本项目
2. 新建 Feat_xxx 分支
3. 提交代码
4. 新建 Pull Request


#### 码云特技

1. 使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2. 码云官方博客 [blog.gitee.com](https://blog.gitee.com)
3. 你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解码云上的优秀开源项目
4. [GVP](https://gitee.com/gvp) 全称是码云最有价值开源项目，是码云综合评定出的优秀开源项目
5. 码云官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6. 码云封面人物是一档用来展示码云会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)