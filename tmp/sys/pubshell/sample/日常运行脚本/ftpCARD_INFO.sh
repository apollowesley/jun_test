#!/bin/sh
# download file cardinfo!!!
#written by guoletao!

SERVER=59.175.174.194
USERNAME=hu
PASSWORD=hu
MYDATE=`date +%Y%m%d`
FILENAME=card_info$MYDATE.rar
log=/usr/local/backup/ftpCARD_INFO.log

echo $(date +"%Y%m%d %H:%M:%S")  >> $log

/usr/bin/ftp -i -n -v $SERVER << SCRIPT
quote USER $USERNAME
quote PASS $PASSWORD
cd ftp/Hu
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
/usr/local/shell/bcp_update_card_info.sh

else
echo "$FILENAME downlaod fail!" >> $log
sendEmail -f nagios2011@163.com -t guoletao@126.com -s smtp.163.com \
-xu nagios2011 -xp nagios@2011 -m cardinfo download failure
fi
echo "---------------" >> $log
