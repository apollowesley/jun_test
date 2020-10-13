package com.bodsite.search.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bodsite.common.search.Search;
import com.bodsite.common.utils.StringUtils;

public class DemoSearchVo extends Search<DemoResultVo> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer skuId;

	private List<Integer> skuIds;

	private String title;

	private String memo;

	private String intro;

	public Map<String, Object> queryMap() {
		Map<String, Object> queryMap = new HashMap<>();
		if(skuIds!=null&&!skuIds.isEmpty()){
			queryMap.put("skuId",skuId);
		}
		if(StringUtils.isNotBlank(title)){
			queryMap.put("title",title);
		}
		if(StringUtils.isNotBlank(memo)){
			queryMap.put("memo",memo);
		}
		if(StringUtils.isNotBlank(intro)){
			queryMap.put("intro",intro);
		}
		return queryMap;
	}

	public Integer getSkuId() {
		return skuId;
	}

	public void setSkuId(Integer skuId) {
		this.skuId = skuId;
	}

	public List<Integer> getSkuIds() {
		return skuIds;
	}

	public void setSkuIds(List<Integer> skuIds) {
		this.skuIds = skuIds;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

}
