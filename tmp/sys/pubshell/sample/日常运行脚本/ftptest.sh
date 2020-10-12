#!/bin/bash
#download file from 8000000200000001input!!!
#written by guoletao!

SERVER=59.175.174.194
USERNAME=hu
PASSWORD=hu
MYDATE=$(date +%Y%m%d --date='1 days ago')
FILENAME=XF8000000200000001$MYDATE'01'.zip
log=/tmp/ftpGet.log

echo $(date +"%Y%m%d %H:%M:%S")  >> $log

/usr/bin/ftp -n $SERVER << SCRIPT
quote USER $USERNAME
quote PASS $PASSWORD
cd ftp/8000000200000001input
lcd /tmp
binary
prompt
mget $FILENAME
close
bye
SCRIPT

if [[ $? == 0 ]]; then
echo "$FILENAME downlaod finished!" >> $log
echo $(date +"%Y%m%d %H:%M:%S")  >> $log

else
echo "$FILENAME downlaod fail!" >> $log
fi
echo "---------------" >> $log
