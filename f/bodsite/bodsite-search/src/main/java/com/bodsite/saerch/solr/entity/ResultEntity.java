package com.bodsite.saerch.solr.entity;

import java.util.List;
import java.util.Map;

public class ResultEntity<T> {
	private long numFound = 0;
	private long start = 0;
	private Float maxScore = null;
	private List<T> lists;
	private Map<String, Map<String, List<String>>> highlighting;
	public long getNumFound() {
		return numFound;
	}
	public void setNumFound(long numFound) {
		this.numFound = numFound;
	}
	public long getStart() {
		return start;
	}
	public void setStart(long start) {
		this.start = start;
	}
	public Float getMaxScore() {
		return maxScore;
	}
	public void setMaxScore(Float maxScore) {
		this.maxScore = maxScore;
	}
	public List<T> getLists() {
		return lists;
	}
	public void setLists(List<T> lists) {
		this.lists = lists;
	}
	public Map<String, Map<String, List<String>>> getHighlighting() {
		return highlighting;
	}
	public void setHighlighting(Map<String, Map<String, List<String>>> highlighting) {
		this.highlighting = highlighting;
	}

}
