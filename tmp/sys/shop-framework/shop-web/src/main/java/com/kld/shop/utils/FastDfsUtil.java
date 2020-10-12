package com.kld.shop.utils;

import com.kld.common.util.ExcelUtil;
import com.kld.common.util.StringUtil;
import org.csource.upload.UploadFileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by anpushang on 2016/3/17.
 * fastdfs 文件上传工具类
 */
public class FastDfsUtil{

    private static final Logger logger = LoggerFactory.getLogger(FastDfsUtil.class);

    /***
     * 批量上传 ,返回上传之后的url集合
     * @param request
     * @return
     */
    public static List<String> fastUploadFileArray(HttpServletRequest request){

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        List<String> imageList = new ArrayList<String>();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            try{
                MultipartFile multipartFile = entity.getValue();
                String fileName = multipartFile.getOriginalFilename();
                String fileSuffix = StringUtil.getFileExt2(fileName);
                String myFileUrl = UploadFileUtil.uploadFile(multipartFile.getInputStream(),fileSuffix,fileName);
                imageList.add(myFileUrl);
                logger.info("图片上传成功..."+myFileUrl);
            }catch (Exception e){
                logger.error("图片上传失败",e.getMessage());
                e.printStackTrace();
            }
        }
        return imageList;
    }

    /***
     * 富文本html文件代码上传到服务器
     * @param ueditorHtml
     * @return
     */
    public static String fastUploadFile(String ueditorHtml){
        byte[] ueditorHtmlBytes = ueditorHtml.getBytes();
        String resultUrl = null;
        try {
            ByteArrayInputStream stream = new ByteArrayInputStream(ueditorHtmlBytes);
            resultUrl = UploadFileUtil.uploadFile(stream, null, null);
            logger.info("图文详情上传成功！！！"+resultUrl);
        } catch (Exception e) {
            logger.error("图文详情上传失败！！！" + e.getMessage(), e);
            e.printStackTrace();
        }
        return resultUrl;
    }

    /***
     * 单张图片上传
     * @param multipartFile
     * @return
     */
    public static String fastUploadFile(MultipartFile multipartFile){
        String myFileUrl = null;
        try{
            if (multipartFile != null && multipartFile.getSize() > 0){
                String fileName = multipartFile.getOriginalFilename();
                String fileSuffix = StringUtil.getFileExt2(fileName);
                myFileUrl = UploadFileUtil.uploadFile(multipartFile.getInputStream(),fileSuffix,fileName);
                logger.info("图片上传成功..."+myFileUrl);
            }
        }catch (Exception e){
            logger.error("图片上传失败",e.getMessage());
            e.printStackTrace();
        }
        return myFileUrl;
    }

}
