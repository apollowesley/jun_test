package com.snakerflow.examples.struts2.web.flow;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.snakerflow.examples.struts2.engine.SnakerEngineFacets;
import com.snakerflow.examples.struts2.web.Struts2Utils;

@Namespace(value = "/flow")
@Results( { @Result(name = LeaveAction.RESULT_RELOAD, location = "leave.action", type = "redirect"),
	@Result(name = LeaveAction.RESULT_APPLY, location = "/WEB-INF/content/flow/leave/apply.jsp"),
	@Result(name = LeaveAction.RESULT_APPLYVIEW, location = "/WEB-INF/content/flow/leave/applyView.jsp"),
	@Result(name = LeaveAction.RESULT_DEPT, location = "/WEB-INF/content/flow/leave/approveDept.jsp"),
	@Result(name = LeaveAction.RESULT_DEPTVIEW, location = "/WEB-INF/content/flow/leave/approveDeptView.jsp"),
	@Result(name = LeaveAction.RESULT_BOSS, location = "/WEB-INF/content/flow/leave/approveBoss.jsp"),
	@Result(name = LeaveAction.RESULT_BOSSVIEW, location = "/WEB-INF/content/flow/leave/approveBossView.jsp")})
public class LeaveAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2049688969294924711L;
	public static final String RESULT_RELOAD = "RELOAD";
	public static final String RESULT_APPLY = "APPLY";
	public static final String RESULT_APPLYVIEW = "APPLYVIEW";
	public static final String RESULT_DEPT = "DEPT";
	public static final String RESULT_DEPTVIEW = "DEPTVIEW";
	public static final String RESULT_BOSS = "BOSS";
	public static final String RESULT_BOSSVIEW = "BOSSVIEW";
	
    private String processId;
    private String orderId;
    private String taskId;
    private String taskName;
	@Autowired
    private SnakerEngineFacets facets;
	/**
	 * 请假申请路由方法
	 */
	public String apply() throws Exception {
		//设置操作人为当前登录用户，请假流程演示时，将申请人、部门经理审批人、总经理审批人都设置为当前用户
		//可通过修改申请页面的部门经理、总经理输入框来改变下一步的处理人
		Struts2Utils.getRequest().setAttribute("operator", "admin");
		//根据taskId是否为空来标识当前请求的页面是否为活动任务的节点页面
		if(StringUtils.isEmpty(orderId) || StringUtils.isNotEmpty(taskId)) {
			//如果实例id为空或者驳回情况下，返回apply.jsp
			return RESULT_APPLY;
		} else {
			//如果orderId非空、taskId为空，则表示申请步骤已提交，此时可获取申请数据
			//由于请假流程中的业务数据，是保存在任务表的variable字段中，所以通过flowData方法获取
			//如果业务数据保存在业务表中，需要业务表的orderId字段来关联流程，进而根据orderId查询出业务数据
			Map<String, Object> datas = facets.flowData(orderId, taskName);
			for(Map.Entry<String, Object> entry : datas.entrySet()) {
				Struts2Utils.getRequest().setAttribute(entry.getKey(), entry.getValue());
			}
			//返回申请的查看页面
			return RESULT_APPLYVIEW;
		}
	}
	
	/**
	 * 部门经理审批路由方法
	 */
	public String approveDept() throws Exception {
		if(StringUtils.isNotEmpty(taskId)) {
			return RESULT_DEPT;
		} else {
			Map<String, Object> datas = facets.flowData(orderId, taskName);
			for(Map.Entry<String, Object> entry : datas.entrySet()) {
				Struts2Utils.getRequest().setAttribute(entry.getKey(), entry.getValue());
			}
			return RESULT_DEPTVIEW;
		}
	}
	
	/**
	 * 总经理审批路由方法
	 */
	public String approveBoss() throws Exception {
		if(StringUtils.isNotEmpty(taskId)) {
			return RESULT_BOSS;
		} else {
			Map<String, Object> datas = facets.flowData(orderId, taskName);
			for(Map.Entry<String, Object> entry : datas.entrySet()) {
				Struts2Utils.getRequest().setAttribute(entry.getKey(), entry.getValue());
			}
			return RESULT_BOSSVIEW;
		}
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
}
