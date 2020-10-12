
https://blog.csdn.net/weixin_34345560/article/details/93885787

https://blog.csdn.net/weixin_41806245/article/details/90266657

https://blog.csdn.net/weixin_41806245/article/details/90769745


https://blog.csdn.net/bbwangj/article/details/82011573
https://blog.csdn.net/wangshuminjava/article/details/90408541

#deployment + service
```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: mscloud-config
spec:
  replicas: 1
  selector:
    matchLabels:
      app: mscloud-config
      project: mscloud
  template:
    metadata:
      labels:
        app: mscloud-config
        project: mscloud
    spec:
      containers:
      - name: config
        image: xiliangma/mscloud-config:latest
        imagePullPolicy: IfNotPresent
        ports:
        - name: dev
          containerPort: 8888
          hostPort: 30001
        resources:
          limits:
            cpu: 1000m
            memory: 1024Mi
          requests:
            cpu: 300m
            memory: 256Mi
        volumeMounts:
        - mountPath: /mscloud/config
          name: config-data
      volumes:
      - name: config-data
        hostPath:
          path: /tmp/mscloud/config

---
apiVersion: v1
kind: Service
metadata:
  name: mscloud-config-service
  labels:
    app: mscloud-config
    project: mscloud
spec:
  selector:
    app: mscloud-config
    project: mscloud
  ports:
  - name: dev
    port: 8888
```
>大部分module需要创建service，这里服务间访问通过service访问
