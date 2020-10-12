./nginx  #打开 nginx
nginx -s reload|reopen|stop|quit  #重新加载配置|重启|停止|退出 nginx
nginx -t   #测试配置是否有语法错误

nginx [-?hvVtq] [-s signal] [-c filename] [-p prefix] [-g directives]

-?,-h           : 打开帮助信息
-v              : 显示版本信息并退出
-V              : 显示版本和配置选项信息，然后退出
-t              : 检测配置文件是否有语法错误，然后退出
-q              : 在检测配置文件期间屏蔽非错误信息
-s signal       : 给一个 nginx 主进程发送信号：stop（停止）, quit（退出）, reopen（重启）, reload（重新加载配置文件）
-p prefix       : 设置前缀路径（默认是：/usr/local/nginx/）
-c filename     : 设置配置文件（默认是：/usr/local/nginx/conf/nginx.conf）
-g directives   : 设置配置文件外的全局指令



nginx图片缓存设置
在http下面设置
 ##cache##
  proxy_connect_timeout 5;
  proxy_read_timeout 60;
  proxy_send_timeout 5;
  proxy_buffer_size 16k;
  proxy_buffers 4 64k;
  proxy_busy_buffers_size 128k;
  proxy_temp_file_write_size 128k;
  proxy_temp_path /home/temp_dir;
  proxy_cache_path /home/cache levels=1:2 keys_zone=cache_one:200m inactive=1d max_size=30g;
  ##end##
  
  在service 下面设置
  
  location ~ .*\.(gif|jpg|png|htm|html|css|js|flv|ico|swf)(.*) {
                proxy_pass http://appserver;
                proxy_redirect off;
                proxy_set_header Host $host;
                proxy_cache cache_one;
                proxy_cache_valid 200 302 1h;
                proxy_cache_valid 301 1d;
                proxy_cache_valid any 1m;
                expires 30d;
          }