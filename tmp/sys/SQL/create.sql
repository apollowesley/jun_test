create database test character set UTF8;
use test;
/*用户表 AUTO_INCREMENT */
 

create table users(
  id varchar(32)   primary key  ,
  name varchar(30),
  pwd varchar(32),
   email varchar(50),
  PASSWORD VARCHAR(20) 
);


 
 


/*创建角色表*/
create table roles(
  id varchar(32) primary key,
  name varchar(30),
  des  varchar(100)
);

/*通过一个中间表映射多对多的关系,多对多就是多个一对多
	联合主键的特点是：两个列不能同时重复
*/
create table roleuser(
   uid varchar(32),
   rid varchar(32) # ,
  # constraint ru_pk primary key(uid,rid) # ,
  # constraint ru_fk1 foreign key(uid) references users(id),
  # constraint ru_fk2 foreign key(rid) references roles(id)
);

/*创建菜单表*/
create table menus(
  id varchar(32) primary key,
  name varchar(50),
  url  varchar(100)
);
/*关联角色到菜单*/
create table rolemenu(
  mid varchar(32),
  rid varchar(32) # ,
  # constraint rm_pk primary key(mid,rid),
  # constraint rm_fk1 foreign key(mid) references menus(id),
   # constraint rm_fk2 foreign key(rid) references roles(id)
);

 
create table contacts(
  id varchar(32) not null,
  name varchar(30),
  sex char(1),
  tel varchar(20),
  addr varchar(100),
  uid varchar(32) # ,
 # constraint c_pk primary key(id),
#  constraint c_fk foreign key(uid) references users(id)
);




create table imgs(
   id varchar(32) primary key,
   oldname varchar(100),
   newname varchar(100),
   dt char(19),
   ip varchar(20),
   note varchar(100)
);




create table active(
  uid varchar(32) primary key,
  code varchar(64) # ,
#  constraint a_fk foreign key(uid) references users(id)
);







CREATE TABLE student(id INT PRIMARY KEY AUTO_INCREMENT,NAME VARCHAR(20),gender VARCHAR(2));




CREATE TABLE person(
  id INT PRIMARY KEY,
  NAME VARCHAR(30)
);
CREATE TABLE cars(
  id INT PRIMARY KEY,
  NAME VARCHAR(30),
  price NUMERIC(5,2),
  pid INT
);
 # ALTER TABLE cars ADD CONSTRAINT c_fk FOREIGN KEY(pid) REFERENCES person(id);
   
  

CREATE TABLE card(
  id INT PRIMARY KEY,
  gov VARCHAR(30) # ,
  #  CONSTRAINT card_fk FOREIGN KEY(id) REFERENCES person(id)
)




CREATE TABLE b_user(
id INT,
NAME VARCHAR(20),
PASSWORD VARCHAR(15),
age int
);

CREATE TABLE bt_user(
id INT PRIMARY KEY AUTO_INCREMENT,
NAME VARCHAR(20),
resume TEXT,
headimage blob
);


CREATE TABLE m_user(
id INT PRIMARY KEY AUTO_INCREMENT,
NAME VARCHAR(20),
PASSWORD VARCHAR(15),
age int
);


CREATE TABLE p_user(
id INT PRIMARY KEY AUTO_INCREMENT,
NAME VARCHAR(20),
PASSWORD VARCHAR(15),
age int
);


//准备mysql的环境：
create table rs_user(
id int,
name varchar(20),
email varchar(18)
)
//准备oracle的环境
create table rs_user(
id number,
name varchar2(20),
email varchar2(18)
)


CREATE TABLE s_user(
	id INT PRIMARY KEY AUTO_INCREMENT,
	NAME VARCHAR(20),
	PASSWORD VARCHAR(15)
)

CREATE TABLE student(
	id INT PRIMARY KEY,
	NAME VARCHAR(21),
	sex CHAR(2),
	grade DOUBLE(5,2)
);



 #账户基本信息表
 create table account
 (
    accountid   varchar(18) ,  #账号
    balance     double(10,2)   #余额
 )
#存款表
 create table inaccount
(
	accountid   varchar(18) ,  #账号
 	inbalance     double(10,2) #存入余额
 )

 CREATE TABLE b_user(
id number,
NAME VARCHAR2(20),
PASSWORD VARCHAR2(15),
age number
);

//准备mysql的环境：
create table rs_user(
id int,
name varchar(20),
email varchar(18)
)
//准备oracle的环境
create table rs_user(
id number,
name varchar2(20),
email varchar2(18)
)



CREATE TABLE vs_user(
id number,
NAME VARCHAR2(20),
PASSWORD VARCHAR2(15),
age number
);

create table t_product(
id bigint primary key auto_increment,
model varchar(50),
picName varchar(20),
prodDesc varchar(100),
price double);


create table t_user(
id bigint primary key auto_increment,
username varchar(50) unique,
name varchar(20),
password varchar(20),
sex varchar(1));


create table t_product(
id bigint primary key auto_increment,
model varchar(50),
picName varchar(20),
prodDesc varchar(100),
price double);


create table t_account(
id bigint primary key auto_increment,
accountNo varchar(20) unique,
balance double);

create table t_stock(
id bigint primary key auto_increment,
stockNo varchar(20) unique,
qty double);


















































 
   INSERT INTO person VALUES(1,'Jack');
INSERT INTO person VALUES(2,'Tom');
INSERT INTO person VALUES(3,'Rose');

INSERT INTO cars VALUES(11,'BWM',89,1);
INSERT INTO cars VALUES(12,'Beaz',77,1);
INSERT INTO cars VALUES(13,'QQ',4,3);


INSERT INTO person VALUES(1,'Jack');
INSERT INTO person VALUES(2,'Tom');
INSERT INTO person VALUES(3,'Rose');

INSERT INTO cars VALUES(11,'BWM',89,1);
INSERT INTO cars VALUES(12,'Beaz',77,1);
INSERT INTO cars VALUES(13,'QQ',4,3);
INSERT INTO cars VALUES(14,'Cheay',7,NULL);


SELECT p.name,c.name
FROM person p INNER JOIN cars c ON p.id=c.pid;

/*外关系-左，右*/
SELECT p.name,c.name
FROM person p RIGHT JOIN cars c ON p.id=c.pid; 

/*查询没有车人*/
SELECT p.name,c.name 
FROM person p LEFT JOIN cars c ON p.id=c.pid
WHERE c.name IS NULL;

SELECT * FROM cars WHERE pid IS NULL;

INSERT INTO card VALUES(3,'北京公安局');

SELECT p.name,c.gov
FROM person p INNER JOIN card c ON p.id=c.id;

INSERT INTO m_user(NAME,PASSWORD,age) VALUES('aa','123',12);
INSERT INTO m_user(NAME,PASSWORD,age) VALUES('bb','1234',13);
INSERT INTO m_user(NAME,PASSWORD,age) VALUES('cc','1235',16);
INSERT INTO m_user(NAME,PASSWORD,age) VALUES('dd','1236',15);

insert into rs_user(id,name,email) values(1,'张三','xx@gmail.com');
insert into rs_user(id,name,email) values(2,'李四','tt@gmail.com');
insert into rs_user(id,name,email) values(3,'王五','qq@gmail.com');
insert into rs_user(id,name,email) values(5,'赵六','mm@gmail.com');
 #办卡完成，初始化数据
INSERT INTO account(accountid,balance) VALUES('123456789',0)
 



/*查询有两个车的人*/
SELECT * FROM 
person p WHERE (SELECT COUNT(*) FROM cars c WHERE p.id=c.pid)=1;

# DESC student;

# DROP TABLE student;

-- 插入记录
INSERT INTO student(NAME,gender) VALUES('张三','男');

-- 查询
SELECT * FROM student;

-- 更新
UPDATE student SET NAME='王五' WHERE id=1;

DELETE FROM student WHERE id=1;


-- 模拟用户登录
-- 用户表


INSERT INTO users(NAME,PASSWORD) VALUES('eric','123456');
INSERT INTO users(NAME,PASSWORD) VALUES('rose','654321');

SELECT * FROM users;

-- 登录成功：用户输入的用户名和密码和users表的某条记录匹配
-- 登录失败：  找不到匹配的数据
SELECT * FROM users WHERE NAME='eric' AND PASSWORD='123456';

SELECT * FROM users;

SELECT * FROM users WHERE 1=1; -- 1=1 表示恒成立(永远成立，永远真)

-- sql注入
SELECT * FROM users WHERE NAME='eric' OR 1=1 -- 'and password='123456';


-- SELECT * FROM users WHERE NAME='eric';
-- SELECT * FROM users WHERE 1=1;

-- 创建存储过程
 




insert into rs_user(id,name,email) values(1,'张三','xx@gmail.com');
insert into rs_user(id,name,email) values(2,'李四','tt@gmail.com');
insert into rs_user(id,name,email) values(3,'王五','qq@gmail.com');
insert into rs_user(id,name,email) values(5,'赵六','mm@gmail.com');

insert into users values('U001','Jack','1234');
insert into users values('U002','张三','4321');

insert into contacts values('C001','Rose','0','1234655','中国上海','U001');
insert into contacts values('C002','李四','1','1234655','中国上地','U001');
insert into contacts values('C003','王五','1','168655','北京上地','U001');
insert into contacts values('C004','马六','0','1334655','中国AAA上地','U002');
insert into contacts values('C005','赵七','1','1774655','中国afdadsfsa上地','U002');
 

insert into users values('U001','Jack','1234');
insert into users values('U002','张三','4321');
insert into users values('U003','Tom','1111');

insert into roles values('R001','管理员','');
insert into roles values('R002','教师','');

insert into roleuser values('U001','R001');
insert into roleuser values('U002','R002');

insert into menus values('M001','系统管理','/sys.jsp');
insert into menus values('M002','用户管理','/user.jsp');
insert into menus values('M003','角色管理','/role.jsp');

insert into rolemenu values('M001','R001');
insert into rolemenu values('M002','R001');
insert into rolemenu values('M003','R001');
insert into rolemenu values('M003','R002');




insert into t_account(accountNo,balance) values('100',1000);
insert into t_stock(stockNo,qty) values('s100',0);


