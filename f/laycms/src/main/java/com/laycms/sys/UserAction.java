package com.laycms.sys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EnumType;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.web.tags.UserTag;
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
import com.laycms.log.entity.LogOperationEnum;
import com.laycms.member.MemberDao;
import com.laycms.member.MemberProfileDao;
import com.laycms.member.entity.Member;
import com.laycms.member.entity.MemberProfile;
import com.laycms.member.entity.UserType;
import com.laycms.util.JsonUtil;
import com.laycms.util.MediaTypeExt;
import com.laycms.util.StatusCode;

/** 
* @author 作者 zbb: 
* @version 创建时间：2017年6月23日 上午10:52:49 
* 类说明 
*/
@RestController
public class UserAction extends BaseAction{
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private MemberProfileDao memberProfileDao;
	@RequestMapping(value="user",method = RequestMethod.GET, produces = MediaTypeExt.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> menuList(ModelMap model, Integer pageNum,String username) {
		EntityView ev = new EntityView();
		if (pageNum == null) {
			pageNum = 1;
		}
		if (StringUtils.isNotEmpty(username)) {
			ev.add(Restrictions.like("username", username,MatchMode.ANYWHERE));
			
		}
		ev.add(Restrictions.eq("userType", UserType.CMSUSER));
		ev.addOrder(Order.desc("id"));
		
		PageContext<Member> pageCtx = memberDao.queryUsePage(ev, pageNum);
		List<Map<String, Object>> retList = new ArrayList<>();
		
		for(Member m:pageCtx.getItemList()){
			Map<String, Object> item = new HashMap<>();
			item.put("id", m.getId());
			item.put("username", m.getUsername());
			
			item.put("mobile","");
			if (m.getMobile()!= null) {
				item.put("mobile", m.getMobile());
			}
			
			if (m.getLocked() != null && m.getLocked()) {
				item.put("lockStatus", "已锁定");
			}else{
				item.put("lockStatus", "");
			}
			MemberProfile mp = memberProfileDao.findUniq("memberId", m.getId());
			if (mp != null) {
				item.put("realname", mp.getRealname());
			}else{
				item.put("realname", "");
			}
			item.put("createTime",m.getCreateTime());
			retList.add(item);
		}
		JSONObject json = new JSONObject();
		json.put("rel", true);
		json.put("msg", "获取成功");
		json.put("list", retList);
		json.put("count", retList.size());
		return new ResponseEntity<>(JsonUtil.toJSONString(json), HttpStatus.OK);
	}
	
	@RequestMapping(value="user/del/{id}",method = RequestMethod.POST, produces = MediaTypeExt.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> del(ModelMap model,HttpServletRequest request, @PathVariable Integer id) {
		JSONObject json = new JSONObject();
		Member member = memberDao.findById(id);
		if (member.getUserType() == UserType.CMSUSER) {
			memberDao.delete(member);
			saveLog(request, id,LogOperationEnum.DELETE, "删除用户，用户名："+member.getUsername());
		}
		json.put("msg", "success");
	    json.put("statuscode", StatusCode.SUCCESS);
		return new ResponseEntity<>(json, HttpStatus.OK);
	}
}
