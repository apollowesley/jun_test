package com.szfore.util.basic;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtil {
	
	/**
	 * 读取图片的高度
	 * @param imagePath
	 * @return
	 */
	public static int getImageHeight(String imagePath)
	{
		int height = 0;
		try {
			BufferedImage bgImg = ImageIO.read(new File(imagePath));
			height = bgImg.getHeight();
			bgImg.flush();
			return height;
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		System.out.println("ImageUtil: 输入的图片路径不存在。");
		return 0;
	}
	
	/**
	 * 读取图片的宽度
	 * @param imagePath
	 * @return
	 */
	public static int getImageWidth(String imagePath)
	{
		int width = 0;
		try {
			BufferedImage bgImg = ImageIO.read(new File(imagePath));
			width = bgImg.getWidth();
			bgImg.flush();
			return width;
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		System.out.println("ImageUtil: 输入的图片路径不存在。");
		return 0;
	}
	
	public static void writeBufferedImageToFile(BufferedImage bufferedImage, String targetImgUrl) 
	{
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		
		try{
			fos = new FileOutputStream(targetImgUrl);
			bos = new BufferedOutputStream(fos);
			ImageIO.write(bufferedImage, "jpg", bos);
			bufferedImage.flush();
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			try {
				bos.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
