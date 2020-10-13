import java.util.List;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.xlz.engine.rocketmq.filter.GrayMessageFilterImpl;
import com.xlz.engine.rocketmq.filter.NormalMessageFilterImpl;
import com.xlz.engine.rocketmq.server.GrayConfigAgent;
 
 
public class SimulationFilterConsumer {
 
    public static void main(String[] args) throws InterruptedException, MQClientException {
    	GrayConfigAgent.getInstance();
    	String group = "ExampleConsumerGroupGray";
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(group);
        consumer.setNamesrvAddr("192.168.232.128:9876;192.168.232.129:9876");
        consumer.subscribe("TopicGrayTest", "TagA");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs,
                    ConsumeConcurrentlyContext context) {
                try {
                    //模拟filter
                    NormalMessageFilterImpl.applicationId = "paygent";
                    NormalMessageFilterImpl.consumerId = "paygent$$TopicGrayTest$$TagA";
                    boolean result1 = new NormalMessageFilterImpl().match(msgs.get(0));
                    
                    GrayMessageFilterImpl.applicationId = "paygent";
                    GrayMessageFilterImpl.consumerId = "paygent$$TopicGrayTest$$TagA";
                    boolean result2 = new GrayMessageFilterImpl().match(msgs.get(0));
                    System.out.println("filter结果NormalMessageFilterImpl："+result1);
                    System.out.println("filter结果GrayMessageFilterImpl："+result2);
                    System.out.println("===================================");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        
   }
    
}