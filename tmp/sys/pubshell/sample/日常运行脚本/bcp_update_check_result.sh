#!/bin/bash
#bcp_update_check_result
#定义变量
DATE=`date +%Y%m%d`
YESTERDAY=$(date +%Y%m%d --date='1 days ago')

CHECK_RESULT_RAR=check_result$DATE.rar
CHECK_RESULT_NEW=check_result$DATE.bcp
CHECK_RESULT_OLD=check_result$YESTERDAY.bcp
LOGFILE=/usr/local/backup/bcp_update_check_result.log
#改变到目标目录
cd  /usr/local/backup/ecartoonftp
#解压文件
/usr/local/backup/scripts/rar/unrar e $CHECK_RESULT_RAR
sleep 20
echo $(date +"%Y%m%d %H:%M:%S")  >> $LOGFILE
/usr/bin/perl /usr/local/backup/scripts/bcp_update_check_result_new.pl \
$CHECK_RESULT_OLD $CHECK_RESULT_NEW >> $LOGFILE 2>&1 

if [[ $? == 0 ]]; then
echo "check_result update finished" >> $LOGFILE
echo $(date +"%Y%m%d %H:%M:%S")  >> $LOGFILE

else
echo "check_result update Fail!" >> $LOGFILE
sendEmail -f nagios2011@163.com -t 305959613@qq.com -s smtp.163.com -xu nagios2011 -xp nagios@2011 \
-u check_result update failure -m check_result update failure from $HOSTNAME
fi
echo "---------------" >> $LOGFILE
