package com.avatarUpload;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GenerateImg {
	String path = this.getClass().getClassLoader().getResource("").getPath().replaceAll("%20", " ");
	String fpath = null;
	String fpathsave = null;

	public GenerateImg() {
	}

	public String generateImg(String filename, String avatarfilename, String top, String left, String width, String height) {

		path = path.replace("WEB-INF/classes/", "");
		try {
			path = java.net.URLDecoder.decode(path, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		fpath = path + "image/avatarTemp/" + filename;
		fpathsave = path + "image/avatar/avatar_uid_" + avatarfilename + ".jpg";
		int widthY = ImageUtils.sizeW(fpath);/* 原始宽度 */
		int heightY = ImageUtils.sizeH(fpath);/* 原始高度 */
		double ScaleY = widthY < heightY ? heightY / 400.0 : widthY / 300.0;/*
																			 * 计算比例
																			 * ，
																			 * 用大的
																			 */
		/* 将参数中的小数去掉 */
		String regEx = "[\\.][0-9]+"; //
		Pattern pat = Pattern.compile(regEx);
		Matcher mat = pat.matcher(top);
		top = mat.replaceAll("");
		mat = pat.matcher(left);
		left = mat.replaceAll("");
		mat = pat.matcher(width);
		width = mat.replaceAll("");
		mat = pat.matcher(height);
		height = mat.replaceAll("");

		int nx = (int) (new Integer(left) * ScaleY);/* 300是页面的框架宽度，nx,新的x位置 */
		int ny = (int) (new Integer(top) * ScaleY);/* 400是页面的框架宽度，ny,新的x位置 */
		int nwidth = (int) (new Integer(width) * ScaleY);/* nwidth, 新的width */
		int nheight = (int) (new Integer(height) * ScaleY);/*
															 * nheight ,
															 * 新的height
															 */
		ImageUtils.cut(fpath, fpathsave, nx, ny, nwidth, nheight);/* 切割图片 */
		ImageUtils.scale2(fpathsave, fpathsave, 99, 99, true);/* 缩放到99px */
		return "success";
	}

	/**
	 * 将图片按宽高的最小从正中间裁剪成一个正方形图片，再缩放到99px
	 * 
	 * @param filename
	 * @param avatarfilename
	 * @param top
	 * @param left
	 * @param width
	 * @param height
	 * @return
	 * @throws IOException
	 * @createTime 2014-9-6 上午8:38:04
	 */
	public String generateImg(String filename, String avatarfilename) throws IOException {

		path = path.replace("WEB-INF/classes/", "");
		try {
			path = java.net.URLDecoder.decode(path, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		fpath = path + "image/avatarTemp/" + filename;
		fpathsave = path + "image/avatar/avatar_uid_" + avatarfilename + ".jpg";
		int width = ImageUtils.sizeW(fpath);/* 原始宽度 */
		int height = ImageUtils.sizeH(fpath);/* 原始高度 */
		double ScaleY = 1;
		int nx;
		int ny;
		int nwidth;
		int nheight;
		if (width > height) {
			nx = (int) (new Integer((width - height) / 2) * ScaleY);// nx,新的x位置
			ny = (int) (new Integer(0) * ScaleY);// ny,新的x位置
			nwidth = (int) (new Integer(height) * ScaleY);// nwidth,新的width
			nheight = (int) (new Integer(height) * ScaleY);// nheight,新的height
		} else {
			nx = (int) (new Integer(0) * ScaleY);// nx,新的x位置
			ny = (int) (new Integer((height - width) / 2) * ScaleY);// ny,新的x位置
			nwidth = (int) (new Integer(width) * ScaleY);// nwidth,新的width
			nheight = (int) (new Integer(width) * ScaleY);// nheight,新的height
		}

		ImageUtils.cut(fpath, fpathsave, nx, ny, nwidth, nheight);/* 切割图片 */
		ImageUtils2.resize(new File(fpathsave), new File(fpathsave), 200, 1.0f);/* 缩放到200px */
		return fpathsave;
	}
}
