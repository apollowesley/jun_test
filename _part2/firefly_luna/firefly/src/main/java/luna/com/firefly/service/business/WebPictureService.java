package luna.com.firefly.service.business;

import luna.com.firefly.entity.business.WebPicture;
import luna.com.firefly.repository.business.WebPictureDao;
import luna.com.firefly.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * <图片接口>
 * 
 * @author 陆小凤
 * @version [1.0 2014-1-8]
 * 
 */

@Component
@Transactional(value = "transactionManager")
public class WebPictureService extends BaseService<WebPicture, Long>
{
    
    @Autowired
    private WebPictureDao picturecDao;
    
}
