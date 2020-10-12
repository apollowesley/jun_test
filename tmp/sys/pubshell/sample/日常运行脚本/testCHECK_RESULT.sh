#!/bin/bash
#download file check_result!!!
#written by guoletao!

MYDATE=`date +%Y%m%d`
FILENAME=check_result$MYDATE.rar
log=/usr/local/backup/ftpCHECK_RESULT.log

cd /usr/local/backup/ecartoonftp

    if [ -s "$FILENAME" ]; then
    echo "文件非空"
    else
echo "$FILENAME downlaod fail!"
sendEmail -f nagios2011@163.com -t 305959613@qq.com -s smtp.163.com \
-xu nagios2011 -xp nagios@2011 -u CHECK_RESULT failure -m $FILENAME downlaod failure from $HOSTNAME
    fi
