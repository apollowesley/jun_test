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

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.snaker.engine.access.Page;
import org.snaker.engine.access.QueryFilter;
import org.snaker.engine.entity.Surrogate;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionSupport;
import com.snakerflow.examples.struts2.engine.SnakerEngineFacets;
import com.snakerflow.examples.struts2.web.Struts2Utils;

/**
 * 委托授权
 * @author yuqs
 * @since 0.1
 */
@Namespace("/snaker")
@Results( { @Result(name = SurrogateAction.RESULT_RELOAD, location = "surrogate.action", type = "redirect"),
	@Result(name = SurrogateAction.RESULT_LIST, location = "/WEB-INF/content/snaker/surrogateList.jsp"),
	@Result(name = SurrogateAction.RESULT_EDIT, location = "/WEB-INF/content/snaker/surrogateEdit.jsp"),
	@Result(name = SurrogateAction.RESULT_VIEW, location = "/WEB-INF/content/snaker/surrogateView.jsp")})
public class SurrogateAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1685772318917181630L;
	public static final String RESULT_RELOAD = "RELOAD";
	public static final String RESULT_LIST = "LIST";
	public static final String RESULT_EDIT = "EDIT";
	public static final String RESULT_VIEW = "VIEW";
	@Autowired
	private SnakerEngineFacets facets;
	private Page<Surrogate> page;
	private Surrogate surrogate;
	private String id;
	
	public String list() {
		facets.searchSurrogate(page, new QueryFilter());
		return RESULT_LIST;
	}
	
	public String create() {
		Struts2Utils.getRequest().setAttribute("processNames", facets.getAllProcessNames());
		return RESULT_EDIT;
	}

	public String edit() {
		surrogate = facets.getSurrogate(id);
		Struts2Utils.getRequest().setAttribute("processNames", facets.getAllProcessNames());
		return RESULT_EDIT;
	}
	
	public String view() {
		surrogate = facets.getSurrogate(id);
		return RESULT_VIEW;
	}
	
	public String update() {
		surrogate.setSdate(surrogate.getSdate() + " 00:00:00");
		surrogate.setEdate(surrogate.getEdate() + " 23:59:59");
		facets.addSurrogate(surrogate);
		return RESULT_RELOAD;
	}
	
	public String delete() {
		facets.deleteSurrogate(id);
		return RESULT_RELOAD;
	}

	public Page<Surrogate> getPage() {
		return page;
	}

	public void setPage(Page<Surrogate> page) {
		this.page = page;
	}

	public Surrogate getSurrogate() {
		return surrogate;
	}

	public void setSurrogate(Surrogate surrogate) {
		this.surrogate = surrogate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
