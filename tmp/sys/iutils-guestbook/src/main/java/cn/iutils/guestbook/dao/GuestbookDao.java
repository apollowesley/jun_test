package cn.iutils.guestbook.dao;

import cn.iutils.common.ICrudDao;
import cn.iutils.common.annotation.MyBatisDao;
import cn.iutils.guestbook.entity.Guestbook;
import org.apache.ibatis.annotations.Param;

/**
* 留言板 DAO接口
* @author iutils.cn
* @version 1.0
*/
@MyBatisDao
public interface GuestbookDao extends ICrudDao<Guestbook> {

    /**
     * 回复
     * @param guestbook
     * @return
     */
    public int reply(Guestbook guestbook);

    /**
     * 举报
     * @param id
     * @return
     */
    public int report(@Param("id") String id);

}
