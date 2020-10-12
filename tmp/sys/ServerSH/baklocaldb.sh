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
#得到15天前的日期，只得到“天”的数据，用于判断是否是每月的一号
FIRST_DAY=`date -d '15 day ago' "+%d"`
#每月一号备份数据保存目录
BACKUP_MONTH="/www/bak/month/data"
BACKUP_PATH="/www/bak/data/$DATE/"
#得到十五天前备份目录的地址
AGO_BACKUP_PATH="/www/bak/data/$AGO_DATE/"


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

#删除15天前的备份文件夹，如果文件夹为每月一号的备份文件夹，则不删除，并将文件夹移动到month/data
if [ -d "$AGO_BACKUP_PATH" ] && [ $FIRST_DAY != 01 ];then
    /bin/rm -rf $AGO_BACKUP_PATH
elif [ $FIRST_DAY = 01 ];then
	if [ ! -f "$BACKUP_MONTH" ];then
    mkdir -p $BACKUP_MONTH
	fi
	mv $AGO_BACKUP_PATH $BACKUP_MONTH
fi
