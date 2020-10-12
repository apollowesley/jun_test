# helm

https://github.com/jfrog/chartcenter/

## helm-chartcenter
alibaba-app-hub	https://apphub.aliyuncs.com

https://chartcenter.io/
>JFrog 发布 Helm Chart 中央存储库 ChartCenter

#step1: 将ChartCenter添加为您的Helm repo
```
helm repo add center https://repo.chartcenter.io
helm repo update
helm search repo center/xxx
```

#step2: 将ChartCenter用作存储库
```
helm search repo center/jfrog/artifactory-jcr
NAME                                CHART Version  App Version  DESCRIPTION
center/jfrog/artifactory-jcr 2.3.1            7.5.7          JFrog Container Reg 

helm search repo center/rancher
NAME                                 CHART Version  App Version  DESCRIPTION
center/rancher-stable/rancher 2.4.5.           v2.4.5        Install Rancher Serve
```