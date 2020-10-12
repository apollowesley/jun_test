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
 *//**
 * 
 */
package net.mingsoft.attention.action.people;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.mingsoft.base.entity.ListJson;
import com.mingsoft.base.filter.DateValueFilter;
import com.mingsoft.base.filter.DoubleValueFilter;
import com.alibaba.fastjson.JSONObject;
import java.util.ArrayList;

import com.mingsoft.base.constant.e.BaseEnum;
import com.mingsoft.base.entity.BaseEntity;
import com.mingsoft.basic.biz.IModelBiz;
import com.mingsoft.basic.entity.BasicEntity;
import com.mingsoft.basic.entity.ModelEntity;
import com.mingsoft.people.action.BaseAction;
import com.mingsoft.people.entity.PeopleEntity;
import com.mingsoft.util.StringUtil;
import net.mingsoft.basic.bean.EUListBean;
import net.mingsoft.basic.bean.ListBean;
import net.mingsoft.attention.biz.IBasicAttentionBiz;
import net.mingsoft.attention.constant.ModelCode;
import net.mingsoft.attention.entity.BasicAttentionEntity;
import net.mingsoft.basic.util.BasicUtil;

/**
 * 
 * 铭飞MS平台－关注插件
 * 
 * @author 铭飞开发团队
 * @version 版本号：100-000-000<br/>
 *          创建日期：2015年3月20日<br/>
 *          历史修订：<br/>
 */
@Controller("peopleAttention")
@RequestMapping("/people/attention")
public class AttentionAction extends BaseAction {

	/**
	 * 注入关注业务层
	 */
	@Autowired
	private IBasicAttentionBiz basicAttentionBiz;
	
	/**
	 * 新增关注
	 * 
	 * @param basicAttention
	 *            <i>basicAttention参数包含字段信息参考：</i><br/>
	 *            basicAttentionBasicId 信息编号<br/>
	 *            basicAttentionType 关注类型 具体平台也可以根据自身的规则定义
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            {code:"错误编码",<br/>
	 *            result:"true｜false",<br/>
	 *            resultMsg:"错误信息", <br/>
	 *            }
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public void save(@ModelAttribute BasicAttentionEntity basicAttention, HttpServletRequest request,
			HttpServletResponse response) {
		if (basicAttention == null || basicAttention.getBasicAttentionBasicId() == 0
				|| basicAttention.getBasicAttentionType() == 0) {
			this.outJson(response, false);
			return;
		}
		// 获取用户session
		PeopleEntity people = (PeopleEntity) this.getPeopleBySession(request);
		basicAttention.setBasicAttentionPeopleId(people.getPeopleId());
		// 获取APPID
		basicAttention.setBasicAttentionAppId(BasicUtil.getAppId());
		basicAttention.setBasicAttentionType(basicAttention.getBasicAttentionType());

		// 检查是否已经存在
		BasicAttentionEntity basicAttentionEntity = this.basicAttentionBiz.getEntityByPeopleAttention(basicAttention);
		if (basicAttentionEntity != null) {
			this.outJson(response, false);
			return;
		}

		this.basicAttentionBiz.saveEntity(basicAttention);
		this.outJson(response, true);
	}

	/**
	 * 判断用户是否关注过信息 <br/>
	 * <i>basicAttention参数包含字段信息参考：</i><br/>
	 * basicAttentionBasicId 信息编号<br/>
	 * basicAttentionType 关注类型 具体平台也可以根据自身的规则定义 ，
	 * 
	 * <dt><span class="strong">返回</span></dt><br/>
	 * {code:"错误编码",<br/>
	 * result:"true存在｜false不存在",<br/>
	 * resultMsg:"错误信息", <br/>
	 * }
	 */
	@RequestMapping("/isExists")
	@ResponseBody
	public void isExists(@ModelAttribute BasicAttentionEntity basicAttention, HttpServletRequest request,
			HttpServletResponse response) {
		if (basicAttention == null || basicAttention.getBasicAttentionBasicId() == 0
				|| basicAttention.getBasicAttentionType() == 0) {
			this.outJson(response, false);
			return;
		}
		// 获取用户session
		PeopleEntity people = (PeopleEntity) this.getPeopleBySession(request);
		basicAttention.setBasicAttentionPeopleId(people.getPeopleId());
		// 获取APPID
		basicAttention.setBasicAttentionAppId(BasicUtil.getAppId());
		basicAttention.setBasicAttentionType(basicAttention.getBasicAttentionType());

		BasicAttentionEntity basicAttentionEntity = this.basicAttentionBiz.getEntityByPeopleAttention(basicAttention);
		if (basicAttentionEntity == null || basicAttentionEntity.getBasicAttentionId() == 0) {
			this.outJson(response, false);
		} else {
			this.outJson(response, true);
		}
	}

	/**
	 * 删除关注
	 * 
	 * @param basic
	 *            <i>basic参数包含字段信息参考：</i><br/>
	 *            basicId 信息编号集合，多个编号用逗号隔开,例如 1,2,3,4 basicAttentionType
	 *            类型，由平台自己定义
	 * 
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            {code:"错误编码",<br/>
	 *            result:"true存在｜false不存在",<br/>
	 *            resultMsg:"错误信息", <br/>
	 *            }
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		// 否则执行多选删除方法
		int[] ids = BasicUtil.getInts("basicId", ",");
		int basicAttentionType = BasicUtil.getInt("basicAttentionType");
		// 删除多条评论
		this.basicAttentionBiz.delete(ids, this.getPeopleBySession(request).getPeopleId(), basicAttentionType);
		this.outJson(response, true);
	}


	/**
	 * 关注列表，只返回basic实体列表 <br/>
	 * <i>basicAttention参数包含字段信息参考：</i><br/>
	 * basicAttentionEntity - basicAttentionEntity参数包含字段信息参考：
	 * basicAttentionType 关注类型 具体平台也可以根据自身的规则定义 ，
	 * modelCode 模块编码 ，
	 * pageNo 页码
	 * pageSize 一页显示数量 一页显示数量
	 * 
	 * <dt><span class="strong">返回</span></dt><br/>
	 * { "list": [
	 * {
	 * "basicPic": "缩略图", 
	 * "basicTitle": "标题", 
	 * "basicComment": 评论数, 
	 * "basicCollect": 收藏数量, 
	 * "basicHit": 点数量, 
	 * "basicAppId": 1, 
	 * "basicCategoryId": 160, 
	 * "basicDateTime": 1468568887000,
	 * "basicThumbnails": "/upload/mall/product/1/1468568853464.jpg", 
	 * "basicTypeIds": [ ], 
	 * "basicUpdateTime": shan, 
	 * }],
	 * "page":{"endRow": 2, 
	 * "firstPage": 1, 
	 * "hasNextPage": true存在下一页false不存在, 
	 * "hasPreviousPage": true存在上一页false不存在, 
	 * "isFirstPage": true是第一页false不是第一页, 
	 * "isLastPage": true是最后一页false不是最后一页, 
	 * "lastPage": 最后一页的页码, 
	 * "navigatePages": 导航数量，实现 1...5.6.7....10效果, 
	 * "navigatepageNums": []导航页码集合, 
	 * "nextPage": 下一页, 
	 * "pageNum": 当前页码, 
	 * "pageSize": 一页显示数量, 
	 * "pages": 总页数, 
	 * "prePage": 上一页, 
	 * "size": 总记录, 
	 * "startRow": , 
	 * "total":总记录数量}
	 * }
	 * }
	 */	
	@RequestMapping(value="/list")
	@ResponseBody
	public void list(@ModelAttribute net.mingsoft.attention.entity.BasicAttentionEntity basicAttentionEntity,
			javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) {
		String modelCode = request.getParameter("modelCode");
		if(!StringUtil.isBlank(modelCode)) {
			int modelId = BasicUtil.getModelCodeId(modelCode);
			basicAttentionEntity.setBasicModelId(modelId);
		}
		basicAttentionEntity.setBasicAttentionPeopleId(this.getPeopleBySession().getPeopleId());
		BasicUtil.startPage();
		List<BaseEntity> basicAttentionList = basicAttentionBiz.query(basicAttentionEntity);
		ListBean _list = new ListBean(basicAttentionList, BasicUtil.endPage(basicAttentionList));
		this.outJson(response, net.mingsoft.base.util.JSONArray.toJSONString(_list, new DoubleValueFilter(),new DateValueFilter("yyyy-MM-dd")));
	}
	
}
