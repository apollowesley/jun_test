# About CoreDNS
CoreDNS is a flexible, extensible DNS server that can serve as the Kubernetes cluster DNS. Like Kubernetes, the CoreDNS project is hosted by the CNCF.You can use CoreDNS instead of kube-dns in your cluster by replacing kube-dns in an existing deployment, or by using tools like kubeadm that will deploy and upgrade the cluster for you.

## coredns-testing
kubectl run -it --rm --restart=Never --image=infoblox/dnstools:latest dnstools

## reference
[link](https://www.jianshu.com/p/834eb6ff2a72)


