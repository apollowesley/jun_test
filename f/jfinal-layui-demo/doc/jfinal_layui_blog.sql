/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50505
Source Host           : localhost:3306
Source Database       : jfinal_layui_blog

Target Server Type    : MYSQL
Target Server Version : 50505
File Encoding         : 65001

Date: 2018-01-04 17:32:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for blog
-- ----------------------------
DROP TABLE IF EXISTS `blog`;
CREATE TABLE `blog` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(200) NOT NULL COMMENT '标题',
  `author_id` bigint(255) DEFAULT NULL,
  `disc` varchar(255) NOT NULL COMMENT '描述',
  `content` mediumtext NOT NULL COMMENT '内容',
  `category` varchar(11) DEFAULT NULL,
  `uv` bigint(11) NOT NULL DEFAULT '0' COMMENT '浏览量',
  `like` bigint(11) NOT NULL DEFAULT '0' COMMENT '赞',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=295 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of blog
-- ----------------------------
INSERT INTO `blog` VALUES ('257', '不得不学的情况下，面对新知识怎样学习最有效？', null, '前天在跟古典做知乎Live 时，收到一个提问： 作为某一个学科的新手，应该采用大量快速地阅读以迅速熟悉该领域的方法，还是用每本读完之后写读书笔记这种精读的方法？ 这个很好的问题，当时做了一下简单的回答，这…阅读全文', '前天在跟古典做知乎Live 时，收到一个提问： 作为某一个学科的新手，应该采用大量快速地阅读以迅速熟悉该领域的方法，还是用每本读完之后写读书笔记这种精读的方法？ 这个很好的问题，当时做了一下简单的回答，这…阅读全文', 'test', '101', '99', '2017-12-27 21:45:51', '2018-01-04 16:24:10');
INSERT INTO `blog` VALUES ('258', '一个效率专家对家务上的优化建议（2）', null, '微信号：read01 文章出处：一个效率专家对家务上的优化建议（2） 写完第一篇之后，发现大家都对这个话题很感兴趣，继续第二篇。 千万不要觉得请个保姆就能解决所有问题。要找到好保姆很困难，不是单是钱的问题，…阅读全文', '微信号：read01 文章出处：一个效率专家对家务上的优化建议（2） 写完第一篇之后，发现大家都对这个话题很感兴趣，继续第二篇。 千万不要觉得请个保姆就能解决所有问题。要找到好保姆很困难，不是单是钱的问题，…阅读全文', 'test', '101', '99', '2017-12-27 21:45:51', '2018-01-04 16:24:10');
INSERT INTO `blog` VALUES ('259', '逻辑思维书籍推荐', null, '文章出处：逻辑思维书籍推荐 前段时间在做知乎Live时，说过写个逻辑思维的书单，结果在写书单才发现，这个工程不小，先列出一部分能立刻出现在大脑中或书架上看到的书，等有时间我会慢慢补充这个书单。 逻辑学（入…阅读全文', '文章出处：逻辑思维书籍推荐 前段时间在做知乎Live时，说过写个逻辑思维的书单，结果在写书单才发现，这个工程不小，先列出一部分能立刻出现在大脑中或书架上看到的书，等有时间我会慢慢补充这个书单。 逻辑学（入…阅读全文', 'test', '101', '99', '2017-12-27 21:45:51', '2018-01-04 16:24:10');
INSERT INTO `blog` VALUES ('260', '关于读书的吐槽', null, '今天世界读书日，好象一瞬间所有人都在谈阅读，随便吐槽几句。 1、阅读不需要等待，不需要特定时间再去看，别跟风。跟风的人大都很少读书。 2、多读书不一定能让你赚到钱。读书跟赚钱并不是因果关系。 3、阅读是一种…阅读全文', '今天世界读书日，好象一瞬间所有人都在谈阅读，随便吐槽几句。 1、阅读不需要等待，不需要特定时间再去看，别跟风。跟风的人大都很少读书。 2、多读书不一定能让你赚到钱。读书跟赚钱并不是因果关系。 3、阅读是一种…阅读全文', 'test', '101', '99', '2017-12-27 21:45:51', '2018-01-04 16:24:10');
INSERT INTO `blog` VALUES ('261', 'warfalcon的Live--???? 利用时间管理工具提升生活质量', null, '大多数读者又看了整整一年的微信号，感谢你们一年的陪伴。在年末给大家提供一场福利，欢迎参加。 我在Blog、知乎、微信公众上都写过非常多的时间管理文章，但很少有人听过我做时间管理分享，不管是免费的还是付费…阅读全文', '大多数读者又看了整整一年的微信号，感谢你们一年的陪伴。在年末给大家提供一场福利，欢迎参加。 我在Blog、知乎、微信公众上都写过非常多的时间管理文章，但很少有人听过我做时间管理分享，不管是免费的还是付费…阅读全文', 'test', '101', '99', '2017-12-27 21:45:51', '2018-01-04 16:24:10');
INSERT INTO `blog` VALUES ('262', '想知道如何养成习惯吗？只需要2.99就能听90分钟的分享', null, '明天晚上9：00，会在知乎上开始第一场Live， 话题是关于培养习惯：无需意志力的习惯养成方法　，这个标题是来源于一项认知误区：在习惯养成过程中，最大的障碍并不是缺乏勇气、胆识或意志力，而是多数人错误…阅读全文', '明天晚上9：00，会在知乎上开始第一场Live， 话题是关于培养习惯：无需意志力的习惯养成方法　，这个标题是来源于一项认知误区：在习惯养成过程中，最大的障碍并不是缺乏勇气、胆识或意志力，而是多数人错误…阅读全文', 'test', '101', '99', '2017-12-27 21:45:51', '2018-01-04 16:24:11');
INSERT INTO `blog` VALUES ('263', '爱别人同时也不要失去自己', null, '出处：爱别人同时也不要失去自己 微信号：read01(warfalcon) 最近看到一个只有2分钟的视频，讲了一条在恋爱中的底线规则：爱别人同时也不要失去自己 。 当你看完这条视频之…阅读全文', '出处：爱别人同时也不要失去自己 微信号：read01(warfalcon) 最近看到一个只有2分钟的视频，讲了一条在恋爱中的底线规则：爱别人同时也不要失去自己 。 当你看完这条视频之…阅读全文', 'test', '101', '99', '2017-12-27 21:45:51', '2018-01-04 16:24:11');
INSERT INTO `blog` VALUES ('264', '学会享受高质量的小长假', null, '微信公众号：read01(warfalcon) 文章出处：学会享受高质量的小长假 五一小长假开始了，估计你已经做了不少的计划和准备，希望这篇文章能帮你更好的去享受假期。 整理与清洁 尽量在假日中，最少留出半天…阅读全文', '微信公众号：read01(warfalcon) 文章出处：学会享受高质量的小长假 五一小长假开始了，估计你已经做了不少的计划和准备，希望这篇文章能帮你更好的去享受假期。 整理与清洁 尽量在假日中，最少留出半天…阅读全文', 'test', '101', '99', '2017-12-27 21:45:51', '2018-01-04 16:24:11');
INSERT INTO `blog` VALUES ('265', '', null, '我的第一场知乎live明天晚上9：00，会在知乎上开始第一场Live， 话题是关于培养习惯：无需意志力的习惯养成方法 ，这个标题是来源于一项认知误区：在习惯养成过程中，最大的障碍并不是缺乏勇气、胆识或意志力，而是多数人错误地以为意志力才是改变的关键，…想知道如何养成习惯吗？只需要2.99就能听90分钟的分享zhuanlan.zhihu.com文章阅读全文', '我的第一场知乎live明天晚上9：00，会在知乎上开始第一场Live， 话题是关于培养习惯：无需意志力的习惯养成方法 ，这个标题是来源于一项认知误区：在习惯养成过程中，最大的障碍并不是缺乏勇气、胆识或意志力，而是多数人错误地以为意志力才是改变的关键，…想知道如何养成习惯吗？只需要2.99就能听90分钟的分享zhuanlan.zhihu.com文章阅读全文', 'test', '101', '99', '2017-12-27 21:45:51', '2018-01-04 16:24:11');
INSERT INTO `blog` VALUES ('266', 'Live：利用时间管理工', null, '发现有很多小伙伴都购买晚了，没有地方提问，单独发个专栏，购买本次的Live的小伙伴要是有问题，可以这里留言。 要是留言太多，可能无法在Live中回复，但尽量会在这个或微信公众号上回应。 这是在Live中提…阅读全文', '发现有很多小伙伴都购买晚了，没有地方提问，单独发个专栏，购买本次的Live的小伙伴要是有问题，可以这里留言。 要是留言太多，可能无法在Live中回复，但尽量会在这个或微信公众号上回应。 这是在Live中提…阅读全文', 'test', '101', '99', '2017-12-27 21:45:51', '2018-01-04 16:24:11');
INSERT INTO `blog` VALUES ('267', '无需意志力的习惯养成方法论Live的21个现场答疑', null, '在2016年10月11日的晚9点，开始了我的第一场知乎Live: 无需意志力的习惯养成方法论，整场分享一共讲了两个半小时共150分钟。这场Live在开讲前参加只需要2.99，结束之后也只设定为30元。…阅读全文', '在2016年10月11日的晚9点，开始了我的第一场知乎Live: 无需意志力的习惯养成方法论，整场分享一共讲了两个半小时共150分钟。这场Live在开讲前参加只需要2.99，结束之后也只设定为30元。…阅读全文', 'test', '101', '99', '2017-12-27 21:45:51', '2018-01-04 16:24:11');
INSERT INTO `blog` VALUES ('268', '时间管理跟加薪有多大关系？', null, '才想起来，今晚8点会有一个Live，主题是：做好时间管理，找到隐藏的加薪机会 地址：知乎 Live - 全新的实时问答 很多人学习时间管理的目标就是为了让自己加薪升职，但常规的时间管理课程都把重点放在提高…阅读全文', '才想起来，今晚8点会有一个Live，主题是：做好时间管理，找到隐藏的加薪机会 地址：知乎 Live - 全新的实时问答 很多人学习时间管理的目标就是为了让自己加薪升职，但常规的时间管理课程都把重点放在提高…阅读全文', 'test', '101', '99', '2017-12-27 21:45:51', '2018-01-04 16:24:11');
INSERT INTO `blog` VALUES ('269', '学会有效的花钱', null, '微信号公众号：warfalcon（read01） 出处：学会有效的花钱 把昨晚微信文章转过来，好象已经晚了。 马上双11了，估计已经有不少人准备好了购物清单，但在你按下购买按钮之前，可以再重新考虑一下，有哪…阅读全文', '微信号公众号：warfalcon（read01） 出处：学会有效的花钱 把昨晚微信文章转过来，好象已经晚了。 马上双11了，估计已经有不少人准备好了购物清单，但在你按下购买按钮之前，可以再重新考虑一下，有哪…阅读全文', 'test', '101', '99', '2017-12-27 21:45:51', '2018-01-04 16:24:11');
INSERT INTO `blog` VALUES ('270', '个人成长的两个底层逻辑', null, '前段时间跟几个新认识的朋友喝茶，有个朋友问了一个问题：我花了好几年时间去学习个人成长，花了不少钱，参加了各种学习班，为什么收效不大？其他几个人也有同感，花了钱、努力了，但却没有获得想象中的变化。 这个问…阅读全文', '前段时间跟几个新认识的朋友喝茶，有个朋友问了一个问题：我花了好几年时间去学习个人成长，花了不少钱，参加了各种学习班，为什么收效不大？其他几个人也有同感，花了钱、努力了，但却没有获得想象中的变化。 这个问…阅读全文', 'test', '101', '99', '2017-12-27 21:45:51', '2018-01-04 16:24:11');
INSERT INTO `blog` VALUES ('271', '怎样能找到靠谱的另一半？', null, '微信号：read(warfalcon) 文章地址：怎样能找到靠谱的另一半？ 昨天本来是无心之举，结果收到了4000多位读者的留言和回应，后台的留言被刷暴了，原打算可能只会收到几百人回应，没有想到单身读者所…阅读全文', '微信号：read(warfalcon) 文章地址：怎样能找到靠谱的另一半？ 昨天本来是无心之举，结果收到了4000多位读者的留言和回应，后台的留言被刷暴了，原打算可能只会收到几百人回应，没有想到单身读者所…阅读全文', 'test', '101', '99', '2017-12-27 21:45:52', '2018-01-04 16:24:11');
INSERT INTO `blog` VALUES ('272', '怎样转职成一名自由职业者？(2)', null, '微信号：warfalcon(read01) 出处：怎样转职成一名自由职业者？(2) 几个月之前，写了怎样转职成一名自由职业者？(1)，然后一拖就是三个月时间，今天排队办事的时候想了几点，开始第二篇。 2、知…阅读全文', '微信号：warfalcon(read01) 出处：怎样转职成一名自由职业者？(2) 几个月之前，写了怎样转职成一名自由职业者？(1)，然后一拖就是三个月时间，今天排队办事的时候想了几点，开始第二篇。 2、知…阅读全文', 'test', '101', '99', '2017-12-27 21:45:52', '2018-01-04 16:24:11');
INSERT INTO `blog` VALUES ('273', 'Live预告：应用逻辑思维来快速解决问题', null, '周六晚上8点有场Live，讲逻辑思维，重点会放到职场应用，怎样梳理自己面临的问题，怎么找到关键点，有逻辑的进行沟通或汇报。 千万注意本场内容难度属于入门阶段，如果你自认为逻辑非常清晰，理性思维很强，不建…阅读全文', '周六晚上8点有场Live，讲逻辑思维，重点会放到职场应用，怎样梳理自己面临的问题，怎么找到关键点，有逻辑的进行沟通或汇报。 千万注意本场内容难度属于入门阶段，如果你自认为逻辑非常清晰，理性思维很强，不建…阅读全文', 'test', '101', '99', '2017-12-27 21:45:52', '2018-01-04 16:24:12');
INSERT INTO `blog` VALUES ('274', '怎样在1年内积累5年的经验', null, '微信号：read01 文章出处：怎样在1年内积累5年的经验 现在这个高速发展的时代，很多行业中知识的更新节奏已经从几十年缩短到几年，当你没办法在短时间内学到新的知识，很容易就会被淘汰。 1、学会控制…阅读全文', '微信号：read01 文章出处：怎样在1年内积累5年的经验 现在这个高速发展的时代，很多行业中知识的更新节奏已经从几十年缩短到几年，当你没办法在短时间内学到新的知识，很容易就会被淘汰。 1、学会控制…阅读全文', 'test', '101', '99', '2017-12-27 21:45:52', '2018-01-04 16:24:12');
INSERT INTO `blog` VALUES ('275', '逻辑沟通术：这样说话让人更容易接受', null, '这篇文章是写给参加知乎Live：【应用逻辑思维来快速解决问题】的小伙伴，有些小伙伴反馈说讲座不错，但理论多，能落地的少，希望能看到一些可以落地更实用的例子。 实际上在Live中只讲了很少的理论，大多数都…阅读全文', '这篇文章是写给参加知乎Live：【应用逻辑思维来快速解决问题】的小伙伴，有些小伙伴反馈说讲座不错，但理论多，能落地的少，希望能看到一些可以落地更实用的例子。 实际上在Live中只讲了很少的理论，大多数都…阅读全文', 'test', '101', '99', '2017-12-27 21:45:52', '2018-01-04 16:24:12');
INSERT INTO `blog` VALUES ('276', '一个效率专家对家务上的优化建议（1）', null, '作者：warfalcon 出处：一个效率专家对家务上的优化建议（1） 结婚之后，你会发现在家务上花费大量的时间，而且这些任务是定期循环，永无止境的。 几年前我开始变成一个自由职业者之后，就开始承担了绝大部分…阅读全文', '作者：warfalcon 出处：一个效率专家对家务上的优化建议（1） 结婚之后，你会发现在家务上花费大量的时间，而且这些任务是定期循环，永无止境的。 几年前我开始变成一个自由职业者之后，就开始承担了绝大部分…阅读全文', 'test', '101', '99', '2017-12-27 21:45:52', '2018-01-04 16:24:12');

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL COMMENT '标题',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=295 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('291', '时间管理', '2018-01-04 14:19:19', '2018-01-04 14:20:36');
INSERT INTO `category` VALUES ('292', '效率提升', '2018-01-04 14:19:41', '2018-01-04 14:20:38');
INSERT INTO `category` VALUES ('293', '工作学习', '2018-01-04 14:20:12', '2018-01-04 14:20:12');
INSERT INTO `category` VALUES ('294', '生活经验', '2018-01-04 14:20:24', '2018-01-04 14:20:24');

-- ----------------------------
-- Table structure for file
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `url` varchar(255) NOT NULL,
  `type` varchar(255) DEFAULT NULL COMMENT '文件类型',
  `category` varchar(255) DEFAULT NULL,
  `module` varchar(255) DEFAULT NULL,
  `ext` varchar(255) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1002 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `userid` varchar(200) NOT NULL COMMENT '标题',
  `content` varchar(255) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=295 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES ('291', '时间管理', null, '2018-01-04 14:19:19', '2018-01-04 14:20:36');
INSERT INTO `comment` VALUES ('292', '效率提升', null, '2018-01-04 14:19:41', '2018-01-04 14:20:38');
INSERT INTO `comment` VALUES ('293', '工作学习', null, '2018-01-04 14:20:12', '2018-01-04 14:20:12');
INSERT INTO `comment` VALUES ('294', '生活经验', null, '2018-01-04 14:20:24', '2018-01-04 14:20:24');

-- ----------------------------
-- Records of file
-- ----------------------------
INSERT INTO `file` VALUES ('902', 'name1', 'url1', null, 'category1', 'module1', 'ext1', '2018-01-04 17:30:42', '2018-01-04 17:30:42');
INSERT INTO `file` VALUES ('903', 'name2', 'url2', null, 'category2', 'module2', 'ext2', '2018-01-04 17:30:43', '2018-01-04 17:30:43');
INSERT INTO `file` VALUES ('904', 'name3', 'url3', null, 'category3', 'module3', 'ext3', '2018-01-04 17:30:43', '2018-01-04 17:30:43');
INSERT INTO `file` VALUES ('905', 'name4', 'url4', null, 'category4', 'module4', 'ext4', '2018-01-04 17:30:43', '2018-01-04 17:30:43');
INSERT INTO `file` VALUES ('906', 'name5', 'url5', null, 'category5', 'module5', 'ext5', '2018-01-04 17:30:43', '2018-01-04 17:30:43');
INSERT INTO `file` VALUES ('907', 'name6', 'url6', null, 'category6', 'module6', 'ext6', '2018-01-04 17:30:43', '2018-01-04 17:30:43');
INSERT INTO `file` VALUES ('908', 'name7', 'url7', null, 'category7', 'module7', 'ext7', '2018-01-04 17:30:44', '2018-01-04 17:30:44');
INSERT INTO `file` VALUES ('909', 'name8', 'url8', null, 'category8', 'module8', 'ext8', '2018-01-04 17:30:44', '2018-01-04 17:30:44');
INSERT INTO `file` VALUES ('910', 'name9', 'url9', null, 'category9', 'module9', 'ext9', '2018-01-04 17:30:45', '2018-01-04 17:30:45');
INSERT INTO `file` VALUES ('911', 'name10', 'url10', null, 'category10', 'module10', 'ext10', '2018-01-04 17:30:46', '2018-01-04 17:30:46');
INSERT INTO `file` VALUES ('912', 'name11', 'url11', null, 'category11', 'module11', 'ext11', '2018-01-04 17:30:46', '2018-01-04 17:30:46');
INSERT INTO `file` VALUES ('913', 'name12', 'url12', null, 'category12', 'module12', 'ext12', '2018-01-04 17:30:46', '2018-01-04 17:30:46');
INSERT INTO `file` VALUES ('914', 'name13', 'url13', null, 'category13', 'module13', 'ext13', '2018-01-04 17:30:47', '2018-01-04 17:30:47');
INSERT INTO `file` VALUES ('915', 'name14', 'url14', null, 'category14', 'module14', 'ext14', '2018-01-04 17:30:47', '2018-01-04 17:30:47');
INSERT INTO `file` VALUES ('916', 'name15', 'url15', null, 'category15', 'module15', 'ext15', '2018-01-04 17:30:47', '2018-01-04 17:30:47');
INSERT INTO `file` VALUES ('917', 'name16', 'url16', null, 'category16', 'module16', 'ext16', '2018-01-04 17:30:47', '2018-01-04 17:30:47');
INSERT INTO `file` VALUES ('918', 'name17', 'url17', null, 'category17', 'module17', 'ext17', '2018-01-04 17:30:47', '2018-01-04 17:30:47');
INSERT INTO `file` VALUES ('919', 'name18', 'url18', null, 'category18', 'module18', 'ext18', '2018-01-04 17:30:47', '2018-01-04 17:30:47');
INSERT INTO `file` VALUES ('920', 'name19', 'url19', null, 'category19', 'module19', 'ext19', '2018-01-04 17:30:47', '2018-01-04 17:30:47');
INSERT INTO `file` VALUES ('921', 'name20', 'url20', null, 'category20', 'module20', 'ext20', '2018-01-04 17:30:47', '2018-01-04 17:30:47');
INSERT INTO `file` VALUES ('922', 'name21', 'url21', null, 'category21', 'module21', 'ext21', '2018-01-04 17:30:47', '2018-01-04 17:30:47');
INSERT INTO `file` VALUES ('923', 'name22', 'url22', null, 'category22', 'module22', 'ext22', '2018-01-04 17:30:47', '2018-01-04 17:30:47');
INSERT INTO `file` VALUES ('924', 'name23', 'url23', null, 'category23', 'module23', 'ext23', '2018-01-04 17:30:47', '2018-01-04 17:30:47');
INSERT INTO `file` VALUES ('925', 'name24', 'url24', null, 'category24', 'module24', 'ext24', '2018-01-04 17:30:47', '2018-01-04 17:30:47');
INSERT INTO `file` VALUES ('926', 'name25', 'url25', null, 'category25', 'module25', 'ext25', '2018-01-04 17:30:47', '2018-01-04 17:30:47');
INSERT INTO `file` VALUES ('927', 'name26', 'url26', null, 'category26', 'module26', 'ext26', '2018-01-04 17:30:48', '2018-01-04 17:30:48');
INSERT INTO `file` VALUES ('928', 'name27', 'url27', null, 'category27', 'module27', 'ext27', '2018-01-04 17:30:48', '2018-01-04 17:30:48');
INSERT INTO `file` VALUES ('929', 'name28', 'url28', null, 'category28', 'module28', 'ext28', '2018-01-04 17:30:48', '2018-01-04 17:30:48');
INSERT INTO `file` VALUES ('930', 'name29', 'url29', null, 'category29', 'module29', 'ext29', '2018-01-04 17:30:48', '2018-01-04 17:30:48');
INSERT INTO `file` VALUES ('931', 'name30', 'url30', null, 'category30', 'module30', 'ext30', '2018-01-04 17:30:48', '2018-01-04 17:30:48');
INSERT INTO `file` VALUES ('932', 'name31', 'url31', null, 'category31', 'module31', 'ext31', '2018-01-04 17:30:48', '2018-01-04 17:30:48');
INSERT INTO `file` VALUES ('933', 'name32', 'url32', null, 'category32', 'module32', 'ext32', '2018-01-04 17:30:48', '2018-01-04 17:30:48');
INSERT INTO `file` VALUES ('934', 'name33', 'url33', null, 'category33', 'module33', 'ext33', '2018-01-04 17:30:48', '2018-01-04 17:30:48');
INSERT INTO `file` VALUES ('935', 'name34', 'url34', null, 'category34', 'module34', 'ext34', '2018-01-04 17:30:49', '2018-01-04 17:30:49');
INSERT INTO `file` VALUES ('936', 'name35', 'url35', null, 'category35', 'module35', 'ext35', '2018-01-04 17:30:49', '2018-01-04 17:30:49');
INSERT INTO `file` VALUES ('937', 'name36', 'url36', null, 'category36', 'module36', 'ext36', '2018-01-04 17:30:49', '2018-01-04 17:30:49');
INSERT INTO `file` VALUES ('938', 'name37', 'url37', null, 'category37', 'module37', 'ext37', '2018-01-04 17:30:49', '2018-01-04 17:30:49');
INSERT INTO `file` VALUES ('939', 'name38', 'url38', null, 'category38', 'module38', 'ext38', '2018-01-04 17:30:49', '2018-01-04 17:30:49');
INSERT INTO `file` VALUES ('940', 'name39', 'url39', null, 'category39', 'module39', 'ext39', '2018-01-04 17:30:49', '2018-01-04 17:30:49');
INSERT INTO `file` VALUES ('941', 'name40', 'url40', null, 'category40', 'module40', 'ext40', '2018-01-04 17:30:49', '2018-01-04 17:30:49');
INSERT INTO `file` VALUES ('942', 'name41', 'url41', null, 'category41', 'module41', 'ext41', '2018-01-04 17:30:49', '2018-01-04 17:30:49');
INSERT INTO `file` VALUES ('943', 'name42', 'url42', null, 'category42', 'module42', 'ext42', '2018-01-04 17:30:49', '2018-01-04 17:30:49');
INSERT INTO `file` VALUES ('944', 'name43', 'url43', null, 'category43', 'module43', 'ext43', '2018-01-04 17:30:50', '2018-01-04 17:30:50');
INSERT INTO `file` VALUES ('945', 'name44', 'url44', null, 'category44', 'module44', 'ext44', '2018-01-04 17:30:50', '2018-01-04 17:30:50');
INSERT INTO `file` VALUES ('946', 'name45', 'url45', null, 'category45', 'module45', 'ext45', '2018-01-04 17:30:50', '2018-01-04 17:30:50');
INSERT INTO `file` VALUES ('947', 'name46', 'url46', null, 'category46', 'module46', 'ext46', '2018-01-04 17:30:50', '2018-01-04 17:30:50');
INSERT INTO `file` VALUES ('948', 'name47', 'url47', null, 'category47', 'module47', 'ext47', '2018-01-04 17:30:51', '2018-01-04 17:30:51');
INSERT INTO `file` VALUES ('949', 'name48', 'url48', null, 'category48', 'module48', 'ext48', '2018-01-04 17:30:51', '2018-01-04 17:30:51');
INSERT INTO `file` VALUES ('950', 'name49', 'url49', null, 'category49', 'module49', 'ext49', '2018-01-04 17:30:51', '2018-01-04 17:30:51');
INSERT INTO `file` VALUES ('951', 'name50', 'url50', null, 'category50', 'module50', 'ext50', '2018-01-04 17:30:51', '2018-01-04 17:30:51');
INSERT INTO `file` VALUES ('952', 'name51', 'url51', null, 'category51', 'module51', 'ext51', '2018-01-04 17:30:52', '2018-01-04 17:30:52');
INSERT INTO `file` VALUES ('953', 'name52', 'url52', null, 'category52', 'module52', 'ext52', '2018-01-04 17:30:52', '2018-01-04 17:30:52');
INSERT INTO `file` VALUES ('954', 'name53', 'url53', null, 'category53', 'module53', 'ext53', '2018-01-04 17:30:53', '2018-01-04 17:30:53');
INSERT INTO `file` VALUES ('955', 'name54', 'url54', null, 'category54', 'module54', 'ext54', '2018-01-04 17:30:53', '2018-01-04 17:30:53');
INSERT INTO `file` VALUES ('956', 'name55', 'url55', null, 'category55', 'module55', 'ext55', '2018-01-04 17:30:53', '2018-01-04 17:30:53');
INSERT INTO `file` VALUES ('957', 'name56', 'url56', null, 'category56', 'module56', 'ext56', '2018-01-04 17:30:53', '2018-01-04 17:30:53');
INSERT INTO `file` VALUES ('958', 'name57', 'url57', null, 'category57', 'module57', 'ext57', '2018-01-04 17:30:53', '2018-01-04 17:30:53');
INSERT INTO `file` VALUES ('959', 'name58', 'url58', null, 'category58', 'module58', 'ext58', '2018-01-04 17:30:53', '2018-01-04 17:30:53');
INSERT INTO `file` VALUES ('960', 'name59', 'url59', null, 'category59', 'module59', 'ext59', '2018-01-04 17:30:53', '2018-01-04 17:30:53');
INSERT INTO `file` VALUES ('961', 'name60', 'url60', null, 'category60', 'module60', 'ext60', '2018-01-04 17:30:54', '2018-01-04 17:30:54');
INSERT INTO `file` VALUES ('962', 'name61', 'url61', null, 'category61', 'module61', 'ext61', '2018-01-04 17:30:54', '2018-01-04 17:30:54');
INSERT INTO `file` VALUES ('963', 'name62', 'url62', null, 'category62', 'module62', 'ext62', '2018-01-04 17:30:55', '2018-01-04 17:30:55');
INSERT INTO `file` VALUES ('964', 'name63', 'url63', null, 'category63', 'module63', 'ext63', '2018-01-04 17:30:55', '2018-01-04 17:30:55');
INSERT INTO `file` VALUES ('965', 'name64', 'url64', null, 'category64', 'module64', 'ext64', '2018-01-04 17:30:55', '2018-01-04 17:30:55');
INSERT INTO `file` VALUES ('966', 'name65', 'url65', null, 'category65', 'module65', 'ext65', '2018-01-04 17:30:55', '2018-01-04 17:30:55');
INSERT INTO `file` VALUES ('967', 'name66', 'url66', null, 'category66', 'module66', 'ext66', '2018-01-04 17:30:55', '2018-01-04 17:30:55');
INSERT INTO `file` VALUES ('968', 'name67', 'url67', null, 'category67', 'module67', 'ext67', '2018-01-04 17:30:55', '2018-01-04 17:30:55');
INSERT INTO `file` VALUES ('969', 'name68', 'url68', null, 'category68', 'module68', 'ext68', '2018-01-04 17:30:55', '2018-01-04 17:30:55');
INSERT INTO `file` VALUES ('970', 'name69', 'url69', null, 'category69', 'module69', 'ext69', '2018-01-04 17:30:55', '2018-01-04 17:30:55');
INSERT INTO `file` VALUES ('971', 'name70', 'url70', null, 'category70', 'module70', 'ext70', '2018-01-04 17:30:56', '2018-01-04 17:30:56');
INSERT INTO `file` VALUES ('972', 'name71', 'url71', null, 'category71', 'module71', 'ext71', '2018-01-04 17:30:56', '2018-01-04 17:30:56');
INSERT INTO `file` VALUES ('973', 'name72', 'url72', null, 'category72', 'module72', 'ext72', '2018-01-04 17:30:56', '2018-01-04 17:30:56');
INSERT INTO `file` VALUES ('974', 'name73', 'url73', null, 'category73', 'module73', 'ext73', '2018-01-04 17:30:57', '2018-01-04 17:30:57');
INSERT INTO `file` VALUES ('975', 'name74', 'url74', null, 'category74', 'module74', 'ext74', '2018-01-04 17:30:57', '2018-01-04 17:30:57');
INSERT INTO `file` VALUES ('976', 'name75', 'url75', null, 'category75', 'module75', 'ext75', '2018-01-04 17:30:57', '2018-01-04 17:30:57');
INSERT INTO `file` VALUES ('977', 'name76', 'url76', null, 'category76', 'module76', 'ext76', '2018-01-04 17:30:57', '2018-01-04 17:30:57');
INSERT INTO `file` VALUES ('978', 'name77', 'url77', null, 'category77', 'module77', 'ext77', '2018-01-04 17:30:57', '2018-01-04 17:30:57');
INSERT INTO `file` VALUES ('979', 'name78', 'url78', null, 'category78', 'module78', 'ext78', '2018-01-04 17:30:58', '2018-01-04 17:30:58');
INSERT INTO `file` VALUES ('980', 'name79', 'url79', null, 'category79', 'module79', 'ext79', '2018-01-04 17:30:58', '2018-01-04 17:30:58');
INSERT INTO `file` VALUES ('981', 'name80', 'url80', null, 'category80', 'module80', 'ext80', '2018-01-04 17:31:00', '2018-01-04 17:31:00');
INSERT INTO `file` VALUES ('982', 'name81', 'url81', null, 'category81', 'module81', 'ext81', '2018-01-04 17:31:00', '2018-01-04 17:31:00');
INSERT INTO `file` VALUES ('983', 'name82', 'url82', null, 'category82', 'module82', 'ext82', '2018-01-04 17:31:01', '2018-01-04 17:31:01');
INSERT INTO `file` VALUES ('984', 'name83', 'url83', null, 'category83', 'module83', 'ext83', '2018-01-04 17:31:01', '2018-01-04 17:31:01');
INSERT INTO `file` VALUES ('985', 'name84', 'url84', null, 'category84', 'module84', 'ext84', '2018-01-04 17:31:01', '2018-01-04 17:31:01');
INSERT INTO `file` VALUES ('986', 'name85', 'url85', null, 'category85', 'module85', 'ext85', '2018-01-04 17:31:01', '2018-01-04 17:31:01');
INSERT INTO `file` VALUES ('987', 'name86', 'url86', null, 'category86', 'module86', 'ext86', '2018-01-04 17:31:01', '2018-01-04 17:31:01');
INSERT INTO `file` VALUES ('988', 'name87', 'url87', null, 'category87', 'module87', 'ext87', '2018-01-04 17:31:01', '2018-01-04 17:31:01');
INSERT INTO `file` VALUES ('989', 'name88', 'url88', null, 'category88', 'module88', 'ext88', '2018-01-04 17:31:02', '2018-01-04 17:31:02');
INSERT INTO `file` VALUES ('990', 'name89', 'url89', null, 'category89', 'module89', 'ext89', '2018-01-04 17:31:02', '2018-01-04 17:31:02');
INSERT INTO `file` VALUES ('991', 'name90', 'url90', null, 'category90', 'module90', 'ext90', '2018-01-04 17:31:02', '2018-01-04 17:31:02');
INSERT INTO `file` VALUES ('992', 'name91', 'url91', null, 'category91', 'module91', 'ext91', '2018-01-04 17:31:02', '2018-01-04 17:31:02');
INSERT INTO `file` VALUES ('993', 'name92', 'url92', null, 'category92', 'module92', 'ext92', '2018-01-04 17:31:03', '2018-01-04 17:31:03');
INSERT INTO `file` VALUES ('994', 'name93', 'url93', null, 'category93', 'module93', 'ext93', '2018-01-04 17:31:03', '2018-01-04 17:31:03');
INSERT INTO `file` VALUES ('995', 'name94', 'url94', null, 'category94', 'module94', 'ext94', '2018-01-04 17:31:03', '2018-01-04 17:31:03');
INSERT INTO `file` VALUES ('996', 'name95', 'url95', null, 'category95', 'module95', 'ext95', '2018-01-04 17:31:03', '2018-01-04 17:31:03');
INSERT INTO `file` VALUES ('997', 'name96', 'url96', null, 'category96', 'module96', 'ext96', '2018-01-04 17:31:03', '2018-01-04 17:31:03');
INSERT INTO `file` VALUES ('998', 'name97', 'url97', null, 'category97', 'module97', 'ext97', '2018-01-04 17:31:03', '2018-01-04 17:31:03');
INSERT INTO `file` VALUES ('999', 'name98', 'url98', null, 'category98', 'module98', 'ext98', '2018-01-04 17:31:03', '2018-01-04 17:31:03');
INSERT INTO `file` VALUES ('1000', 'name99', 'url99', null, 'category99', 'module99', 'ext99', '2018-01-04 17:31:03', '2018-01-04 17:31:03');
INSERT INTO `file` VALUES ('1001', 'name100', 'url100', null, 'category100', 'module100', 'ext100', '2018-01-04 17:31:03', '2018-01-04 17:31:03');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `sign` varchar(255) DEFAULT NULL,
  `experience` varchar(255) DEFAULT NULL,
  `score` varchar(255) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=315 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('215', 'username1', 'password1', 'nickname1', 'sex1', 'sign1', 'experience1', 'score1', '2018-01-04 17:31:03', '2018-01-04 17:31:03');
INSERT INTO `user` VALUES ('216', 'username2', 'password2', 'nickname2', 'sex2', 'sign2', 'experience2', 'score2', '2018-01-04 17:31:03', '2018-01-04 17:31:03');
INSERT INTO `user` VALUES ('217', 'username3', 'password3', 'nickname3', 'sex3', 'sign3', 'experience3', 'score3', '2018-01-04 17:31:03', '2018-01-04 17:31:03');
INSERT INTO `user` VALUES ('218', 'username4', 'password4', 'nickname4', 'sex4', 'sign4', 'experience4', 'score4', '2018-01-04 17:31:04', '2018-01-04 17:31:04');
INSERT INTO `user` VALUES ('219', 'username5', 'password5', 'nickname5', 'sex5', 'sign5', 'experience5', 'score5', '2018-01-04 17:31:04', '2018-01-04 17:31:04');
INSERT INTO `user` VALUES ('220', 'username6', 'password6', 'nickname6', 'sex6', 'sign6', 'experience6', 'score6', '2018-01-04 17:31:05', '2018-01-04 17:31:05');
INSERT INTO `user` VALUES ('221', 'username7', 'password7', 'nickname7', 'sex7', 'sign7', 'experience7', 'score7', '2018-01-04 17:31:05', '2018-01-04 17:31:05');
INSERT INTO `user` VALUES ('222', 'username8', 'password8', 'nickname8', 'sex8', 'sign8', 'experience8', 'score8', '2018-01-04 17:31:05', '2018-01-04 17:31:05');
INSERT INTO `user` VALUES ('223', 'username9', 'password9', 'nickname9', 'sex9', 'sign9', 'experience9', 'score9', '2018-01-04 17:31:06', '2018-01-04 17:31:06');
INSERT INTO `user` VALUES ('224', 'username10', 'password10', 'nickname10', 'sex10', 'sign10', 'experience10', 'score10', '2018-01-04 17:31:07', '2018-01-04 17:31:07');
INSERT INTO `user` VALUES ('225', 'username11', 'password11', 'nickname11', 'sex11', 'sign11', 'experience11', 'score11', '2018-01-04 17:31:08', '2018-01-04 17:31:08');
INSERT INTO `user` VALUES ('226', 'username12', 'password12', 'nickname12', 'sex12', 'sign12', 'experience12', 'score12', '2018-01-04 17:31:09', '2018-01-04 17:31:09');
INSERT INTO `user` VALUES ('227', 'username13', 'password13', 'nickname13', 'sex13', 'sign13', 'experience13', 'score13', '2018-01-04 17:31:10', '2018-01-04 17:31:10');
INSERT INTO `user` VALUES ('228', 'username14', 'password14', 'nickname14', 'sex14', 'sign14', 'experience14', 'score14', '2018-01-04 17:31:11', '2018-01-04 17:31:11');
INSERT INTO `user` VALUES ('229', 'username15', 'password15', 'nickname15', 'sex15', 'sign15', 'experience15', 'score15', '2018-01-04 17:31:11', '2018-01-04 17:31:11');
INSERT INTO `user` VALUES ('230', 'username16', 'password16', 'nickname16', 'sex16', 'sign16', 'experience16', 'score16', '2018-01-04 17:31:12', '2018-01-04 17:31:12');
INSERT INTO `user` VALUES ('231', 'username17', 'password17', 'nickname17', 'sex17', 'sign17', 'experience17', 'score17', '2018-01-04 17:31:13', '2018-01-04 17:31:13');
INSERT INTO `user` VALUES ('232', 'username18', 'password18', 'nickname18', 'sex18', 'sign18', 'experience18', 'score18', '2018-01-04 17:31:14', '2018-01-04 17:31:14');
INSERT INTO `user` VALUES ('233', 'username19', 'password19', 'nickname19', 'sex19', 'sign19', 'experience19', 'score19', '2018-01-04 17:31:14', '2018-01-04 17:31:14');
INSERT INTO `user` VALUES ('234', 'username20', 'password20', 'nickname20', 'sex20', 'sign20', 'experience20', 'score20', '2018-01-04 17:31:15', '2018-01-04 17:31:15');
INSERT INTO `user` VALUES ('235', 'username21', 'password21', 'nickname21', 'sex21', 'sign21', 'experience21', 'score21', '2018-01-04 17:31:16', '2018-01-04 17:31:16');
INSERT INTO `user` VALUES ('236', 'username22', 'password22', 'nickname22', 'sex22', 'sign22', 'experience22', 'score22', '2018-01-04 17:31:16', '2018-01-04 17:31:16');
INSERT INTO `user` VALUES ('237', 'username23', 'password23', 'nickname23', 'sex23', 'sign23', 'experience23', 'score23', '2018-01-04 17:31:17', '2018-01-04 17:31:17');
INSERT INTO `user` VALUES ('238', 'username24', 'password24', 'nickname24', 'sex24', 'sign24', 'experience24', 'score24', '2018-01-04 17:31:18', '2018-01-04 17:31:18');
INSERT INTO `user` VALUES ('239', 'username25', 'password25', 'nickname25', 'sex25', 'sign25', 'experience25', 'score25', '2018-01-04 17:31:18', '2018-01-04 17:31:18');
INSERT INTO `user` VALUES ('240', 'username26', 'password26', 'nickname26', 'sex26', 'sign26', 'experience26', 'score26', '2018-01-04 17:31:19', '2018-01-04 17:31:19');
INSERT INTO `user` VALUES ('241', 'username27', 'password27', 'nickname27', 'sex27', 'sign27', 'experience27', 'score27', '2018-01-04 17:31:19', '2018-01-04 17:31:19');
INSERT INTO `user` VALUES ('242', 'username28', 'password28', 'nickname28', 'sex28', 'sign28', 'experience28', 'score28', '2018-01-04 17:31:19', '2018-01-04 17:31:19');
INSERT INTO `user` VALUES ('243', 'username29', 'password29', 'nickname29', 'sex29', 'sign29', 'experience29', 'score29', '2018-01-04 17:31:19', '2018-01-04 17:31:19');
INSERT INTO `user` VALUES ('244', 'username30', 'password30', 'nickname30', 'sex30', 'sign30', 'experience30', 'score30', '2018-01-04 17:31:19', '2018-01-04 17:31:19');
INSERT INTO `user` VALUES ('245', 'username31', 'password31', 'nickname31', 'sex31', 'sign31', 'experience31', 'score31', '2018-01-04 17:31:19', '2018-01-04 17:31:19');
INSERT INTO `user` VALUES ('246', 'username32', 'password32', 'nickname32', 'sex32', 'sign32', 'experience32', 'score32', '2018-01-04 17:31:19', '2018-01-04 17:31:19');
INSERT INTO `user` VALUES ('247', 'username33', 'password33', 'nickname33', 'sex33', 'sign33', 'experience33', 'score33', '2018-01-04 17:31:20', '2018-01-04 17:31:20');
INSERT INTO `user` VALUES ('248', 'username34', 'password34', 'nickname34', 'sex34', 'sign34', 'experience34', 'score34', '2018-01-04 17:31:20', '2018-01-04 17:31:20');
INSERT INTO `user` VALUES ('249', 'username35', 'password35', 'nickname35', 'sex35', 'sign35', 'experience35', 'score35', '2018-01-04 17:31:20', '2018-01-04 17:31:20');
INSERT INTO `user` VALUES ('250', 'username36', 'password36', 'nickname36', 'sex36', 'sign36', 'experience36', 'score36', '2018-01-04 17:31:21', '2018-01-04 17:31:21');
INSERT INTO `user` VALUES ('251', 'username37', 'password37', 'nickname37', 'sex37', 'sign37', 'experience37', 'score37', '2018-01-04 17:31:21', '2018-01-04 17:31:21');
INSERT INTO `user` VALUES ('252', 'username38', 'password38', 'nickname38', 'sex38', 'sign38', 'experience38', 'score38', '2018-01-04 17:31:21', '2018-01-04 17:31:21');
INSERT INTO `user` VALUES ('253', 'username39', 'password39', 'nickname39', 'sex39', 'sign39', 'experience39', 'score39', '2018-01-04 17:31:21', '2018-01-04 17:31:21');
INSERT INTO `user` VALUES ('254', 'username40', 'password40', 'nickname40', 'sex40', 'sign40', 'experience40', 'score40', '2018-01-04 17:31:21', '2018-01-04 17:31:21');
INSERT INTO `user` VALUES ('255', 'username41', 'password41', 'nickname41', 'sex41', 'sign41', 'experience41', 'score41', '2018-01-04 17:31:21', '2018-01-04 17:31:21');
INSERT INTO `user` VALUES ('256', 'username42', 'password42', 'nickname42', 'sex42', 'sign42', 'experience42', 'score42', '2018-01-04 17:31:21', '2018-01-04 17:31:21');
INSERT INTO `user` VALUES ('257', 'username43', 'password43', 'nickname43', 'sex43', 'sign43', 'experience43', 'score43', '2018-01-04 17:31:22', '2018-01-04 17:31:22');
INSERT INTO `user` VALUES ('258', 'username44', 'password44', 'nickname44', 'sex44', 'sign44', 'experience44', 'score44', '2018-01-04 17:31:22', '2018-01-04 17:31:22');
INSERT INTO `user` VALUES ('259', 'username45', 'password45', 'nickname45', 'sex45', 'sign45', 'experience45', 'score45', '2018-01-04 17:31:23', '2018-01-04 17:31:23');
INSERT INTO `user` VALUES ('260', 'username46', 'password46', 'nickname46', 'sex46', 'sign46', 'experience46', 'score46', '2018-01-04 17:31:23', '2018-01-04 17:31:23');
INSERT INTO `user` VALUES ('261', 'username47', 'password47', 'nickname47', 'sex47', 'sign47', 'experience47', 'score47', '2018-01-04 17:31:23', '2018-01-04 17:31:23');
INSERT INTO `user` VALUES ('262', 'username48', 'password48', 'nickname48', 'sex48', 'sign48', 'experience48', 'score48', '2018-01-04 17:31:23', '2018-01-04 17:31:23');
INSERT INTO `user` VALUES ('263', 'username49', 'password49', 'nickname49', 'sex49', 'sign49', 'experience49', 'score49', '2018-01-04 17:31:24', '2018-01-04 17:31:24');
INSERT INTO `user` VALUES ('264', 'username50', 'password50', 'nickname50', 'sex50', 'sign50', 'experience50', 'score50', '2018-01-04 17:31:24', '2018-01-04 17:31:24');
INSERT INTO `user` VALUES ('265', 'username51', 'password51', 'nickname51', 'sex51', 'sign51', 'experience51', 'score51', '2018-01-04 17:31:25', '2018-01-04 17:31:25');
INSERT INTO `user` VALUES ('266', 'username52', 'password52', 'nickname52', 'sex52', 'sign52', 'experience52', 'score52', '2018-01-04 17:31:25', '2018-01-04 17:31:25');
INSERT INTO `user` VALUES ('267', 'username53', 'password53', 'nickname53', 'sex53', 'sign53', 'experience53', 'score53', '2018-01-04 17:31:25', '2018-01-04 17:31:25');
INSERT INTO `user` VALUES ('268', 'username54', 'password54', 'nickname54', 'sex54', 'sign54', 'experience54', 'score54', '2018-01-04 17:31:26', '2018-01-04 17:31:26');
INSERT INTO `user` VALUES ('269', 'username55', 'password55', 'nickname55', 'sex55', 'sign55', 'experience55', 'score55', '2018-01-04 17:31:26', '2018-01-04 17:31:26');
INSERT INTO `user` VALUES ('270', 'username56', 'password56', 'nickname56', 'sex56', 'sign56', 'experience56', 'score56', '2018-01-04 17:31:27', '2018-01-04 17:31:27');
INSERT INTO `user` VALUES ('271', 'username57', 'password57', 'nickname57', 'sex57', 'sign57', 'experience57', 'score57', '2018-01-04 17:31:28', '2018-01-04 17:31:28');
INSERT INTO `user` VALUES ('272', 'username58', 'password58', 'nickname58', 'sex58', 'sign58', 'experience58', 'score58', '2018-01-04 17:31:29', '2018-01-04 17:31:29');
INSERT INTO `user` VALUES ('273', 'username59', 'password59', 'nickname59', 'sex59', 'sign59', 'experience59', 'score59', '2018-01-04 17:31:29', '2018-01-04 17:31:29');
INSERT INTO `user` VALUES ('274', 'username60', 'password60', 'nickname60', 'sex60', 'sign60', 'experience60', 'score60', '2018-01-04 17:31:29', '2018-01-04 17:31:29');
INSERT INTO `user` VALUES ('275', 'username61', 'password61', 'nickname61', 'sex61', 'sign61', 'experience61', 'score61', '2018-01-04 17:31:29', '2018-01-04 17:31:29');
INSERT INTO `user` VALUES ('276', 'username62', 'password62', 'nickname62', 'sex62', 'sign62', 'experience62', 'score62', '2018-01-04 17:31:29', '2018-01-04 17:31:29');
INSERT INTO `user` VALUES ('277', 'username63', 'password63', 'nickname63', 'sex63', 'sign63', 'experience63', 'score63', '2018-01-04 17:31:29', '2018-01-04 17:31:29');
INSERT INTO `user` VALUES ('278', 'username64', 'password64', 'nickname64', 'sex64', 'sign64', 'experience64', 'score64', '2018-01-04 17:31:30', '2018-01-04 17:31:30');
INSERT INTO `user` VALUES ('279', 'username65', 'password65', 'nickname65', 'sex65', 'sign65', 'experience65', 'score65', '2018-01-04 17:31:30', '2018-01-04 17:31:30');
INSERT INTO `user` VALUES ('280', 'username66', 'password66', 'nickname66', 'sex66', 'sign66', 'experience66', 'score66', '2018-01-04 17:31:30', '2018-01-04 17:31:30');
INSERT INTO `user` VALUES ('281', 'username67', 'password67', 'nickname67', 'sex67', 'sign67', 'experience67', 'score67', '2018-01-04 17:31:30', '2018-01-04 17:31:30');
INSERT INTO `user` VALUES ('282', 'username68', 'password68', 'nickname68', 'sex68', 'sign68', 'experience68', 'score68', '2018-01-04 17:31:30', '2018-01-04 17:31:30');
INSERT INTO `user` VALUES ('283', 'username69', 'password69', 'nickname69', 'sex69', 'sign69', 'experience69', 'score69', '2018-01-04 17:31:31', '2018-01-04 17:31:31');
INSERT INTO `user` VALUES ('284', 'username70', 'password70', 'nickname70', 'sex70', 'sign70', 'experience70', 'score70', '2018-01-04 17:31:31', '2018-01-04 17:31:31');
INSERT INTO `user` VALUES ('285', 'username71', 'password71', 'nickname71', 'sex71', 'sign71', 'experience71', 'score71', '2018-01-04 17:31:31', '2018-01-04 17:31:31');
INSERT INTO `user` VALUES ('286', 'username72', 'password72', 'nickname72', 'sex72', 'sign72', 'experience72', 'score72', '2018-01-04 17:31:31', '2018-01-04 17:31:31');
INSERT INTO `user` VALUES ('287', 'username73', 'password73', 'nickname73', 'sex73', 'sign73', 'experience73', 'score73', '2018-01-04 17:31:31', '2018-01-04 17:31:31');
INSERT INTO `user` VALUES ('288', 'username74', 'password74', 'nickname74', 'sex74', 'sign74', 'experience74', 'score74', '2018-01-04 17:31:31', '2018-01-04 17:31:31');
INSERT INTO `user` VALUES ('289', 'username75', 'password75', 'nickname75', 'sex75', 'sign75', 'experience75', 'score75', '2018-01-04 17:31:31', '2018-01-04 17:31:31');
INSERT INTO `user` VALUES ('290', 'username76', 'password76', 'nickname76', 'sex76', 'sign76', 'experience76', 'score76', '2018-01-04 17:31:31', '2018-01-04 17:31:31');
INSERT INTO `user` VALUES ('291', 'username77', 'password77', 'nickname77', 'sex77', 'sign77', 'experience77', 'score77', '2018-01-04 17:31:32', '2018-01-04 17:31:32');
INSERT INTO `user` VALUES ('292', 'username78', 'password78', 'nickname78', 'sex78', 'sign78', 'experience78', 'score78', '2018-01-04 17:31:32', '2018-01-04 17:31:32');
INSERT INTO `user` VALUES ('293', 'username79', 'password79', 'nickname79', 'sex79', 'sign79', 'experience79', 'score79', '2018-01-04 17:31:33', '2018-01-04 17:31:33');
INSERT INTO `user` VALUES ('294', 'username80', 'password80', 'nickname80', 'sex80', 'sign80', 'experience80', 'score80', '2018-01-04 17:31:33', '2018-01-04 17:31:33');
INSERT INTO `user` VALUES ('295', 'username81', 'password81', 'nickname81', 'sex81', 'sign81', 'experience81', 'score81', '2018-01-04 17:31:33', '2018-01-04 17:31:33');
INSERT INTO `user` VALUES ('296', 'username82', 'password82', 'nickname82', 'sex82', 'sign82', 'experience82', 'score82', '2018-01-04 17:31:33', '2018-01-04 17:31:33');
INSERT INTO `user` VALUES ('297', 'username83', 'password83', 'nickname83', 'sex83', 'sign83', 'experience83', 'score83', '2018-01-04 17:31:34', '2018-01-04 17:31:34');
INSERT INTO `user` VALUES ('298', 'username84', 'password84', 'nickname84', 'sex84', 'sign84', 'experience84', 'score84', '2018-01-04 17:31:34', '2018-01-04 17:31:34');
INSERT INTO `user` VALUES ('299', 'username85', 'password85', 'nickname85', 'sex85', 'sign85', 'experience85', 'score85', '2018-01-04 17:31:34', '2018-01-04 17:31:34');
INSERT INTO `user` VALUES ('300', 'username86', 'password86', 'nickname86', 'sex86', 'sign86', 'experience86', 'score86', '2018-01-04 17:31:34', '2018-01-04 17:31:34');
INSERT INTO `user` VALUES ('301', 'username87', 'password87', 'nickname87', 'sex87', 'sign87', 'experience87', 'score87', '2018-01-04 17:31:34', '2018-01-04 17:31:34');
INSERT INTO `user` VALUES ('302', 'username88', 'password88', 'nickname88', 'sex88', 'sign88', 'experience88', 'score88', '2018-01-04 17:31:34', '2018-01-04 17:31:34');
INSERT INTO `user` VALUES ('303', 'username89', 'password89', 'nickname89', 'sex89', 'sign89', 'experience89', 'score89', '2018-01-04 17:31:34', '2018-01-04 17:31:34');
INSERT INTO `user` VALUES ('304', 'username90', 'password90', 'nickname90', 'sex90', 'sign90', 'experience90', 'score90', '2018-01-04 17:31:35', '2018-01-04 17:31:35');
INSERT INTO `user` VALUES ('305', 'username91', 'password91', 'nickname91', 'sex91', 'sign91', 'experience91', 'score91', '2018-01-04 17:31:35', '2018-01-04 17:31:35');
INSERT INTO `user` VALUES ('306', 'username92', 'password92', 'nickname92', 'sex92', 'sign92', 'experience92', 'score92', '2018-01-04 17:31:35', '2018-01-04 17:31:35');
INSERT INTO `user` VALUES ('307', 'username93', 'password93', 'nickname93', 'sex93', 'sign93', 'experience93', 'score93', '2018-01-04 17:31:35', '2018-01-04 17:31:35');
INSERT INTO `user` VALUES ('308', 'username94', 'password94', 'nickname94', 'sex94', 'sign94', 'experience94', 'score94', '2018-01-04 17:31:35', '2018-01-04 17:31:35');
INSERT INTO `user` VALUES ('309', 'username95', 'password95', 'nickname95', 'sex95', 'sign95', 'experience95', 'score95', '2018-01-04 17:31:35', '2018-01-04 17:31:35');
INSERT INTO `user` VALUES ('310', 'username96', 'password96', 'nickname96', 'sex96', 'sign96', 'experience96', 'score96', '2018-01-04 17:31:35', '2018-01-04 17:31:35');
INSERT INTO `user` VALUES ('311', 'username97', 'password97', 'nickname97', 'sex97', 'sign97', 'experience97', 'score97', '2018-01-04 17:31:35', '2018-01-04 17:31:35');
INSERT INTO `user` VALUES ('312', 'username98', 'password98', 'nickname98', 'sex98', 'sign98', 'experience98', 'score98', '2018-01-04 17:31:35', '2018-01-04 17:31:35');
INSERT INTO `user` VALUES ('313', 'username99', 'password99', 'nickname99', 'sex99', 'sign99', 'experience99', 'score99', '2018-01-04 17:31:35', '2018-01-04 17:31:35');
INSERT INTO `user` VALUES ('314', 'username100', 'password100', 'nickname100', 'sex100', 'sign100', 'experience100', 'score100', '2018-01-04 17:31:35', '2018-01-04 17:31:35');
