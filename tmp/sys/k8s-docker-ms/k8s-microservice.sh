#!/usr/bin/env bash
# 脚本出错就停止
set -e
set -x
#1. k8s创建命名空间
kubectl create ns ns-boot-ms

#2. 在指定的命名空间下创建nginx-demo应用
kubectl run --image=reg.jdy.com/sentinel/docker_boot-ms-admin:V1.0 boot-ms-admin -n ns-boot-ms --port=8769
kubectl run --image=reg.jdy.com/sentinel/docker_boot-ms-client:V1.0 boot-ms-client -n ns-boot-ms --port=8768
#3. 查看pods
kubectl get pods  -n ns-boot-ms

#4. 查看创建过程
kubectl describe pod nginx-demo-7d588546bf-zj79q -n ns-boot-ms

#5. 查看pods明细信息
 kubectl describe pods -n ns-boot-ms

#6. 查看pod的ip
kubectl get pods -n ns-boot-ms -o wide
#7. 访问
curl 172.30.88.7

#8. 查看创建的部署
kubectl get deployment -n ns-boot-ms
#9. 发布给外部
kubectl expose deployment nginx-demo --name=nginx-service --type=NodePort -n ns-boot-ms

#10. 查看服务
kubectl get svc -n ns-boot-ms

#用json格式展示
 kc get svc nginx-service -o json -n ns-boot-ms

#外部访问：
http://172.20.182.35:33360/

#推送镜像到私有仓库
docker push reg.jdy.com/sentinel/nginx:v1

#14. 扩容/缩容
#    只需改变replicas的值即可达到目的
kubectl scale --replicas=3 deploy/boot-ms-client -n  ns-boot-ms
#11. 进入nginx容器
kubectl exec -n ns-boot-ms -it nginx-demo-7d588546bf-zj79q  /bin/bash
#从容器中考出文件
kubectl cp nginx-demo-7d588546bf-zj79q:/usr/share/nginx/html/index.html index.html -n ns-boot-ms
#拷贝文件到容器中
kubectl cp index.html nginx-demo-7d588546bf-zj79q:/usr/share/nginx/html/index.html  -n ns-boot-ms

 kubectl get secrets -n ns-boot-ms
 vi deploy.yml
 kubectl get pods -n ns-boot-ms
 kubectl get deployments -n ns-boot-ms
 kubectl -n ns-boot-ms delete deployment boot-ms-client
 kubectl apply -f deploy.yml
 kubectl get deployments -n ns-boot-ms
 kubectl -n ns-boot-ms get pods
 kubectl -n ns-boot-ms describe pods boot-ms-client-9d9666cb5-sm54l
 kubectl get svc -n ns-boot-ms
 kubectl get pods -n ns-boot-ms -o wide
 curl 172.30.40.4:8768/hello
 curl 172.20.183.175:31507/hello
 curl 10.254.251.122:31507/hello
 kubectl delete svc boot-ms-client-service -n ns-boot-ms
 kubectl expose deployment boot-ms-client --name=boot-ms-client-service --type=NodePort -n ns-boot-ms
 kubectl get svc -n ns-boot-ms
 kubectl -n ns-boot-ms delete svc boot-ms-client-service
 kubectl -n ns-boot-ms delete deployments/boot-ms-client

#暴露服务
kubectl -n ns-boot-ms expose deployment nginx --name=nginx-service --type=NodePort
