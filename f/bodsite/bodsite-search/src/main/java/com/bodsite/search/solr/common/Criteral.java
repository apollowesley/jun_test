package com.bodsite.search.solr.common;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.params.CommonParams;

import com.bodsite.common.utils.StringUtils;
import com.github.pagehelper.Page;

public class Criteral {
	// 分页数据
	private Page<?> page;
	// q – 查询字符串，必须的。
	private StringBuilder q = new StringBuilder();
	// fq – （filter
	// query）过虑查询，作用：在q查询符合结果中同时是fq查询符合的，例如：q=mm&fq=date_time:[20081001 TO
	// 20091031]，找关键字mm，并且date_time是20081001到20091031之间的。官方文档：http://wiki.apache.org/solr/CommonQueryParameters
	private List<String> fq = new ArrayList<String>();
	// qf: query fields，指定solr从哪些field中搜索。
	private StringBuilder qf = new StringBuilder();
	// pf: 用于指定一组field，当query完全匹配pf指定的某一个field时，来进行boost。
	private StringBuilder pf = new StringBuilder();
	// bf: 用函数的方式计算boost。 Function (with optional boosts) that will be included
	// in the user's query to influence the score. Any function supported
	// natively by Solr can be used, along with a boost value, e.g.:
	// recip(rord(myfield),1,2,3)^1.5wt: writer type，指定输出格式，可以有 xml, json, php,
	// phps。
	private StringBuilder bf = new StringBuilder();
	// fl – 指定返回那些字段内容，用逗号或空格分隔多个。
	private List<String> fl;
	// hl.fl 高亮值
	private List<String> hls;
	// indent – 返回的结果是否缩进，默认关闭，用 indent=true|on 开启，一般调试
	private boolean indent;
	// 单词停用，true或false。
	private boolean stopwords = true;
	// 此参数用于控制小写单词作为布尔运算符，如”and”and“or”。设置与lowercaseOperators=true来允许此。默认为true。
	private boolean lowercaseOperators = true;
	// defType: 指定query parser，常用defType=lucene, defType=dismax, defType=edismax
	private String defType;
	// 返回结果格式
	private String format = "json";
	// q.op – 覆盖schema.xml的defaultOperator（有空格时用”AND”还是用”OR”操作逻辑），一般默认指定:OR/AND
	private String q_op;
	// sort – 排序
	private Map<String, ORDER> sortMap = new LinkedHashMap<String, ORDER>();

	public SolrQuery toSolrQuery() {
		SolrQuery solrQuery = new SolrQuery();
		if (page != null) {
			solrQuery.add(CommonParams.START, String.valueOf(page.getStartRow()));
			if (page.getPageSize() > 0) {
				solrQuery.add(CommonParams.ROWS, String.valueOf(page.getPageSize()));
			}
		}
		solrQuery.set("defType", defType);
		solrQuery.set(CommonParams.Q, q.length()>0?q.toString():"*");
		solrQuery.set(CommonParams.FL, StringUtils.join(fl, ","));
		solrQuery.set(CommonParams.FQ, StringUtils.join(fq, " AND "));
		solrQuery.set("qf", qf.toString());
		solrQuery.set("pf", pf.toString());
		solrQuery.set("bf", bf.toString());
		solrQuery.set("q.op", q_op);
		solrQuery.set(CommonParams.SORT, sortToString());
		solrQuery.set(CommonParams.WT, format);
		if (!stopwords) {
			solrQuery.set("stopwords", "false");
		}
		if (!lowercaseOperators) {
			solrQuery.set("lowercaseOperators", "false");
		}
		if (indent) {
			solrQuery.set("indent", "true");
		}
		if (this.hls != null && !hls.isEmpty()) {
			solrQuery.set("hl", "true");
			//Object[] strs =  hls.toArray();
			//solrQuery.setParam("hl.fl", (String[])strs);
			solrQuery.set("hl.fl", StringUtils.join(hls, ","));
		}
		return solrQuery;
	}

	private String sortToString() {
		StringBuilder sb = new StringBuilder();
		if (sortMap!=null&&!sortMap.isEmpty()) {
			for (Map.Entry<String, ORDER> sort : sortMap.entrySet()) {
				if (sb.length() > 0) {
					sb.append(",");
				}
				sb.append(sort.getKey());
				sb.append(" ");
				sb.append(sort.getValue().reverse());
			}
		}
		return sb.toString();
	}
	
	
	public void setFqByMap(Map<String, ?> fqMap) {
		if(fqMap!=null&&!fqMap.isEmpty()){
			for(Map.Entry<String,?> map:fqMap.entrySet()){
				saveFq(map);
			}
		}
	}
	
	private <T> void saveFq(Map.Entry<String,T> map){
		List<String> list = escape(map.getValue());
		if(list==null||list.isEmpty()){
			return;
		}
		fq.add(eqOrIn(map.getKey(),list));
	}

	public String eqOrIn(String propertyName, List<String> values) {
		if(values.size()==1){
			return propertyName+":"+values.get(0);
		}else{
			String vals = StringUtils.join(values, " OR ");
			return propertyName+":"+"("+vals+")";
		}
	}
	
	private <T> List<String> escape(T t){
		List<String> listNew = new ArrayList<String>();
		if(t instanceof List){
			List<?> list = (List<?>) t;
			for(Object value:list){
				if(value==null){
					continue;
				}
				listNew.add(ClientUtils.escapeQueryChars(String.valueOf(value)));
			}
		}else{
			listNew.add(ClientUtils.escapeQueryChars(String.valueOf(t)));
		}
		return listNew;
	}
	
	public void setQByMap(Map<String, ?> queryMap) {
		if(queryMap!=null&&!queryMap.isEmpty()){
			for(Map.Entry<String,?> map:queryMap.entrySet()){
				saveQ(map);
			}
		}
	}
	
	private <T> void saveQ(Map.Entry<String,T> map){
		List<String> list = escape(map.getValue());
		if(list==null||list.isEmpty()){
			return;
		}
		addQ(eqOrIn(map.getKey(),list),null);
	}
	
	public void addQ(String name,String value){
		if(q.length()>0){
			this.q.append(" AND ");
		}
		this.q.append(name);
		if(StringUtils.isNotBlank(value)){
			this.q.append(":");
			this.q.append(value);
		}
	}
	
	public void addQf(String name, Float val){
		if(val==null){
			val = 0f;
		}
		if(this.qf.length()>0){
			this.qf.append(' ');
		}
		this.qf.append(name);
		this.qf.append('^');
		this.qf.append(val);
	}

	public StringBuilder getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = new StringBuilder(q);
	}

	public List<String> getFq() {
		return fq;
	}

	public void setFq(List<String> fq) {
		this.fq = fq;
	}

	public StringBuilder getQf() {
		return qf;
	}

	public void setQf(String qf) {
		this.qf = new StringBuilder(qf);
	}

	public StringBuilder getPf() {
		return pf;
	}

	public void setPf(String pf) {
		this.pf = new StringBuilder(pf);
	}

	public StringBuilder getBf() {
		return bf;
	}

	public void setBf(String bf) {
		this.bf = new StringBuilder(bf);
	}

	public List<String> getFl() {
		return fl;
	}

	public void setFl(List<String> fl) {
		this.fl = fl;
	}

	public Page<?> getPage() {
		return page;
	}

	public void setPage(Page<?> page) {
		this.page = page;
	}

	public List<String> getHls() {
		return hls;
	}

	public void setHls(List<String> hls) {
		this.hls = hls;
	}

	public boolean isIndent() {
		return indent;
	}

	public void setIndent(boolean indent) {
		this.indent = indent;
	}

	public boolean isStopwords() {
		return stopwords;
	}

	public void setStopwords(boolean stopwords) {
		this.stopwords = stopwords;
	}

	public boolean isLowercaseOperators() {
		return lowercaseOperators;
	}

	public void setLowercaseOperators(boolean lowercaseOperators) {
		this.lowercaseOperators = lowercaseOperators;
	}

	public String getDefType() {
		return defType;
	}

	public void setDefType(String defType) {
		this.defType = defType;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getQ_op() {
		return q_op;
	}

	public void setQ_op(String q_op) {
		this.q_op = q_op;
	}

	public Map<String, ORDER> getSortMap() {
		return sortMap;
	}

	public void setSortMap(Map<String, ORDER> sortMap) {
		this.sortMap = sortMap;
	}


}
