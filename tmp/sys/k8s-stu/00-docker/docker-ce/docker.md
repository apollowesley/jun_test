# docker起步发展
docker  --pass层 2014起步，2015大爆发  
Doing the old thing the new way! //变革

#特点：
- 容器
- 虚拟化
- 轻量级
- 可移植
- 分布式

Docker是一个开源的应用容器引擎，可以轻松的为任何应用创建一个轻量级的、可移植的、自给自足的容器。利用Linux的LXC、AUFS、Go语言、cgroup实现了资源的独立，可以很轻松的实现文件、资源、网络等隔离，其最终的目标是实现类似PaaS平台的应用隔离。

## Docker三个基本概念
- 镜像（Image）
- 容器（Container）
- 仓库（Repository）

### Docker 镜像
Docker 镜像就是一个只读的模板,一个镜像可以包含一个完整的 ubuntu操作系统环境，里面仅安装了 Apache 或用户需要的其它应用程序，镜像可以用来创建Docker 容器Docker提供了一个很简单的机制来创建镜像或者更新现有的镜像，用户甚至可以直接从其他人那里下载一个已经做好的镜像来直接使用。

### Docker 容器
Docker 利用容器来运行应用
容器是从镜像创建的运行实例。它可以被启动、开始、停止、删除。每个容器都是相互隔离的、保证安全的平台。可以把容器看做是一个简易版的 Linux 环境（包括root用户权限、进程空间、用户空间和网络空间等）和运行在其中的应用程序。
>注：镜像是只读的，容器在启动的时候创建一层可写层作为最上层

### Docker 仓库
仓库是集中存放镜像文件的场所。有时候会把仓库和仓库注册服务器（Registry）混为一谈，并不严格区分。实际上，仓库注册服务器上往往存放着多个仓库，每个仓库中又包含了多个镜像，每个镜像有不同的标签（tag）

仓库分为公开仓库（Public）和私有仓库（Private）两种形式
最大的公开仓库是 Docker Hub，存放了数量庞大的镜像供用户下载。 国内的公开仓库包括 Docker Pool等，可以提供大陆用户更稳定快速的访问。

当然，用户也可以在本地网络内创建一个私有仓库
当用户创建了自己的镜像之后就可以使用 push 命令将它上传到公有或者私有仓库，这样下次在另外一台机器上使用这个镜像时候，只需要从仓库上 pull 下来就可以了

>注：Docker 仓库的概念跟 Git 类似，注册服务器可以理解为 GitHub 这样的托管服务

Docker值得关注的特性：
文件系统隔离：每个进程容器运行在一个完全独立的根文件系统里
资源隔离：系统资源，像CPU和内存等可以分配到不同的容器中，使用cgroup
网络隔离：每个进程容器运行在自己的网络空间，虚拟接口和IP地址
日志记录：Docker将会收集和记录每个进程容器的标准流（stdout/stderr/stdin），用于实时检索或批量检索
变更管理：容器文件系统的变更可以提交到新的映像中，并可重复使用以创建更多的容器。无需使用模板或手动配置。
交互式shell：Docker可以分配一个虚拟终端并关联到任何容器的标准输入上，例如运行一个一次***互shell。

### Docker通常用于如下场景
- web应用的自动化打包和发布；
- 自动化测试和持续集成、发布；
在服务型环境中部署和调整数据库或其他的后台应用；
从头编译或者扩展现有的OpenShift或Cloud Foundry平台来搭建自己的PaaS环境

https://www.cnblogs.com/sunsky303/p/12102714.html


https://www.cnblogs.com/sunsky303/p/11965582.html

https://www.jianshu.com/p/a9363d3edd64
https://www.jianshu.com/p/e6eb77e01026
https://www.jianshu.com/p/bcd9aeb179ef


https://www.cnblogs.com/lori/p/7193641.html
https://www.cnblogs.com/sunsky303/p/11356263.html
https://blog.csdn.net/weixin_34345560/article/details/88755573
https://blog.csdn.net/weixin_34345560/article/details/88734120
https://blog.csdn.net/wangshuminjava/article/details/86620106