package com.antdsp.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class CaptchaUtils {
	
	private static final int WIDTH = 200;
	private static final int HEIGHT = 50;
	private static final int CODE_LENGTH = 4;
	
	private static final String chars = "23456789abcdefghjklmnopqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ";
	
	private static final Random RANDOM = new Random();
	
	public static String createText() {
		return createText(CODE_LENGTH);
	}
	
	public static String createText(int length) {
		char[] code = new char[length];
		for(int i=0; i< length; i++) {
			code[i] = chars.charAt(RANDOM.nextInt(chars.length()));
		}
		return new String(code);
	}
	
	public static BufferedImage createImage(String text) {
		
		BufferedImage image = new BufferedImage(WIDTH , HEIGHT , BufferedImage.TYPE_INT_RGB);
		
		//创建画笔
		Graphics2D pen = image.createGraphics();
		//开始画图
		drawImage(pen, text);
		return image;
	}
	
	/**
	 * 画图片
	 */
	private static void drawImage(Graphics pen, String text) {
		drawBackground(pen);
		drawLine(pen , 2);
		drawOval(pen, 10);
		drawCode(pen, text);
	}
	
	private static void drawBackground(Graphics pen) {
		pen.setColor(new Color(240,240,240));
		pen.fillRect(0, 0, WIDTH	, HEIGHT);
	}
	
	/**
	 * 画干扰线
	 */
	private static void drawLine(Graphics pen, int count) {
		Color color ;
		for(int i=1; i<= count; i++) {
			color = randomColor();
			pen.setColor(color);
			pen.drawLine(0, randomNum(HEIGHT), WIDTH, randomNum(HEIGHT));
			color = null;
		}
	}
	
	/**
	 * 画干扰圈
	 * @param pen	画笔
	 * @param count 	画多少个
	 */
	private static void drawOval(Graphics pen , int count) {
		Color color ;
		for(int i=1; i<= count; i++) {
			color = randomColor();
			pen.setColor(color);
			pen.drawOval(randomNum(WIDTH), randomNum(HEIGHT), 2+randomNum(10) , 2+randomNum(10));
			color = null;
		}
	}
	
	/**
	 * 画验证码
	 * @param pen
	 * @param code
	 */
	private static void drawCode(Graphics pen , String code) {
		
		int width = WIDTH / code.length();	//单个字符的宽度
		Color color ;
		for(int i = 0; i< code.length(); i++) {
			color = randomColor();
			
			pen.setColor(color);
			pen.setFont(randomForn());
			pen.drawString(code.substring(i, i+1), width * i + 5 , 30);
			color = null;
		}
	}
	
	private static Color randomColor() {
		//240 避免出现白色
		int red = randomNum(240);
		int green = randomNum(240);
		int blue = randomNum(240);
		return new Color(red, green, blue);
	}
	
	private static Font randomForn() {
		int style = randomNum(4);		//生成随机的样式, 0(无样式), 1(粗体), 2(斜体), 3(粗体+斜体)
		int size = randomNum(5) + 28;	//生成随机字号, 24 ~ 28
		return new Font("Verdana", style, size);
	}
	
	private static int randomNum(int num) {
		return RANDOM.nextInt(num);
	}

}
