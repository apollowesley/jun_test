

[root@k8s-master ~]# kubectl explain pod.spec.volumes.hostPath

[root@k8s-master test]# kubectl apply -f 1.yaml --dry-run  //测试yaml语法是否OK

```
apiVersion: v1
kind: Pod
metadata:
  name: test-hostpath
  namespace: default
spec:
  containers:
  - name: test-hostpath
    image: ubuntu
    args: ["/bin/bash","-c"]
    volumeMounts:
    - mountPath: /tmp/data
      name: hostpath-data
  volumes:
  - name: hostpath-data
    hostPath:
      path: /tmp/data
      type: DirectoryOrCreate
```
