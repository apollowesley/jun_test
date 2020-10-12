#!/usr/bin/env bash

#创建容器 限定内存为 1G
docker build --rm -f "Dockerfile" -t java-springboot:latest .
docker run -m 1G -d -p 8080:8080 java-springboot

#查看日志
docker logs -f -t --tail 500 dd272