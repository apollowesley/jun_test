import java.util.ArrayList;
import java.util.List;

import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;

public class MessageProducer {
	public static void main(String[] args) throws Exception {
		DefaultMQProducer producer = new DefaultMQProducer("ExampleProducerGroup");
		producer.setNamesrvAddr("192.168.232.128:9876;192.168.232.129:9876;");  
		producer.start();
		List<String> list = new ArrayList<>();
		list.add("{\"name\":\"zhangsan\"}");
		list.add("{\"name\":\"zhangsan1\"}");
		list.add("{\"name\":\"wangmazi\"}");
		list.add("{\"name\":\"lisi\"}");
		list.add("{\"name\":\"wangwu\"}");
		for (String msgStr : list) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			Message msg = new Message("TopicGrayTest" , "TagA" , msgStr.getBytes() );
			//UserModel model = new UserModel();
			//Message msg = new Message("TopicGrayTest" , "TagA" , model );
			//msg.putUserProperty("SequenceId", String.valueOf(i));
			SendResult result = producer.send(msg);
			System.out.println(result);
		}
		producer.shutdown();
	}
}
