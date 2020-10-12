#! /bin/bash
# 统计武汉通注册总人数，及当天注册人数，并且发邮件；
# 定义用于备份数据库的用户名和密码 
user=root
userPWD=DATAbase@WH2011
#定义日志文件；
count_log=/usr/local/backup/shellBAK/wuhantoon_count.log
#注册总人数；
TotalUser_tmp=`mysql -u$user -p$userPWD -e "select count(*) from wuhantoon.user";`
#当天注册人数；
TodayUser_tmp=`mysql -uroot -pDATAbase@WH2011 -e "select count(*) from wuhantoon.user where regtime like '$(date +%Y-%m-%d) %';"`

#注册总人数（处理count（*））；
TotalUser=$(echo $TotalUser_tmp|awk '{print $2}')
#当天注册人数（处理count（*））；
TodayUser=$(echo $TodayUser_tmp|awk '{print $2}')
echo " "  >> $count_log
echo "网站注册总人数: $TotalUser" >> $count_log
echo "网站$(date +%Y-%m-%d) 注册人数: $TodayUser" >> $count_log
echo "=======截止当天晚上23:58统计的数据==================="  >> $count_log

tail -n 4 $count_log > /usr/local/backup/shellBAK/wuhantoon_count_tmp.log

/usr/local/bin/sendEmail -f nagios2011@163.com -t bryanhu@qq.com,chenwentaokl@yahoo.com.cn,guoletao@126.com -s smtp.163.com \
-xu nagios2011 -xp nagios@2011 -u "wuhantoon_Total_users from $HOSTNAME in $(date +%Y-%m-%d)" -m \
< /usr/local/backup/shellBAK/wuhantoon_count_tmp.log


#/usr/local/bin/sendEmail -f noreply@postmail.wuhan360.net -t bryanhu@qq.com,chenwentaokl@yahoo.com.cn,guoletao@126.com \
#-s mail.postmail.wuhan360.net -xu noreply -xp noreply@WH2012 -u "wuhantoon_Total_users from $HOSTNAME in $(date +%Y-%m-%d)" -m \
#< /usr/local/backup/shellBAK/wuhantoon_count_tmp.log
