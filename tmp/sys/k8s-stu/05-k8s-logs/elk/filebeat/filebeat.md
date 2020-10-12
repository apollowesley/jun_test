# filebeat
https://www.elastic.co/guide/en/beats/filebeat/7.1/index.html

https://www.elastic.co/cn/downloads/

//docker
https://www.elastic.co/cn/downloads/beats/filebeat

//linux
https://www.elastic.co/cn/downloads/beats/filebeat



https://www.cnblogs.com/kingle-study/p/10366175.html

https://www.cnblogs.com/kingle-study/p/10365631.html
https://blog.csdn.net/a464057216/article/details/51233375


# fiebeat daemonset --->es (log type auto create index)
https://www.docker.elastic.co/r/beats/filebeat-oss:7.1.1


https://www.docker.elastic.co/search?q=filebeat

//k8s daemonset
https://github.com/elastic/beats


curl -L -O https://raw.githubusercontent.com/elastic/beats/7.x/deploy/kubernetes/filebeat-kubernetes.yaml


https://kubernetes.io/docs/concepts/workloads/controllers/daemonset/

https://github.com/elastic/beats/7.x/deploy/kubernetes/filebeat-kubernetes.yaml

https://github.com/elastic/beats/blob/master/deploy/kubernetes/filebeat-kubernetes.yaml

docker pull docker.elastic.co/beats/filebeat:7.1.1

https://www.docker.elastic.co/r/beats/filebeat:7.1.0


https://www.elastic.co/guide/en/beats/filebeat/7.x/running-on-kubernetes.html


## filebeat-oss 7.1.1
https://artifacts.elastic.co/downloads/beats/filebeat/filebeat-oss-7.1.1-linux-x86_64.tar.gz

wget https://d3g5vo6xdbdb9a.cloudfront.net/downloads/kibana-plugins/opendistro-alerting/opendistro-alerting-1.1.0.0.zip


##reference
https://www.cnblogs.com/smile361/p/7688545.html



## 监控多个文件并写入es
[link](https://blog.csdn.net/shgh_2004/article/details/98650114)
>监控文件打标签，根据标签写入不同的索引中



https://www.docker.elastic.co/r/elasticsearch

https://www.docker.elastic.co/r/elasticsearch/elasticsearch:7.1.1


[](https://blog.csdn.net/shenhonglei1234/article/details/85109762)


```
[root@c-3-103 ~]# kubectl apply -f es.yaml 
[root@c-3-103 ~]# kubectl -n elasticsearch get pods  -o wide
NAME              READY   STATUS    RESTARTS   AGE     IP           NODE      NOMINATED NODE   READINESS GATES
elasticsearch-0   1/1     Running   0          5m51s   10.10.1.14   c-3-104   <none>           <none>
[root@c-3-103 ~]#
[root@c-3-104 ~]# chmod -R 777 /tmp/esdata/

[root@c-3-103 ~]# kubectl get statefulset --all-namespaces
NAMESPACE       NAME            READY   AGE
elasticsearch   elasticsearch   1/1     9m14s
[root@c-3-103 ~]# 
```

http://192.168.3.103:30001/



kubectl get ConfigMap,DaemonSet,ClusterRoleBinding,ServiceAccount -n kube-system | grep filebeat



## filebeat-elastic version 
[link](https://github.com/elastic/beats/pull/11296)
```
I did a bit of user testing:

Filebeat default -> Elasticsearch OSS: Fails to connect, as expected. It doesn't actually bail, but logs the error. I think that is fine for now.
The error, however, is not very clear: Connection marked as failed because the onConnect callback failed: cannot retrieve the elasticsearch license or no license endpoint: error from server, response code: 400. I think the error is technically correct becasue _xpack/license doesn't exist, but we should probably catch this somehow and provide a very clear error message. The exact wording should be discussed.

Filebeat OSS -> Elasticsearch OSS: Works, as expected.

Filebeat OSS -> Elasticsearch default: Works, as expected.

Filebeat default -> Elasticsearch default: Works, as expected.
```