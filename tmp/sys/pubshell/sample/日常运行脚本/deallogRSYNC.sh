#!/bin/bash
#备份万网服务器目录/data/backup/ecartoonftp/deal-log/下log文件到114服务器。
log=/usr/local/backup/deallogRSYNC.log
echo $(date +"%Y%m%d %H:%M:%S")  >> $log
rsync  -vztdopg --delete -e ssh root@112.125.57.30:/usr/local/backup/ecartoonftp/deal-log/ /usr/local/backup/wuhan360_deal-log/
if [ "$?" == 0 ]
then
 echo "dealLog rsyncBACKUP SUCESS!" >> $log
else
 echo "dealLog rsyncBACKUP failure!" >> $log
fi
echo $(date +"%Y%m%d %H:%M:%S")  >> $log
echo "-----------------------" >> $log
exit
