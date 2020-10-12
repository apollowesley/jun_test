#!/bin/bash 
# Name:mysqlFullBackup.sh 
# PS:MySQL DataBase Full Backup. 
# Write by:guoletao
# Last Modify:2011-03-31 
# 定义脚本目录 
scriptsDir=`pwd` 
# 定义数据库目录 
mysqlDir=/usr/local/mysql 
# 定义用于备份数据库的用户名和密码 
user=root 
userPWD=DATAbase@WH2011
#hostname=DBserver
# 定义备份目录 
dataBackupDir=/usr/local/backup/mysqlFULL
# 定义备份日志文件 
logFile=$dataBackupDir/mysqlbackup0.log 
DATE=`date +"%Y%m%d-%H%M%S"` 
echo $(date +"%Y%m%d %H:%M:%S")  >> $logFile
cd $dataBackupDir/full 
# 定义备份文件名 
# dumpFile=mysql_$DATE.sql 
GZDumpFile=mysql_$DATE.sql.gz 
# 使用mysqldump备份数据库，同时压缩备份文件，请根据具体情况设置参数 
/usr/local/mysql/bin/mysqldump -u$user -p$userPWD --opt --default-character-set=utf8 --extended-insert=false --triggers -R --hex-blob -x --all-databases | gzip > $GZDumpFile 
if [[ $? == 0 ]]; then
echo "database full backup finished" >> $logFile 
echo $(date +"%Y%m%d %H:%M:%S")  >> $logFile

# Delete old backup files(mtime>2). 
/usr/local/shell/rmbackup.sh 
else 
echo "DataBase Backup Fail!" >> $logFile 
sendEmail -f nagios2011@163.com -t 305959613@qq.com -s smtp.163.com -xu nagios2011 -xp nagios@2011 \
-u mysqlFullBackup failure -m mysqlFullBackup failure from $HOSTNAME
fi 
echo "---------------" >> $logFile 
