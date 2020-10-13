package com.laycms.sys;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.laycms.BaseAction;
import com.laycms.base.EntityView;
import com.laycms.base.PageContext;
import com.laycms.sys.dao.MenuDao;
import com.laycms.sys.entity.Menu;
import com.laycms.util.JsonUtil;
import com.laycms.util.MediaTypeExt;
import com.laycms.util.StatusCode;

/** 
* @author 作者 zbb: 
* @version 创建时间：2017年6月22日 下午5:30:16 
* 类说明 
*/
@RestController
public class MenuAction extends BaseAction{
	
	@Autowired
	private MenuDao menuDao;

	@RequestMapping(value="menu",method = RequestMethod.GET, produces = MediaTypeExt.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> menuList(ModelMap model, Integer pageNum,String menuName) {
		EntityView ev = new EntityView();
		if (pageNum == null) {
			pageNum = 1;
		}
		if (StringUtils.isNotEmpty(menuName)) {
			ev.add(Restrictions.like("menuName", menuName,MatchMode.ANYWHERE));
			
		}
		
		ev.addOrder(Order.asc("menuSeq"));
		
		PageContext<Menu> pageContext = menuDao.queryUsePage(ev, pageNum);
		
		Map<String, Object> ret = new HashMap<>();
		ret.put("rel", true);
		ret.put("msg", "获取成功");
		ret.put("list", pageContext.getItemList());
		ret.put("count", pageContext.getPageBean().getTotalCount());
		return new ResponseEntity<>(JsonUtil.toJSONString(ret), HttpStatus.OK);
	}
	
	
	@RequestMapping(value="menu/{id}",method = RequestMethod.POST, produces = MediaTypeExt.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> del(ModelMap model, @PathVariable Integer id) {
		JSONObject json = new JSONObject();
		System.out.println(id);
		json.put("msg", "success");
	    json.put("statuscode", StatusCode.SUCCESS);
		return new ResponseEntity<>(json, HttpStatus.OK);
	}
}
