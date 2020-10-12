# elk-k8s
https://www.elastic.co/guide/index.html

https://www.elastic.co/cn/downloads/

https://discuss.elastic.co/

https://elasticsearch.cn/

https://github.com/elastic/cloud-on-k8s

https://www.elastic.co/cn/elastic-cloud-kubernetes

https://www.elastic.co/guide/en/cloud-on-k8s/master/index.html

https://zhuanlan.zhihu.com/p/105453664


## install

kubectl apply -f https://download.elastic.co/downloads/eck/1.1.2/all-in-one.yaml

wget -k https://download.elastic.co/downloads/eck/1.1.2/all-in-one.yaml

kubectl apply -f all-in-one.yaml

//elastic-operator logs
kubectl -n elastic-system logs -f statefulset.apps/elastic-operator

[root@general-k8s-master yaml]# kubectl get pods,svc,deployment,crd -n elastic-system


//elasticsearch
https://www.elastic.co/guide/en/cloud-on-k8s/current/k8s-deploy-elasticsearch.html

PASSWORD=$(kubectl get secret quickstart-es-elastic-user -o go-template='{{.data.elastic | base64decode}}')
curl -u "elastic:$PASSWORD" -k "https://quickstart-es-http:9200"



kubectl get kibana,elasticsearch


elastic
kubectl get secret quickstart-es-elastic-user -o=jsonpath='{.data.elastic}' | base64 --decode; echo


#es_home
/usr/share/elasticsearch




# eror
#错误描述
```
curl: (35) Peer reports incompatible or unsupported protocol version.
```
#centos curl协议不支持
yum update -y nss curl libcurl

```
[root@general-k8s-master eck]# kubectl get svc,pod,deployment
NAME                              TYPE        CLUSTER-IP     EXTERNAL-IP   PORT(S)    AGE
service/kubernetes                ClusterIP   10.20.0.1      <none>        443/TCP    28h
service/quickstart-es-es-sigle    ClusterIP   None           <none>        <none>     77s
service/quickstart-es-http        ClusterIP   10.20.74.96    <none>        9200/TCP   81s
service/quickstart-es-transport   ClusterIP   None           <none>        9300/TCP   81s
service/quickstart-kb-http        ClusterIP   10.20.237.22   <none>        5601/TCP   78s

NAME                                 READY   STATUS    RESTARTS   AGE
pod/quickstart-es-es-sigle-0         1/1     Running   0          77s
pod/quickstart-kb-5cfbfdf49d-hzxzk   1/1     Running   0          78s

NAME                                  READY   UP-TO-DATE   AVAILABLE   AGE
deployment.extensions/quickstart-kb   1/1     1            1           78s
[root@general-k8s-master eck]# 
[root@general-k8s-master eck]# 
[root@general-k8s-master eck]# 
[root@general-k8s-master eck]# 
[root@general-k8s-master eck]# 
[root@general-k8s-master eck]# kubectl get elasticsearch,kibana
NAME                                                    HEALTH   NODES   VERSION   PHASE   AGE
elasticsearch.elasticsearch.k8s.elastic.co/quickstart   green    1       7.5.0     Ready   101s

NAME                                      HEALTH   NODES   VERSION   AGE
kibana.kibana.k8s.elastic.co/quickstart   green    1       7.5.0     2m43s
```


#认证口令
elastic
kubectl get secret quickstart-es-elastic-user -o=jsonpath='{.data.elastic}' | base64 --decode; echo


kubectl -n elasticsearch get secret quickstart-es-elastic-user -o=jsonpath='{.data.elastic}' | base64 --decode; echo




//均可以在es/kibana登录
elastic
31eW3267h3Pf9VPQa57NluBB


https://172.16.0.131:30001/    elasticsearch
https://172.16.0.131:30002/    kibana
admin/ingcreations   //superuser



# eck helm on k8s
https://logz.io/blog/deploying-the-elk-stack-on-kubernetes-with-helm/

https://rancher.ingcreations.com/