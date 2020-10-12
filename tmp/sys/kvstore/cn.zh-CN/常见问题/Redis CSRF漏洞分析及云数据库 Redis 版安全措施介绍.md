# Redis CSRF漏洞分析及云数据库 Redis 版安全措施介绍 {#concept_xkd_5vz_xdb .concept}

## CSRF 介绍 { .section}

CSRF（Cross-site request forgery）跨站请求伪造，也被称为 One Click Attack 或者 Session Riding，通常缩写为 CSRF 或者 XSRF，是一种对网站的恶意利用。

![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/13767/3937_zh-CN.png)

上图为 CSRF 攻击的一个简单模型：用户访问恶意网站 B，恶意网站 B 返回给用户的 HTTP 信息中要求用户访问网站 A，而由于用户和网站 A 之间可能已经有信任关系导致这个请求就像用户真实发送的一样会被执行。

上图为 CSRF 攻击的一个简单模型用户访问恶意网站 B，恶意网站 B 返回给用户的 HTTP 信息中要求用户访问网站 A，而由于用户和网站 A 之间可能已经有信任关系，导致这个请求就像用户真实发送的一样会被执行。

## Redis CSRF 攻击模型 { .section}

![](http://static-aliyun-doc.oss-cn-hangzhou.aliyuncs.com/assets/img/13767/3938_zh-CN.png)

根据上面 CSRF 的原理，恶意网站可以让用户发送一个 HTTP 请求给 Redis。由于 Redis 支持文本协议，而在解析协议过程中如果碰到非法的协议并不会断开链接，这个时候攻击者可以通过在正常的 HTTP 请求之后携带 Redis 命令从而在 Redis 上执行命令，而如果用户和 Redis 之间没有密码验证，则可以正常执行 Redis 命令并对数据进行加密勒索，就像之前 MongoDB 赎金事件一样。

## 内核修复 { .section}

Redis 作者在3.2.7版本对该问题进行了一个修复，解决方案是对于 POST 和 Host: 的关键字进行特殊处理记录日志并断开该链接避免后续 Redis 合法请求的执行。

## Redis 安全风险 { .section}

更早之前 Redis 暴露了一个安全漏洞，黑客在某一条件下可以拿到 Redis 服务的 root 权限，这些安全漏洞发生的原因主要是因为用户对于 Redis 的安全机制了解比较少，还有用户缺少 Redis 相关的运维经验，同时由于 Redis 也没有提供更丰富的安全防护机制。云数据库 Redis 版提供了更安全的 Redis 服务，对于云上的 Redis 服务建议使用云数据库 Redis 服务。

## 云数据库 Redis 版安全规范 { .section}

**内网访问，避免公网访问**

云数据库 Redis 版只提供可信任的内网访问，用户不能通过公网访问到云数据库 Redis 版。

**物理网络隔离**

云数据库 Redis 版物理机网络和用户网络物理隔离，用户虚拟机无法直接访问后端物理机网络。

**VPC 网络隔离**

对于阿里云用户如果使用专有云 VPC 网络，则能够保证只有同一个 VPC 网络下的服务可以互相访问。

**白名单**

云数据库 Redis 版支持白名单设置，用户可以在控制台设置可以访问的 IP 白名单。

**密码访问**

云数据库 Redis 版对于经典网络的实例强制开启密码鉴权，用户可以通过设置复杂的密码避免密码被攻破。

**访问权限隔离**

云数据库 Redis 版后端每个实例进行权限和访问目录的隔离，每个实例都只能访问自己实例的路径，避免实例之间的互相影响。

**禁用危险命令**

云数据库 Redis 版禁用了一些危险的系统管理命令 config，save 等，用户如果需要进行参数修改需要通过控制台二次验证之后才能修改，同时也避免了直接操作后端的配置文件及管理命令。

**安全监控**

云数据库 Redis 版具有完善的物理机安全监控，定期扫描和更新安全监控策略，提早发现安全风险。

**Redis 集群密码**

原生 Redis 3.0的 cluster 版本不支持密码验证，云数据库 Redis 版集群版本支持密码验证，提高了安全性。

