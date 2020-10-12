package com.uploadimage;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletInputStream;

import lombok.extern.slf4j.Slf4j;

import com.avatarUpload.ImageUtils;
import com.avatarUpload.ImageUtils2;
import com.hibernate.voDao.BillsImages;
import com.hibernate.voDao.BillsImagesDAO;
import com.weiwo.exception.BeanException;

/**
 * 上传图片，并生成小文件
 * 
 * 
 * @author peaches
 */
@Slf4j
public class FileUpload {
	String path = this.getClass().getClassLoader().getResource("").getPath();
	ServletInputStream in = null;
	String fpath = "";
	String fileNameOrigin = "";
	String fileSize = "";

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

	public String getFileUpload(String uid, String uname, String savePath) throws IOException {
		if (savePath == null) {
			savePath = "image/images/";
		}
		path = path.replace("WEB-INF/classes/", "");
		try {
			path = java.net.URLDecoder.decode(path, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		fpath = path + savePath;
		/* 格式化当前时间 */
		SimpleDateFormat formatterms1 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date currentimems1 = new java.util.Date();
		String currentdatems1 = formatterms1.format(currentimems1);
		fpath += currentdatems1 + "/";
		File d = new File(fpath);// 建立代表目录的File对象，并得到它的一个引用
		// d.setWritable(true, false); // 在linux 下java
		// 的默认的文件写入权限仅局限在执行目录之下。如果需要在其他目录写入文件或者文件夹
		// 需要手动设置以下权限。
		if (!d.exists()) {// 检查目录是否存在
			d.mkdirs();// 建立目录
		}
		String r = null;
		try {
			r = getFileUpload(in, fpath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/* 存入数据库 */
		savatodatabase(savePath + currentdatems1 + "/" + r, uid, uname);
		/* 生成小图片 */
		generatesmallimage(fpath + r, "small");
		generatesmallimage(fpath + r, "normal");
		return currentdatems1 + "/" + r;
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
		// String type = new String(b, 0, l);//
		// eg.Content-Type:application/octet-stream(程序文件)
		// System.out.println("type      " + type);
		l = in.readLine(b, 0, b.length);
		// String nulll = new String(b, 0, l);// 此值应为空
		// System.out.println("nulll     " + nulll);
		int nIndex = info.toLowerCase().indexOf("filename=\"");
		int nLastIndex = info.toLowerCase().indexOf("\"", nIndex + 10);
		String filepath = info.substring(nIndex + 10, nLastIndex);
		int na = filepath.lastIndexOf("\\");
		// fileNameOrigin = filepath.substring(na + 1);
		fileNameOrigin = filepath;
		if (fileNameOrigin.length() > 254) {
			fileNameOrigin = fileNameOrigin.substring(fileNameOrigin.length() - 254);
		}

		if (!checkextension(filepath)) {
			throw new BeanException("不支持的文件格");
		}
		/* 随机生成一个名字 */
		/* 使用文件名最后三个字母作为扩展名 */
		String filename = GenerateFileName() + "." + filepath.substring(filepath.length() - 3);

		String path = fpath + filename;

		File fi = new File(path);// 建立目标文件
		if (!fi.exists() && !fi.createNewFile()) {
			return "建立文件失败";
		}
		BufferedOutputStream f = new BufferedOutputStream(new FileOutputStream(fi));
		int length = 0;
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
			length += l;
		}
		NumberFormat n = NumberFormat.getInstance();
		n.setMaximumFractionDigits(2);
		fileSize = length + "Byte " + n.format(length / 1024.0) + "KB " + n.format(length / 1024.0 / 1024) + "MB ";
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

	public void savatodatabase(String dir, String uid, String uname) {
		/*
		 * 写入数据库中图片记录 问题：两个表的主键都是自增加，但是同时双方都需要引用另一个的主键。需要，先存入图片，再存入账单，再更新图片？？？
		 */
		BillsImagesDAO billsImagesDao = new BillsImagesDAO();
		BillsImages billsImages = new BillsImages();
		billsImages.setBidir(dir);
		billsImages.setUid(Integer.parseInt(uid));
		billsImages.setUsername(uname);
		billsImages.setBitime(new Date());
		billsImages.setFilename(fileNameOrigin);
		billsImages.setFilesize(fileSize);
		billsImagesDao.save(billsImages);
	}

	/**
	 * 图片类型和最大像素对应关系
	 */
	static final HashMap<String, Integer> imageTypes = new HashMap<String, Integer>() {
		{
			put("small", 240);
		}
		{
			put("normal", 1280);
		}
	};

	/**
	 * 
	 * @param dir
	 * @param imageType
	 *            small(max=240)|normal(max=1280)
	 * @throws IOException
	 * @createTime 2014-11-1 上午12:25:38
	 */
	public void generatesmallimage(String dir, String imageType) throws IOException {
		String[] filepath = dir.split("/");
		String[] filename = filepath[filepath.length - 1].split("\\.");// 要求只能包含一个.，用于生成small文件名
		String filenamesmall = filename[0] + imageType + "." + filename[1];
		if ("gif".equals(filename[1]) && "normal".equals(imageType)) {
			// gif图片不生成normal图片
			return;
		}
		filenamesmall = dir.replace(filepath[filepath.length - 1], filenamesmall);
		int width = ImageUtils.sizeW(dir);
		int height = ImageUtils.sizeH(dir);
		int max = height > width ? height : width;
		double scale;
		if (max > imageTypes.get(imageType)) {
			scale = max * 1.0 / imageTypes.get(imageType);
		} else {
			scale = 1.0;
		}

		if ("normal".equals(imageType)) {
			ImageUtils2.resize(new File(dir), new File(filenamesmall), max, 0.6f);// 不压缩尺寸，只压缩质量
		} else {
			ImageUtils2.resize(new File(dir), new File(filenamesmall), (int) (max / scale), 0.95f);// 1M的图片自动压缩，也会oom。512的服务器，哎~
		}
	}

	public boolean checkextension(String fileName) {
		fileName = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		return ",jpg,jpeg,gif,png,bmp,".contains("," + fileName + ",");
	}

	public void close() {
		try {
			in.close();
		} catch (Exception e) {
			log.error("上传图片后，关闭流出错！" + e);
		}
	}
}
