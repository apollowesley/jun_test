#! /bin/bash
#这个脚本的作为为把新生成的bus_route.csv，bus_station.csv，psam_line_plate.csv文件替换到/usr/local/backup/ecartoonftp/下相同名称的文件！
Lastdate=$(cat /usr/local/backup/ecartoonftp/psam_bak/psam_lastDate.log)
psamLog_DIR="/usr/local/backup/ecartoonftp/psam_bak/bak_$Lastdate"

test -f /usr/local/backup/ecartoonftp/psam_line_plate.csv && rm -rf /usr/local/backup/ecartoonftp/psam_line_plate.csv
cp -a $psamLog_DIR/psam_line_plate.csv /usr/local/backup/ecartoonftp/.


test -f /usr/local/backup/ecartoonftp/bus_route.csv && rm -rf /usr/local/backup/ecartoonftp/bus_route.csv
cp -a $psamLog_DIR/bus_route.csv /usr/local/backup/ecartoonftp/.

test -f /usr/local/backup/ecartoonftp/bus_station.csv && rm -rf /usr/local/backup/ecartoonftp/bus_station.csv
cp -a $psamLog_DIR/bus_station.csv /usr/local/backup/ecartoonftp/.

cd /usr/local/backup/ecartoonftp/
ls -l |grep csv
