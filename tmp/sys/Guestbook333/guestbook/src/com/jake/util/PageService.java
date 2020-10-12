package com.jake.util;

public class PageService {
	StringBuffer bf = new StringBuffer();

	public String getPage(int pageNum, int total, String path) {
		if (pageNum == 1 && total == 1) {
			bf.append("上一页   下一页    当前页为：" + pageNum + "，总页数为：" + total);
		} else if (pageNum == 1 && total > 1) {
			bf.append("上一页   ");
			bf.append("<a href='" + path + "?pageNum=" + (pageNum+1)
					+ "'>下一页   </a>");
			bf.append("当前页为：" + pageNum + "，总页数为：" + total);
		} else if (pageNum == total && total > 1) {
			bf.append("<a href='" + path + "?pageNum=");
			bf.append(pageNum-1);
			bf.append("'>上一页   </a>");
			bf.append(" 下一页   ");
			bf.append("当前页为：" + pageNum + "，总页数为：" + total);
		}else{
			bf.append("<a href='" + path + "?pageNum=" + (pageNum-1)
					+ "'>上一页   </a>");
			bf.append("<a href='" + path + "?pageNum=" + (pageNum+1)
					+ "'>下一页   </a>");
			bf.append("当前页为：" + pageNum + "，总页数为：" + total);
		}
		return bf.toString();
	}

}
