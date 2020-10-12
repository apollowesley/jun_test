package org.neuedu.fly.web;

import org.neuedu.fly.util.Const;

import javax.imageio.ImageIO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * @description
 * @auther: CDHONG.IT
 * @date: 2019/8/18-16:22
 **/
@WebServlet("/verifyCodeImg")
public class VerifyCodeImg extends HttpServlet {
    private static final int WIDTH = 150;//设置验证码图片宽度
    private static final int HEIGHT = 50;//设置验证码图片高度
    private static final int RAND_CHAR_SIZE = 4;//设置验证码长度

    //验证码的字符库
    private static final String str = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    //通过随机数取字符库中的字符组合成4位验证码
    private static Random random = new Random();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //创建画布
        BufferedImage bufferedImage = new BufferedImage(WIDTH+10, HEIGHT+20, BufferedImage.TYPE_INT_RGB);
        //获得画笔
        Graphics2D graphics = (Graphics2D)(bufferedImage.getGraphics());
        //填充背景颜色
        graphics.fillRect(0, 0, WIDTH+10, HEIGHT+20);
        //设置画笔颜色随机生成
        graphics.setColor(getRandColor());
        //声明变量存储生成的验证码
        StringBuilder code = new StringBuilder();
        //随机生成4个字符
        for (int i = 1; i <= RAND_CHAR_SIZE; i++) {
            graphics.setFont(new Font("华文新魏", Font.BOLD, 45));  //设置字体
            graphics.setColor(getRandColor());    //随机生成颜色
            //随机生成字符索引
            String cha = String.valueOf(str.charAt(random.nextInt(str.length())));
            //将随机生成的字符写入画布
            graphics.drawString(cha,WIDTH/5*i-20, random.nextInt((HEIGHT+8)/2) + (HEIGHT+8)/2);
            code.append(cha);
        }
        System.out.println("验证码："+code);
        //把生成的验证码存放在Session中
        req.getSession().setAttribute(Const.VERIFY_CODE,code.toString());

        //画干扰线:
        graphics.setColor(getRandColor()); //随机生成画笔颜色
        graphics.setStroke(new BasicStroke(3.0f));
        //画干扰线到画布中
        graphics.drawLine(0, random.nextInt(HEIGHT), WIDTH, random.nextInt(HEIGHT));
        graphics.dispose();  //资源销毁

        //设置响应头通知浏览器以图片的形式打开
        resp.setContentType("image/jpeg");

        //设置响应头控制浏览器不要缓存
        resp.setDateHeader("expries", -1);
        resp.setHeader("Cache-Control", "no-cache");
        resp.setHeader("Pragma", "no-cache");

        //将画布写到浏览器中
        ImageIO.write(bufferedImage, "JPEG", resp.getOutputStream());
    }

    //生成随机颜色
    private Color getRandColor() {
        return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }
}
