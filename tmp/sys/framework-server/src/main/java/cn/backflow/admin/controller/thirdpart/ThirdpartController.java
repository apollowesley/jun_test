package cn.backflow.admin.controller.thirdpart;

import cn.backflow.admin.controller.FileMappingController;
import cn.backflow.admin.controller.base.BaseSpringController;
import cn.backflow.admin.entity.Dict;
import cn.backflow.admin.entity.FileMapping;
import cn.backflow.admin.entity.User;
import cn.backflow.admin.service.DictService;
import cn.backflow.admin.service.FileMappingService;
import cn.backflow.lib.thirdpart.QiniuUtil;
import cn.backflow.lib.util.JsonMap;
import com.qiniu.http.Response;
import com.qiniu.storage.model.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 第三方资源操作接口
 * Created by Nandy on 2016/7/1.
 */
@RestController
@RequestMapping("thirdpart")
public class ThirdpartController extends BaseSpringController {

    private final FileMappingService fileMappingService;
    private final DictService dictService;

    @Autowired
    public ThirdpartController(FileMappingService fileMappingService, DictService dictService) {
        this.fileMappingService = fileMappingService;
        this.dictService = dictService;
    }

    @RequestMapping(value = "filebrowser")
    public Object filebrowser(@RequestParam("action") String action, HttpServletRequest request) {
        User user = getCurrentUser(request);
        JsonMap json = JsonMap.succeed();
        try {
            switch (action) {
                case "folder":
                    List<Dict> dicts = dictService.findByCode("file_browser_folder");
                    json.put("folders", dicts);
                    break;
                case "files":
                    String folder = request.getParameter("folder");
                    json.put("files", fileMappingService.findByFolder(folder, 1, 50).getItems());
                    break;
                case "create":
                    break;
                case "upload":
                    doUpload(request, json);
                    break;
                case "move":
                    break;
                case "delete":
                    if (!user.isAdmin()) {
                        json.success(false);
                        json.msg("非管理员不能执行删除操作.");
                        break;
                    }
                    String[] keys = request.getParameterValues("keys[]");
                    fileMappingService.deleteByKeys(keys);
                    QiniuUtil.delete(keys);
                    break;
            }
        } catch (Exception e) {
            json.success(false);
            json.put("msg", e.getMessage());
        }
        return json;
    }

    private void doUpload(HttpServletRequest request, JsonMap json) throws InterruptedException, IOException {
        MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
        List<MultipartFile> filelist = req.getFiles("files");
        List<FileMapping> mappings = new ArrayList<>();
        List<String> failed = new ArrayList<>();
        Date now = new Date();
        for (MultipartFile file : filelist) {
            Response res = QiniuUtil.upload(file.getBytes(), file.getOriginalFilename());
            if (!res.isOK()) {
                failed.add(file.getOriginalFilename());
                continue;
            }
            mappings.add(FileMappingController.fromFileAndQiniuResponse(file, res, now));
        }
        fileMappingService.save(mappings);
        if (!failed.isEmpty()) {
            json.put("failed", failed);
        }
        json.put("succeed", mappings);
    }

    // {msg: 'File very big!', error: 1, images: []}
    @RequestMapping("jodit/upload")
    public Object jodit_upload(MultipartHttpServletRequest request) {
        JsonMap json = JsonMap.succeed();
        List<String> images = new ArrayList<>();
        StringBuilder str = new StringBuilder();
        try {
            for (Map.Entry<String, MultipartFile> entry : request.getFileMap().entrySet()) {
                MultipartFile file = entry.getValue();
                Response res = QiniuUtil.upload(entry.getValue().getBytes(), entry.getValue().getOriginalFilename());
                if (!res.isOK()) {
                    str.append(file.getOriginalFilename()).append("上传失败");
                    json.put("error", 1);
                } else {
                    images.add(QiniuUtil.buildUrl(res.url()));
                }
            }
        } catch (Exception e) {
            json.msg(e.getMessage());
            json.put("error", 1);
            e.printStackTrace();
        }
        json.put("images", images);
        json.put("msg", str.toString());
        return json;
    }

    @RequestMapping("editor/upload")
    public void editor_upload(MultipartHttpServletRequest request, HttpServletResponse response) throws IOException {
        String result;
        try {
            MultipartFile file = request.getFile("file");
            Response res = QiniuUtil.upload(file.getBytes(), file.getOriginalFilename());
            if (!res.isOK()) {
                result = "error|" + file.getOriginalFilename() + "上传失败";
            } else {
                result = QiniuUtil.buildUrl(res.url());
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = "error|" + e.getMessage();
        }
        response.getWriter().write(result);
    }

    private List<FileMapping> createFileMappings(FileInfo[] infos) {
        List<String> keys = new ArrayList<>();
        for (FileInfo info : infos) {
            keys.add(info.key);
        }
        return fileMappingService.findByKeys(keys);
    }
}