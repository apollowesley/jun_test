创建websocket服务模块步骤 例如Chat模块
在websocket目录下创建文件 Chat.php 请参考示例
在Message.php 内创建同名类,启动服务绑定的各回调绑定到对应的目录内同名文件。
例如对接服务接口onTask，需要绑定到open目录下的Chat.php 内的onOpen方法，逻辑代码就写在这里

整站设计思路。
1，接收消息处理
1.1 h5客户端通过websocket发过来的消息
onMessage 接收消息不做任何处理。只从全局变量获取到fd对应的用户信息。递交给task触发多进程工作模式;
task多进程处理业务逻辑，处理完毕返回给finish主线程。然后在根据情况进行推送push给用户
1.2，udp端口发过来的消息
onPacket 接收到的消息不做任何处理，直接push推送给用户。

common/Push 封装了4重推送方法 
1. pushFd($server, $to_fd, $msg = '') 
根据连接fd推送消息到客户端
2. pushFdAll($server, $msg = '', $allFd = [])
$allFd为空，发送消息给所有在线用户，或者指定某些用户，$allFd=[32,2332];

3. pushUid($server, $to_uid, $msg)
发送消息给uid用户,支持数据备份，数据存储。用户不在线消息,写入数据库

4. pushUidAll($server, $msg = '', $UidAll = [3232,90908]) 
发送消息给指定某些uid用户,支持数据备份，数据存储。用户不在线消息,写入数据库