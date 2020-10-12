#! /bin/bash
#Written by guolt
#date:2012-7-31
#本脚本请以root用户执行；
#创建psam比较后产生的目录：
#下载JC*.csv文件路径：/hu/ftp/8000000200000002/bus_terminal/
psamLog_DIR="/usr/local/backup/ecartoonftp/psam_bak/bak_$(date +%Y%m%d)"
psamUpdate_LOG="/usr/local/backup/ecartoonftp/psam_bak/bak_$(date +%Y%m%d)/tmp/psamUpdate_$(date +%Y%m%d).log"
bus_terminal_DIR="/usr/local/backup/ecartoonftp/bus_terminal"

if [ ! -d $psamLog_DIR ];then
      mkdir $psamLog_DIR
fi

#判断相关目录是否有JC.csv文件；
find /usr/local/backup/ecartoonftp/psam_bak/ -maxdepth 1 -name "JC*.txt"
if [ $? == 0 ];then
    find /usr/local/backup/ecartoonftp/psam_bak/ -maxdepth 1 -name "JC*.csv" -print > $psamLog_DIR/bus_terminal.tmp
    cd /usr/local/backup/ecartoonftp/psam_bak/
    chown webdev.webdev JC*.csv

else
    echo "/usr/local/backup/ecartoonftp/psam_bak 目录下没有发现JC.csv文件.请从bus_terminal下载！！！！"
    exit 1;

fi



cd $psamLog_DIR

#在新的目录中生成bus_route.csv文件；
mysql -uroot -pDATAbase@WH2011 -e "select bus_route_id,line INTO OUTFILE '/tmp/bus_route.csv'  fields terminated by ',' lines terminated by '\n' from gjxt.bus_route;" 

#在新的目录中生成bus_station.csv文件；
mysql -uroot -pDATAbase@WH2011 -e "select id,bus_route_id,name INTO OUTFILE '/tmp/bus_station.csv' fields terminated by ',' lines terminated by '\n' from gjxt.bus_station;"

#生成PSAM数据处理日志的存放目录；
if [ ! -d $psamLog_DIR/tmp  ];then
    mkdir -p $psamLog_DIR/tmp
fi

#移动tmp产生的csv文件到$psamLog_DIR/tmp/目录，并且修改数组；
mv /tmp/bus_route.csv $psamLog_DIR/.
mv /tmp/bus_station.csv $psamLog_DIR/.
chown webdev.webdev $psamLog_DIR/bus_route.csv
chown webdev.webdev $psamLog_DIR/bus_station.csv
chown -R webdev.webdev $psamLog_DIR

#copy正式系统中的psam_line_plate.csv文件到检查目录中，并且备份一份；
cp -a /usr/local/backup/ecartoonftp/psam_line_plate.csv $psamLog_DIR/.
cp -a /usr/local/backup/ecartoonftp/psam_line_plate.csv $psamLog_DIR/tmp/psam_line_plate.csv.old_$(date +%Y%m%d)

#开始处理psam数据：
cd $psamLog_DIR
for JC in $(cat bus_terminal.tmp);do
    cat $JC >> psam_line_plate.csv
    mv $JC /usr/local/backup/ecartoonftp/bus_terminal/.
done

chown webdev.webdev psam_line_plate.csv


#开始处理数据；
cd $psamLog_DIR
/usr/bin/perl /usr/local/backup/scripts/bcp_check_psam.pl > $psamUpdate_LOG

if [ $? == 0 ];then
    echo "$(date +%Y%m%d)" > /usr/local/backup/ecartoonftp/psam_bak/psam_lastDate.log
    echo "#############################################################"|tee -a $psamLog_DIR/readme.txt
    echo "$psamUpdate_LOG已经生成；请处理日志乱码问题!!!" | tee -a $psamLog_DIR/readme.txt
    echo "VIM  :% s/\r//g" | tee -a $psamLog_DIR/readme.txt
    echo "grep $'\xEF\xBB\xBF' psam_line_plate.csv " | tee -a $psamLog_DIR/readme.txt
    echo "#############################################################" | tee -a $psamLog_DIR/readme.txt
    echo "==============$(date +%Y%m%d)处理该数据=====================" >> $psamLog_DIR/readme.txt
else
    echo "$psamUpdate_LOG失败！！！！！！！！！！！"
fi

#处理psam日志文件，并且发送日志到相关人员！
cd $psamLog_DIR/tmp
cat $psamUpdate_LOG | grep '^missed route' > missed_route_$(date +%Y%m%d).txt
cat $psamUpdate_LOG | grep -A 2 '^missed lines' >  missed_lines_$(date +%Y%m%d).txt

#incomplete psam数据不用管！

#duplicated数据：（严工处理）,一般不用发送！！！
cat $psamUpdate_LOG | grep -A 2 'duplicated' > duplicated_psam_$(date +%Y%m%d).txt

echo "For went: missed_route_$(date +%Y%m%d).txt AND missed_route_$(date +%Y%m%d).txt "| tee -a $psamLog_DIR/readme.txt
echo "For yan: duplicated_psam_$(date +%Y%m%d).txt "| tee -a $psamLog_DIR/readme.txt

#发送日志给相关人员！
/usr/local/bin/sendEmail -f nagios2011@163.com -t chenwentaokl@yahoo.com.cn -cc styxshixg@gmail.com bryanyuhu@yahoo.com get_shell@foxmail.com -s smtp.163.com \
-xu nagios2011 -xp nagios@2011 -a missed_route_$(date +%Y%m%d).txt missed_lines_$(date +%Y%m%d).txt -u PSAM update log in $(date +%Y%m%d) ! \
-m "文韬:\n 你好，PSAM数据更新信息请参考附件！！！\n 这封邮件由脚本处理数据后自动发出的，如果有什么问题，请回复邮件到：get_shell@foxmail.com！\n 谢谢！！！"
