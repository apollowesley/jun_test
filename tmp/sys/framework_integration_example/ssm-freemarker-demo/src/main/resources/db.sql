USE test;

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user`( 
   `id` INT(10) NOT NULL  AUTO_INCREMENT, 
   `user_name` VARCHAR(50) NOT NULL , 
   `pass_word` VARCHAR(50) NOT NULL, 
   PRIMARY KEY (`id`)
 );
 
INSERT INTO `user` VALUES (1, 'xieke1', '1234586866');
INSERT INTO `user` VALUES (2, 'xieke2', '1234586866');
INSERT INTO `user` VALUES (3, 'xieke3', '1234586686');
INSERT INTO `user` VALUES (4, 'xieke4', '1234586686');
INSERT INTO `user` VALUES (5, 'xieke5', '1234586686');