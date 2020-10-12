# ipvs
#ipvs相关(忽略)   //service工作 lvs模式效率较高，默认iptables

https://blog.csdn.net/wangshuminjava/article/details/100089917
https://blog.csdn.net/wangshuminjava/article/details/100089471


## step1: all nodes
```
yum install -y ipset ipvsadm  //各个节点，为了查看ipvs代理规则
```

```
cat > /etc/sysconfig/modules/ipvs.modules <<EOF
#!/bin/bash
modprobe -- ip_vs
modprobe -- ip_vs_rr
modprobe -- ip_vs_wrr
modprobe -- ip_vs_sh
modprobe -- nf_conntrack_ipv4
EOF

chmod 755 /etc/sysconfig/modules/ipvs.modules
bash /etc/sysconfig/modules/ipvs.modules
lsmod | grep -e ip_vs -e nf_conntrack_ipv4
```

