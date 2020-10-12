#!/bin/bash
# download file cardinfo!!!
#written by guoletao!

MYDATE=`date +%Y%m%d`
FILENAME=card_info$MYDATE.rar
log=/usr/local/backup/ftpCARD_INFO.log
declare -i i=1
declare -i sum=0

echo $(date +"%Y%m%d %H:%M:%S")  >> $log

while ((sum<=3));do

cd /usr/local/backup/ecartoonftp
/usr/bin/wget -c --ftp-user=hu --ftp-password=hu ftp://59.175.174.194/ftp/Hu/$FILENAME
    if [ "$?" == 0 ];then
      break
    fi

let sum+=i
let ++i
sleep 15
done

if [ -s "$FILENAME" ]; then
echo "$FILENAME downlaod finished!" >> $log
echo $(date +"%Y%m%d %H:%M:%S")  >> $log
#如果文件下载成功，就把文件scp到115服务器的/backup/ecartoonftp目录里;
scp $FILENAME 192.168.100.115:/backup/ecartoonftp/. &
/usr/local/shell/bcp_update_card_info.sh

else
echo "$FILENAME downlaod fail!" >> $log
/usr/local/bin/sendEmail -f nagios2011@163.com -t 305959613@qq.com -s smtp.163.com \
-xu nagios2011 -xp nagios@2011 -u CARD_INFO failure -m $FILENAME download failure from $HOSTNAME
fi
echo "---------------" >> $log

unset MYDATE
unset FILENAME
unset log
