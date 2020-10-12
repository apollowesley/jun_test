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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.snaker.engine.access.Page;
import org.snaker.engine.access.QueryFilter;
import org.snaker.engine.entity.HistoryOrder;
import org.snaker.engine.entity.HistoryTask;
import org.snaker.engine.entity.Process;
import org.snaker.engine.entity.Task;
import org.snaker.engine.helper.AssertHelper;
import org.snaker.engine.helper.StreamHelper;
import org.snaker.engine.helper.StringHelper;
import org.snaker.engine.model.ProcessModel;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.snakerflow.examples.struts2.engine.SnakerEngineFacets;
import com.snakerflow.examples.struts2.engine.SnakerHelper;
import com.snakerflow.examples.struts2.web.Struts2Utils;


/**
 * 流程定义
 * @author yuqs
 * @since 0.1
 */
@Namespace("/snaker")
@Results( { @Result(name = ProcessAction.RESULT_RELOAD, location = "process.action", type = "redirect"),
	@Result(name = ProcessAction.RESULT_LIST, location = "/WEB-INF/content/snaker/processList.jsp"),
	@Result(name = ProcessAction.RESULT_EDIT, location = "/WEB-INF/content/snaker/processEdit.jsp"),
	@Result(name = ProcessAction.RESULT_VIEW, location = "/WEB-INF/content/snaker/processView.jsp"),
	@Result(name = ProcessAction.RESULT_DESIGNER, location = "/WEB-INF/content/snaker/processDesigner.jsp"),
	@Result(name = ProcessAction.RESULT_DIAGRAM, location = "/WEB-INF/content/snaker/diagram.jsp"),
	@Result(name = ProcessAction.RESULT_DEPLOY, location = "/WEB-INF/content/snaker/processDeploy.jsp")})
public class ProcessAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6568882981009396249L;
	public static final String JSON_TYPE = "application/json";
	public static final String RESULT_RELOAD = "RELOAD";
	public static final String RESULT_LIST = "LIST";
	public static final String RESULT_EDIT = "EDIT";
	public static final String RESULT_VIEW = "VIEW";
	public static final String RESULT_DESIGNER = "DESIGNER";
	public static final String RESULT_DIAGRAM = "DIAGRAM";
	public static final String RESULT_DEPLOY = "DEPLOY";
	@Autowired
	private SnakerEngineFacets facets;
	private Page<Process> page = new Page<Process>();
	private Process process;
	private String processId;
	private String orderId;
	private File attach;
	private String attachFileName;
	
	public String execute() throws Exception {
		return list();
	}
	
	/**
	 * 流程定义查询列表
	 */
	public String list() throws Exception {
		String displayName = Struts2Utils.getRequest().getParameter("displayName");
		QueryFilter filter = new QueryFilter();
		if(StringHelper.isNotEmpty(displayName)) {
			filter.setDisplayName(displayName);
		}
		facets.getEngine().process().getProcesss(page, filter);
		return RESULT_LIST;
	}
	
	/**
	 * 初始化流程定义
	 */
	@Action(value = "init")
	public String init() throws Exception {
		facets.initFlows();
		return RESULT_RELOAD;
	}
	
	/**
	 * 根据流程定义部署
	 */
	public String deploy() throws Exception {
		return RESULT_DEPLOY;
	}
	
	/**
	 * 新建流程定义[web流程设计器]
	 */
	public String designer() throws Exception {
		if(StringUtils.isNotEmpty(processId)) {
			Process process = facets.getEngine().process().getProcessById(processId);
			AssertHelper.notNull(process);
			ProcessModel processModel = process.getModel();
			if(processModel != null) {
				String json = SnakerHelper.getModelJson(processModel);
				Struts2Utils.getRequest().setAttribute("process", json);
			}
		}
		return RESULT_DESIGNER;
	}
	
	/**
	 * 编辑流程定义
	 */
	public String processEdit() throws Exception {
		process = facets.getEngine().process().getProcessById(processId);
		if(process.getDBContent() != null) {
            try {
            	Struts2Utils.getRequest().setAttribute("content", 
            			StringHelper.textXML(new String(process.getDBContent(), "UTF-8")));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
		return RESULT_EDIT;
	}
	
	/**
	 * 根据流程定义ID，删除流程定义
	 */
	public String delete() throws Exception {
		facets.getEngine().process().undeploy(processId);
		return RESULT_RELOAD;
	}
	
	/**
	 * 添加流程定义后的部署
	 */
	public String processDeploy() throws Exception {
		InputStream input = null;
		try {
			input = new FileInputStream(attach);
			if(StringUtils.isNotEmpty(processId)) {
                facets.getEngine().process().redeploy(processId, input);
			} else {
                facets.getEngine().process().deploy(input);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return RESULT_RELOAD;
	}
	
	/**
	 * 保存流程定义[web流程设计器]
	 */
	public void deployXml() throws Exception {
		boolean result = true;
		InputStream input = null;
		try {
			String model = Struts2Utils.getRequest().getParameter("model");
			String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" + SnakerHelper.convertXml(model);
			System.out.println("model xml=\n" + xml);
			input = StreamHelper.getStreamFromString(xml);
			if(StringUtils.isNotEmpty(processId)) {
				facets.getEngine().process().redeploy(processId, input);
			} else {
				facets.getEngine().process().deploy(input);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		} finally {
			if(input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		Struts2Utils.renderJson(result);
	}
	
	public void json() throws Exception {
        List<Task> tasks = null;
        if(StringUtils.isNotEmpty(orderId)) {
            tasks = facets.getEngine().query().getActiveTasks(new QueryFilter().setOrderId(orderId));
        }
        process = facets.getEngine().process().getProcessById(processId);
        AssertHelper.notNull(process);
        ProcessModel model = process.getModel();
        Map<String, String> jsonMap = new HashMap<String, String>();
        if(model != null) {
            jsonMap.put("process", SnakerHelper.getModelJson(model));
        }

        //{"activeRects":{"rects":[{"paths":[],"name":"任务3"},{"paths":[],"name":"任务4"},{"paths":[],"name":"任务2"}]},"historyRects":{"rects":[{"paths":["TO 任务1"],"name":"开始"},{"paths":["TO 分支"],"name":"任务1"},{"paths":["TO 任务3","TO 任务4","TO 任务2"],"name":"分支"}]}}
        if(tasks != null && !tasks.isEmpty()) {
            jsonMap.put("active", SnakerHelper.getActiveJson(tasks));
        }
        Struts2Utils.renderJson(jsonMap);
	}
	
	public String display() throws Exception {
		HistoryOrder order = facets.getEngine().query().getHistOrder(orderId);
		Struts2Utils.getRequest().setAttribute("order", order);
		List<HistoryTask> tasks = facets
				.getEngine()
				.query()
				.getHistoryTasks(new QueryFilter().setOrderId(orderId));
		Struts2Utils.getRequest().setAttribute("tasks", tasks);
		return RESULT_VIEW;
	}

    /**
     * 显示独立的流程图
     */
    public String diagram() throws Exception {
        return RESULT_DIAGRAM;
    }

	public void setPage(Page<Process> page) {
		this.page = page;
	}

	public Page<Process> getPage() {
		return page;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setAttach(File attach) {
		this.attach = attach;
	}

	public void setAttachFileName(String attachFileName) {
		this.attachFileName = attachFileName;
	}

	public File getAttach() {
		return attach;
	}

	public String getAttachFileName() {
		return attachFileName;
	}
}
