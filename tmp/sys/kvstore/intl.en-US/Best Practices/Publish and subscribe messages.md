# Publish and subscribe messages {#concept_agy_wxn_tdb .concept}

## Scenarios {#section_imr_qzv_wdb .section}

ApsaraDB for Redis provides publishing \(pub\) and subscription \(sub\) functions, just like Redis. This allows multiple clients to subscribe to messages published by a client.

It must be noted that messages published using ApsaraDB for Redis are non-persistent. That means the message publisher is only responsible for publishing a message and does not save previously sent messages, whether or not they were received by anyone. Thus, messages are lost once published. Message subscribers receive messages published after their subscription. They do not receive the earlier messages in the channel.  

In addition, a message publisher \(publish client\) does not connect to a server exclusively. While publishing messages, you can perform more operations \(for example, List\) from the same client, at the same time. However, a message subscriber \(subscribe client\) connects to a server exclusively. That is, during the subscription, the client may not perform any other operations. Rather, the operations are blocked while the client is waiting for messages in the channel. Thus, message subscribers must use a dedicated server connection or thread \(see the following example\).      

## Sample Code {#section_jmr_qzv_wdb .section}

**For the message publisher \(publish client\)**

```
package message.kvstore.aliyun.com;
import redis.clients.jedis.Jedis;
public class KVStorePubClient {
    private Jedis jedis;//
    public KVStorePubClient(String host,int port, String password){
        jedis = new Jedis(host,port);
        //KVStore instance password
        String authString = jedis.auth(password);//password
        if (! authString.equals("OK"))
        {
            System.err.println("AUTH Failed: " + authString);
            return;
        }
    }
    public void pub(String channel,String message){
        System. out. println ("> Publish> channel: "+ channel +"> message sent: "+ message );
        jedis.publish(channel, message);
    }
    public void close(String channel){
         System.out.println(" >>> PUBLISH End > Channel:"+channel+" > Message:quit");
         //The message publisher stops sending by sending a “quit” message
        jedis.publish(channel, "quit");
    }
}
```

**For the message subscriber \(subscribe client\)**

```
package message.kvstore.aliyun.com;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
public class KVStoreSubClient extends Thread{
    private Jedis jedis;
    private String channel;
    private JedisPubSub listener;
    public KVStoreSubClient(String host,int port, String password){
        jedis = new Jedis(host,port);
                //ApsaraDB for Redis instance password
                String authString = jedis.auth(password);//password
                if (! authString.equals("OK"))
                {
                    System.err.println("AUTH Failed: " + authString);
                    return;
                }
    }
    public void setChannelAndListener(JedisPubSub listener,String channel){
        this.listener=listener;
        this.channel=channel;
    }
    private void subscribe(){
        if(listener==null || channel==null){
            System.err.println("Error:SubClient> listener or channel is null");
        }
         System.out.println(" >>> SUBSCRIBE > Channel:"+channel);
        System.out.println();
         //When the recipient is listening for subscribed messages, the process is blocked until the quit message is received (passively) or the subscription is cancelled actively
        jedis.subscribe(listener, channel);
    }
    public void unsubscribe(String channel){
        System.out.println(" >>> UNSUBSCRIBE > Channel:"+channel);
        System.out.println();
        listener.unsubscribe(channel);
    }
    @ Override
    public void run() {
        try {
            System.out.println();
            System.out.println("---------SUBSCRIBE Begin-------");
            Subscribe ();
            System.out.println("----------SUBSCRIBE End-------");
            System.out.println();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
```

**For the message listener**

```
package message.kvstore.aliyun.com;
import redis.clients.jedis.JedisPubSub;
public class KVStoreMessageListener extends JedisPubSub{
    @Override
    public void onMessage(String channel, String message) {
        System.out.println(" <<< SUBSCRIBE< Channel:" + channel + " >Recceived Message:" + message );
        System.out.println();
        //When a quit message is received, the subscription is cancelled (passively)
        if(message.equalsIgnoreCase("quit")){
            this.unsubscribe(channel);
        }
    }
    @Override
    public void onPMessage(String pattern, String channel, String message) {
        // TODO Auto-generated method stub
    }
    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        // TODO Auto-generated method stub
    }
    @ Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
        // Todo auto-generated method stub
    }
    @Override
    public void onPUnsubscribe(String pattern, int subscribedChannels) {
        // TODO Auto-generated method stub
    }
    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        // TODO Auto-generated method stub
    }
}
```

**Sample main process**

```
package message.kvstore.aliyun.com;
import java.util.Random;
import redis.clients.jedis.JedisPubSub;
public class KVStorePubSubTest {
    //Information for connecting ApsaraDB for Redis, which can be obtained from the console
    static final String host = "xxxxxxxxxx.m.cnhza.kvstore.aliyuncs.com";
    static final int port = 6379;
    Static final string Password = "password"; // Password
    public static void main(String[] args) throws Exception{
            KVStorePubClient pubClient = new KVStorePubClient(host, port,password);
            final String channel = "KVStore Channel-A";
            //The message sender starts sending messages, but there are no subscribers, the messages will not be received
            pubClient.pub(channel, "Alibaba Cloud message 1:(no subscribers, this message will not be received)");
            // Message recipient
            KVStoreSubClient subClient = new KVStoreSubClient(host, port,password);
            JedisPubSub listener = new KVStoreMessageListener();
            subClient.setChannelAndListener(listener, channel);
            // Message recipient starts subscribing
            subClient.start();
            //The message sender continues sending messages
            for (int i = 0; i < 5; i++) {
                String message=UUID.randomUUID().toString();
                pubClient.pub(channel, message);
                Thread.sleep(1000);
            }
            // Message recipient cancels the subscription
            subClient.unsubscribe(channel);
            Thread.sleep(1000);
            pubClient.pub(channel, "Alibaba Cloud message 2:(subscription cancaled, this message will not be received)");
             //The message publisher stops sending by sending a “quit” message
            //When other message recipients, if any, receive "quit" in listener.onMessage(), the "unsubscribe" operation is performed.
            pubClient.close(channel);
        }
    }
```

## Output {#section_y4r_qzv_wdb .section}

After you access the ApsaraDB for Redis instance with the correct address and password and run the preceding Java code, the following output is displayed.

```
  >>>   >>> PUBLISH > Channel:KVStore Channel-A > Sends the message Alibaba Cloud Message 1: (there are no subscribers yet, so no one would receive the message)
----------SUBSCRIBE starts-------
  >>>  >>> SUBSCRIBE > Channel:KVStore Channel-A
  >>> >>> PUBLISH > Channel:KVStore Channel-A > Sends the message 0f9c2cee-77c7-4498-89a0-1dc5a2f65889
<<< SUBSCRIBE < Channel:KVStore Channel-A > Receives the message 0f9c2cee-77c7-4498-89a0-1dc5a2f65889
  >>> >>> PUBLISH > Channel:KVStore Channel-A > Sends the message ed5924a9-016b-469b-8203-7db63d06f812
  <<< SUBSCRIBE < Channel:KVStore Channel-A > Receives the message ed5924a9-016b-469b-8203-7db63d06f812
  >>> >>> PUBLISH > Channel:KVStore Channel-A > Sends the message f1f84e0f-8f35-4362-9567-25716b1531cd
  <<<  SUBSCRIBE < Channel:KVStore Channel-A > Receives the message f1f84e0f-8f35-4362-9567-25716b1531cd
  >>> >>> PUBLISH > Channel:KVStore Channel-A > Sends the message 746bde54-af8f-44d7-8a49-37d1a245d21b
  <<<  SUBSCRIBE < Channel:KVStore Channel-A > Receives the message 746bde54-af8f-44d7-8a49-37d1a245d21b
  >>> >>> PUBLISH > Channel:KVStore Channel-A > Sends the message 8ac3b2b8-9906-4f61-8cad-84fc1f15a3ef
  <<<  SUBSCRIBE < Channel:KVStore Channel-A > Receives the message 8ac3b2b8-9906-4f61-8cad-84fc1f15a3ef
  >>> >>> UNSUBSCRIBE > Channel:KVStore Channel-A
----------SUBSCRIBE ends-------
  >>> >>> PUBLISH > Channel:KVStore Channel-A > Sends the message Aliyun Message 2: (the subscription has been cancelled, so the message is not received)
  >>> >>> PUBLISH ends > Channel:KVStore Channel-A > Message: quit
```

The preceding example demonstrates a situation with one publisher and one subscriber. Actually, there can be many publishers, subscribers, and even many message channels. In such cases, you are required to slightly change the code that best fit the situation.

