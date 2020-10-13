create database youxue;

CREATE TABLE IF NOT EXISTS `admin` (
  `uid` varchar(200) NOT NULL COMMENT '管理员唯一编码',
  `email` varchar(100) NOT NULL COMMENT '管理员登录邮箱',
  `password` varchar(200) NOT NULL COMMENT '管理员登录密码',
  `real_name` varchar(20) NOT NULL COMMENT '真实姓名',
  `gender` varchar(2) NOT NULL COMMENT '性别',
  `figure_small` varchar(500) DEFAULT NULL COMMENT '头像小图',
  `figure_normal` varchar(500) DEFAULT NULL COMMENT '头像大图',
  `phone` varchar(20) NOT NULL COMMENT '手机号',
  `qq` varchar(20) DEFAULT NULL COMMENT 'QQ号',
  `weibo` varchar(50) DEFAULT NULL COMMENT '新浪微博账号',
  `wechat` varchar(50) DEFAULT NULL COMMENT '微信账号',
  `area` varchar(20) DEFAULT NULL COMMENT '所在区域或城市',
  `location` varchar(100) DEFAULT NULL COMMENT '详细地址',
  `admin_type` varchar(30) DEFAULT NULL COMMENT '管理员类型，如super, normal',
  `is_valid` char(1) DEFAULT NULL COMMENT '是否合法，Y or N',
  `add_time` datetime DEFAULT NULL COMMENT '管理员添加时间',
  `add_by_uid` varchar(200) NOT NULL COMMENT '由谁添加',
  `login_time` datetime DEFAULT NULL COMMENT '管理员最近一次登录时间',
  `update_time` datetime DEFAULT NULL COMMENT '管理员最近一次更新时间',
  `update_by_uid` varchar(200) NOT NULL COMMENT '由谁更新'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `admin` (`uid`, `email`, `password`, `real_name`, `gender`, `figure_small`, `figure_normal`, `phone`, `qq`, `weibo`, `wechat`, `area`, `location`, `admin_type`, `is_valid`, `add_time`, `add_by_uid`, `login_time`, `update_time`, `update_by_uid`) VALUES
('9f72435e-4ddb-4982-6580-52c4e7e7f122', 'wgssmarter@126.com', '6a485e25c123bfa91bda5ae2d7d1e66f', '王根参', '男', NULL, NULL, '18507074625', '847315251', 'Wgssmart', '847315251', '赣州市章贡区', '赣州市章贡区经济开发区塞纳春天小区13栋904', 'super', 'Y', '2015-02-02 20:29:46', '9f72435e-4ddb-4982-6580-52c4e7e7f122', '2015-02-14 15:04:50', '2015-02-02 20:29:46', '9f72435e-4ddb-4982-6580-52c4e7e7f122');

-- --------------------------------------------------------

CREATE TABLE IF NOT EXISTS `tutorship` (
  `uid` varchar(200) NOT NULL COMMENT ' 辅导唯一编码',
  `subject` varchar(20) NOT NULL COMMENT '辅导科目',
  `hours` int(11) NOT NULL COMMENT '课时',
  `days` int(11) DEFAULT NULL COMMENT '辅导总天数',
  `location` varchar(100) DEFAULT NULL COMMENT '辅导地点',
  `class_time` varchar(200) DEFAULT NULL COMMENT '辅导时间说明',
  `start_time` date DEFAULT NULL COMMENT '辅导开始时间',
  `request` varchar(200) DEFAULT NULL COMMENT '教师要求',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注',
  `student_uid` varchar(200) NOT NULL COMMENT '哪个学生发布的辅导',
  `teacher_uid` varchar(200) DEFAULT NULL COMMENT '哪个教师辅导',
  `student_latest_thinking` varchar(1000) DEFAULT NULL COMMENT '学生最近发布的心声',
  `teacher_latest_thinking` varchar(1000) DEFAULT NULL COMMENT '教师最近发布的心声',
  `status` varchar(20) DEFAULT NULL COMMENT '状态：报名中，辅导中，已结束',
  `is_valid` char(1) NOT NULL COMMENT '是否合法, Y or N',
  `add_by_uid` varchar(200) NOT NULL COMMENT '由谁添加',
  `add_time` datetime NOT NULL COMMENT '添加辅导时间',
  `update_time` datetime NOT NULL COMMENT '辅导最近一次更新时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE IF NOT EXISTS `user` (
  `uid` varchar(200) NOT NULL COMMENT '用户唯一编码',
  `parent_uid` varchar(200) DEFAULT NULL COMMENT '父uid',
  `email` varchar(100) DEFAULT NULL COMMENT '用户登录邮箱',
  `password` varchar(200) DEFAULT NULL COMMENT '用户登录密码',
  `nickname` varchar(50) DEFAULT NULL COMMENT '用户昵称',
  `gender` varchar(2) DEFAULT NULL COMMENT '用户性别',
  `qq_access_token` varchar(100) DEFAULT NULL COMMENT 'qq登录access token',
  `qq_open_id` varchar(100) DEFAULT NULL COMMENT 'qq用户唯一编码',
  `weibo_access_token` varchar(100) DEFAULT NULL COMMENT '微博登录access token',
  `weibo_uid` varchar(50) DEFAULT NULL COMMENT '微博用户唯一编码',
  `bind_with` varchar(20) DEFAULT NULL COMMENT '社交绑定，qq, weibo, wechat等',
  `real_name` varchar(20) DEFAULT NULL COMMENT '用户真实姓名',
  `figure_small` varchar(500) DEFAULT NULL COMMENT '用户头像小图',
  `figure_normal` varchar(500) DEFAULT NULL COMMENT '用户头像正常图',
  `location` varchar(100) DEFAULT NULL COMMENT '用户区域位置',
  `phone` varchar(20) DEFAULT NULL COMMENT '用户手机号',
  `qq` varchar(20) DEFAULT NULL COMMENT '用户QQ号',
  `weibo` varchar(50) DEFAULT NULL COMMENT '用户微博账号',
  `wechat` varchar(50) DEFAULT NULL COMMENT '用户微信账号',
  `my_email` varchar(100) DEFAULT NULL COMMENT '用户常用邮箱',
  `user_type` varchar(10) DEFAULT NULL COMMENT '用户类型',
  `school` varchar(50) DEFAULT NULL COMMENT '学生学校名称',
  `grade` varchar(20) DEFAULT NULL COMMENT '学生年级',
  `where_from` varchar(50) DEFAULT NULL COMMENT '教师来自哪，工作单位或学校',
  `speciality` varchar(50) DEFAULT NULL COMMENT '教师专长',
  `evaluation` varchar(200) DEFAULT NULL COMMENT '用户自我评价',
  `t_type` varchar(20) DEFAULT NULL COMMENT '教师类型，大学生，在职教师或机构',
  `is_valid` char(1) DEFAULT NULL COMMENT '是否合法，Y或N',
  `status` varchar(10) DEFAULT NULL COMMENT '状态：报名中，辅导中，无辅导 ',
  `reg_time` datetime DEFAULT NULL COMMENT '用户注册时间',
  `login_time` datetime DEFAULT NULL COMMENT '用户最近一次登录时间',
  `update_time` datetime DEFAULT NULL COMMENT '用户最近一次更新时间',
  `kid_add_time` datetime DEFAULT NULL COMMENT '添加孩子信息的时间',
  `kid_update_time` datetime DEFAULT NULL COMMENT '最近一次修改孩子信息的时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


ALTER TABLE `admin`
  ADD PRIMARY KEY (`uid`), ADD KEY `email` (`email`,`password`,`phone`);


ALTER TABLE `tutorship`
  ADD PRIMARY KEY (`uid`);


ALTER TABLE `user`
  ADD PRIMARY KEY (`uid`), ADD KEY `email` (`email`,`password`,`nickname`,`real_name`,`phone`,`qq`,`weibo`,`wechat`,`user_type`,`is_valid`,`status`), ADD KEY `qq_access_token` (`qq_access_token`,`qq_open_id`,`weibo_access_token`,`weibo_uid`), ADD KEY `gender` (`gender`), ADD KEY `parent_uid` (`parent_uid`), ADD KEY `bind_with` (`bind_with`), ADD KEY `my_email` (`my_email`);
