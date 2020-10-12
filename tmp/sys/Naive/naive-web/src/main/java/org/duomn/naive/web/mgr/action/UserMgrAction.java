package org.duomn.naive.web.mgr.action;

import java.util.ArrayList;
import java.util.List;

import org.duomn.naive.common.util.BusinessUtil;
import org.duomn.naive.web.entity.PageVo;
import org.duomn.naive.web.entity.Pager;
import org.duomn.naive.web.mgr.entity.UserVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/UserMgrAction.action")
public class UserMgrAction {
	
	@RequestMapping(params = "method=toUserList")
	public String toUserList() {
		return "jsp/user/usermgr-list";
	}

	@RequestMapping(params = "method=listUser")
	public String listUser(PageVo pageVo, ModelMap model) {
		List<UserVo> list = new ArrayList<UserVo>(10);
		for (int i = 0; i < 10; i++) {
			UserVo user = new UserVo();
			user.setUserId(BusinessUtil.getUUID());
			user.setUserNum(i);
			user.setUserName("abc" + i);
			user.setSex(i % 2 == 0 ? "男" : "女");
			user.setPhone("1234567890" + i);
			user.setEmail("abc" + i + "@sina.com");
			list.add(user);
		}
		Pager<UserVo> pager = new Pager<UserVo>();
		pager.setPageNo(1);
		pager.setPageSize(10);
		pager.setTotalRows(10);
		pager.setResultList(list);
		model.put("pager", pager);
		return "jsp/user/usermgr-list-xml";
	}
	
}
