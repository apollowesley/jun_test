#!/bin/bash
# tar /data/web/logs
#written by guoletao!
# Last Modify:2010-04-01

DATA=/data/web/logs
MYDATE=`date +%Y%m%d`
FILENAME=/usr/local/backup/webLOG/weblog$MYDATE.tar.gz
log=/usr/local/backup/webLOG/webTAR.log

echo $(date +"%Y%m%d %H:%M:%S")  >> $log

/bin/tar zcvf $FILENAME $DATA
if [[ $? == 0 ]]; then
echo "$FILENAME tar finished!" >> $log
echo $(date +"%Y%m%d %H:%M:%S")  >> $log

else
echo "$FILENAME tar fail!" >> $log
fi
echo "---------------" >> $log

