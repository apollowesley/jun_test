# nginx

## location 匹配优先级
```
01、精确匹配,用 = 表示
location =  /test { ... }

02、前缀匹配,用^~表示
location ^~ /test { ... } 
注意：若有多个前缀字符串可以匹配，将选择具有最长匹配前缀的位置，并使用其配置

03、正则匹配，用 ~或~*分别表示区分大小写和不区分大小写的正则匹配
location ~* /test { ... }  
注意：正则表达式的搜索在第一个匹配项上终止，并使用其配置

04、常规字符匹配
location /test { ... }
 
05、通用匹配，交给 / 通用匹配
location / { ... }
```

## nginx 优化
https://www.cnblogs.com/sunsky303/p/10648861.html

## nginx 跨域
https://blog.csdn.net/weixin_34345560/article/details/93072274


# Basic Auth
htpasswd 是Apache自带的用于生成Basic Auth密匙文件的工具，htpasswd is used to create and update the flat-files used to store usernames and password for basic authentication of HTTP users.

因为用的Nginx 没用Apache, 所以需要单独安装一下
apt-get install apache2-utils

输入用户、密码 生成到指定位置，生成的文件路径需要配置在Nginx中
htpasswd -c /data/nginx/db/passwd.db user1

```
server {
       listen 80;
       server_name elk.xxx.com;
       charset utf-8;

       location / {
          proxy_pass http://elk;
          auth_basic 'ELK center';         #basic auth输入框的提示                                  
          auth_basic_user_file /data/nginx/db/passwd.db;   #生成的密匙文件
       }
}
```
Nginx重启生效之后， 通过浏览器再访问该路由 会提示 对话框输入账号密码，
Basic Auth也可以通过各种语言 以参数的形式 进行HTTP请求。

通过Basic Auth之后， 浏览器下次访问时会自动带着header Authorization: Basic YWRtaW46SGFuZGVAMTIzXzIwMTc=

# tengine
https://blog.csdn.net/boonya/article/details/106785539


## nginx ssl
```
    ##这里是将http默认的80端口重定向到https
    server {
        listen       80;
        server_name  www.dalaoyang.cn;
        rewrite ^ https://$http_host$request_uri? permanent; 
    }
    
    ##这里是将默认请求https的443端口拦截
    ##并请求转发到http://127.0.0.1:8888/
    server {
        listen 443;
        server_name www.dalaoyang.cn;
        ssl on;     
        ssl_certificate 1_dalaoyang.cn_bundle.crt;
        ssl_certificate_key 2_dalaoyang.cn.key;
        ssl_session_timeout 5m;
        location / {
            proxy_set_header X-Real-IP $remote_addr;
            proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
            proxy_set_header Host $http_host;
            proxy_set_header X-NginX-Proxy true;
               #你的项目端口号
            proxy_pass http://127.0.0.1:8888/;
            proxy_redirect off;
        }
    }
```


https://blog.csdn.net/bbwangj/article/details/82806128
https://blog.csdn.net/bbwangj/article/details/82817874
https://blog.csdn.net/bbwangj/article/details/80345932

## nginx主机多证书
https://cloud.tencent.com/developer/article/1114839

## tomcat ssl
https://cloud.tencent.com/developer/article/1114853