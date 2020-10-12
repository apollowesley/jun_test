package com.sise.school.teach.bussiness.file.controller;

import com.sise.school.teach.bussiness.book.service.BookService;
import com.sise.school.teach.bussiness.book.vo.req.BookReqVO;
import com.sise.school.teach.bussiness.video.service.VideoService;
import com.sise.school.teach.bussiness.video.vo.req.VideoReqVO;
import com.sise.school.teach.utils.FileUploadUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.sise.idea.service.ApplicationSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.sise.school.teach.constants.ApplicationConstants.BOOK_VISIT_URL_HEAD_KEY;
import static com.sise.school.teach.constants.ApplicationConstants.IMG_VISIT_URL_HEAD_KEY;
import static com.sise.school.teach.constants.ApplicationConstants.VIDEO_VISIT_URL_HEAD_KEY;

/**
 * @author idea
 * @data 2018/10/18
 */
@Api(description = "文件控制器")
@RestController
@RequestMapping(value = "/file")
@Slf4j
public class FileController {


    private static final String[] IMAGE_TYPE={",gif",".png",".jpg"};

    private static final String[] VIDEO_TYPE={".mp4"};

    private static final String[] PDF_TYPE={".pdf"};


    @Autowired
    private VideoService videoService;
    @Autowired
    private BookService bookService;

    @PostMapping(value = "/upload")
    public Map upload(@RequestParam("upload_file") MultipartFile file, HttpServletRequest request) {
        FileUploadUtil fileUploadUtil = new FileUploadUtil();
        String fileName = fileUploadUtil.uploadImg(file);

        Map resultMap = new HashMap<>();
        resultMap.put("error", 0);
        resultMap.put("url", ApplicationSettingService.getValue(IMG_VISIT_URL_HEAD_KEY)+fileName);
        return resultMap;
    }

    @PostMapping(value = "/uploadVideo")
    public Map uploadVideo(@RequestParam("upload_file") MultipartFile[] file, HttpServletRequest request) {
        Map resultMap = new HashMap<>();
        String videoName= request.getParameter("videoName");
        String des=request.getParameter("des");
        if(StringUtils.isBlank(videoName)|| StringUtils.isBlank(des)){
            log.error("参数异常");
            resultMap.put("error", "视频名称和描述不能为空！");
            return resultMap;
        }
        try {
            videoService.insert(buildVideoReqVO(file,request));
        } catch (Exception e) {
            log.error("上传视频出现异常，异常为{}",e);
            resultMap.put("error", "服务器未知异常");
            return resultMap;
        }
        resultMap.put("success", "上传成功");
        resultMap.put("url", ApplicationSettingService.getValue(VIDEO_VISIT_URL_HEAD_KEY)+"");
        return resultMap;
    }

    @PostMapping(value = "/uploadBook")
    public Map uploadBook(@RequestParam("upload_file") MultipartFile[] file, HttpServletRequest request) {
        Map resultMap = new HashMap<>();
        String videoName= request.getParameter("bookName");
        String des=request.getParameter("des");
        if(StringUtils.isBlank(videoName)|| StringUtils.isBlank(des)){
            log.error("参数异常");
            resultMap.put("error", "图书名称和描述不能为空！");
            return resultMap;
        }
        try {
            bookService.insert(buildBookReqVO(file,request));
        } catch (Exception e) {
            log.error("上传图书出现异常，异常为{}",e);
            resultMap.put("error", "服务器未知异常");
            return resultMap;
        }
        resultMap.put("success", "上传成功");
        resultMap.put("url", ApplicationSettingService.getValue(BOOK_VISIT_URL_HEAD_KEY)+"");
        return resultMap;
    }


    private static BookReqVO buildBookReqVO(MultipartFile[] file ,HttpServletRequest request) throws Exception {
        if(file.length==0){
            throw new Exception("上传的文件参数为空！");
        }
        String imageUrl=null;
        String pdfUrl = null;
        for (MultipartFile multipartFile : file) {
            boolean isImage=false;
            for (String imageTyoe : IMAGE_TYPE) {
                if(multipartFile.getOriginalFilename().contains(imageTyoe)){
                    FileUploadUtil fileUploadUtil = new FileUploadUtil();
                    imageUrl = fileUploadUtil.uploadImg(multipartFile);
                    isImage=true;
                    break;
                }
            }
            if(!isImage) {
                for (String pdfType : PDF_TYPE) {
                    if (multipartFile.getOriginalFilename().contains(pdfType)) {
                        FileUploadUtil fileUploadUtil = new FileUploadUtil();
                        pdfUrl = fileUploadUtil.uploadPdf(multipartFile);
                    }
                }
            }
        }
        String bookName= request.getParameter("bookName");
        String des=request.getParameter("des");
        BookReqVO bookReqVO=new BookReqVO();

        if(StringUtils.isNotBlank(imageUrl)){
            bookReqVO.setBookImg(ApplicationSettingService.getValue(IMG_VISIT_URL_HEAD_KEY)+imageUrl);
        }else{
            bookReqVO.setBookImg(StringUtils.EMPTY);
        }
        bookReqVO.setBookType(1);
        if(StringUtils.isNotBlank(pdfUrl)){
            bookReqVO.setBookUrl(ApplicationSettingService.getValue(BOOK_VISIT_URL_HEAD_KEY)+pdfUrl);
        }else{
            bookReqVO.setBookUrl(StringUtils.EMPTY);
        }
        bookReqVO.setDes(des.trim());
        bookReqVO.setBookName(bookName.trim());
        return bookReqVO;
    }


    private static VideoReqVO buildVideoReqVO(MultipartFile[] file, HttpServletRequest request) throws Exception {
        if(file.length==0){
           throw new Exception("上传的文件参数为空！");
        }
        String imageUrl=null;
        String mp4Url = null;
        for (MultipartFile multipartFile : file) {
            boolean isImage=false;
            for (String imageTyoe : IMAGE_TYPE) {
                if(multipartFile.getOriginalFilename().contains(imageTyoe)){
                    FileUploadUtil fileUploadUtil = new FileUploadUtil();
                    imageUrl = fileUploadUtil.uploadImg(multipartFile);
                    isImage=true;
                    break;
                }
            }
            if(!isImage) {
                for (String videoType : VIDEO_TYPE) {
                    if (multipartFile.getOriginalFilename().contains(videoType)) {
                        FileUploadUtil fileUploadUtil = new FileUploadUtil();
                        mp4Url = fileUploadUtil.uploadVideo(multipartFile);
                    }
                }
            }
        }
        String videoName= request.getParameter("videoName");
        String des=request.getParameter("des");
        VideoReqVO videoReqVO=new VideoReqVO();

        if(StringUtils.isNotBlank(imageUrl)){
            videoReqVO.setPicture(ApplicationSettingService.getValue(IMG_VISIT_URL_HEAD_KEY)+imageUrl);
        }else{
            videoReqVO.setPicture(StringUtils.EMPTY);
        }

        if(StringUtils.isNotBlank(mp4Url)){
            videoReqVO.setVideoUrl(ApplicationSettingService.getValue(VIDEO_VISIT_URL_HEAD_KEY)+mp4Url);
        }else{
            videoReqVO.setVideoUrl(StringUtils.EMPTY);
        }

        videoReqVO.setDes(des.trim());
        videoReqVO.setVideoName(videoName.trim());
        return videoReqVO;
    }
}
