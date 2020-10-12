package com.evil.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;

/**
 * 将数据以JSon的形式写入PrintWriter中的工具类
 * @author Evil
 */
public class JsonUtil {
	public static void returnMsg(ReturnMsg rm) {
		try {
			HttpServletResponse response = Struts2Utils.getResponse();
			response.setContentType("application/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.print(JSONArray.toJSONString(rm));
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void writeJsonData(Object object) {
		try {
			HttpServletResponse response = Struts2Utils.getResponse();
			response.setContentType("application/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.print(JSONArray.toJSONString(object));
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
