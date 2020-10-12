#!/bin/bash
#备份shell和perl源代码目录,并且删除30天以外的备份文件。
shellDIR=/usr/local/shell
perlDIR=/usr/local/backup/scripts
BAkDIR=/usr/local/backup/shellBAK
log=/usr/local/backup/shellBAK/shellBACK.log

cd $BAkDIR
echo $(date +"%Y%m%d %H:%M:%S")  >> $log
tar zcvf shell$(date +%Y%m%d).tar.gz $shellDIR
tar zcvf perl$(date +%Y%m%d).tar.gz $perlDIR
if [[ $? == 0 ]];then
echo "shell and perl backup sucess!!!" >> $log
echo $(date +"%Y%m%d %H:%M:%S")  >> $log
find . -name "*.tar.gz" -mtime +30 -print |xargs rm -rf

else
echo "shell and perl backup failure!!!" >> $log
fi
echo "---------------------------" >> $log
