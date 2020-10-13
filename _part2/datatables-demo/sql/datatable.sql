/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50520
Source Host           : localhost:3306
Source Database       : datatable

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2014-12-02 12:36:02
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `ajax`
-- ----------------------------
DROP TABLE IF EXISTS `ajax`;
CREATE TABLE `ajax` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `engine` varchar(255) NOT NULL DEFAULT '',
  `browser` varchar(255) NOT NULL DEFAULT '',
  `platform` varchar(255) NOT NULL DEFAULT '',
  `version` float NOT NULL DEFAULT '0',
  `grade` varchar(20) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=34 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of ajax
-- ----------------------------
INSERT INTO `ajax` VALUES ('1', 'Trident', 'Internet Explorer 4.0', 'Win 95+', '4', 'X');
INSERT INTO `ajax` VALUES ('2', 'Trident', 'Internet Explorer 5.0', 'Win 95+', '5', 'C');
INSERT INTO `ajax` VALUES ('3', 'Trident', 'Internet Explorer 5.5', 'Win 95+', '5.5', 'A');
INSERT INTO `ajax` VALUES ('4', 'Trident', 'Internet Explorer 6', 'Win 98+', '6', 'A');
INSERT INTO `ajax` VALUES ('5', 'Trident', 'Internet Explorer 7', 'Win XP SP2+', '7', 'A');
INSERT INTO `ajax` VALUES ('6', 'Trident', 'AOL browser (AOL desktop)', 'Win XP', '6', 'A');
INSERT INTO `ajax` VALUES ('7', 'Gecko', 'Firefox 1.0', 'Win 98+ / OSX.2+', '1.7', 'A');
INSERT INTO `ajax` VALUES ('8', 'Gecko', 'Firefox 1.5', 'Win 98+ / OSX.2+', '1.8', 'A');
INSERT INTO `ajax` VALUES ('9', 'Gecko', 'Firefox 2.0', 'Win 98+ / OSX.2+', '1.8', 'A');
INSERT INTO `ajax` VALUES ('10', 'Gecko', 'Firefox 3.0', 'Win 2k+ / OSX.3+', '1.9', 'A');
INSERT INTO `ajax` VALUES ('11', 'Gecko', 'Camino 1.0', 'OSX.2+', '1.8', 'A');
INSERT INTO `ajax` VALUES ('12', 'Gecko', 'Camino 1.5', 'OSX.3+', '1.8', 'A');
INSERT INTO `ajax` VALUES ('13', 'Gecko', 'Netscape 7.2', 'Win 95+ / Mac OS 8.6-9.2', '1.7', 'A');
INSERT INTO `ajax` VALUES ('14', 'Gecko', 'Netscape Browser 8', 'Win 98SE+', '1.7', 'A');
INSERT INTO `ajax` VALUES ('15', 'Gecko', 'Netscape Navigator 9', 'Win 98+ / OSX.2+', '1.8', 'A');
INSERT INTO `ajax` VALUES ('16', 'Gecko', 'Mozilla 1.0', 'Win 95+ / OSX.1+', '1', 'A');
INSERT INTO `ajax` VALUES ('17', 'Gecko', 'Mozilla 1.1', 'Win 95+ / OSX.1+', '1.1', 'A');
INSERT INTO `ajax` VALUES ('18', 'Gecko', 'Mozilla 1.2', 'Win 95+ / OSX.1+', '1.2', 'A');
INSERT INTO `ajax` VALUES ('19', 'Gecko', 'Mozilla 1.3', 'Win 95+ / OSX.1+', '1.3', 'A');
INSERT INTO `ajax` VALUES ('20', 'Gecko', 'Mozilla 1.4', 'Win 95+ / OSX.1+', '1.4', 'A');
INSERT INTO `ajax` VALUES ('21', 'Gecko', 'Mozilla 1.5', 'Win 95+ / OSX.1+', '1.5', 'A');
INSERT INTO `ajax` VALUES ('22', 'Gecko', 'Mozilla 1.6', 'Win 95+ / OSX.1+', '1.6', 'A');
INSERT INTO `ajax` VALUES ('23', 'Gecko', 'Mozilla 1.7', 'Win 98+ / OSX.1+', '1.7', 'A');
INSERT INTO `ajax` VALUES ('24', 'Gecko', 'Mozilla 1.8', 'Win 98+ / OSX.1+', '1.8', 'A');
INSERT INTO `ajax` VALUES ('25', 'Gecko', 'Seamonkey 1.1', 'Win 98+ / OSX.2+', '1.8', 'A');
INSERT INTO `ajax` VALUES ('26', 'Gecko', 'Epiphany 2.20', 'Gnome', '1.8', 'A');
INSERT INTO `ajax` VALUES ('27', 'Webkit', 'Safari 1.2', 'OSX.3', '125.5', 'A');
INSERT INTO `ajax` VALUES ('28', 'Webkit', 'Safari 1.3', 'OSX.3', '312.8', 'A');
INSERT INTO `ajax` VALUES ('29', 'Webkit', 'Safari 2.0', 'OSX.4+', '419.3', 'A');
INSERT INTO `ajax` VALUES ('30', 'Webkit', 'Safari 3.0', 'OSX.4+', '522.1', 'A');
INSERT INTO `ajax` VALUES ('31', 'Webkit', 'OmniWeb 5.5', 'OSX.4+', '420', 'A');
INSERT INTO `ajax` VALUES ('32', 'Webkit', 'iPod Touch / iPhone', 'iPod', '420.1', 'A');
INSERT INTO `ajax` VALUES ('33', 'Webkit', 'S60', 'S60', '413', 'A');

-- ----------------------------
-- Table structure for `datatables_demo`
-- ----------------------------
DROP TABLE IF EXISTS `datatables_demo`;
CREATE TABLE `datatables_demo` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(250) NOT NULL DEFAULT '',
  `last_name` varchar(250) NOT NULL DEFAULT '',
  `position` varchar(250) NOT NULL DEFAULT '',
  `email` varchar(250) NOT NULL DEFAULT '',
  `office` varchar(250) NOT NULL DEFAULT '',
  `start_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `age` int(8) DEFAULT NULL,
  `salary` int(8) DEFAULT NULL,
  `extn` varchar(8) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of datatables_demo
-- ----------------------------
INSERT INTO `datatables_demo` VALUES ('1', 'Tiger', 'Nixon', 'System Architect', 't.nixon@datatables.net', 'Edinburgh', '2011-04-25 00:00:00', '61', '320800', '5421');
INSERT INTO `datatables_demo` VALUES ('2', 'Garrett', 'Winters', 'Accountant', 'g.winters@datatables.net', 'Tokyo', '2011-07-25 00:00:00', '63', '170750', '8422');
INSERT INTO `datatables_demo` VALUES ('3', 'Ashton', 'Cox', 'Junior Technical Author', 'a.cox@datatables.net', 'San Francisco', '2009-01-12 00:00:00', '66', '86000', '1562');
INSERT INTO `datatables_demo` VALUES ('4', 'Cedric', 'Kelly', 'Senior Javascript Developer', 'c.kelly@datatables.net', 'Edinburgh', '2012-03-29 00:00:00', '22', '433060', '6224');
INSERT INTO `datatables_demo` VALUES ('5', 'Airi', 'Satou', 'Accountant', 'a.satou@datatables.net', 'Tokyo', '2008-11-28 00:00:00', '33', '162700', '5407');
INSERT INTO `datatables_demo` VALUES ('6', 'Brielle', 'Williamson', 'Integration Specialist', 'b.williamson@datatables.net', 'New York', '2012-12-02 00:00:00', '61', '372000', '4804');
INSERT INTO `datatables_demo` VALUES ('7', 'Herrod', 'Chandler', 'Sales Assistant', 'h.chandler@datatables.net', 'San Francisco', '2012-08-06 00:00:00', '59', '137500', '9608');
INSERT INTO `datatables_demo` VALUES ('8', 'Rhona', 'Davidson', 'Integration Specialist', 'r.davidson@datatables.net', 'Tokyo', '2010-10-14 00:00:00', '55', '327900', '6200');
INSERT INTO `datatables_demo` VALUES ('9', 'Colleen', 'Hurst', 'Javascript Developer', 'c.hurst@datatables.net', 'San Francisco', '2009-09-15 00:00:00', '39', '205500', '2360');
INSERT INTO `datatables_demo` VALUES ('10', 'Sonya', 'Frost', 'Software Engineer', 's.frost@datatables.net', 'Edinburgh', '2008-12-13 00:00:00', '23', '103600', '1667');
INSERT INTO `datatables_demo` VALUES ('11', 'Jena', 'Gaines', 'Office Manager', 'j.gaines@datatables.net', 'London', '2008-12-19 00:00:00', '30', '90560', '3814');
INSERT INTO `datatables_demo` VALUES ('12', 'Quinn', 'Flynn', 'Support Lead', 'q.flynn@datatables.net', 'Edinburgh', '2013-03-03 00:00:00', '22', '342000', '9497');
INSERT INTO `datatables_demo` VALUES ('13', 'Charde', 'Marshall', 'Regional Director', 'c.marshall@datatables.net', 'San Francisco', '2008-10-16 00:00:00', '36', '470600', '6741');
INSERT INTO `datatables_demo` VALUES ('14', 'Haley', 'Kennedy', 'Senior Marketing Designer', 'h.kennedy@datatables.net', 'London', '2012-12-18 00:00:00', '43', '313500', '3597');
INSERT INTO `datatables_demo` VALUES ('15', 'Tatyana', 'Fitzpatrick', 'Regional Director', 't.fitzpatrick@datatables.net', 'London', '2010-03-17 00:00:00', '19', '385750', '1965');
INSERT INTO `datatables_demo` VALUES ('16', 'Michael', 'Silva', 'Marketing Designer', 'm.silva@datatables.net', 'London', '2012-11-27 00:00:00', '66', '198500', '1581');
INSERT INTO `datatables_demo` VALUES ('17', 'Paul', 'Byrd', 'Chief Financial Officer (CFO)', 'p.byrd@datatables.net', 'New York', '2010-06-09 00:00:00', '64', '725000', '3059');
INSERT INTO `datatables_demo` VALUES ('18', 'Gloria', 'Little', 'Systems Administrator', 'g.little@datatables.net', 'New York', '2009-04-10 00:00:00', '59', '237500', '1721');
INSERT INTO `datatables_demo` VALUES ('19', 'Bradley', 'Greer', 'Software Engineer', 'b.greer@datatables.net', 'London', '2012-10-13 00:00:00', '41', '132000', '2558');
INSERT INTO `datatables_demo` VALUES ('20', 'Dai', 'Rios', 'Personnel Lead', 'd.rios@datatables.net', 'Edinburgh', '2012-09-26 00:00:00', '35', '217500', '2290');
INSERT INTO `datatables_demo` VALUES ('21', 'Jenette', 'Caldwell', 'Development Lead', 'j.caldwell@datatables.net', 'New York', '2011-09-03 00:00:00', '30', '345000', '1937');
INSERT INTO `datatables_demo` VALUES ('22', 'Yuri', 'Berry', 'Chief Marketing Officer (CMO)', 'y.berry@datatables.net', 'New York', '2009-06-25 00:00:00', '40', '675000', '6154');
INSERT INTO `datatables_demo` VALUES ('23', 'Caesar', 'Vance', 'Pre-Sales Support', 'c.vance@datatables.net', 'New York', '2011-12-12 00:00:00', '21', '106450', '8330');
INSERT INTO `datatables_demo` VALUES ('24', 'Doris', 'Wilder', 'Sales Assistant', 'd.wilder@datatables.net', 'Sidney', '2010-09-20 00:00:00', '23', '85600', '3023');
INSERT INTO `datatables_demo` VALUES ('25', 'Angelica', 'Ramos', 'Chief Executive Officer (CEO)', 'a.ramos@datatables.net', 'London', '2009-10-09 00:00:00', '47', '1200000', '5797');
INSERT INTO `datatables_demo` VALUES ('26', 'Gavin', 'Joyce', 'Developer', 'g.joyce@datatables.net', 'Edinburgh', '2010-12-22 00:00:00', '42', '92575', '8822');
INSERT INTO `datatables_demo` VALUES ('27', 'Jennifer', 'Chang', 'Regional Director', 'j.chang@datatables.net', 'Singapore', '2010-11-14 00:00:00', '28', '357650', '9239');
INSERT INTO `datatables_demo` VALUES ('28', 'Brenden', 'Wagner', 'Software Engineer', 'b.wagner@datatables.net', 'San Francisco', '2011-06-07 00:00:00', '28', '206850', '1314');
INSERT INTO `datatables_demo` VALUES ('29', 'Fiona', 'Green', 'Chief Operating Officer (COO)', 'f.green@datatables.net', 'San Francisco', '2010-03-11 00:00:00', '48', '850000', '2947');
INSERT INTO `datatables_demo` VALUES ('30', 'Shou', 'Itou', 'Regional Marketing', 's.itou@datatables.net', 'Tokyo', '2011-08-14 00:00:00', '20', '163000', '8899');
INSERT INTO `datatables_demo` VALUES ('31', 'Michelle', 'House', 'Integration Specialist', 'm.house@datatables.net', 'Sidney', '2011-06-02 00:00:00', '37', '95400', '2769');
INSERT INTO `datatables_demo` VALUES ('32', 'Suki', 'Burks', 'Developer', 's.burks@datatables.net', 'London', '2009-10-22 00:00:00', '53', '114500', '6832');
INSERT INTO `datatables_demo` VALUES ('33', 'Prescott', 'Bartlett', 'Technical Author', 'p.bartlett@datatables.net', 'London', '2011-05-07 00:00:00', '27', '145000', '3606');
INSERT INTO `datatables_demo` VALUES ('34', 'Gavin', 'Cortez', 'Team Leader', 'g.cortez@datatables.net', 'San Francisco', '2008-10-26 00:00:00', '22', '235500', '2860');
INSERT INTO `datatables_demo` VALUES ('35', 'Martena', 'Mccray', 'Post-Sales support', 'm.mccray@datatables.net', 'Edinburgh', '2011-03-09 00:00:00', '46', '324050', '8240');
INSERT INTO `datatables_demo` VALUES ('36', 'Unity', 'Butler', 'Marketing Designer', 'u.butler@datatables.net', 'San Francisco', '2009-12-09 00:00:00', '47', '85675', '5384');
INSERT INTO `datatables_demo` VALUES ('37', 'Howard', 'Hatfield', 'Office Manager', 'h.hatfield@datatables.net', 'San Francisco', '2008-12-16 00:00:00', '51', '164500', '7031');
INSERT INTO `datatables_demo` VALUES ('38', 'Hope', 'Fuentes', 'Secretary', 'h.fuentes@datatables.net', 'San Francisco', '2010-02-12 00:00:00', '41', '109850', '6318');
INSERT INTO `datatables_demo` VALUES ('39', 'Vivian', 'Harrell', 'Financial Controller', 'v.harrell@datatables.net', 'San Francisco', '2009-02-14 00:00:00', '62', '452500', '9422');
INSERT INTO `datatables_demo` VALUES ('40', 'Timothy', 'Mooney', 'Office Manager', 't.mooney@datatables.net', 'London', '2008-12-11 00:00:00', '37', '136200', '7580');
INSERT INTO `datatables_demo` VALUES ('41', 'Jackson', 'Bradshaw', 'Director', 'j.bradshaw@datatables.net', 'New York', '2008-09-26 00:00:00', '65', '645750', '1042');
INSERT INTO `datatables_demo` VALUES ('42', 'Olivia', 'Liang', 'Support Engineer', 'o.liang@datatables.net', 'Singapore', '2011-02-03 00:00:00', '64', '234500', '2120');
INSERT INTO `datatables_demo` VALUES ('43', 'Bruno', 'Nash', 'Software Engineer', 'b.nash@datatables.net', 'London', '2011-05-03 00:00:00', '38', '163500', '6222');
INSERT INTO `datatables_demo` VALUES ('44', 'Sakura', 'Yamamoto', 'Support Engineer', 's.yamamoto@datatables.net', 'Tokyo', '2009-08-19 00:00:00', '37', '139575', '9383');
INSERT INTO `datatables_demo` VALUES ('45', 'Thor', 'Walton', 'Developer', 't.walton@datatables.net', 'New York', '2013-08-11 00:00:00', '61', '98540', '8327');
INSERT INTO `datatables_demo` VALUES ('46', 'Finn', 'Camacho', 'Support Engineer', 'f.camacho@datatables.net', 'San Francisco', '2009-07-07 00:00:00', '47', '87500', '2927');
INSERT INTO `datatables_demo` VALUES ('47', 'Serge', 'Baldwin', 'Data Coordinator', 's.baldwin@datatables.net', 'Singapore', '2012-04-09 00:00:00', '64', '138575', '8352');
INSERT INTO `datatables_demo` VALUES ('48', 'Zenaida', 'Frank', 'Software Engineer', 'z.frank@datatables.net', 'New York', '2010-01-04 00:00:00', '63', '125250', '7439');
INSERT INTO `datatables_demo` VALUES ('49', 'Zorita', 'Serrano', 'Software Engineer', 'z.serrano@datatables.net', 'San Francisco', '2012-06-01 00:00:00', '56', '115000', '4389');
INSERT INTO `datatables_demo` VALUES ('50', 'Jennifer', 'Acosta', 'Junior Javascript Developer', 'j.acosta@datatables.net', 'Edinburgh', '2013-02-01 00:00:00', '43', '75650', '3431');
INSERT INTO `datatables_demo` VALUES ('51', 'Cara', 'Stevens', 'Sales Assistant', 'c.stevens@datatables.net', 'New York', '2011-12-06 00:00:00', '46', '145600', '3990');
INSERT INTO `datatables_demo` VALUES ('52', 'Hermione', 'Butler', 'Regional Director', 'h.butler@datatables.net', 'London', '2011-03-21 00:00:00', '47', '356250', '1016');
INSERT INTO `datatables_demo` VALUES ('53', 'Lael', 'Greer', 'Systems Administrator', 'l.greer@datatables.net', 'London', '2009-02-27 00:00:00', '21', '103500', '6733');
INSERT INTO `datatables_demo` VALUES ('54', 'Jonas', 'Alexander', 'Developer', 'j.alexander@datatables.net', 'San Francisco', '2010-07-14 00:00:00', '30', '86500', '8196');
INSERT INTO `datatables_demo` VALUES ('55', 'Shad', 'Decker', 'Regional Director', 's.decker@datatables.net', 'Edinburgh', '2008-11-13 00:00:00', '51', '183000', '6373');
INSERT INTO `datatables_demo` VALUES ('56', 'Michael', 'Bruce', 'Javascript Developer', 'm.bruce@datatables.net', 'Singapore', '2011-06-27 00:00:00', '29', '183000', '5384');
INSERT INTO `datatables_demo` VALUES ('57', 'Donna', 'Snider', 'Customer Support', 'd.snider@datatables.net', 'New York', '2011-01-25 00:00:00', '27', '112000', '4226');
