#!/bin/bash
#处理小额消费积分脚本！
LOGFILE=/usr/local/backup/bcp_count_deal_new.log

echo $(date +"%Y%m%d %H:%M:%S")  >> $LOGFILE
/usr/bin/perl /usr/local/backup/scripts/bcp_count_deal_new.pl >> $LOGFILE 2>&1
if [[ $? == 0 ]]; then
   echo "bcp_count_del_new update finished" >> $LOGFILE
else
   echo "bcp_count_del_new update failure!" >> $LOGFILE
   sendEmail -f nagios2011@163.com -t 305959613@qq.com -s smtp.163.com -xu nagios2011 -xp nagios@2011 \
-m bcp_count_del_new update failure in 114DB!
fi
echo $(date +"%Y%m%d %H:%M:%S")  >> $LOGFILE
echo "---------------------------------------------" >> $LOGFILE
