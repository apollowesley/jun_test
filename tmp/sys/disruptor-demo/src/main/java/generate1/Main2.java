package generate1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.IgnoreExceptionHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.WorkHandler;
import com.lmax.disruptor.WorkerPool;

public class Main2 {  
    public static void main(String[] args) throws InterruptedException {  
        int BUFFER_SIZE=1024;  
        int THREAD_NUMBERS=4;  
        
        EventFactory<generate1.Trade> eventFactory = new EventFactory<generate1.Trade>() {
            public generate1.Trade newInstance() {
                return new generate1.Trade();
            }  
        };  
        
        RingBuffer<generate1.Trade> ringBuffer = RingBuffer.createSingleProducer(eventFactory, BUFFER_SIZE);
          
        SequenceBarrier sequenceBarrier = ringBuffer.newBarrier();  
          
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_NUMBERS);  
          
        WorkHandler<generate1.Trade> handler = new generate1.TradeHandler();

        WorkerPool<generate1.Trade> workerPool = new WorkerPool<generate1.Trade>(ringBuffer, sequenceBarrier, new IgnoreExceptionHandler(), handler);
          
        workerPool.start(executor);  
          
        //下面这个生产8个数据
        for(int i=0;i<8;i++){  
            long seq=ringBuffer.next();  
            ringBuffer.get(seq).setPrice(Math.random()*9999);  
            ringBuffer.publish(seq);  
        }  
          
        Thread.sleep(1000);  
        workerPool.halt();  
        executor.shutdown();  
    }  
}  
