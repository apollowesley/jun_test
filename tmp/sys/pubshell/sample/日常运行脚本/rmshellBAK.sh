#!/bin/bash 
# Name:rmshellBAK.sh 
# 定期清除超过一个月的文件；
# Write by:guoletao
# Last Modify:2011-07-02 
# 
# 定义备份目录 
shellBACK=/usr/local/backup/wuhan360_shell/
# 删除 30天以外的日志备份文件 
find $shellBACK -name "*.tar.gz" -type f -mtime +30 -exec rm {} \; > /dev/null 2>&1
find /usr/local/backup/wuhan360_csv/ -name "csv*.tar.gz" -type f -mtime +30|xargs rm -rf
