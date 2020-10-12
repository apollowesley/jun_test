package com.evil.tag;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 分页标签处理类
 */
public class PagerTag extends TagSupport {
	private static final long serialVersionUID = 5729832874890369508L;
	private String url; // 请求URI
	private int numPerPage = 10;// 每页要显示的记录数
	private int pageNum = 1; // 当前页号
	private int recordCount; // 总记录数
	private String funcName = null;
	private String dataBar = null;

	private String submitType = "form";

	public int doStartTag() throws JspException {
		int pageCount = (recordCount + numPerPage - 1) / numPerPage; // 计算总页数

		// 拼写要输出到页面的HTML文本
		StringBuilder htmlBuffer = new StringBuilder();

		if (recordCount == 0) {
			htmlBuffer.append("<li><strong>没有可显示的项目</strong></li>\r\n");
		} else {
			// 页号越界处理
			if (pageNum > pageCount) {
				pageNum = pageCount;
			}
			if (pageNum < 1) {
				pageNum = 1;
			}
			// 获取请求中的所有参数
			HttpServletRequest request = (HttpServletRequest) pageContext
					.getRequest();
			Enumeration<String> enumeration = request.getParameterNames();
			String name = null; // 参数名
			String value = null; // 参数值

			if ("form".equals(submitType)) {// form提交方式
				// 添加form表单其实标签
				htmlBuffer.append("<form method=\"post\" action=\"")
						.append(this.url).append("\" name=\"qPagerForm\">\r\n");

				// 把请求中的所有参数当作隐藏表单域
				while (enumeration.hasMoreElements()) {
					name = enumeration.nextElement();
					value = request.getParameter(name);
					// 去除页号
					if ("pageNum".equals(name)) {
						if (null != value && !"".equals(value)) {
							pageNum = Integer.parseInt(value);
						}
						continue;
					} else if ("numPerPage".equals(name)) {
						if (null != value && !"".equals(value)) {
							numPerPage = Integer.parseInt(value);
						}
						continue;
					}
					htmlBuffer.append("<input type=\"hidden\" name=\"")
							.append(name).append("\" value=\"").append(value)
							.append("\"/>\r\n");
				}

				// 把当前页号设置成请求参数
				htmlBuffer.append("<input type=\"hidden\" name=\"")
						.append("pageNum").append("\" value=\"")
						.append(pageNum).append("\"/>\r\n");
				// 把每页显示的数量设置成参数
				htmlBuffer.append("<input type=\"hidden\" name=\"")
						.append("numPerPage").append("\" value=\"")
						.append(numPerPage).append("\"/>\r\n");

				// 输出统计数据
//				htmlBuffer.append("&nbsp;<strong >共" + recordCount + "行,"
//						+ pageCount + "页,每页" + numPerPage + "行  </strong>");
				// 上一页处理 <li><a href="#">2</a></li>
				if (pageNum == 1) {/* &laquo;&nbsp; */
					htmlBuffer
							.append("<li class=\"disabled\"><a href=\"javascript:void(0)\">上一页")
							.append("</a></li>\r\n");
				} else {
					htmlBuffer.append("<li><a href=\"javascript:turnOverPage(")
							.append((pageNum - 1))
							.append(")\">上一页</a></li>\r\n");
				}

				// 如果前面页数过多,显示"..."
				int start = 1;
				if (this.pageNum > 4) {
					start = this.pageNum - 1;
					htmlBuffer
							.append("<li><a href=\"javascript:turnOverPage(1)\">1</a>\r\n</li>");
					htmlBuffer
							.append("<li><a href=\"javascript:turnOverPage(2)\">2</a>\r\n</li>");
					htmlBuffer.append("&hellip;\r\n");
				}
				// 显示当前页附近的页
				int end = this.pageNum + 1;
				if (end > pageCount) {
					end = pageCount;
				}
				for (int i = start; i <= end; i++) {
					if (pageNum == i) { // 当前页号不需要超链接
						htmlBuffer
								.append("<li class=\"active\"><a href=\"javascript:void(0)\">")
								.append(i).append("</a></li>\r\n");
					} else {
						htmlBuffer
								.append("<li><a href=\"javascript:turnOverPage(")
								.append(i).append(")\">").append(i)
								.append("</a></li>\r\n");
					}
				}
				// 如果后面页数过多,显示"..."
				if (end < pageCount - 2) {
					htmlBuffer.append("&hellip;\r\n");
				}
				if (end < pageCount - 1) {
					htmlBuffer.append("<li><a href=\"javascript:turnOverPage(")
							.append(pageCount - 1).append(")\">")
							.append(pageCount - 1).append("</a></li>\r\n");
				}
				if (end < pageCount) {
					htmlBuffer.append("<li><a href=\"javascript:turnOverPage(")
							.append(pageCount).append(")\">").append(pageCount)
							.append("</a></li>\r\n");
				}

				// 下一页处理
				if (pageNum == pageCount) {/* &nbsp;&raquo; */
					htmlBuffer
							.append("<li class=\"disabled\"><a href=\"javascript:void(0)\">下一页")
							.append("</a></li>\r\n");
				} else {
					htmlBuffer.append("<li><a href=\"javascript:turnOverPage(")
							.append((pageNum + 1))
							.append(")\">下一页</a><li>\r\n");
				}
				htmlBuffer.append("</form>\r\n");

				// 生成提交表单的JS
				htmlBuffer.append("<script language=\"javascript\">\r\n");
				htmlBuffer.append("  function turnOverPage(no){\r\n");
				htmlBuffer.append("    if(no>").append(pageCount).append("){");
				htmlBuffer.append("    no=").append(pageCount).append(";}\r\n");
				htmlBuffer.append("    if(no<1){no=1;}\r\n");
				htmlBuffer
						.append("    document.qPagerForm.pageNum.value=no;\r\n");
				htmlBuffer.append("    document.qPagerForm.submit();\r\n");
				htmlBuffer.append("  }\r\n");
				htmlBuffer.append("</script>\r\n");
			} else if ("ajax".equals(submitType)) {// ajax提交方式
				// 生成ajax提交函数
				htmlBuffer.append("<script language=\"javascript\">\r\n");
				htmlBuffer.append("	function " + this.funcName + "(no){\r\n");
				htmlBuffer.append("		$(\"#" + this.dataBar + "\").load(\""
						+ this.url + "\",{");
				htmlBuffer.append("\"pageNum\":no,");

				while (enumeration.hasMoreElements()) {
					name = enumeration.nextElement();
					value = request.getParameter(name);
					// 去除页号
					if ("pageNum".equals(name)) {
						if (null != value && !"".equals(value)) {
							pageNum = Integer.parseInt(value);
						}
						continue;
					}
					htmlBuffer.append("\"" + name + "\":\"" + value + "\",");
				}
				htmlBuffer.deleteCharAt(htmlBuffer.lastIndexOf(","));// 删除最后一个逗号
				htmlBuffer.append("});}\r\n</script>\r\n");
				// htmlBuffer.append("alert($);}\r\n</script>\r\n");

				// 输出统计数据
				htmlBuffer.append("&nbsp;共<strong>").append(recordCount)
						.append("</strong>项").append(",<strong>")
						.append(pageCount).append("</strong>页:&nbsp;\r\n");
				// 上一页处理
				if (pageNum == 1) {
					htmlBuffer.append(
							"<span class=\"disabled\">&laquo;&nbsp;上一页")
							.append("</span>\r\n");
				} else {
					htmlBuffer
							.append("<li><a href=\"javascript:" + this.funcName
									+ "(").append((pageNum - 1))
							.append(")\">&laquo;&nbsp;上一页</a></li>\r\n");
				}

				// 如果前面页数过多,显示"..."
				int start = 1;
				if (this.pageNum > 4) {
					start = this.pageNum - 1;
					htmlBuffer.append("<li><a href=\"javascript:"
							+ this.funcName + "(1)\">1</a></li>\r\n");
					htmlBuffer.append("<li><a href=\"javascript:"
							+ this.funcName + "(2)\">2</a></li>\r\n");
					htmlBuffer.append("&hellip;\r\n");
				}
				// 显示当前页附近的页
				int end = this.pageNum + 1;
				if (end > pageCount) {
					end = pageCount;
				}
				for (int i = start; i <= end; i++) {
					if (pageNum == i) { // 当前页号不需要超链接
						htmlBuffer.append("<span class=\"current\">").append(i)
								.append("</span>\r\n");
					} else {
						htmlBuffer
								.append("<li><a href=\"javascript:"
										+ this.funcName + "(").append(i)
								.append(")\">").append(i).append("</a>\r\n");
					}
				}
				// 如果后面页数过多,显示"..."
				if (end < pageCount - 2) {
					htmlBuffer.append("&hellip;\r\n");
				}
				if (end < pageCount - 1) {
					htmlBuffer
							.append("<li><a href=\"javascript:" + this.funcName
									+ "(").append(pageCount - 1).append(")\">")
							.append(pageCount - 1).append("</a></li>\r\n");
				}
				if (end < pageCount) {
					htmlBuffer
							.append("<li><a href=\"javascript:" + this.funcName
									+ "(").append(pageCount).append(")\">")
							.append(pageCount).append("</a></li>\r\n");
				}

				// 下一页处理
				if (pageNum == pageCount) {
					htmlBuffer.append(
							"<span class=\"disabled\">下一页&nbsp;&raquo;")
							.append("</span>\r\n");
				} else {
					htmlBuffer
							.append("<li><a href=\"javascript:" + this.funcName
									+ "(").append((pageNum + 1))
							.append(")\">下一页&nbsp;&raquo;</a></li>\r\n");
				}

			}
		}
		// 把生成的HTML输出到响应中
		try {
			pageContext.getOut().println(htmlBuffer.toString());
		} catch (IOException e) {
			throw new JspException(e);
		}
		return SKIP_BODY; // 本标签主体为空,所以直接跳过主体
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setNumPerPage(int numPerPage) {
		if (numPerPage < 1) {
			this.numPerPage = 10;
			return;
		}
		this.numPerPage = numPerPage;
	}

	public void setPageNum(int pageNum) {
		if (pageNum < 1) {
			this.pageNum = 1;
			return;
		}
		this.pageNum = pageNum;
	}

	public void setRecordCount(int recordCount) {
		if (recordCount < 0) {
			this.recordCount = 0;
			return;
		}
		this.recordCount = recordCount;
	}

	public void setSubmitType(String submitType) {
		this.submitType = submitType;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public void setDataBar(String dataBar) {
		this.dataBar = dataBar;
	}

}