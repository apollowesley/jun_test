package cn.iutils.guestbook.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.iutils.common.service.CrudService;
import cn.iutils.guestbook.dao.GuestbookDao;
import cn.iutils.guestbook.entity.Guestbook;

/**
* 留言板 Service层
* @author iutils.cn
* @version 1.0
*/
@Service
@Transactional(readOnly = true)
public class GuestbookService extends CrudService<GuestbookDao, Guestbook> {

    /**
     * 回复
     * @param guestbook
     * @return
     */
    @Transactional(readOnly = false)
    public int reply(Guestbook guestbook){
        return dao.reply(guestbook);
    }

    /**
     * 举报
     * @param id
     * @return
     */
    @Transactional(readOnly = false)
    public int report(String id){
        return dao.report(id);
    }

}
