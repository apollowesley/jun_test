https://blog.csdn.net/weixin_41806245/article/details/91348366
https://blog.csdn.net/bbwangj/article/details/81776542
https://blog.csdn.net/weixin_41806245/article/details/91346571



## service 
ClusterIP
NodePort
LoadBalancer

#NodePort

//暴露在所有节点上
```
apiVersion: v1
kind: Service
metadata:
  name: nginx-service-nodeport
spec:
  selector:
      app: nginx
  ports:
    - name: http
      port: 8000
      protocol: TCP
      targetPort: 80
      #nodePort: 30010
    - name: https
      port: 8443
      protocol: TCP
      targetPort: 443
  type: NodePort
```

//直接暴露
```
kubectl expose deployment nginx-deployment-2 --type=NodePort  //pod deployment 直接暴露，自动生成service
```

//暴露在服务所在的节点上
```
apiVersion: v1
kind: Service
metadata:
  labels:
    app: nginx
  name: nginx-service
  namespace: default
spec:
  clusterIP: 10.24.0.100 
  externalTrafficPolicy: Cluster
  ports:
  - name: nginx
    protocol: TCP
    port: 80
    targetPort: 80
    nodePort: 30010
  selector:
    app: nginx
  type: NodePort
```

