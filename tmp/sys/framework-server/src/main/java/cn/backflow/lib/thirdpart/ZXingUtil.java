package cn.backflow.lib.thirdpart;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * ZXing工具类
 * <p>
 * 目前仅支持二维码的生成和解析, 生成二维码时支持添加logo头像
 * --------------------------------------------------------------------------------------------------------------------
 * 介绍--用于解析多种格式条形码(EAN-13)和二维码(QRCode)的开源Java类库,其提供了多种应用的类库,如javase/jruby/cpp/csharp/android
 * --------------------------------------------------------------------------------------------------------------------
 * 经测试:用微信扫描GBK编码的中文二维码时出现乱码,用UTF-8编码时微信可正常识别
 * 并且MultiFormatWriter.encode()时若传入hints参数来指定UTF-8编码中文时,微信压根就不识别所生成的二维码
 * 所以这里使用的是这种方式new String(content.getBytes("UTF-8"), "ISO-8859-1")
 * --------------------------------------------------------------------------------------------------------------------
 * 将logo图片加入二维码中间时,需注意以下几点
 * 1)生成二维码的纠错级别建议采用最高等级H,这样可以增加二维码的正确识别能力(我测试过,不设置级别时,二维码工具无法读取生成的二维码图片)
 * 2)头像大小最好不要超过二维码本身大小的1/5,而且只能放在正中间部位,这是由于二维码本身结构造成的(你就把它理解成图片水印吧)
 * 3)在仿照腾讯微信在二维码四周增加装饰框,那么一定要在装饰框和二维码之间留出白边,这是为了二维码可被识别
 */
public abstract class ZXingUtil {

    public static void main(String[] args) throws IOException, WriterException {
        // encode("http://www.baidu.com", 280, 280);
    }

    /**
     * 为二维码图片增加logo头像
     * 其原理类似于图片加水印
     *
     * @param imagePath 二维码图片存放路径(含文件名)
     * @param logoPath  logo头像存放路径(含文件名)
     */
    private static void overlayImage(String imagePath, String logoPath) throws IOException {
        BufferedImage image = ImageIO.read(new File(imagePath));
        int logoWidth = image.getWidth() / 5;   // 设置logo图片宽度为二维码图片的五分之一
        int logoHeight = image.getHeight() / 5; // 设置logo图片高度为二维码图片的五分之一
        int logoX = (image.getWidth() - logoWidth) / 2;   // 设置logo图片的位置,这里令其居中
        int logoY = (image.getHeight() - logoHeight) / 2; // 设置logo图片的位置,这里令其居中
        Graphics2D graphics = image.createGraphics();
        graphics.drawImage(ImageIO.read(new File(logoPath)), logoX, logoY, logoWidth, logoHeight, null);
        graphics.dispose();
        ImageIO.write(image, imagePath.substring(imagePath.lastIndexOf(".") + 1), new File(imagePath));
    }


    /**
     * 生成二维码
     *
     * @param content  二维码内容
     * @param charset  编码二维码内容时采用的字符集(传null时默认采用UTF-8编码)
     * @param path     二维码图片存放路径(含扩展名)
     * @param width    生成的二维码图片宽度
     * @param height   生成的二维码图片高度
     * @param logoPath logo头像存放路径(含文件名,若不加logo则传null即可)
     * @return 生成二维码结果(true or false)
     */
    public static boolean encodeQRCodeImage(String content, String charset, String path, int width, int height, String logoPath) {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        // 指定编码格式
        // hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        // 指定纠错级别(L--7%,M--15%,Q--25%,H--30%)
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 编码内容,编码类型(这里指定为二维码),生成图片宽度,生成图片高度,设置参数
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(new String(content.getBytes(charset == null ? "UTF-8" : charset), "ISO-8859-1"), BarcodeFormat.QR_CODE, width, height, hints);
        } catch (Exception e) {
            System.err.println("编码待生成二维码图片的文本时发生异常 !");
            e.printStackTrace();
            return false;
        }
        // 生成的二维码图片默认背景为白色,前景为黑色,但是在加入logo图像后会导致logo也变为黑白色,至于是什么原因还没有仔细去读它的源码
        // 所以这里对其第一个参数黑色将ZXing默认的前景色0xFF000000稍微改了一下0xFF000001,最终效果也是白色背景黑色前景的二维码,且logo颜色保持原有不变
        MatrixToImageConfig config = new MatrixToImageConfig(0xFF000001, 0xFFFFFFFF);
        // 这里要显式指定MatrixToImageConfig,否则还会按照默认处理将logo图像也变为黑白色(如果打算加logo的话,反之则不须传MatrixToImageConfig参数)
        try {
            MatrixToImageWriter.writeToPath(bitMatrix, path.substring(path.lastIndexOf(".") + 1), Paths.get(path), config);
        } catch (IOException e) {
            System.err.println("生成二维码图片[" + path + "]时遇到异常 !");
            e.printStackTrace();
            return false;
        }
        // 此时二维码图片已经生成了,只不过没有logo头像,所以接下来根据传入的logoPath参数来决定是否加logo头像
        if (null == logoPath) {
            return true;
        }
        // 如果此时最终生成的二维码不是我们想要的,那么可以扩展MatrixToImageConfig类(反正ZXing提供了源码)
        // 扩展时可以重写其writeToFile方法,令其返回toBufferedImage()方法所生成的BufferedImage对象(尽管这种做法未必能解决为题,故需根据实际情景测试)
        // 然后替换这里overlapImage()里面的第一行BufferedImage image = ImageIO.read(new File(imagePath));
        // 即private static void overlayImage(BufferedImage image, String imagePath, String logoPath)
        try {
            // 这里不需要判断logoPath是否指向了一个具体的文件,因为这种情景下overlapImage会抛IO异常
            overlayImage(path, logoPath);
            return true;
        } catch (IOException e) {
            System.err.println("为二维码图片[" + path + "]添加logo头像[" + logoPath + "]时遇到异常!");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 解析二维码
     *
     * @param path    二维码图片存放路径(含扩展名)
     * @param charset 解码二维码内容时采用的字符集(传null时默认采用UTF-8编码)
     * @return 解析成功后返回二维码文本, 否则返回空字符串
     */
    public static String decodeQRCodeImage(String path, String charset) {
        BufferedImage image;
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
        if (null == image) {
            System.err.println("Could not decode QRCodeImage");
            return "";
        }
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
        Map<DecodeHintType, String> hints = new HashMap<>();
        hints.put(DecodeHintType.CHARACTER_SET, charset == null ? "UTF-8" : charset);
        Result result;
        try {
            result = new MultiFormatReader().decode(bitmap, hints);
            return result.getText();
        } catch (NotFoundException e) {
            System.out.println("二维码图片[" + path + "]解析失败!");
            e.printStackTrace();
            return "";
        }
    }

    public static void encodeQRCode(String content, OutputStream out) throws IOException, WriterException {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 0);
        MatrixToImageConfig config = new MatrixToImageConfig(0xFF000001, 0xFFFFFFFF);
        encodeQRCode(content, 220, 220, "png", hints, out, config);
    }

    /**
     * 生成二维码
     *
     * @param content 生成内容
     * @param width   图片宽度
     * @param height  图片高度
     * @param format  图像类型
     * @param hints   EncodeHintType
     * @param path    输出文件路径
     * @param config  MatrixToImageConfig
     */
    public static void encodeQRCode(String content, int width, int height, String format, Map<EncodeHintType, Object> hints, Path path, MatrixToImageConfig config) throws WriterException, IOException {
        BitMatrix matrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints); // 生成矩阵
        MatrixToImageWriter.writeToPath(matrix, format, path, config); // 输出图像
    }


    /**
     * 生成二维码
     *
     * @param content 生成内容
     * @param width   图片宽度
     * @param height  图片高度
     * @param format  图像类型
     * @param hints   EncodeHintType
     * @param out     OutputSteam
     * @param config  MatrixToImageConfig
     */
    public static void encodeQRCode(String content, int width, int height, String format, Map<EncodeHintType, Object> hints, OutputStream out, MatrixToImageConfig config) throws IOException, WriterException {
        BitMatrix matrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints); // 生成矩阵
        MatrixToImageWriter.writeToStream(matrix, format, out, config); // 输出图像
    }
}