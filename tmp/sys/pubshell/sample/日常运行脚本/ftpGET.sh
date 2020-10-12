#!/bin/sh
#download file from 8000000200000001input!!!
#written by guoletao!
# Last Modify:2011-04-04

SERVER=59.175.174.194
USERNAME=hu
PASSWORD=hu
MYDATE=$(date +%Y%m%d --date='1 days ago')
FILENAME=XF8000000200000001$MYDATE'01'.zip
log=/usr/local/backup/ftpGet.log

echo $(date +"%Y%m%d %H:%M:%S")  >> $log

/usr/bin/ftp -i -n -v $SERVER << SCRIPT
quote USER $USERNAME
quote PASS $PASSWORD
cd ftp/8000000200000001input
lcd /usr/local/backup/ecartoonftp
binary
verbose
mget $FILENAME
close
bye
SCRIPT

cd /usr/local/backup/ecartoonftp
if [ -f $FILENAME ]; then
echo "$FILENAME downlaod finished!" >> $log
echo $(date +"%Y%m%d %H:%M:%S")  >> $log
/usr/local/shell/bcp_update_card_alllines.sh

else
echo "$FILENAME downlaod fail!" >> $log
sendEmail -f nagios2011@163.com -t guoletao@126.com -s smtp.163.com \
-xu nagios2011 -xp nagios@2011 -m 8000000200000001input download failure
fi
echo "---------------" >> $log
