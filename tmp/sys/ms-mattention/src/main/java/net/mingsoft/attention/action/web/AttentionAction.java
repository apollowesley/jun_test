/**
The MIT License (MIT) * Copyright (c) 2016 铭飞科技

 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */package net.mingsoft.attention.action.web;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mingsoft.people.action.BaseAction;

import net.mingsoft.attention.biz.IBasicAttentionBiz;
import net.mingsoft.attention.entity.BasicAttentionEntity;

@Controller("attention")
@RequestMapping("/attention")
public class AttentionAction extends BaseAction {
	
	/**
	 * 注入关注业务层
	 */
	@Autowired
	private IBasicAttentionBiz basicAttentionBiz;
	
	/**
	 * 统计当前所属类别的总数
	 * @param basicAttentionEntity
	 * <i>basicAttention参数包含字段信息参考：</i><br/>
	 * basicAttentionBasicId - 信息编号：
	 * basicAttentionType 关注类型 具体平台也可以根据自身的规则定义
	 * <dt><span class="strong">返回</span></dt><br/>
	 * 直接返回总数，注意：非json格式
	 */
	@RequestMapping(value="/count")
	@ResponseBody
	public void count(@ModelAttribute BasicAttentionEntity basicAttentionEntity,HttpServletRequest request, HttpServletResponse response) {
		if(basicAttentionEntity != null){
			this.outJson(response, basicAttentionBiz.count(basicAttentionEntity));
		}else{
			this.outJson(response, 0);
		}
	}
	
}
