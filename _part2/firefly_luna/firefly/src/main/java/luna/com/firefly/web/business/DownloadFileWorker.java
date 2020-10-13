package luna.com.firefly.web.business;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.BlockingQueue;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;
import luna.com.firefly.entity.business.WebPicture;
import luna.com.firefly.entity.system.SystemConfig;
import luna.com.firefly.utils.DownLoadUtils;
import luna.com.firefly.utils.ImageUtils;
import luna.com.firefly.utils.ThumbnailGenerator;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.google.common.collect.Queues;

/**
 * <多线程任务>
 * 
 * @author 陆小凤
 * @version [版本号, 2015年11月12日]
 */
@Lazy(false)
@Slf4j
@Component("threadManageWorker")
public class DownloadFileWorker extends BusinessBasicController
{
    
    private BlockingQueue<WebPicture> downloadQueue = Queues.newLinkedBlockingQueue(1024);
    
    /**
     * @PostConstruct是Java EE 5引入的注解，Spring允许开发者在受管Bean中使用它。当DI容器实例化当前受管Bean时，
     * @PostConstruct注解的方法会被自动触发，从而完成一些初始化工作，示例代码如下
     * 
     */
    @PostConstruct
    public void init()
    {
        log.info("Enter func[init].");
        
        Thread worker = new Thread(new ExeDownloadWorker());
        worker.setUncaughtExceptionHandler(new ErrHandler());
        worker.start();
    }
    
    /**
     * <文件下载处理 -线程>
     */
    private class ExeDownloadWorker implements Runnable
    {
        
        @Override
        public void run()
        {
            System.out.println("线程开启!!!!");
            boolean isContinue = true;
            while (isContinue)
            {
                try
                {
                    WebPicture picture = downloadQueue.take();
                    if (null != picture)
                    {
                        SystemConfig sc = systemConfigService.getSystemConfigByName("file.save.path");
                        String savePath = "";
                        String thumbnailFileName = "";
                        String fileName = picture.getName() + "." + ImageUtils.getPrefix(picture.getSource());
                        if (null != sc)
                        {
                            savePath = sc.getConfigValue();
                            if (!savePath.endsWith("/"))
                            {
                                savePath = savePath + "/";
                            }
                        }
                        thumbnailFileName = savePath + "s_" + fileName;
                        savePath = savePath + fileName;
                        // 文件下载
                        DownLoadUtils.FileDownLoad(picture.getSource(), savePath);
                        // 生成缩略图
                        ThumbnailGenerator.transform(savePath, thumbnailFileName, 100, 100, 100);
                        picture.setValue(savePath);
                        picture.setThumbnail(thumbnailFileName);
                        
                        // 保存缩略图和 原图地址
                        pictureService.save(picture);
                    }
                }
                catch (InterruptedException e)
                {
                    log.error("线程中断 e:{}", e);
                }
                catch (Exception e)
                {
                    log.error("下载中断 e:{}", e);
                }
                
            }
            
        }
    }
    
    private class ErrHandler implements UncaughtExceptionHandler
    {
        
        @Override
        public void uncaughtException(Thread thread, Throwable throwable)
        {
            log.error("thread name:[{}],throwable stackTrace:[{}]",
                thread.getName(),
                ExceptionUtils.getStackFrames(throwable));
        }
    }
    
    public void putToQueue(WebPicture picture)
    {
        try
        {
            this.downloadQueue.put(picture);
        }
        catch (InterruptedException e)
        {
            log.error("Inner func[download], catch InterruptedException while put  to download. {}", e);
        }
        catch (Exception e)
        {
            log.error("Inner func[download], catch execption while put  to download. {}", e);
        }
    }
    
}