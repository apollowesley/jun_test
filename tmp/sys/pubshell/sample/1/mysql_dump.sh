#!/bin/bash


 mysql mysqldump 只导出表结构 不导出数据
复制代码 代码如下:

mysqldump --opt -d 数据库名 -u root -p > xxx.sql

备份数据库
复制代码 代码如下:

#mysqldump　数据库名　>数据库备份名
#mysqldump　-A　-u用户名　-p密码　数据库名>数据库备份名
#mysqldump　-d　-A　--add-drop-table　-uroot　-p　>xxx.sql

1.导出结构不导出数据
复制代码 代码如下:

mysqldump　--opt　-d　数据库名　-u　root　-p　>　xxx.sql　　

2.导出数据不导出结构
复制代码 代码如下:

mysqldump　-t　数据库名　-uroot　-p　>　xxx.sql　

3.导出数据和表结构
复制代码 代码如下:

mysqldump　数据库名　-uroot　-p　>　xxx.sql　

4.导出特定表的结构
复制代码 代码如下:

mysqldump　-uroot　-p　-B　数据库名　--table　表名　>　xxx.sql　　

导入数据：
　　由于mysqldump导出的是完整的SQL语句，所以用mysql客户程序很容易就能把数据导入了：
复制代码 代码如下:

#mysql　数据库名　<　文件名
#source　/tmp/xxx.sql　　


MYSQLdump参数详解

mysqldump备份：

复制代码 代码如下:

mysqldump -u用户名 -p密码 -h主机 数据库 a -w “sql条件” –lock-all-tables > 路径

mysqldump还原：

复制代码 代码如下:

mysqldump -u用户名 -p密码 -h主机 数据库 < 路径

mysqldump按条件导出：

复制代码 代码如下:

mysqldump -u用户名 -p密码 -h主机 数据库 a –where “条件语句” –no-建表> 路径
mysqldump -uroot -p1234 dbname a –where “tag='88′” –no-create-info> c:\a.sql

mysqldump按条件导入：

复制代码 代码如下:

mysqldump -u用户名 -p密码 -h主机 数据库 < 路径

案例：

复制代码 代码如下:

mysql -uroot -p1234 db1 < c:\a.txt

mysqldump导出表：

复制代码 代码如下:

mysqldump -u用户名 -p密码 -h主机 数据库 表

案例：mysqldump -uroot -p sqlhk9 a –no-data

主要参数

–compatible=name
它告诉 mysqldump，导出的数据将和哪种数据库或哪个旧版本的 MySQL 服务器相兼容。值可以为 ansi、mysql323、mysql40、postgresql、oracle、mssql、db2、maxdb、no_key_options、no_tables_options、no_field_options 等，要使用几个值，用逗号将它们隔开。当然了，它并不保证能完全兼容，而是尽量兼容。
–complete-insert，-c
导出的数据采用包含字段名的完整 INSERT 方式，也就是把所有的值都写在一行。这么做能提高插入效率，但是可能会受到 max_allowed_packet 参数的影响而导致插入失败。因此，需要谨慎使用该参数，至少我不推荐。
–default-character-set=charset
指定导出数据时采用何种字符集，如果数据表不是采用默认的 latin1 字符集的话，那么导出时必须指定该选项，否则再次导入数据后将产生乱码问题。
–disable-keys
告诉 mysqldump 在 INSERT 语句的开头和结尾增加 /*!40000 ALTER TABLE table DISABLE KEYS */; 和 /*!40000 ALTER TABLE table ENABLE KEYS */; 语句，这能大大提高插入语句的速度，因为它是在插入完所有数据后才重建索引的。该选项只适合 MyISAM 表。
–extended-insert = true|false
默认情况下，mysqldump 开启 –complete-insert 模式，因此不想用它的的话，就使用本选项，设定它的值为 false 即可。
–hex-blob
使用十六进制格式导出二进制字符串字段。如果有二进制数据就必须使用本选项。影响到的字段类型有 BINARY、VARBINARY、BLOB。
–lock-all-tables，-x
在开始导出之前，提交请求锁定所有数据库中的所有表，以保证数据的一致性。这是一个全局读锁，并且自动关闭 –single-transaction 和 –lock-tables 选项。
–lock-tables
它和 –lock-all-tables 类似，不过是锁定当前导出的数据表，而不是一下子锁定全部库下的表。本选项只适用于 MyISAM 表，如果是 Innodb 表可以用 –single-transaction 选项。
–no-create-info，-t
只导出数据，而不添加 CREATE TABLE 语句。
–no-data，-d
不导出任何数据，只导出数据库表结构。
–opt
这只是一个快捷选项，等同于同时添加 –add-drop-tables –add-locking –create-option –disable-keys –extended-insert –lock-tables –quick –set-charset 选项。本选项能让 mysqldump 很快的导出数据，并且导出的数据能很快导回。该选项默认开启，但可以用 –skip-opt 禁用。注意，如果运行 mysqldump 没有指定 –quick 或 –opt 选项，则会将整个结果集放在内存中。如果导出大数据库的话可能会出现问题。
–quick，-q
该选项在导出大表时很有用，它强制 mysqldump 从服务器查询取得记录直接输出而不是取得所有记录后将它们缓存到内存中。
–routines，-R
导出存储过程以及自定义函数。
–single-transaction
该选项在导出数据之前提交一个 BEGIN SQL语句，BEGIN 不会阻塞任何应用程序且能保证导出时数据库的一致性状态。它只适用于事务表，例如 InnoDB 和 BDB。
本选项和 –lock-tables 选项是互斥的，因为 LOCK TABLES 会使任何挂起的事务隐含提交。
要想导出大表的话，应结合使用 –quick 选项。
–triggers
同时导出触发器。该选项默认启用，用 –skip-triggers 禁用它。
其他参数详情请参考手册，我通常使用以下 SQL 来备份 MyISAM 表：
/usr/local/mysql/bin/mysqldump -uyejr -pyejr ”
–default-character-set=utf8 –opt –extended-insert=false ”
–triggers -R –hex-blob -x db_name > db_name.sql
使用以下 SQL 来备份 Innodb 表：
/usr/local/mysql/bin/mysqldump -uyejr -pyejr ”
–default-character-set=utf8 –opt –extended-insert=false ”
–triggers -R –hex-blob –single-transaction db_name > db_name.sql
另外，如果想要实现在线备份，还可以使用 –master-data 参数来实现，如下：
/usr/local/mysql/bin/mysqldump -uyejr -pyejr ”
–default-character-set=utf8 –opt –master-data=1 ”
–single-transaction –flush-logs db_name > db_name.sql
它只是在一开始的瞬间请求锁表，然后就刷新binlog了，而后在导出的文件中加入CHANGE MASTER 语句来指定当前备份的binlog位置，如果要把这个文件恢复到slave里去，就可以采用这种方法来做。
1.2 还原

用 mysqldump 备份出来的文件是一个可以直接倒入的 SQL 脚本，有两种方法可以将数据导入。

直接用 mysql 客户端
例如：

复制代码 代码如下:

/usr/local/mysql/bin/mysql -uyejr -pyejr db_name < db_name.sql

用 SOURCE 语法 （实验不成功！！！）
其实这不是标准的 SQL 语法，而是 mysql 客户端提供的功能，例如：
SOURCE /tmp/db_name.sql;
这里需要指定文件的绝对路径，并且必须是 mysqld 运行用户(例如 nobody)有权限读取的文件。
