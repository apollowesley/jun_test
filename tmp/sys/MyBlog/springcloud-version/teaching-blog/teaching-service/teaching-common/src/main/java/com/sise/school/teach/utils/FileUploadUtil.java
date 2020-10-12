package com.sise.school.teach.utils;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.sise.idea.service.ApplicationSettingService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;

import static com.sise.school.teach.constants.ApplicationConstants.*;

/**
 * 文件上传工具
 *
 * @author idea
 * @data 2018/10/6
 */
@Configuration
@Getter
@Setter
@Slf4j
@ConfigurationProperties(prefix = "file.save")
public class FileUploadUtil {


    /**
     * 图片文件格式的类型
     */
    public final static String[] IMG_TYPE_ARR = {".bmp", ".jpg", ".png", ".jpeg", ".tiff", ".mp4", ".wmv"};

    /**
     * 视频类型
     */
    public final static String[] VIDEO_TYPE_ARR = {".mp4"};

    public final static String[] PDF_TYPE_ARR = {".pdf"};

    /**
     * 上传视频文件
     *
     * @param file
     * @return
     */
    public String uploadVideo(MultipartFile file) {
        if (isillegalFile(file)) {
            throw new RuntimeException("[文件上传]传入参数异常！");
        }
        //getname只会获取input表单里面的name,getOriginalFilename可以获取文件名称
        String fileName = file.getOriginalFilename();
        if (StringUtils.isNotBlank(fileName)) {
            String suffixName = fileName.substring(fileName.lastIndexOf("."), fileName.length());
            if (validateVideoFileType(suffixName)) {
                String aimPath = ApplicationSettingService.getValue(VIDEO_FILE_PATH_KEY);
                return uploadCoreHandle(file, suffixName, aimPath);
            }
        }
        return null;
    }

    /**
     * 上传图片文件
     *
     * @param file
     * @return
     */
    public String uploadImg(MultipartFile file) {
        if (isillegalFile(file)) {
            throw new RuntimeException("[文件上传]传入参数异常！");
        }
        //getname只会获取input表单里面的name,getOriginalFilename可以获取文件名称
        String fileName = file.getOriginalFilename();
        if (StringUtils.isNotBlank(fileName)) {
            String suffixName = fileName.substring(fileName.lastIndexOf("."), fileName.length());
            if (validateImgFileType(suffixName)) {
                String aimPath = ApplicationSettingService.getValue(IMG_FILE_PATH_KEY);
                return uploadCoreHandle(file, suffixName, aimPath);
            }
        }
        return null;
    }

    /**
     * 上传图片文件
     *
     * @param file
     * @return
     */
    public String uploadPdf(MultipartFile file) {
        if (isillegalFile(file)) {
            throw new RuntimeException("[文件上传]传入参数异常！");
        }
        //getname只会获取input表单里面的name,getOriginalFilename可以获取文件名称
        String fileName = file.getOriginalFilename();
        if (StringUtils.isNotBlank(fileName)) {
            String suffixName = fileName.substring(fileName.lastIndexOf("."), fileName.length());
            if (validatePdfFileType(suffixName)) {
                String aimPath = ApplicationSettingService.getValue(BOOK_FILE_PATH_KEY);
                return uploadCoreHandle(file, suffixName, aimPath);
            }
        }
        return null;
    }

    /**
     * 上传文件核心部分
     *
     * @param suffixName
     * @return
     */
    private String uploadCoreHandle(MultipartFile file, String suffixName, String aimPath) {
        long currentTime = System.currentTimeMillis();
        String newName = currentTime + suffixName;
        File dest = new File(aimPath + currentTime + suffixName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            log.info("[文件上传]上传地址" + dest);
            return newName;
        } catch (Exception e) {
            log.error("[文件上传]上传文件过程出现异常，异常为{}" + e);
        }
        return null;
    }

    /**
     * 检测文件基本属性
     *
     * @param file
     * @return
     */
    public boolean isillegalFile(MultipartFile file) {
        if (file == null) {
            log.error("[文件上传]传入参数为null！");
            return true;
        }
        if (file.isEmpty()) {
            log.error("[文件上传]上传文件不得为空！");
            return true;
        }
        return false;
    }

    public boolean validateImgFileType(String type) {
        return Arrays.asList(IMG_TYPE_ARR).contains(type.toLowerCase());
    }

    public boolean validatePdfFileType(String type) {
        return Arrays.asList(PDF_TYPE_ARR).contains(type.toLowerCase());
    }

    public boolean validateVideoFileType(String type) {
        return Arrays.asList(VIDEO_TYPE_ARR).contains(type.toLowerCase());
    }
}
