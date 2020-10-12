# Pipeline {#concept_nl3_r1w_wdb .concept}

## Scenario introduction {#section_sdc_qbw_wdb .section}

Similar to Redis, ApsaraDB for Redis provides the pipeline feature. A client interacts with a server through one-way pipelines, one for sending requests and the other for receiving responses. You can send operation requests consecutively from the client to the server. However, during this period, the server does not send the responses to each operation request. The client receives the response to each request from the server until it sends a quit message to the server.

Pipelines are useful, for example, when several operation commands need to be quickly submitted to the server but the responses and operation results are not required immediately. In this case, pipelines are used as a batch processing tool to optimize the performance. The performance is enhanced because the overhead of the TCP connection is reduced.

However, the client using pipelines in the app connects to the server exclusively, and non-pipeline operations are blocked until the pipelines are closed. If you need to perform other operations at the same time, you can establish a dedicated connection for pipeline operations to separate them from conventional operations.

## Sample code 1 {#section_tdc_qbw_wdb .section}

**Performance comparison**

``` {#codeblock_ky5_mdk_y0c}
package pipeline.kvstore.aliyun.com;
import java.util.Date;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
public class RedisPipelinePerformanceTest {
        static final String host = "xxxxxx.m.cnhza.kvstore.aliyuncs.com";
        static final int port = 6379;
        static final String password = "password";
        public static void main(String[] args) {
            Jedis jedis = new Jedis(host, port);
                //ApsaraDB for Redis instance password
                String authString = jedis.auth(password); // password
                if (! authString.equals("OK")) {
                    System.err.println("AUTH Failed: " + authString);
                    jedis.close();
                    return;
                }
                //Executes several commands consecutively
                final int COUNT=5000;
                String key = "KVStore-Tanghan";
                // 1 ---Without using pipeline operations---
                jedis.del(key);//Initializes the key
                Date ts1 = new Date();
                for (int i = 0; i < COUNT; i++) { 
                     //Sends a request and receives the response
                    jedis.incr(key);
                }
                Date ts2 = new Date();
                System.out.println("Without Pipeline > value is:"+jedis.get(key)+" > Time elapsed:" + (ts2.getTime() - ts1.getTime())+ "ms");
                //2 ---Using pipeline operations---
                jedis.del(key);//Initializes the key
                Pipeline p1 = jedis.pipelined();
                Date ts3 = new Date();
                for (int i = 0; i < COUNT; i++) { 
                    //Sends the request 
                    p1.incr(key);
                }
                // Receives the response
                p1.sync();
                Date ts4 = new Date();
                 System.out.println("Using Pipeline > value is:"+jedis.get(key)+" > Time elapsed:" + (ts4.getTime() - ts3.getTime())+ "ms");
                jedis.close();
        }
    }
```

## Output 1 {#section_p2c_qbw_wdb .section}

After you access the ApsaraDB for Redis instance with the correct address and password and run the preceding Java code, the following output is displayed. The output shows that the performance is enhanced with pipelines.

``` {#codeblock_tqv_8o1_la0}
Without pipelines > value: 5000 > Time elapsed: 5844 ms
With pipelines > value: 5000 > Time elapsed: 78 ms
```

## Sample code 2 {#section_r2c_qbw_wdb .section}

With pipelines defined in Jedis, responses are processed in two methods, as shown in the following sample code:

``` {#codeblock_ifr_nec_gad}
package pipeline.kvstore.aliyun.com;
import java.util.List;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
    public class PipelineClientTest {
        static final String host = "xxxxxxxx.m.cnhza.kvstore.aliyuncs.com";
        static final int port = 6379;
        static final String password = "password";
        public static void main(String[] args) {
            Jedis jedis = new Jedis(host, port);
                //ApsaraDB for Redis instance password
                String authString = jedis.auth(password);// password
                if (! authString.equals("OK")) {
                    System.err.println("AUTH Failed: " + authString);
                    jedis.close();
                    return;
                }
                String key = "KVStore-Test1";
                jedis.del(key);//Initialization
                // -------- Method 1
                Pipeline p1 = jedis.pipelined();
                System.out.println("-----Method 1-----");
                for (int i = 0; i < 5; i++) { 
                    p1.incr(key);
                    System.out.println("Pipeline sends requests");
                }
                 // After sending all requests, the client starts receiving responses
                System.out.println("Sending requests completed. Start to receive response");
                List<Object> responses = p1.syncAndReturnAll(); 
                if (responses == null || responses.isEmpty()) {
                    jedis.close();
                    throw new RuntimeException("Pipeline error: no responds received");
                }
                for (Object resp : responses) {
                    System.out.println("Pipeline receives response: " + resp.toString());
                }
                System.out.println();
                //-------- Method 2
                System.out.println("-----Method 2-----");
                jedis.del(key);//Initialization
                Pipeline p2 = jedis.pipelined();  
                //Declare the responses first
                Response<Long> r1 = p2.incr(key);  
                System.out.println("Pipeline sends requests");
                Response<Long> r2 = p2.incr(key); 
                System.out.println("Pipeline sends requests");
                Response<Long> r3 = p2.incr(key); 
                System.out.println("Pipeline sends requests");
                Response<Long> r4 = p2.incr(key);   
                System.out.println("Pipeline sends requests");
                Response<Long> r5 = p2.incr(key); 
                System.out.println("Pipeline sends requests");
                try{  
                     r1.get(); //Errors occur because the client has not started receiving responses
                }catch(Exception e){  
                    System.out.println(" <<< Pipeline error: the client has not started receiving responses  >>> ");  
                }  
              // After sending all requests, the client starts receiving responses
                System.out.println("Sending requests completed. Start to receive response");
                p2.sync();  
                System.out.println("Pipeline receives response: " + r1.get());  
                System. Out. println ("Pipeline receives response:" + r2.get ());  
                System. Out. println ("Pipeline receives response:" + r3.get ());
                System. Out. println ("Pipeline receives response:" + r4.get ());
                System. Out. println ("Pipeline receives response:" + r5.get ());
                jedis.close();
            }
    }
```

## Output 2 {#section_vfc_qbw_wdb .section}

After you access the ApsaraDB for Redis instance with the correct address and password and run the preceding Java code, the following output is displayed.

``` {#codeblock_5qj_hrt_d1l}
----- Method 1 -----
Pipeline sends a request
Pipeline sends a request
Pipeline sends a request
Pipeline sends a request
Pipeline sends a request
After sending all requests, the client starts receiving responses.
Pipeline receives response 1
Pipeline receives response 2
Pipeline receives response 3
Pipeline receives response 4
Pipeline receives response 5
----- Method 2 -----
Pipeline sends a request
Pipeline sends a request
Pipeline sends a request
Pipeline sends a request
Pipeline sends a request
 <Pipeline error: The client has not started receiving responses> 
After sending all requests, the client starts receiving responses.
Pipeline receives response 1
Pipeline receives response 2
Pipeline receives response 3
Pipeline receives response 4
Pipeline receives response 5
```

