package com.laycms.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.laycms.BaseAction;
import com.laycms.base.EntityView;
import com.laycms.base.PageContext;
import com.laycms.config.SystemConfig;
import com.laycms.member.entity.Member;
import com.laycms.member.entity.UserType;
import com.laycms.util.DateUtils;

/** 
* @author 作者 zbb: 
* @version 创建时间：2016年11月25日 上午8:39:57 
* 类说明 
*/
@RestController
public class MemberAction extends BaseAction{

	@Autowired
	private MemberDao memberDao;
	@Autowired
	private MemberProfileDao memberProfileDao;
	@Autowired
	private SystemConfig systemConfig;

	@Autowired
	private MemberService memberService;
	
	@RequestMapping(value="member/")
	public String memberList(ModelMap mMap,HttpServletRequest request,Integer pageNum, Integer numPerPage,String username,
			String mobile,String id){
		try {
			
			EntityView ev = new EntityView();
			String userType = request.getParameter("userType");
			if(StringUtils.isNotEmpty(username)){
				ev.add(Restrictions.like("username",username,MatchMode.ANYWHERE));
			}
			if(StringUtils.isNotEmpty(id)){
				ev.add(Restrictions.eq("id",Integer.valueOf(id)));
			}
			if(StringUtils.isNotEmpty(userType)){
				if(userType.equals(UserType.STUDENT.toString())){
					ev.add(Restrictions.eq("userType", UserType.STUDENT));
				}
				if(userType.equals(UserType.TEACHER.toString())){
					ev.add(Restrictions.eq("userType", UserType.TEACHER));
				}
			}
			if(StringUtils.isNotEmpty(mobile)){
				ev.add(Restrictions.eq("mobile", mobile));
			}

			ev.add(Restrictions.or(Restrictions.eq("userType", UserType.STUDENT), Restrictions.eq("userType", UserType.TEACHER)));
			ev.addOrder(Order.desc("createTime"));
			PageContext<Member> pageCtx = memberDao.queryUsePage(ev, pageNum, numPerPage);
			mMap.put("pageCtx", pageCtx);
			mMap.put("mobile", mobile);
			mMap.put("id", id);
			mMap.put("username", username);
			mMap.put("userType", userType==null?"":userType);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "member/list";
	}
	
	
	 /*
	    {
		  code: 0, //状态码，0代表成功，其它失败
		  msg: "", //状态信息，一般可为空
		  count: 1000, //数据总量
		  data: [] //数据，字段是任意的。如：[{"id":1,"username":"贤心"}, {"id":2,"username":"佟丽娅"}]
		}
	 */
	@RequestMapping(value="member/list")
	@ResponseBody
	public String list(ModelMap mMap,HttpServletRequest request,Integer page, Integer limit,String username,
			String mobile,Integer id){
		
		JSONObject jsonObject = new JSONObject();
		EntityView ev = new EntityView();
		String userType = request.getParameter("userType");
		if(StringUtils.isNotEmpty(username)){
			ev.add(Restrictions.like("username",username,MatchMode.ANYWHERE));
		}
		if(id != null){
			ev.add(Restrictions.eq("id",id));
		}
		if(StringUtils.isNotEmpty(userType)){
			if(userType.equals(UserType.STUDENT.toString())){
				ev.add(Restrictions.eq("userType", UserType.STUDENT));
			}
			if(userType.equals(UserType.TEACHER.toString())){
				ev.add(Restrictions.eq("userType", UserType.TEACHER));
			}
		}
		if(StringUtils.isNotEmpty(mobile)){
			ev.add(Restrictions.eq("mobile", mobile));
		}
		
		ev.add(Restrictions.or(Restrictions.eq("userType", UserType.STUDENT), Restrictions.eq("userType", UserType.TEACHER)));
		ev.addOrder(Order.desc("createTime"));
		PageContext<Member> pageCtx = memberDao.queryUsePage(ev, page, limit);
		
		List<Map<String, Object>> users = new ArrayList<>();
		for(Member member:pageCtx.getItemList()){
			Map<String, Object> item = new HashMap<>();
			item.put("id", member.getId());
			item.put("username", 	member.getUsername());
			item.put("mobile", 	member.getMobile());
			item.put("username", 	member.getUsername());
			item.put("userType", 	member.getUserType().getLabel());
			item.put("createTime", 	DateUtils.formatDatetime(member.getCreateTime()));
			users.add(item);
		}
		
		jsonObject.put("code", 0);
		jsonObject.put("msg", "");
		jsonObject.put("count",pageCtx.getPageBean().getTotalCount());
		jsonObject.put("data",users);
		return jsonObject.toJSONString();
	}
}
