# kong
https://konghq.com/kong/
[install_doc](https://konghq.com/install/?itm_source=website&itm_medium=nav)

https://hub.docker.com/_/kong/

https://www.jianshu.com/p/a68e45bcadb6

# docker-kong express
[link](https://docs.konghq.com/install/docker/?_ga=2.112940486.377578438.1593075554-33507025.1592647112)
01、kong-net
```
[root@c-3 yum.repos.d]# docker network create --subnet 172.20.20.0/24 kong-net
```

02、start db
#cassandra nosql
```
docker run -d --name kong-database \
    --network=kong-net \
    -p 9042:9042 \
    cassandra:3
```
#postgresql
```
docker run -d --name kong-database \
    --network=kong-net \
    -p 5432:5432 \
    -e "POSTGRES_USER=kong" \
    -e "POSTGRES_PASSWORD=kong" \
    -e "POSTGRES_DB=kong" \
    postgres:9.6
```
>kong使用db可以选一个使用

03、prepare db
#cassandra
```
docker run --rm \
     --network=kong-net \
     -e "KONG_DATABASE=cassandra" \
     -e "KONG_CASSANDRA_CONTACT_POINTS=kong-database" \
     kong:latest kong migrations bootstrap
```
#postgrse
```
docker run --rm \
     --network=kong-net \
     -e "KONG_DATABASE=postgres" \
     -e "KONG_PG_HOST=kong-database" \
     -e "KONG_PG_USER=kong" \
     -e "KONG_PG_PASSWORD=kong" \
     kong:latest kong migrations bootstrap
```
>KONG_DATABASE values: cassandra or postgres

04、start kong
#use cassandra
```
docker run -d --name kong \
     --network=kong-net \
     -e "KONG_DATABASE=cassandra" \
     -e "KONG_CASSANDRA_CONTACT_POINTS=kong-database" \
     -e "KONG_PROXY_ACCESS_LOG=/dev/stdout" \
     -e "KONG_ADMIN_ACCESS_LOG=/dev/stdout" \
     -e "KONG_PROXY_ERROR_LOG=/dev/stderr" \
     -e "KONG_ADMIN_ERROR_LOG=/dev/stderr" \
     -e "KONG_ADMIN_LISTEN=0.0.0.0:8001, 0.0.0.0:8444 ssl" \
     -p 8000:8000 \
     -p 8443:8443 \
     -p 0.0.0.0:8001:8001 \
     -p 0.0.0.0:8444:8444 \
     kong:latest
```

#use postgresql
```
 $ docker run -d --name kong \
     --network=kong-net \
     -e "KONG_DATABASE=postgres" \
     -e "KONG_PG_HOST=kong-database" \
     -e "KONG_PG_USER=kong" \
     -e "KONG_PG_PASSWORD=kong" \
     -e "KONG_PROXY_ACCESS_LOG=/dev/stdout" \
     -e "KONG_ADMIN_ACCESS_LOG=/dev/stdout" \
     -e "KONG_PROXY_ERROR_LOG=/dev/stderr" \
     -e "KONG_ADMIN_ERROR_LOG=/dev/stderr" \
     -e "KONG_ADMIN_LISTEN=0.0.0.0:8001, 0.0.0.0:8444 ssl" \
     -p 8000:8000 \
     -p 8443:8443 \
     -p 8001:8001 \
     -p 8444:8444 \
     kong:latest
```

05、use kong
```
curl -i http://localhost:8001/

#return 200 ok
curl -I -m 10 -o /dev/null -s -w '%{http_code}\n' http://localhost:8001/
```

#kong default listen ports means
```
Kong默认监听下面端口：
8000，监听来自客户端的HTTP流量，转发到你的upstream服务上
8443，监听HTTPS的流量，功能跟8000一样。可以通过配置文件禁止
8001，Kong的HTTP监听的api管理接口
8444，Kong的HTTPS监听的API管理接口
```

#kong-dashboard konga  
https://github.com/pantsel/konga
```
docker run -p 1337:1337 \
    --network kong-net \
    --name konga \
    -e "NODE_ENV=development" \
    -e "TOKEN_SECRET=123123" \
    pantsel/konga
```
>注册admin账户添加kong-admin-api 地址管理

#kong-dashboard/konga version limit
```
docker run --rm -p 8080:8080 --network=kong-net pgbi/kong-dashboard start --kong-url http://kong:8001
```

# kong-dashboard
https://github.com/PGBI/kong-dashboard
>注意kong版本的限制

https://github.com/pantsel/konga