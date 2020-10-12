package com.util.thumb;

import java.io.File;
import java.io.IOException;

import net.coobird.thumbnailator.Thumbnails;

import com.szfore.util.basic.ImageUtil;

public class Thumbnai{
	public static void generateTumbByWidth(String srcImage, String descPath, int destWidth)
	{
		int thumbWidth = calThumbWidth(srcImage, destWidth);
		try {
			Thumbnails.of(new File(srcImage))
					  .width(thumbWidth)
					  .outputQuality(0.8f)  
					  .toFile(new File(descPath));
		}catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	private static int calThumbWidth(String srcImage, int destWidth)
	{
		int srcImgWidth = ImageUtil.getImageWidth(srcImage);
		return srcImgWidth >= destWidth ? destWidth : srcImgWidth;
	}
}
