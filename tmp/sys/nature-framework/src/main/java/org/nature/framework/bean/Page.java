package org.nature.framework.bean;

import java.util.Enumeration;
import java.util.List;

import org.nature.framework.cache.NatureContext;
import org.nature.framework.core.NatureMap;

public class Page extends NatureMap{
	private static final long serialVersionUID = 1L;
	
	public Page() {
	}

	public Page(int pageSize,int pageNum) {
		put("pageSize", pageSize);
		put("pageNum", pageNum);
	}

	public void pagination(){
		String uri = NatureContext.getRequest().getRequestURI(); 
		String params = apendParams();
		int pageNum = getPageNum();
		int pageCnt = getInt("pageCnt");
		int rowCnt = getInt("rowCnt");
		
		StringBuffer pagination = new StringBuffer();
		pagination.append("<div id='_pagination' class='_pagination'>");
		pagination.append("<span class='_desc_word'>共 </span>");
		pagination.append("<span class='_desc_num'>"+rowCnt+"</span>");
		pagination.append("<span class='_desc_word'>条 </span>");
		pagination.append("<span class='_desc_num'>"+pageNum+"</span>");
		pagination.append("<span class='_desc_word_'>/</span>");
		pagination.append("<span class='_desc_num'>"+pageCnt+"</span>");
		pagination.append("<span class='_desc_word'>页 </span>");
		if (pageNum>1) {
			pagination.append("<a class='_link_word' href='"+uri+"?page.pageNum=1"+params+"'>首页</a>");
			pagination.append("&nbsp;&nbsp;");
			pagination.append("<a class='_link_word' href='"+uri+"?page.pageNum="+(pageNum-1)+params+"'>上一页</a>");
		}else {
			pagination.append("<a class='_link_word'>首页</a>");
			pagination.append("&nbsp;&nbsp;");
			pagination.append("<a class='_link_word'>上一页</a>");
		}
//		if (pageNum<=4) {
//			for (int i = 1; i <=3; i++) {
//				pagination.append("<a class='_link_word' href='"+uri+"?page.pageNum="+(i)+params+"'> "+(i)+" </a>");
//				if (i==pageCnt) {
//					break;
//				}
//			}
//			for (int i = 4; i <=7; i++) {
//				pagination.append("<a class='_link_word' href='"+uri+"?page.pageNum="+(i)+params+"'> "+(i)+" </a>");
//				if (i==pageCnt) {
//					break;
//				}
//			}
//		}else {
//			for (int i = pageNum-3; i <=pageNum+3; i++) {
//				pagination.append("<a class='_link_word' href='"+uri+"?page.pageNum="+(i)+params+"'> "+(i)+" </a>");
//				if (i==pageCnt) {
//					break;
//				}
//			}
//		}
		pagination.append("&nbsp;&nbsp;");
		if (pageNum<pageCnt) {
			pagination.append("<a class='_link_word' href='"+uri+"?page.pageNum="+(pageNum+1)+params+"'>下一页</a>");
			pagination.append("&nbsp;&nbsp;");
			pagination.append("<a class='_link_word' href='"+uri+"?page.pageNum="+pageCnt+params+"'>尾页</a>");
		}else {
			pagination.append("<a class='_link_word'>下一页</a>");
			pagination.append("&nbsp;&nbsp;");
			pagination.append("<a class='_link_word'>尾页</a>");
		}
		
		pagination.append("</div>");
		put("pagination", pagination.toString());
	}
	
	private String apendParams() {
		StringBuffer sb = new StringBuffer();
		Enumeration<String> parameterNames = NatureContext.getRequest().getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String name = parameterNames.nextElement();
			if (!name.contains("page.")) {
				String value = NatureContext.getRequest().getParameter(name);
				sb.append("&").append(name).append("=").append(value);
			}
		}
		return sb.toString();
	}

	public int getPageSize() {
		return getInt("pageSize");
	}

	public void setPageSize(int pageSize) {
		put("pageSize", pageSize);
	}

	
	public int getPageNum() {
		int pageNum = getInt("pageNum");
		if (pageNum<=0) {
			pageNum=1;
		}
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.put("pageNum", pageNum);
	}

	public int getRowNum() {
		int rowNum = getInt("pageSize")*(getInt("pageNum")-1);
		return rowNum;
	}


	public void setRowCnt(int rowCnt) {
		int pageCnt = (rowCnt+getInt("pageSize")-1)/getInt("pageSize");
		put("pageCnt", pageCnt);
		put("rowCnt", rowCnt);
	}

	@SuppressWarnings("unchecked")
	public List<NatureMap> getDataList() {
		return (List<NatureMap>) get("dataList");
	}

	public void setDataList(List<? extends NatureMap> dataList) {
		put("dataList", dataList);
	}

}
