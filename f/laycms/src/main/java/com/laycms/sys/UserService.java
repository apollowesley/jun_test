package com.laycms.sys;

import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laycms.base.EntityView;
import com.laycms.sys.dao.MenuDao;
import com.laycms.sys.entity.Menu;

/** 
* @author 作者 zbb: 
* @version 创建时间：2017年6月22日 下午5:35:17 
* 类说明 
*/
@Service
public class UserService {
	@Autowired
	private MenuDao menuDao;
	
	public List<Menu> findMenuByParentId(Integer parentId) {
		EntityView ev = new EntityView();
		if(parentId == null || parentId.intValue() == 0) {
			ev.add(Restrictions.isNull("parentMenuId"));
		} else {
			ev.add(Restrictions.eq("parentMenuId", parentId));
		}
		
		ev.addOrder(Order.asc("menuSeq"));
		
		return menuDao.findByEntityView(ev);
	}
}
