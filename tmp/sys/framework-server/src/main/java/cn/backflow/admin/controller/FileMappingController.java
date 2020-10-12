package cn.backflow.admin.controller;

import cn.backflow.admin.common.pagination.PageRequest;
import cn.backflow.admin.controller.base.BaseSpringController;
import cn.backflow.admin.entity.FileMapping;
import cn.backflow.admin.entity.User;
import cn.backflow.admin.service.FileMappingService;
import cn.backflow.lib.thirdpart.QiniuUtil;
import cn.backflow.lib.util.JsonMap;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.StringMap;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.QueryParam;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("filemapping")
public class FileMappingController extends BaseSpringController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileMappingController.class);

    private static final String DEFAULT_SORT_COLUMNS = "folder desc"; // 默认文件夹在最前

    private final FileMappingService fileMappingService;

    @Autowired
    public FileMappingController(FileMappingService fileMappingService) {this.fileMappingService = fileMappingService;}

    /* 列表 */
    @RequestMapping
    public Object index(HttpServletRequest request) throws Exception {
        PageRequest pr = pageRequest(request, DEFAULT_SORT_COLUMNS);

        // 默认查询根目录下的文件
        if (!pr.getFilters().containsKey("parent")) {
            pr.addFilter("parent", 0);
        }
        String sort = pr.getFilter("sort");
        String asc = pr.getFilter("asc");
        pr.setSortColumns(sort + ("true".equals(asc) ? "" : " desc"));

        return fileMappingService.findPage(pr);
    }

    // @Permissions("filemapping.view")
    @RequestMapping("{id}")
    public Object get(@PathVariable Integer id) throws Exception {
        return fileMappingService.getById(id);
    }

    /* 获取指定ID文件的所有父文件(夹),包含该文件(夹)本身 */
    @RequestMapping("parents")
    public Object parents(@QueryParam("parent") Integer parent) throws Exception {
        List<FileMapping> mappings = fileMappingService.findParentsById(parent);
        return mappings.stream().map(m -> {
            HashMap<String, Object> map = new HashMap<>(2);
            map.put("name", m.getName());
            map.put("id", m.getId());
            return map;
        }).collect(Collectors.toList());
    }

    // 上传文件, 文件名为 file
    @RequestMapping("upload")
    // @Permissions("filemapping.upload")
    public Object upload(MultipartHttpServletRequest request) {
        JsonMap json = JsonMap.succeed();
        MultipartFile file = request.getFile("file");
        FileMapping mapping;
        try {
            Response res = QiniuUtil.upload(file.getInputStream(), file.getOriginalFilename());
            LOGGER.info(res.bodyString());
            if (!res.isOK()) {
                return json.success(false).msg(file.getOriginalFilename() + " 上传失败!");
            }
            mapping = fromFileAndQiniuResponse(file, res, new Date());
        } catch (Exception e) {
            e.printStackTrace();
            return json.success(false).msg(e.getMessage());
        }
        fileMappingService.save(mapping);
        return json.put("file", mapping);
    }

    @RequestMapping("uptoken")
    public Object uptoken() {
        return JsonMap.succeed().put("uptoken", QiniuUtil.uptoken());
    }

    // 批量上传 (一次多个文件, 文件名为 files)
    @RequestMapping("uploads")
    // @Permissions("filemapping.upload")
    public Object uploads(MultipartHttpServletRequest request) {
        JsonMap json = JsonMap.succeed();

        List<MultipartFile> filelist = request.getFiles("files");
        List<FileMapping> mappings = new ArrayList<>();
        List<String> failed = new ArrayList<>();
        Date now = new Date();
        try {
            for (MultipartFile file : filelist) {
                Response res = QiniuUtil.upload(file.getBytes(), file.getOriginalFilename());
                LOGGER.info(res.toString());
                if (!res.isOK()) {
                    failed.add(file.getOriginalFilename());
                    continue;
                }
                mappings.add(fromFileAndQiniuResponse(file, res, now));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return json.success(false).msg(e.getMessage());
        }
        fileMappingService.save(mappings);
        if (!failed.isEmpty()) {
            json.put("failed", failed);
        }
        return json.put("files", mappings);
    }

    /* 保存或重命名文件 */
    // @Permissions("filemapping.add")
    @RequestMapping(method = RequestMethod.POST)
    public Object save(@Valid FileMapping mapping, BindingResult errors, HttpServletRequest request) throws Exception {
        JsonMap json = JsonMap.succeed();
        if (errors.hasErrors()) {
            return filedErrors(errors, json);
        }

        // 创建文件时, 补充缺失的属性
        if (mapping.getFolder() == 0) {
            if (mapping.getUrl() == null) {
                if (mapping.getKey() == null) {
                    return json.success(false).msg("文件地址(url)不能为空!");
                }
                mapping.setUrl(QiniuUtil.buildUrl(mapping.getKey()));
            }
            if (mapping.getExt() == null) {
                mapping.setExt(FilenameUtils.getExtension(mapping.getName()));
            }
        }
        User user = getCurrentUser(request);
        Date now = new Date();
        mapping.setCreator(user.getId());
        mapping.setUploaded(now);
        mapping.setUpdated(now);
        fileMappingService.saveOrUpdate(mapping);
        return json.put("file", mapping);
    }

    /* 更新文件 */
    // @Permissions("filemapping.edit")
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Object update(@PathVariable Integer id, @Valid FileMapping mapping, BindingResult errors, HttpServletRequest request) throws Exception {
        JsonMap json = JsonMap.succeed();
        if (errors.hasErrors()) {
            return filedErrors(errors, json);
        }
        fileMappingService.update(mapping);
        return json.put("file", mapping);
    }

    /* 删除 */
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public Object delete(@PathVariable Integer id) {
        fileMappingService.deleteById(id);
        return JsonMap.succeed();
    }

    /* 批量删除 */
    @RequestMapping(method = RequestMethod.DELETE)
    public Object delete(@RequestParam("items[]") Set<Integer> items, HttpServletRequest request) {
        fileMappingService.deleteBatch(items);
        return JsonMap.succeed();
    }

    /* 按key批量删除 */
    @RequestMapping(value = "keys", method = RequestMethod.DELETE)
    public Object delete(@RequestParam("items[]") String[] items) throws Exception {
        fileMappingService.deleteByKeys(items);
        return JsonMap.succeed();
    }

    public static FileMapping fromFileAndQiniuResponse(MultipartFile file, Response res, Date time) throws QiniuException {
        FileMapping mapping = new FileMapping();
        if (res.isJson()) {
            StringMap map = res.jsonToMap();
            mapping.setKey(map.get("key").toString());
            mapping.setHash(map.get("hash").toString());
        }
        mapping.setUrl(QiniuUtil.buildUrl(mapping.getKey()));
        mapping.setSize(file.getSize());
        mapping.setName(file.getOriginalFilename());
        mapping.setExt(FilenameUtils.getExtension(mapping.getName()));
        mapping.setMime(file.getContentType());
        mapping.setUploaded(time);
        mapping.setUpdated(time);
        return mapping;
    }

    public static FileMapping fromFileInfo(FileInfo info) {
        FileMapping mapping = new FileMapping();
        Date now = new Date();
        mapping.setKey(info.key);
        mapping.setHash(info.hash);
        mapping.setSize(info.fsize);
        mapping.setUrl(QiniuUtil.buildUrl(info.key));
        mapping.setName(info.key.substring(info.key.lastIndexOf("/") + 1));
        mapping.setMime(info.mimeType);
        mapping.setUploaded(new Date(info.putTime));
        mapping.setExt(FilenameUtils.getExtension(info.key));
        mapping.setUploaded(now);
        mapping.setUpdated(now);
        return mapping;
    }
}