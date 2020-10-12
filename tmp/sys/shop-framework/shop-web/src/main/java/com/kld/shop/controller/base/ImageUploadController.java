package com.kld.shop.controller.base;

import com.baidu.ueditor.ActionEnter;
import com.kld.common.util.StringUtil;
import com.kld.shop.allocation.Constant;
import org.csource.upload.UploadFileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by anpushang on 2016/3/17.
 * 上传公共controller
 */
@Controller
public class ImageUploadController {

    private static final Logger logger = LoggerFactory.getLogger(ImageUploadController.class);


    @RequestMapping("/plugins/ueditor/jsp/imageUp")
    public void uploadImg(HttpServletRequest request,HttpServletResponse response, String action) {

        if (action.equals("uploadimage")) {
            MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
            // 获取file框的
            Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
            // 遍历获取的所有文件
            List<String> picUrlS = new ArrayList<String>();
            for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
                try {
                    MultipartFile mf = entity.getValue();
                    // 判断文件名是否为空。为空set null值
                    String myfileUrl = UploadFileUtil.uploadFile(
                            mf.getInputStream(),
                            StringUtil.getFileExt2(mf.getOriginalFilename()), null);
                    if (null != myfileUrl) {
                        String newUrl = Constant.IMAGE_URL01 + myfileUrl;
                        picUrlS.add(Constant.IMAGE_URL01 + myfileUrl);
                        String status = "{\"state\": \"SUCCESS\",\"title\": \"\",\"original\":\"" + mf.getOriginalFilename() + "\",\"type\": \"" + StringUtil.getFileExt2(myfileUrl) + "\",\"url\":\"" + newUrl
                                + "\",\"size\": \"" + mf.getSize() + "\"}";
                        response.getWriter().print(status);
                        logger.info("uedtior上传图片，图片图片服务器返回 ：" + myfileUrl + ";原始文件名：" + mf.getOriginalFilename());
                    } else {
                        response.getWriter().print("{\"state\": \"Server is Error!\"}");
                    }
                } catch (Exception e) {
                    try {
                        response.getWriter().print("{\"state\": \"Server is Error!\"}");
                    } catch (IOException e1) {
                        logger.error("富文本编辑器上传图片失败！！！" + e.getMessage(), e);
                    }
                    logger.error("富文本编辑器上传图片失败！！！" + e.getMessage(), e);
                }
            }
        } else if (action.equals("catchimage")) {
            String[] source = request.getParameterValues("linkUp[]");
            StringBuffer status = new StringBuffer();
            status.append("{\"state\": \"SUCCESS\", list: [");
            for (int i = 0; i < source.length; i++) {
                try {
                    URL url = new URL(source[i]);
                    HttpURLConnection uc = (HttpURLConnection) url.openConnection();
                    uc.setDoInput(true);//设置是否要从 URL 连接读取数据,默认为true
                    uc.connect();
                    // 判断文件名是否为空。为空set null值
                    String myfileUrl = UploadFileUtil.uploadFile(uc.getInputStream(),
                            StringUtil.getFileExt2(source[i]), null);
                    String newUrl = Constant.IMAGE_URL01 + myfileUrl;

                    if (i == source.length - 1) {
                        status.append("{\"state\": \"SUCCESS\",\"title\": \"\",\"source\":\"" + source[i] + "\",\"type\": \"" + StringUtil.getFileExt2(myfileUrl) + "\",\"url\":\"" + newUrl
                                + "\",\"size\": \"" + uc.getContentLength() + "\"}]}");
                    } else {
                        status.append("{\"state\": \"SUCCESS\",\"title\": \"\",\"source\":\"" + source[i] + "\",\"type\": \"" + StringUtil.getFileExt2(myfileUrl) + "\",\"url\":\"" + newUrl
                                + "\",\"size\": \"" + uc.getContentLength() + "\"},");
                    }
                } catch (Exception e) {
                    logger.error("文件上传失败！" + e.getMessage(), e);
                }
            }
            try {
                response.getWriter().print(status);
            } catch (IOException e) {
                logger.error("输出失败！" + e.getMessage(), e);
            }
        } else if (action.equals("config")) {
            try {
                request.setCharacterEncoding("utf-8");
                response.setHeader("Content-Type", "text/html");
                request.setCharacterEncoding("utf-8");
                response.setHeader("Content-Type", "text/html");
                String rootPath = request.getSession().getServletContext().getRealPath("/");
                response.getWriter().write(new ActionEnter(request, rootPath).exec());
            } catch (IOException e) {

                logger.error("富文本编辑器！！！" + e.getMessage(), e);
            }
        }
    }


    /**
     * 上传单张图片
     * @param request
     * @param response
     */
    @RequestMapping("/item/imageUp")
    public void uploadImages(HttpServletRequest request,HttpServletResponse response) {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // 获取file框的
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            try {
                MultipartFile mf = entity.getValue();
                // 判断文件名是否为空。为空set null值
                String myfileUrl = UploadFileUtil.uploadFile(
                        mf.getInputStream(),
                        StringUtil.getFileExt2(mf.getOriginalFilename()), null);
                String newUrl = Constant.IMAGE_URL01 + myfileUrl;
                if (null != myfileUrl) {

                    logger.info("图片上传成功ImageUrl：" + newUrl);
                    response.getWriter().write(
                            "{\"success\":\"true\"," + "\"data\":{\"BaseUrl\":\""
                                    + myfileUrl + "\",\"Url\":\"" + newUrl
                                    + "\",\"filename\":\""
                                    + mf.getOriginalFilename() + "\"}}");
                } else {
                    logger.error("图片服务器上传图片失败！！");
                    response.getWriter().write(
                            "{\"success\":\"flase\"," + "\"data\":{\"BaseUrl\":\"\",\"Url\":\"\",\"filename\":\""
                                    + mf.getOriginalFilename() + "\"}}");

                }
            } catch (Exception e) {
                try {
                    response.getWriter().write(
                            "{\"success\":\"false\","
                                    + "\"data\":{\"BaseUrl\":\"" + ""
                                    + "\",\"Url\":\"" + ""
                                    + "\",\"filename\":\"" + "" + "\"}}");
                } catch (IOException e1) {
                    logger.error("展示属性图片上传失败！！！" + e1.getMessage(), e1);
                }
                logger.error("展示属性图片上传失败！！！" + e.getMessage(), e);
            }
        }
    }

}
