import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.xlz.engine.rocketmq.consumer.ConsumerManager;
public class NormalConsumer {

	public static void main(String[] args) throws MQClientException {
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("ExampleConsumerGroupNormal1");
		consumer.setNamesrvAddr("192.168.232.128:9876;192.168.232.129:9876;");  
		// only subsribe messages have property a, also a >=0 and a <= 3
		//consumer.subscribe("TopicTest", MessageSelector.bySql("a between 0 and 3");
		
		consumer.subscribe("TopicGrayTest", "TagA");
		
		/*String filterCodePath = System.getProperty("user.dir") + "\\src\\com\\xlz\\mq\\filter\\NormalMessageFilterImpl.java";
        String filterCode = MixAll.file2String(filterCodePath);
        System.out.println(filterCode);
        consumer.subscribe("TopicGrayTest", "com.xlz.mq.filter.NormalMessageFilterImpl", filterCode);*/
		
		consumer.registerMessageListener(new MessageListenerConcurrently() {
		    @Override
		    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
		    	for(MessageExt msg : msgs){
					try {
						//Object object = toObject (msg.getBody()); 
						System.out.println(msg.getMsgId() + "====>" + new String(msg.getBody(),"utf-8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
		    	}
		    	return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		    }
		});
		consumer.start();
		//ConsumerManager.getInstance().registerConsumer(consumer);
	}
	
	 /**  
     * 数组转对象  
     * @param bytes  
     * @return  
     */  
    public static Object toObject (byte[] bytes) {      
        Object obj = null;      
        try {        
            ByteArrayInputStream bis = new ByteArrayInputStream (bytes);        
            ObjectInputStream ois = new ObjectInputStream (bis);        
            obj = ois.readObject();      
            ois.close();   
            bis.close();   
        } catch (IOException ex) {        
            ex.printStackTrace();   
        } catch (ClassNotFoundException ex) {        
            ex.printStackTrace();   
        }      
        return obj;    
    }   
}
