#! /bin/bash
# 每天定时发送bcp的update日志到指定邮箱，确认升级情况！

tail -n 7 /usr/local/backup/bcp_update_card_alllines.log > /usr/local/backup/bcp_report/bcp_report.txt
tail -n 7 /usr/local/backup/bcp_update_card_info.log >> /usr/local/backup/bcp_report/bcp_report.txt
tail -n 7 /usr/local/backup/bcp_update_check_result.log >> /usr/local/backup/bcp_report/bcp_report.txt

#/usr/local/bin/sendEmail -f nagios2011@163.com -t guoletao@126.com -s smtp.163.com \
#-xu nagios2011 -xp nagios@2011 -u "bcp_report from $HOSTNAME in $(date +%Y-%m-%d)" -m < /usr/local/backup/bcp_report/bcp_report.txt


/usr/local/bin/sendEmail -f noreply@postmail.wuhan360.net -t guoletao@126.com -s mail.postmail.wuhan360.net \
-xu noreply -xp noreply@WH2012 -u "bcp_report from $HOSTNAME in $(date +%Y-%m-%d)" -m < /usr/local/backup/bcp_report/bcp_report.txt



