-- 查看当前数据库版本
select version();

show variables like '%char%';

-- 用户表
-- 编号 手机 邮箱 昵称 性别  城市  签名 头像 密码 QQ 新浪  财富值  飞吻 用户等级 用户角色 账号状态 邮箱状态 邮箱激活码 注册时间  修改时间  
create table tbl_user(
	id int primary key auto_increment  comment '编号',
	tel varchar(11) comment '手机',
	email varchar(25) comment '邮箱',
	nick_name varchar(25) comment '昵称',
	sex varchar(3) comment '性别',
	city varchar(20) comment '城市',
	signature varchar(100) comment '签名',
	head_img varchar(200) default 'default.jpg' comment '头像',
	password varchar(64) comment '密码',
	qq varchar(15) comment 'QQ',
	sina varchar(36) comment '新浪',
	wealth decimal(10,2) default 0 comment '财富值',
	kiss int default 0 comment '飞吻',
	grade varchar(10) default '' comment '用户等级:无 vip1-9',
	role varchar(16) default 0 comment'用户角色: -1管理员 0普通用户 1版主 ',
	user_status int(2) default 0 comment '账号状态: 0启用 1禁用',
	email_status int(2) default 0 comment '邮箱状态: 0未激活 1激活',
	email_code int(6) comment '邮箱激活码,唯一不重复，使用UUID生成',
	create_time timestamp default current_timestamp comment '注册时间',
	update_time timestamp comment '修改时间'
) charset='utf8' comment '用户表';


-- 专栏分类（提问，分享，讨论，建议）
-- 编号 名称 排序号 创建人 创建时间 修改时间
create table tbl_column(
	id int primary key auto_increment comment '编号',
	name varchar(25) comment '名称',
	sort_num int(2) comment '排序号',
	user_id int comment '创建人',
	create_time timestamp default current_timestamp comment '创建时间',
	update_time timestamp comment '修改时间',
	constraint fk_column_user_id foreign key(user_id) references tbl_user(id)
) charset='utf8' comment '专栏分类表';


-- 帖子表
-- 编号 所在专栏 标题 内容详情 悬赏(飞吻) 帖子状态 是否精帖 是否置顶 是否已结 发帖人 人气(浏览数) 回复数 发布时间 修改时间
create table tbl_post(
	id int primary key auto_increment comment '编号',
	column_id int comment '所在专栏',
	title varchar(300) comment '标题',
	context varchar(2000) comment '内容详情',
	kiss int comment '悬赏(飞吻)',
	status int(2) default 0 comment '帖子状态,0待审核，1已审核',
	is_fine int(1) default 0 comment '是否精帖 0否，1是',
	is_top int(1) default 0  comment '是否置顶 0否，1是',
	is_closed int(1) default 0  comment '是否已结 0否，1是',
	user_id int comment '发帖人',
	browse_num int default 0 comment '人气(浏览数) 0否，1是',
	reply_num int default 0 comment '回复数',
	create_time timestamp default current_timestamp comment '创建时间',
	update_time timestamp comment '修改时间',
	constraint fk_post_column_id foreign key(column_id) references tbl_column(id),
	constraint fk_post_user_id foreign key(user_id) references tbl_user(id)
) charset='utf8' comment '帖子表';

-- 回帖表
-- 编号 帖子编号 回复内容 点赞数 回复状态 回复人 回复时间 
create table tbl_reply(
	id int primary key auto_increment comment '编号',
	post_id int comment '帖子编号',
	replay_context varchar(1000) comment '回复内容',
	praise_count int default 0 comment '点赞数',
	replay_status int(2) default 0 comment '回复状态 0未审核 1审核通过',
	user_id int comment '回复人',
	create_time timestamp default current_timestamp comment '回复时间',
	constraint fk_reply_post_id foreign key(post_id) references tbl_post(id),
	constraint fk_reply_user_id foreign key(user_id) references tbl_user(id)
) charset='utf8' comment '回帖表';


-- 点赞表
create table tbl_praise(
	reply_id int comment '回贴ID',
	user_id int comment '点赞人ID',
	create_time timestamp default current_timestamp comment '点赞时间',
	constraint fk_praise_reply_id foreign key(reply_id) references tbl_reply(id),
	constraint fk_praise_user_id foreign key(user_id) references tbl_user(id),
	primary key(reply_id,user_id)
) charset='utf8' comment '点赞表';

-- select r.*,( select count(1) from tbl_praise where user_id= r.user_id and reply_id=r.id) isPre from tbl_reply r;


-- 收藏表
-- 编号 用户编号 帖子编号 收藏时间 
create table tbl_collect(
	id int primary key auto_increment comment '编号',
	user_id int comment '用户编号',
	post_id int comment '帖子编号',
	create_time timestamp default current_timestamp comment '收藏时间',
	constraint fk_collect_post_id foreign key(post_id) references tbl_post(id),
	constraint fk_collect_user_id foreign key(user_id) references tbl_user(id)
) charset='utf8' comment '收藏表';

-- 签到表
-- 编号 用户编号 签到总数 签到时间
create table tbl_sign(
	id int primary key auto_increment comment '编号',
	user_id int comment '用户编号',
	sign_count int default 0 comment '签到总数',
	create_time timestamp default current_timestamp comment '签到时间',
	constraint fk_sign_user_id foreign key(user_id) references tbl_user(id)
) charset='utf8' comment '签到表';


----------------------------------------------填充数据----------------------------------------------------
INSERT INTO `tbl_user` VALUES (1, '13628357343', NULL, '邙星魂', NULL, NULL, NULL, 'default.jpg', '123456', NULL, NULL, 0.00, 0, '', '0', 0, 0, NULL, '2019-08-22 09:10:14', '2019-08-22 09:13:26');


INSERT INTO `tbl_column` VALUES (1, '提问', 1, 1, '2019-08-22 09:33:54', '2019-08-22 09:33:50');
INSERT INTO `tbl_column` VALUES (2, '讨论', 2, 1, '2019-08-22 09:34:15', '2019-08-22 09:34:08');
INSERT INTO `tbl_column` VALUES (3, '分享', 3, 1, '2019-08-22 09:34:33', '2019-08-22 09:34:29');
INSERT INTO `tbl_column` VALUES (4, '建议', 4, 1, '2019-08-22 09:34:48', '2019-08-22 09:34:45');


--帖子信息查询
SELECT post.id,head_img headImg,nick_name nickName,col.name columnName,title,post.kiss,reply_num replyNum,
is_top isTop,is_fine isFine,is_closed isClosed,browse_num browseNum,post.create_time createTime
FROM tbl_post post 
join tbl_user user on post.user_id = user.id
join tbl_column col on col.id = post.column_id 






-- 等级分析
发帖、回帖、删帖、加入精华、置顶等均给予不同的飞吻。

行为	    发帖		回帖		邮箱激活		加精		置顶		管理员删帖   回复采纳
飞吻  	10点	 	2点		5点			20点		50点		-20         xxx

200分以下		vip1
201～400			vip2
401～800			vip3
800～1500		vip4	
1501～4000		vip5
4001～8000		vip6
8001～15000		vip7
15000～50000		vip8
50001（含）以	vip9

-- 飞吻计算规则
连续签到天数	每天可获飞吻
＜5		5
≥5		10
≥15		15
≥30		20
≥100	30
≥365	50