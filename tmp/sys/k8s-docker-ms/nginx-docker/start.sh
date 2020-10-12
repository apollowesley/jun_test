#!/usr/bin/env bash
set -e
set -x
#构建镜像
docker build -t reg.jdy.com/sentinel/nginx:v1 .
#推送到私有仓库
docker push reg.jdy.com/sentinel/nginx:v1
#创建部署
kubectl apply -f nginx-deploy.yml
#创建服务
kubectl -n ns-boot-ms expose deployment nginx --name=nginx-service --type=NodePort