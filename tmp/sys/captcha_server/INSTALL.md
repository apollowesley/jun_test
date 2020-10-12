#安装步骤

1，建立web worker使用的低权限用户

groupadd www

useradd  www -g www -s /sbin/nologin


2，安装依赖库

yum install readline-devel pcre-devel openssl-devel gcc


3，安装gd: gd-2.0.33

yum install -y libjpeg-devel libpng-devel freetype-devel fontconfig-devel libXpm-devel

wget http://www.boutell.com/gd/http/gd-2.0.33.tar.gz

tar zvxf gd-2.0.33.tar.gz

cd gd-2.0.33

./configure

make

make install


4，下载nginx sysguard模块

wget https://github.com/alibaba/nginx-http-sysguard/archive/master.zip -O nginx-http-sysguard-master.zip

unzip nginx-http-sysguard-master.zip


5，安装openresty

wget http://openresty.org/download/openresty-1.9.15.1.tar.gz

tar zvxf openresty-1.9.15.1.tar.gz

cd openresty-1.9.15.1
~~~
先应用nginx-http-sysguard的补丁到nginx核心代码

cd bundle/nginx-1.9.15/

patch -p1 < /root/packages/nginx-http-sysguard-master/nginx_sysguard_1.3.9.patch

cd ../../
~~~
./configure --prefix=/usr/local/openresty \
            --with-luajit --with-debug \
            --with-http_iconv_module \
            --user=www --group=www \
            --with-http_realip_module \
            --with-http_stub_status_module --add-module=/root/packages/nginx-http-sysguard-master

make

make install

mv /usr/local/openresty/nginx/sbin/nginx /usr/local/openresty/nginx/sbin/openresty

加系统启动停止脚本

vi /etc/rc.d/init.d/openresty

~~~
#! /bin/sh
# chkconfig: 2345 55 25
# Description: Startup script for nginx webserver on Debian. Place in /etc/init.d and
# run 'update-rc.d -f nginx defaults', or use the appropriate command on your
# distro. For CentOS/Redhat run: 'chkconfig --add nginx'

### BEGIN INIT INFO
# Provides:          nginx
# Required-Start:    $all
# Required-Stop:     $all
# Default-Start:     2 3 4 5
# Default-Stop:      0 1 6
# Short-Description: starts the nginx web server
# Description:       starts nginx using start-stop-daemon
### END INIT INFO

PATH=/usr/local/sbin:/usr/local/bin:/sbin:/bin:/usr/sbin:/usr/bin
DESC="openresty daemon"
NAME=nginx
EXENAME=openresty
DAEMON=/usr/local/openresty/nginx/sbin/$EXENAME
CONFIGFILE=/usr/local/openresty/nginx/conf/$NAME.conf
PIDFILE=/usr/local/openresty/nginx/logs/$NAME.pid
SCRIPTNAME=/etc/init.d/$EXENAME

set -e
[ -x "$DAEMON" ] || exit 0

do_start() {
$DAEMON -c $CONFIGFILE || echo -n "nginx already running"
}

do_stop() {
kill -INT `cat $PIDFILE` || echo -n "nginx not running"
}

do_reload() {
kill -HUP `cat $PIDFILE` || echo -n "nginx can't reload"
}

case "$1" in
start)
echo -n "Starting $DESC: $NAME"
do_start
echo "."
;;
stop)
echo -n "Stopping $DESC: $NAME"
do_stop
echo "."
;;
reload|graceful)
echo -n "Reloading $DESC configuration..."
do_reload
echo "."
;;
restart)
echo -n "Restarting $DESC: $NAME"
do_stop
do_start
echo "."
;;
*)
echo "Usage: $SCRIPTNAME {start|stop|reload|restart}" >&2
exit 3
;;
esac

exit 0
~~~

给脚本加权限

chmod a+x /etc/rc.d/init.d/openresty


把nginx加入chkconfig，并设置开机启动

chkconfig --add openresty

chkconfig openresty on


启动、停止、加载配置

service openresty start

service openresty stop

service openresty reload


开端口
iptables -I INPUT -p tcp --dport 9000 -j ACCEPT

/etc/rc.d/init.d/iptables save

/etc/rc.d/init.d/iptables status


配置ld

vi /etc/ld.so.conf.d/local.conf

/usr/local/lib

/usr/local/openresty/lualib

/usr/local/openresty/luajit/lib

执行

ldconfig



6，安装lua-resty-UUID

yum -y install libuuid-devel

wget https://github.com/dcshi/lua-resty-UUID/archive/master.zip -O lua-resty-UUID-master.zip

unzip lua-resty-UUID-master.zip

cd lua-resty-UUID-master/clib

make

cp *.so /usr/local/openresty/lualib/

cd ../

cp lib/resty/*.lua /usr/local/openresty/lualib/resty/



7，安装lua-gd

wget "http://jaist.dl.sourceforge.net/project/lua-gd/lua-gd/lua-gd-2.0.33r2 (for Lua 5.1)/lua-gd-2.0.33r2.tar.gz"

tar zvxf lua-gd-2.0.33r2.tar.gz

cd lua-gd-2.0.33r2

修改Makefile，关联luajit库
~~~
# Name of .pc file. "lua5.1" on Debian/Ubuntu
export PKG_CONFIG_PATH=/usr/local/openresty/luajit/lib/pkgconfig
LUAPKG=luajit
OUTFILE=gd.so
CFLAGS=`gdlib-config --cflags` `pkg-config $(LUAPKG) --cflags` -O3 -Wall
GDFEATURES=`gdlib-config --features |sed -e "s/GD_/-DGD_/g"`
LFLAGS=-fPIC -shared `gdlib-config --ldflags` `gdlib-config --libs` -lgd `pkg-config $(LUAPKG) --libs`
INSTALL_PATH=/usr/local/openresty/lualib
~~~

make

make install




8，安装redis

mkdir /usr/local/openresty/redis

mkdir /usr/local/openresty/redis/bin

mkdir /usr/local/openresty/redis/data

mkdir /usr/local/openresty/redis/log

mkdir /usr/local/openresty/redis/etc


wget http://download.redis.io/releases/redis-3.2.3.tar.gz

tar zvxf redis-3.2.3.tar.gz

cd redis-3.2.3

make

cp src/redis-cli /usr/local/openresty/redis/bin/

cp src/redis-server /usr/local/openresty/redis/bin/

cp src/redis-sentinel /usr/local/openresty/redis/bin/

cp redis.conf /usr/local/openresty/redis/etc/

cp sentinel.conf /usr/local/openresty/redis/etc/


vi /usr/local/openresty/redis/etc/redis.conf
~~~
daemonize yes
pidfile /usr/local/openresty/redis/log/redis-6379.pid
port 6379
timeout 300
tcp-keepalive 10
loglevel notice
logfile /usr/local/openresty/redis/log/redis-6379.log
databases 16
save 900 1
save 300 10
save 60 10000
stop-writes-on-bgsave-error yes
rdbcompression yes
rdbchecksum yes
dbfilename dump-6379.rdb
dir /usr/local/openresty/redis/data
slave-serve-stale-data yes
slave-read-only yes
repl-disable-tcp-nodelay no
slave-priority 100
appendonly no
appendfsync everysec
no-appendfsync-on-rewrite no
auto-aof-rewrite-percentage 100
auto-aof-rewrite-min-size 64mb
lua-time-limit 5000
slowlog-log-slower-than 10000
slowlog-max-len 128
hash-max-ziplist-entries 512
hash-max-ziplist-value 64
list-max-ziplist-entries 512
list-max-ziplist-value 64
set-max-intset-entries 512
zset-max-ziplist-entries 128
zset-max-ziplist-value 64
activerehashing yes
client-output-buffer-limit normal 0 0 0
client-output-buffer-limit slave 256mb 64mb 60
client-output-buffer-limit pubsub 32mb 8mb 60
hz 10
~~~

启动redis

/usr/local/openresty/redis/bin/redis-server /usr/local/openresty/redis/etc/redis.conf

访问数据

/usr/local/openresty/redis/bin/redis-cli









