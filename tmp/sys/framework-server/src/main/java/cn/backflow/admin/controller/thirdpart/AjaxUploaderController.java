package cn.backflow.admin.controller.thirdpart;

import cn.backflow.admin.controller.base.BaseSpringController;
import cn.backflow.lib.thirdpart.QiniuUtil;
import cn.backflow.lib.thirdpart.ZXingUtil;
import cn.backflow.lib.util.FileUtil;
import cn.backflow.lib.util.JsonMap;
import cn.backflow.lib.util.StringUtil;
import com.qiniu.http.Response;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.io.*;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 为 simple-ajax-uploader.js 与 jquery.form.js 定制的请求处理控制器
 * Create by backflow on 2015-12-31
 */
@RestController
@RequestMapping("img")
public class AjaxUploaderController extends BaseSpringController {

    private Logger logger = LoggerFactory.getLogger(AjaxUploaderController.class);

    /**
     * 文件上传
     *
     * @param type    上传类型
     * @param request HttpServletRequest
     * @return JSON响应数据
     */
    @RequestMapping("{type}_upload")
    public Object upload(@PathVariable("type") String type, HttpServletRequest request) throws Exception {
        int contentLength = request.getContentLength();

        return doAjaxUpload(request, contentLength); // html5 ajax 上传

        // doIframeUpload(request, contentLength, "upload"); // iframe form 上传
    }

    private Object doAjaxUpload(HttpServletRequest request, int contentLength) throws Exception {
        MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
        MultipartFile file = req.getFile("file");

        Response res = QiniuUtil.upload(file.getBytes(), file.getOriginalFilename());

        JsonMap json = JsonMap.succeed();
        if (!res.isOK()) {
            json.success(false);
            json.msg(res.error);
            return json;
        }
        json.put("url", res.url());
        json.put("size", contentLength);
        return json;
    }

    private Object doIframeUpload(HttpServletRequest request, int contentLength, String storepath) throws Exception {
        String progresskey = request.getParameter("APC_UPLOAD_PROGRESS");
        Part part = request.getPart("file");
        String filename = FileUtil.newFilename(getFileName(part), StringUtil.shorten());
        File file = new File(storepath, filename);
        FileUtils.touch(file);

        BufferedOutputStream bof = new BufferedOutputStream(new FileOutputStream(file));
        InputStream inputStream = part.getInputStream();
        int len, step = 0, current = 0;
        float size = contentLength / 1024;
        byte[] data = new byte[1024];
        while (-1 != (len = inputStream.read(data))) {
            current += len;
            if (++step % 50 == 0) { // 每上传50Kb更新一次进度
                float[] status = new float[]{contentLength, current, size};
                request.getSession().setAttribute("upload_" + progresskey, status);
                logger.debug("upload thread =>" + Arrays.toString(status));
            }
            bof.write(data, 0, len);
        }
        bof.close();
        inputStream.close();

        JsonMap json = new JsonMap();
        json.put("success", true);
        json.put("url", storepath + filename);
        json.put("size", contentLength);
        return json;
    }

    private String getFileName(Part part) {
        String h = part.getHeader("content-disposition");
        return h.substring(h.lastIndexOf("=") + 2, h.length() - 1);
    }

    @RequestMapping("upload_progress")
    public Object uploadProgress(HttpSession session,
                                 @RequestParam(value = "getkey", required = false) String getkey,
                                 @RequestParam(value = "progresskey", required = false) String progresskey) throws Exception {
        Map<String, Object> json = new HashMap<>();
        if (StringUtil.isNotBlank(getkey)) {
            json.put("key", System.currentTimeMillis());
            json.put("success", true);
        } else if (StringUtil.isNotBlank(progresskey)) {
            float total = 0f, curr = 0f, size = 0f;
            float[] status = (float[]) session.getAttribute("upload_" + progresskey);
            if (status != null) {
                total = status[0];
                curr = status[1];
                size = status[2];
            }
            int percent = total > 0 ? Math.round(curr / total * 100) : 0;
            json.put("pct", percent);
            json.put("size", size);
            json.put("success", true);
            logger.debug("progress thread =>" + json);
        }
        return json;
    }

    @RequestMapping("reactor_img_upload")
    public Object imageUpload(MultipartHttpServletRequest request) {
        JsonMap json = new JsonMap();
        MultipartFile img = request.getFile("file");
        String type = img.getContentType();
        if (!type.startsWith("image")) return json.put("error", "文件格式不正确, 请上传gif|jpg|jpeg|png格式的图片");

        String realPath = request.getServletContext().getRealPath("upload");
        try {
            File file = FileUtil.touch(realPath, img.getOriginalFilename(), StringUtil.shorten());
            img.transferTo(file);
            json.put("filelink", "/upload" + file.getName());
        } catch (IOException e) {
            json.put("error", e.getMessage());
            e.printStackTrace();
        }
        return json;
    }

    @RequestMapping("qrcode")
    public void qrcode(@RequestParam("content") String content, HttpServletResponse response) throws Exception {

        int cacheAge = 60;
        response.setDateHeader("Expires", new Date().getTime() + cacheAge * 1000);
        response.setContentType("image/png");
        response.setHeader("Content-disposition", "attachment; filename=" + URLDecoder.decode(content + ".png", "ISO8859_1"));
        response.setHeader("Cache-Control", "max-age=" + cacheAge);

        logger.info("generate qrcode for: {}", content);
        ZXingUtil.encodeQRCode(content, response.getOutputStream());
    }
}