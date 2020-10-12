package com.evil.struts2.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.evil.pojo.system.Right;
import com.evil.service.RightService;
import com.evil.util.JsonUtil;
import com.evil.util.Page;
import com.evil.util.ReturnMsg;

@Controller("RightAction")
@Scope("prototype")
public class RightAction extends BaseAction<Right> {
	private static final long serialVersionUID = -7578086887382543757L;
	@Resource
	private RightService rightService;

	private List<Right> allRights;

	/**
	 * 到角色的管理界面
	 * 
	 * @return
	 */
	public String toRightManagePage() {
		Page page = new Page();
		page.setNumPerPage(numPerPage);
		page.setCurrentPage(pageNum);
		Map<String, Object> map = new HashMap<String, Object>();
		if (model != null) {
			if (model.getRightName() != null) {
				map.put("rightName", "%" + model.getRightName() + "%");
			}
			if (model.getRightUrl() != null) {
				map.put("rightUrl", "%" + model.getRightUrl() + "%");
			}
		}
		String sortfield = orderField + " " + orderDirection;
		pageResult = rightService.findRightsPage(page, map, sortfield,
				" id desc");
		return "rightManagePage";
	}

	/**
	 * 批量修改权限界面
	 */
	public String toUpdateRightPage() {
		model = rightService.getEntity(model.getId());
		return "updateRightPage";
	}

	public String toAddRightPage() {
		return "addRightPage";
	}

	/**
	 * 更新/保存权限
	 */
	public void doSaveOrUpdateRight() {
		ReturnMsg rm = new ReturnMsg();
		try {
			rightService.saveOrUpdateRight(model);
			rm.setCallbackType("closeCurrent");
			rm.setNavTabId("rightTab");
		} catch (Exception e) {
			rm=ReturnMsg.ERRORMsg("操作失败");
			rm.setCallbackType("");
		} finally {
			JsonUtil.returnMsg(rm);
		}
	}

	/**
	 * 删除权限
	 */
	public void deleteRight() {
		ReturnMsg rm = new ReturnMsg();
		try {
			rightService.deleteEntity(model);
			rm.setNavTabId("rightTab");
		} catch (Exception e) {
			rm = ReturnMsg.ERRORMsg("删除失败" + e.getMessage());
		} finally {
			rm.setCallbackType("");
			rm.setNavTabId("rightTab");
			JsonUtil.returnMsg(rm);
		}
	}

	// get/set方法...
	public List<Right> getAllRights() {
		return allRights;
	}

	public void setAllRights(List<Right> allRights) {
		this.allRights = allRights;
	}

}
