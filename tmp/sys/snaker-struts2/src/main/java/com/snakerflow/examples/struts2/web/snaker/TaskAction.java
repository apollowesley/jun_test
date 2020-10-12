/* Copyright 2013-2015 www.snakerflow.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.snakerflow.examples.struts2.web.snaker;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.snaker.engine.access.Page;
import org.snaker.engine.access.QueryFilter;
import org.snaker.engine.entity.HistoryOrder;
import org.snaker.engine.entity.Task;
import org.snaker.engine.entity.WorkItem;
import org.snaker.engine.model.TaskModel.TaskType;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.snakerflow.examples.struts2.engine.SnakerEngineFacets;
import com.snakerflow.examples.struts2.web.Struts2Utils;

/**
 * Snaker流程引擎常用Controller
 * @author yuqs
 * @since 0.1
 */
@Namespace("/snaker")
@Results( { @Result(name = TaskAction.RESULT_ACTIVETASK, location = "/snaker/task!active.action", type = "redirect"),
	@Result(name = TaskAction.RESULT_TASKHOME, location = "/WEB-INF/content/snaker/activeTask.jsp"),
	@Result(name = TaskAction.RESULT_TASKMORE, location = "/WEB-INF/content/snaker/activeTaskMore.jsp"),
	@Result(name = TaskAction.RESULT_CCMORE, location = "/WEB-INF/content/snaker/activeCCMore.jsp"),
	@Result(name = TaskAction.RESULT_TASKHISTORY, location = "/WEB-INF/content/snaker/historyTask.jsp"),
	@Result(name = TaskAction.RESULT_ACTOR, location = "/WEB-INF/content/snaker/actor.jsp")})
public class TaskAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8561905004485261045L;
	public static final String RESULT_ACTIVETASK = "ACTIVETASK";
	public static final String RESULT_TASKHOME = "TASKHOME";
	public static final String RESULT_ACTOR = "ACTOR";
	public static final String RESULT_TASKMORE = "TASKMORE";
	public static final String RESULT_CCMORE = "CCMORE";
	public static final String RESULT_TASKHISTORY = "TASKHISTORY";
	@Autowired
	private SnakerEngineFacets facets;
	private Page<WorkItem> page = new Page<WorkItem>();
	private Page<HistoryOrder> ccPage = new Page<HistoryOrder>();
    private String orderId;
    private String taskId;
    private String taskName;
    private Integer taskType;
	
	public String active() {
		String[] assignees = new String[]{"admin"};
		
		Page<WorkItem> majorPage = new Page<WorkItem>(5);
		Page<WorkItem> aidantPage = new Page<WorkItem>(3);
		Page<HistoryOrder> ccorderPage = new Page<HistoryOrder>(3);
		List<WorkItem> majorWorks = facets.getEngine()
				.query()
				.getWorkItems(majorPage, new QueryFilter()
				.setOperators(assignees)
				.setTaskType(TaskType.Major.ordinal()));
		List<WorkItem> aidantWorks = facets.getEngine()
				.query()
				.getWorkItems(aidantPage, new QueryFilter()
				.setOperators(assignees)
				.setTaskType(TaskType.Aidant.ordinal()));
		List<HistoryOrder> ccWorks = facets.getEngine()
				.query()
				.getCCWorks(ccorderPage, new QueryFilter()
				.setOperators(assignees)
				.setState(1));
		HttpServletRequest request = Struts2Utils.getRequest();
		request.setAttribute("majorWorks", majorWorks);
		request.setAttribute("majorTotal", majorPage.getTotalCount());
		request.setAttribute("aidantWorks", aidantWorks);
		request.setAttribute("aidantTotal", aidantPage.getTotalCount());
		request.setAttribute("ccorderWorks", ccWorks);
		request.setAttribute("ccorderTotal", ccorderPage.getTotalCount());
		return RESULT_TASKHOME;
	}
	
    public String addActor() {
        return RESULT_ACTOR;
    }

    public void doAddActor() {
        List<Task> tasks = facets.getEngine().query().getActiveTasks(new QueryFilter().setOrderId(orderId));
        String operator = Struts2Utils.getParameter("operator");
        for(Task task : tasks) {
            if(task.getTaskName().equalsIgnoreCase(taskName) && StringUtils.isNotEmpty(operator)) {
                facets.getEngine().task().addTaskActor(task.getId(), operator);
            }
        }
        Struts2Utils.renderJson("success");
    }

    public void tip() {
        List<Task> tasks = facets.getEngine().query().getActiveTasks(new QueryFilter().setOrderId(orderId));
        StringBuilder builder = new StringBuilder();
        String createTime = "";
        for(Task task : tasks) {
            if(task.getTaskName().equalsIgnoreCase(taskName)) {
                String[] actors = facets.getEngine().query().getTaskActorsByTaskId(task.getId());
                for(String actor : actors) {
                    builder.append(actor).append(",");
                }
                createTime = task.getCreateTime();
            }
        }
        if(builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        Map<String, String> data = new HashMap<String, String>();
        data.put("actors", builder.toString());
        data.put("createTime", createTime);
        Struts2Utils.renderJson(data);
    }
	
	/**
	 * 活动任务查询列表
	 * @param model
	 * @return
	 */
	public String activeTaskList() {
		String[] assignees = new String[]{"admin"};
		facets.getEngine().query().getWorkItems(page, 
				new QueryFilter().setOperators(assignees).setTaskType(taskType));
		return RESULT_TASKMORE;
	}
	
	/**
	 * 活动任务查询列表
	 * @param model
	 * @return
	 */
	public String activeCCList() {
		String[] assignees = new String[]{"admin"};
		facets.getEngine()
				.query()
				.getCCWorks(ccPage, new QueryFilter()
				.setOperators(assignees)
				.setState(1));
		return RESULT_CCMORE;
	}
	
	/**
	 * 活动任务的驳回
	 * @param model
	 * @param taskId
	 * @return
	 */
	public void reject() {
		String error = "";
		try {
			facets.executeAndJump(taskId, "admin", null, null);
		} catch(Exception e) {
			error = "?error=1";
		}
		try {
			Struts2Utils.getResponse().sendRedirect("/snaker/task/active.action" + error);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 历史完成任务查询列表
	 */
	public String history() {
		facets.getEngine().query().getHistoryWorkItems(page, 
				new QueryFilter().setOperator("admin"));
		return RESULT_TASKHISTORY;
	}

	public Page<WorkItem> getPage() {
		return page;
	}

	public void setPage(Page<WorkItem> page) {
		this.page = page;
	}

	public Page<HistoryOrder> getCcPage() {
		return ccPage;
	}

	public void setCcPage(Page<HistoryOrder> ccPage) {
		this.ccPage = ccPage;
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

	public Integer getTaskType() {
		return taskType;
	}

	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}
}
