#!/bin/bash

#2015-07-17
#本脚本用于网站文件的备份。

#设置信息：站点根目录，排除不需要进行备份的目录
WEBPATH=/www/web/
FILTER=,default,

#备份当天日期
DATE=`date +%Y%m%d`
#得到15天前的日期，用于清除超过15天的备份文件
AGO_DATE=`date -d '15 day ago' "+%Y%m%d"`
#得到15天前的日期，只得到“天”的数据，用于判断是否是每月的一号
FIRST_DAY=`date -d '15 day ago' "+%d"`
#备份文件目录
BACKUP_PATH="/www/bak/website/$DATE"
#每月一号备份数据保存目录
BACKUP_MONTH="/www/bak/month/website"
#得到十五天前备份目录的地址
AGO_BACKUP_PATH="/www/bak/website/$AGO_DATE/"

#检查备份目录文件夹是否存在，不存在则创建
if [ ! -f "$BACKUP_PATH" ];then
    mkdir -p $BACKUP_PATH
else
    echo error >> /dev/null
fi

#进入站点所在目录
cd $WEBPATH

#遍历WEBPATH中所有文件夹名，排除FILTER中所列文件夹后，分别打包各个站点文件
for dir in $(ls);
do
    if [[ -f $dir || "$FILTER" == *,$dir,* ]]; then
		continue
	fi
	$(tar czPf "${BACKUP_PATH}/${DATE}_${dir}.tar.gz" "$dir")
done


#删除15天前的备份文件夹，如果文件夹为每月一号的备份文件夹，则不删除，并将文件夹移动到month/website
if [ -d "$AGO_BACKUP_PATH" ] && [ $FIRST_DAY != 01 ];then
    /bin/rm -rf $AGO_BACKUP_PATH
elif [ $FIRST_DAY = 01 ];then
	if [ ! -f "$BACKUP_MONTH" ];then
    mkdir -p $BACKUP_MONTH
	fi
	mv $AGO_BACKUP_PATH $BACKUP_MONTH
fi

cd /root