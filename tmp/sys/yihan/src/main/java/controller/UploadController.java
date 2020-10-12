package controller;

import mapper.ImgMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import pojo.Img;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class UploadController {
    //    @Autowired
//    private UserService userService;
    @Autowired
    private ImgMapper imgMapper;
    @Value("${local.src}")
    private String path;

    @ResponseBody//将即将返回的对象转成json字符串,再返回到浏览器
    @RequestMapping("/upload")
    public Map<String, Object> update(MultipartFile file) {//保存文件到本地路径
        String randomName = UUID.randomUUID().toString();//绝对不会重复的随机数
        String oldName = file.getOriginalFilename();//原始文件名
        System.out.println(oldName);
        String ext = oldName.substring(oldName.lastIndexOf("."));//从最后一个'.'开始截取扩展名
        File longFile = new File(path + randomName + ext);//路径+随机数+扩展名
        String newName = randomName + ext;//新文件名
        System.out.println(newName);
        Map<String, Object> map = new HashMap();
        try {
            file.transferTo(longFile);//保存文件
            map.put("success", true);
            map.put("src", newName);
            map.put("msg", "文件上传成功!");
        } catch (IOException e) {
            map.put("success", false);
            map.put("msg", "文件上传失败,请重试!");
        }
        System.out.println(longFile);
//        try {
//            BufferedImage sourceImg = ImageIO.read(new FileInputStream(longFile));
//            Img img = new Img(newName, sourceImg.getWidth(), sourceImg.getHeight());
//            System.out.println(img);
//            imgMapper.addImg(img);
//        } catch (IOException e) {
//        }

        return map;
    }
}