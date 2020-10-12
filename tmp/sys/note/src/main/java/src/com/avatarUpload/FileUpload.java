package com.avatarUpload;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletInputStream;

import lombok.extern.slf4j.Slf4j;

/**
 * 这个类仅用于头像上传
 * 
 * 
 * @author peaches
 */
@Slf4j
public class FileUpload {
	String path = this.getClass().getClassLoader().getResource("").getPath();
	ServletInputStream in = null;
	String fpath = "";

	public FileUpload() {
		fpath = "";
		in = null;
	}

	public void setInputStream(ServletInputStream in) {
		this.in = in;
	}

	public void setFpath(String p) {
		fpath = p;
	}

	public String getFpath() {
		return fpath;
	}

	public String getParameter() {
		String r = null;
		try {
			r = getParameter(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}

	public String getFileUpload() {
		path = path.replace("WEB-INF/classes/", "");
		try {
			path = java.net.URLDecoder.decode(path, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		fpath = path + "image/avatarTemp/";
		String r = null;
		try {
			r = getFileUpload(in, fpath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}

	public String getParameter(ServletInputStream in)// 只能按顺序提取
			throws Exception {
		int l = 0;
		byte[] b = new byte[1024];
		l = in.readLine(b, 0, b.length);// 依次是读取属性的开始符、名称、属性值的类型、属性的值
		String si = new String(b);
		if (si.startsWith("----------------------------")) {// 表示是从开始符开始读，否则应为刚读取文件后的一个属性，此时应少读一次
			l = in.readLine(b, 0, b.length);
		}
		l = in.readLine(b, 0, b.length);
		l = in.readLine(b, 0, b.length);
		String value = new String(b, 0, l - 2);
		return value;
	}

	public String getFileUpload(ServletInputStream in, String fpath)// 需要提供输入流和存储路径
			throws Exception {
		// out.println("文件信息:<br>");
		long begin = System.currentTimeMillis();// 传送时间计时开始
		int l = 0;
		byte[] b = new byte[1024];
		l = in.readLine(b, 0, b.length);
		String sign = new String(b, 0, l);// eg.-----------------------------7d9dd29630a34
		l = in.readLine(b, 0, b.length);
		String info = new String(b, 0, l, "utf-8");// eg.Content-Disposition:form-data;
		// name="file";
		l = in.readLine(b, 0, b.length);
		// String type=new
		// String(b,0,l);//eg.Content-Type:application/octet-stream(程序文件)
		l = in.readLine(b, 0, b.length);
		// String nulll=new String(b,0,l);//此值应为空
		int nIndex = info.toLowerCase().indexOf("filename=\"");
		int nLastIndex = info.toLowerCase().indexOf("\"", nIndex + 10);
		String filepath = info.substring(nIndex + 10, nLastIndex);
		int na = filepath.lastIndexOf("\\");
		String filenameOrigin = filepath.substring(na + 1);
		if (checkextension(filepath)) {

		}
		/* 随机生成一个名字 */
		String filename = GenerateFileName() + "." + filepath.substring(filepath.length() - 3);

		String path = fpath + filename;

		File fi = new File(path);// 建立目标文件
		if (!fi.exists() && !fi.createNewFile()) {
			return "建立文件失败";
		}
		BufferedOutputStream f = new BufferedOutputStream(new FileOutputStream(fi));

		while ((l = in.readLine(b, 0, b.length)) > 0) {
			if (l == sign.length()) {
				String sign1 = new String(b, 0, sign.length());
				// out.println(sign1+"<br>");
				if (sign1.startsWith(sign)) {
					break;
				}
			}
			f.write(b, 0, l);
			f.flush();
		}
		f.flush();
		f.close();
		long end = System.currentTimeMillis();// 传送时间计时结束
		// out.println("上传文件用时："+(end-begin)+"毫秒<br>");
		// return end - begin;
		return filename;
	}

	private final String[] strs = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
			"t", "u", "v", "w", "x", "y", "z" };

	/** 随机生成一个文件名，因为中文会出错，另外，网页也无法显示中文路径，再者，这也是临时文件，会删除的，无所谓 */
	public String GenerateFileName() {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < 17; i++) {
			Integer thenum = (int) (Math.random() * 36);
			resultSb.append(strs[thenum]);
		}
		return resultSb.toString();
	}

	public boolean checkextension(String fileName) {
		fileName = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
		System.out.println(fileName);
		return ",jpg,jpeg,gif,png,bmp,".contains("," + fileName + ",");
	}

	public void close() {
		try {
			in.close();
		} catch (Exception e) {
			log.error("关闭流出错！" + e);
		}
	}
}
