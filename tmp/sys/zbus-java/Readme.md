                /\\\       
                \/\\\        
                 \/\\\    
     /\\\\\\\\\\\ \/\\\         /\\\    /\\\  /\\\\\\\\\\     
     \///////\\\/  \/\\\\\\\\\  \/\\\   \/\\\ \/\\\//////     
           /\\\/    \/\\\////\\\ \/\\\   \/\\\ \/\\\\\\\\\\    
          /\\\/      \/\\\  \/\\\ \/\\\   \/\\\ \////////\\\  
         /\\\\\\\\\\\ \/\\\\\\\\\  \//\\\\\\\\\   /\\\\\\\\\\  
         \///////////  \/////////    \/////////   \//////////       QQ Group: 467741880

# zbus java client
zbus strives to make Message Queue and Remote Procedure Call fast, light-weighted and easy to build your own elastic and micro-service oriented architecture to interoperate many different platforms. Simply put, ZBUS = MQ + RPC.

zbus has two server implementations, Go and Java versions, and these servers are protocol compatible, however, Go version is strongly recommended and in active development.
 
## Maven
zbus-java has a very few dependencies, simply add the following maven configuration to your project.

	<dependency>
		<groupId>io.zbus</groupId>
		<artifactId>zbus</artifactId>
		<version>0.8.3</version>
	</dependency>

## API Demo

Only demos the gist of API, more configurable usage calls for your further interest.

### Produce message

    Broker broker = new Broker("localhost:15555");  

	Producer p = new Producer(broker);
	p.declareTopic("hong"); 
		
	Message msg = new Message();
	msg.setTopic("hong"); 
	msg.setBody("hello " + System.currentTimeMillis()); 
	
	Message res = p.publish(msg); 


### Consume message

    Broker broker = new Broker("localhost:15555");   
		
	ConsumerConfig config = new ConsumerConfig(broker);
	config.setTopic("MyTopic"); 
	config.setMessageHandler(new MessageHandler() { 
		@Override
		public void handle(Message msg, MqClient client) throws IOException {
			System.out.println(msg);
		}
	});
	
	Consumer consumer = new Consumer(config);
	consumer.start(); 

### RPC client

    Broker broker = new Broker("localhost:15555");   
	
	RpcInvoker rpc = new RpcInvoker(broker, "MyRpc");
	
	//Way 1) Raw request
	Request req = new Request();
	req.setMethod("plus");
	req.setParams(new Object[]{1,2});
	
	Response res = rpc.invokeSync(req);
	System.out.println(res);
	
	//asynchronous call
	rpc.invokeAsync(req, new ResultCallback<Response>() { 
		@Override
		public void onReturn(Response result) { 
			Integer res = (Integer)result.getResult(); 
			System.out.println(res);
		}
	});
	
	
	//Way 2) More abbreviated
	int result = rpc.invokeSync(Integer.class, "plus", 1, 2);
	System.out.println(result); 
	
	
	
	//Way 3) Strong typed proxy
	InterfaceExample api = rpc.createProxy(InterfaceExample.class);
	RpcTestCases.testDynamicProxy(api);  
	
	
	broker.close(); 

### RPC service

    RpcProcessor processor = new RpcProcessor();
	processor.addModule(new InterfaceExampleImpl());  
	
	
	Broker broker = new Broker("localhost:15555");   
	ConsumerConfig config = new ConsumerConfig();
	config.setBroker(broker);
	config.setTopic("MyRpc");
	config.setMessageHandler(processor);   
	
	Consumer consumer = new Consumer(config); 
	
	consumer.start(); 

 