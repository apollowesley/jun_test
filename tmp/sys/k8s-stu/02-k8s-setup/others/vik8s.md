## vik8s
https://gitee.com/ihaiker/vik8s

#env
```
master 192.168.3.50
work   192.168.3.51

centos 7.x
```

#step1: download
```
https://gitee.com/ihaiker/vik8s/releases

vik8s-linux-amd64-v0.4.3.tar.gz
https://gitee.com/ihaiker/vik8s/attach_files/428424/download
```

#step2: ssh
```
[root@localhost bin]# ssh-keygen -t rsa -P ''
[root@localhost bin]# ssh-copy-id 192.168.3.50 //master
[root@localhost bin]# ssh-copy-id 192.168.3.51 //work
```

#step3: vik8s
```
[root@localhost bin]# ./vik8s --help
very easy install k8s。Build: 2020-07-06 16:36:56, Go: go1.13.8, GitLog: f7f7841

Usage:
  vik8s [command]

Available Commands:
  bash        Run commands uniformly in the cluster
  completion  Generates bash completion scripts
  config      Show yaml file used by vik8s deployment cluster
  data        Create a data folder link
  etcd        Install ETCD cluster
  help        Help about any command
  hosts       Add Management Host
  ingress     install kubernetes ingress controller
  init        init k8s
  join        join to k8s
  reduce      Simplify kubernetes configuration file
  reset       reset
  sidecars    

Flags:
      --china           Whether domestic network (default true)
  -c, --cloud string    Multi-kubernetes cluster selection (default "default")
  -f, --config string   The folder where the configuration file is located (default "/root/.vik8s")
  -h, --help            help for vik8s
  -v, --version         version for vik8s

Use "vik8s [command] --help" for more information about a command.
[root@localhost bin]# 


#init master
vik8s init --master 192.168.3.50

```

## error
```
Assert: [192.168.3.50:22,localhost] The kernel version is too low, please upgrade the kernel first, your current versio
n is: 3.10.0, the minimum requirement is 4.1.0

support:
centos7/8  shit
```