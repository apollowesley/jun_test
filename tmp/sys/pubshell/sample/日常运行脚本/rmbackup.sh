#!/bin/sh 
# Name:rmBackup.sh 
# PS:Delete old Backup. 
# Write by:guoletao
# Last Modify:2011-07-02 
# 
# 定义备份目录 
dataBackupDir=/usr/local/backup/mysqlFULL
# 删除 mtime>5日志备份文件 
find $dataBackupDir -name "mysql_*.gz" -type f -mtime +5 -exec rm {} \; > /dev/null 2>&1 
