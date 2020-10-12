#!/bin/bash
#定义变量
DATE=`date +%Y%m%d`
YESTERDAY=$(date +%Y%m%d --date='1 days ago')

CARD_INFO_RAR=card_info$DATE.rar
CARD_INFO_NEW=card_info$DATE.bcp
CARD_INFO_OLD=card_info$YESTERDAY.bcp
LOGFILE=/usr/local/backup/bcp_update_card_info.log
#改变到目标目录
cd  /usr/local/backup/ecartoonftp
#解压文件
/usr/local/backup/scripts/rar/unrar e $CARD_INFO_RAR
sleep 20
echo $(date +"%Y%m%d %H:%M:%S")  >> $LOGFILE
/usr/bin/perl /usr/local/backup/scripts/bcp_update_card_info_new.pl \
$CARD_INFO_OLD $CARD_INFO_NEW >> /usr/local/backup/bcp_update_card_info.log 2>&1 

if [[ $? == 0 ]]; then
echo "card_info update finished" >> $LOGFILE
echo $(date +"%Y%m%d %H:%M:%S")  >> $LOGFILE

else
echo "card_info update fail!" >> $LOGFILE
sendEmail -f nagios2011@163.com -t 305959613@qq.com -s smtp.163.com -xu nagios2011 -xp nagios@2011 \
-u card_info update failure -m card_info update failure from $HOSTNAME
fi
echo "---------------" >> $LOGFILE
