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
package net.mingsoft.attention.entity;

import java.util.Date;

import com.mingsoft.base.entity.BaseEntity;
import com.mingsoft.basic.entity.BasicEntity;

/**
 * 
 * 铭飞关注模块
 * @author 铭飞开发团队
 * @version 
 * 版本号：100-000-000<br/>
 * 创建日期：2016年7月8日<br/>
 * 历史修订：<br/>
 */
public class BasicAttentionEntity extends BaseEntity{
	
	
	/**
	 * 自增长ID
	 */
	private int basicAttentionId;
	
	/**
	 * 关联用户Id
	 */
	private int basicAttentionPeopleId;
	
	/**
	 * 关联关注内容Id
	 */
	private int basicAttentionBasicId;
	
	/**
	 * 关联应用Id
	 */
	private int basicAttentionAppId;
	
	/**
	 * 模块编号id
	 * 不参与表结构
	 */
	private int basicModelId;
	
	/**
	 * 关注类型
	 */
	private int basicAttentionType;
	
	/**
	 * 关注时间
	 */
	private 	Date basicAttentionTime = new Date();
	
	/**
	 * 收藏内容的标题
	 */
	private BasicEntity basicEntity;
	
	
	/**
	 * 收藏内容的链接地址
	 */
	private String basicAttentionUrl;
	
	
	
	public String getBasicAttentionUrl() {
		return basicAttentionUrl;
	}

	public void setBasicAttentionUrl(String basicAttentionUrl) {
		this.basicAttentionUrl = basicAttentionUrl;
	}
	
	/**
	 * 获取模块编号id
	 * @return
	 */
	public int getBasicModelId() {
		return basicModelId;
	}

	/**
	 * 设置模块编号id
	 * @param basicModelId
	 */
	public void setBasicModelId(int basicModelId) {
		this.basicModelId = basicModelId;
	}

	/**
	 * 获取basicEntity
	 * @return  basicEntity
	 */
	public BasicEntity getBasicEntity() {
		return basicEntity;
	}

	/**
	 * 设置basicEntity
	 * @param basicEntity
	 */
	public void setBasicEntity(BasicEntity basicEntity) {
		this.basicEntity = basicEntity;
	}

	/**
	 * 获取basicAttentionId
	 * @return  basicAttentionId
	 */
	public int getBasicAttentionId() {
		return basicAttentionId;
	}

	/**
	 * 设置basicAttentionId
	 * @param basicAttentionId
	 */
	public void setBasicAttentionId(int basicAttentionId) {
		this.basicAttentionId = basicAttentionId;
	}

	/**
	 * 获取basicAttentionPeopleId
	 * @return  basicAttentionPeopleId
	 */
	public int getBasicAttentionPeopleId() {
		return basicAttentionPeopleId;
	}

	/**
	 * 设置basicAttentionPeopleId
	 * @param basicAttentionPeopleId
	 */
	public void setBasicAttentionPeopleId(int basicAttentionPeopleId) {
		this.basicAttentionPeopleId = basicAttentionPeopleId;
	}

	/**
	 * 获取basicAttentionBasicId
	 * @return  basicAttentionBasicId
	 */
	public int getBasicAttentionBasicId() {
		return basicAttentionBasicId;
	}

	/**
	 * 设置basicAttentionBasicId
	 * @param basicAttentionBasicId
	 */
	public void setBasicAttentionBasicId(int basicAttentionBasicId) {
		this.basicAttentionBasicId = basicAttentionBasicId;
	}

	/**
	 * 获取basicAttentionAppId
	 * @return  basicAttentionAppId
	 */
	public int getBasicAttentionAppId() {
		return basicAttentionAppId;
	}

	/**
	 * 设置basicAttentionAppId
	 * @param basicAttentionAppId
	 */
	public void setBasicAttentionAppId(int basicAttentionAppId) {
		this.basicAttentionAppId = basicAttentionAppId;
	}

	/**
	 * 获取basicAttentionType
	 * @return  basicAttentionType
	 */
	public int getBasicAttentionType() {
		return basicAttentionType;
	}

	/**
	 * 设置basicAttentionType
	 * @param basicAttentionType
	 */
	public void setBasicAttentionType(int basicAttentionType) {
		this.basicAttentionType = basicAttentionType;
	}

	/**
	 * 获取basicAttentionTime
	 * @return  basicAttentionTime
	 */
	public Date getBasicAttentionTime() {
		return basicAttentionTime;
	}

	/**
	 * 设置basicAttentionTime
	 * @param basicAttentionTime
	 */
	public void setBasicAttentionTime(Date basicAttentionTime) {
		this.basicAttentionTime = basicAttentionTime;
	}
	
	
}
