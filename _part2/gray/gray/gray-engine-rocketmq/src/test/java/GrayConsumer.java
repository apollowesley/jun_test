import java.io.UnsupportedEncodingException;
import java.util.List;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.MixAll;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.xlz.engine.rocketmq.consumer.ConsumerManager;
import com.xlz.engine.rocketmq.filter.NormalMessageFilterImpl;
 
 
public class GrayConsumer {
 
    public static void main(String[] args) throws InterruptedException, MQClientException {
    	String group = "ExampleConsumerGroupGray";
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(group);
        consumer.setNamesrvAddr("192.168.232.128:9876;192.168.232.129:9876");
        // 使用Java代码，在服务器做消息过滤
        String filterCodePath = System.getProperty("user.dir") + "\\src\\main\\java\\com\\xlz\\engine\\rocketmq\\filter\\GrayMessageFilterImpl.java";
        String filterCode = MixAll.file2String(filterCodePath);
        System.out.println(filterCode);
        consumer.subscribe("TopicGrayTest", "com.xlz.engine.rocketmq.filter.NormalMessageFilterImpl", filterCode);
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                    ConsumeConcurrentlyContext context) {
                try {
                    System.out.println(msgs.get(0).getMsgId() + "====>" + new String(msgs.get(0).getBody(),"utf-8"));
                
                    //模拟filter
                    NormalMessageFilterImpl.applicationId = "";
                    NormalMessageFilterImpl.consumerId = "";
                    new NormalMessageFilterImpl().match(msgs.get(0));
                
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        ConsumerManager.getInstance().registerConsumer(consumer);
        
   }
    
}