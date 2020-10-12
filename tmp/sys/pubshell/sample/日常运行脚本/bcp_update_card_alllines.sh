#!/bin/bash
#bcp_update_card_alllines
#定义变量
DATE=`date +%Y%m%d`
YESTERDAY=$(date +%Y%m%d --date='1 days ago')
ALLLINES_ZIP=XF8000000200000001$YESTERDAY'00'.zip
#ALLLINES_ZIP=XF8000000200000001$YESTERDAY'01'.zip  #清算失败后的文件命名方式;
CIDS_VS_LINES=cids_vs_lines$(date +%Y%m%d --date='2 days ago').result
JY=JY8000000200000001$YESTERDAY'00'.txt
LOGFILE=/usr/local/backup/bcp_update_card_alllines.log
#改变到目标目录
cd  /usr/local/backup/ecartoonftp
#解压文件
unzip $ALLLINES_ZIP
sleep 20
echo $(date +"%Y%m%d %H:%M:%S")  >> $LOGFILE
/usr/bin/perl /usr/local/backup/scripts/bcp_update_card_latestlines_new.pl \
$CIDS_VS_LINES $JY >> $LOGFILE 2>&1 

if [[ $? == 0 ]]; then
mv cids_vs_lines_tmp.result cids_vs_lines$YESTERDAY.result
mv missed_psam_tmp.result missed_psam$YESTERDAY.result
echo "bcp_update_card_alllines updated finished" >> $LOGFILE
echo $(date +"%Y%m%d %H:%M:%S")  >> $LOGFILE

else
echo "bcp_update_card_alllines updated Fail!" >> $LOGFILE
sendEmail -f nagios2011@163.com -t 305959613@qq.com -s smtp.163.com -xu nagios2011 -xp nagios@2011 \
-u bcp_update_card_alllines update failure -m bcp_update_card_alllines update failure from $HOSTNAME
fi
echo "---------------" >> $LOGFILE


