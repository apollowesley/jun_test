#!/bin/bash

#2015-07-17
#本脚本用于数据库的备份，仅备份本机上的数据库 此文件需要在bakdata.sh之前执行
DBHOST=localhost
DBUSER=root
PASSWD=1a2byoulom
DATABASES="performance_schema|information_schema|mysql|Database|PureFTP|phpmyadmin"
DATABASE=
SQL="show databases;"
GETDATA=`mysql -h $DBHOST -u $DBUSER --password=$PASSWD $DATABASE -e "$SQL" |egrep -v "$DATABASES"`
DATE=`date +%Y%m%d`
AGO_DATE=`date -d '15 day ago' "+%Y%m%d"`
BACKUP_PATH="/www/bak/data/$DATE/"

#检查备份目录是否存在,不存在则创建
if [ ! -f "$BACKUP_PATH" ] ; then
     mkdir -p  $BACKUP_PATH
else
    echo error >> /dev/null
fi
#进入备份目录文件夹
cd $BACKUP_PATH

#将databaselist中所列出的数据库遍历导出，然后循环打包
for GETDATA in $GETDATA
do
    /usr/bin/mysqldump -p$PASSWD --opt $GETDATA > $GETDATA.sql 2>/dev/null
    
	/bin/tar czf ${DATE}_${GETDATA}.tar.gz  $GETDATA.sql 2>/dev/null
done
#备份文件打包后，删除所有导出的.sql文件
/bin/rm -rf $BACKUP_PATH/*.sql