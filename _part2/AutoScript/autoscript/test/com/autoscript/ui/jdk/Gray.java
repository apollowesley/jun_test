package com.autoscript.ui.jdk;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Gray {
	
	public static void main(String[] args) throws IOException {
		BufferedImage transforImage;
		BufferedImage finalImage;
		TransGray gray = new TransGray();
		transforImage = gray.srcImage;
		finalImage = gray.transformGrayJ2D(transforImage, null);// 这里可能有问题,关于RenderingHints的取值不是很清楚
		File f1 = new File("d:/openfolder1.jpg");// 新图像输出
		ImageIO.write(finalImage, "jpg", f1);
		System.out.println("End");
	}
}

class TransGray {
	int width;
	int height;
	BufferedImage srcImage;

	public TransGray() throws IOException {
		File file = new File("d:/openfolder.jpg");
		srcImage = ImageIO.read(file);
		width = srcImage.getWidth();
		height = srcImage.getHeight();
	}

	public BufferedImage transformGrayJ2D(BufferedImage srcImage,
			RenderingHints hints) {
		BufferedImage dstImage = new BufferedImage(srcImage.getWidth(),
				srcImage.getHeight(), srcImage.getType());
		if (hints == null) {
			Graphics2D g2 = dstImage.createGraphics();
			hints = g2.getRenderingHints();
			g2.dispose();
			g2 = null;
		}
		ColorSpace grayCS = ColorSpace.getInstance(ColorSpace.CS_GRAY);// 此抽象类用做一个颜色空间标记,标识
																		// Color
																		// 对象的特定颜色空间,或者通过
																		// ColorModel
																		// 对象标识
																		// Image、BufferedImage
																		// 或
																		// GraphicsDevice
																		// 的特定颜色空间。此类包含了可将指定颜色空间中的颜色与
																		// sRGB
																		// 和定义良好的
																		// CIEXYZ
																		// 颜色空间中的颜色进行相互转换的方法。
		ColorConvertOp colorConvertOp = new ColorConvertOp(grayCS, hints);// 此类对源图像中的数据执行逐像素的颜色转换。得到的颜色值可以扩展到目标图像的精度。颜色转换可以通过
																			// ColorSpace
																			// 对象的数组或
																			// ICC_Profile
																			// 对象的数组指定。
		colorConvertOp.filter(srcImage, dstImage);// 对源 BufferedImage 进行颜色转换
		return dstImage;
	}

}