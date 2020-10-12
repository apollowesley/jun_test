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

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.snaker.engine.access.Page;
import org.snaker.engine.access.QueryFilter;
import org.snaker.engine.entity.Process;
import org.snaker.engine.entity.HistoryOrder;
import org.snaker.engine.model.TaskModel;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.snakerflow.examples.struts2.engine.SnakerEngineFacets;
import com.snakerflow.examples.struts2.web.Struts2Utils;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yuqs
 * @since 2.0
 */
@Namespace("/snaker")
@Results( { @Result(name = FlowAction.RESULT_ACTIVETASK, location = "/snaker/task!active.action", type = "redirect"),
	@Result(name = FlowAction.RESULT_ORDER, location = "/WEB-INF/content/snaker/order.jsp"),
	@Result(name = FlowAction.RESULT_ALL, location = "/WEB-INF/content/snaker/all.jsp")})
public class FlowAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7341509986497158621L;
	public static final String RESULT_ACTIVETASK = "ACTIVETASK";
	public static final String RESULT_ORDER = "ORDER";
	public static final String RESULT_ALL = "ALL";
	public static final String RESULT_VIEW = "VIEW";
    @Autowired
    private SnakerEngineFacets facets;
    private Page<HistoryOrder> page = new Page<HistoryOrder>();
    private String processId;
    private String orderId;
    private String taskId;
    
    @SuppressWarnings("unchecked")
    public String process() {
    	HttpServletRequest request = Struts2Utils.getRequest();
        Map<String, Object> params = new HashMap<String, Object>();
        Enumeration<String> paraNames = request.getParameterNames();
        while (paraNames.hasMoreElements()) {
            String element = paraNames.nextElement();
            int index = element.indexOf("_");
            String paraValue = request.getParameter(element);
            if(index == -1) {
                params.put(element, paraValue);
            } else {
                char type = element.charAt(0);
                String name = element.substring(index + 1);
                Object value = null;
                switch(type) {
                    case 'S':
                        value = paraValue;
                        break;
                    case 'I':
                        value = ConvertUtils.convert(paraValue, Integer.class);
                        break;
                    case 'L':
                        value = ConvertUtils.convert(paraValue, Long.class);
                        break;
                    case 'B':
                        value = ConvertUtils.convert(paraValue, Boolean.class);
                        break;
                    case 'D':
                        value = ConvertUtils.convert(paraValue, Date.class);
                        break;
                    case 'N':
                        value = ConvertUtils.convert(paraValue, Double.class);
                        break;
                    default:
                        value = paraValue;
                        break;
                }
                params.put(name, value);
            }
        }
        String processId = request.getParameter("processId");
        String orderId = request.getParameter("orderId");
        String taskId = request.getParameter("taskId");
        String nextOperator = request.getParameter("");
        if (StringUtils.isEmpty(orderId) && StringUtils.isEmpty(taskId)) {
            facets.startAndExecute(processId, "admin", params);
        } else {
            String methodStr = request.getParameter("method");
            int method;
            try {
                method = Integer.parseInt(methodStr);
            } catch(Exception e) {
                method = 0;
            }
            switch(method) {
                case 0://任务执行
                    facets.execute(taskId, "admin", params);
                    break;
                case -1://驳回、任意跳转
                    facets.executeAndJump(taskId, "admin", params, request.getParameter("nodeName"));
                    break;
                case 1://转办
                    if(StringUtils.isNotEmpty(nextOperator)) {
                        facets.transferMajor(taskId, "admin", nextOperator.split(","));
                    }
                    break;
                case 2://协办
                    if(StringUtils.isNotEmpty(nextOperator)) {
                        facets.transferAidant(taskId, "admin", nextOperator.split(","));
                    }
                    break;
                default:
                    facets.execute(taskId, "admin", params);
                    break;
            }
        }
        String ccOperator = request.getParameter("ccoperator");
        if(StringUtils.isNotEmpty(ccOperator)) {
            facets.getEngine().order().createCCOrder(orderId, ccOperator.split(","));
        }
        return RESULT_ACTIVETASK;
    }
    /**
     * 流程实例查询
     * @param model
     * @param page
     * @return
     */
    public String order() {
        facets.getEngine().query().getHistoryOrders(page, new QueryFilter());
        return RESULT_ORDER;
    }

    /**
     * 抄送实例已读
     * @param id
     * @param url
     * @return
     */
    public void ccread() {
    	String id = Struts2Utils.getRequest().getParameter("id");
    	String url = Struts2Utils.getRequest().getParameter("url");
    	String[] assignees = new String[]{"admin"};
        facets.getEngine().order().updateCCStatus(id, assignees);
        try {
			Struts2Utils.getResponse().sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    /**
     * 通用的流程展现页面入口
     * 将流程中的各环节表单以tab+iframe方式展现
     */
    public String all() {
        if(StringUtils.isNotEmpty(processId)) {
        	Struts2Utils.getRequest().setAttribute("process", 
        			facets.getEngine().process().getProcessById(processId));
        }
        if(StringUtils.isNotEmpty(orderId)) {
        	Struts2Utils.getRequest().setAttribute("order", 
        			facets.getEngine().query().getOrder(orderId));
        }
        if(StringUtils.isNotEmpty(taskId)) {
        	Struts2Utils.getRequest().setAttribute("task", 
        			facets.getEngine().query().getTask(taskId));
        }
        return RESULT_ALL;
    }

    /**
     * 节点信息以json格式返回
     * all页面以节点信息构造tab及加载iframe
     */
    public void node() {
        Process process = facets.getEngine().process().getProcessById(processId);
        List<TaskModel> models = process.getModel().getModels(TaskModel.class);
        List<TaskModel> viewModels = new ArrayList<TaskModel>();
        for(TaskModel model : models) {
            TaskModel viewModel = new TaskModel();
            viewModel.setName(model.getName());
            viewModel.setDisplayName(model.getDisplayName());
            viewModel.setForm(model.getForm());
            viewModels.add(viewModel);
        }
        Struts2Utils.renderJson(viewModels);;
    }
	public Page<HistoryOrder> getPage() {
		return page;
	}
	public void setPage(Page<HistoryOrder> page) {
		this.page = page;
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
}
