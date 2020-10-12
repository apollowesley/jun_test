#!/bin/bash
#数据库导入。
wuhantoonDIR=/usr/local/backup/wuhan360_mysqldump/wuhantoon
wuhantoonFILE=wuhantoon_$(date +%Y%m%d).sql.gz
log=/usr/local/backup/wuhan360_mysqldump/wuhan360_mysqldump.log

echo $(date +"%Y%m%d %H:%M:%S")  >> $log
cd $wuhantoonDIR
if [[ -f $wuhantoonFILE ]]; then
gunzip $wuhantoonFILE
mysql -uroot -pDATAbase@WH2011 wuhantoon < wuhantoon_$(date +%Y%m%d).sql
echo "wuhantoon database import success." >> $log
else
echo "wuhantoon database import failure." >> $log
fi

echo $(date +"%Y%m%d %H:%M:%S")  >> $log
echo "-------------------------------------------" >>$log
