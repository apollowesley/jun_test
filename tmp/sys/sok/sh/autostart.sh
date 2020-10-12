#!/bin/bash
# # 启动地址路径根据swoole 启动脚本路径修改即可

# $basedir 启动路径
# 执行脚本 参数 [start|stop|reset]  
 

basedir="/mnt/hgfs/WWW/yichenSwooleMsg/serve/run" #执行目录
filename="start" #执行文件名

# basedir=$(dirname $(dirname $0))
# echo $0
# echo $basedir
port=(9501 8880) #服务端口号 数组执行的时候会遍历 
serve=(http websocket) #启动服务类型数组 
mod=${2-Chat} #默认启动模块
action=${1-reset} #默认执行命令 
check_port() {
        echo "正在检测端口check_port..."
        netstat -tlpn | grep "\b$1\b"
}
# printf $port $serve
for data in ${serve[@]}
    do
    	echo ${data}
done

for data in ${port[@]}
    do
    	echo ${data}
done

#杀死进程
stop(){
for data in ${port[@]}
    do

    if check_port ${data};
    then

        pid=$(lsof -F p  -i:${data} | cut -b 2-); #获取进程id
        kill -9 $pid; #杀死进程
            echo "端口存在is kill-${data} OK";
    else
            echo "端口死亡 no start ";
            DATE_N=`date "+%Y-%m%d %H:%M:%S"`;
            echo "时间：${DATE_N}" >check_port.log; #记录死亡日志
    fi
        echo ${data}
done
}

#开启服务
start(){
	for data in ${serve[@]}
        do
        	php $basedir/$filename  ${data} $mod &
            echo ${data}
    done
}

case "$action" in
        "start")

    if check_port $port
        then
            echo "is have start"
        else
            echo "start"
            start
    fi
                    ;;
            "stop")
                    echo "stop"

                    stop 
                    ;;
            "restart")
                    echo "restart"
                    stop
                    start 
                    ;;
            *)
                    #其它输入
                    echo "output error,please input 1/2/2"
                    ;;
esac
