package com.handy.controller.entry;

import cn.hutool.core.date.DateUtil;
import com.handy.common.vo.ResultVO;
import com.handy.oss.OssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author handy
 * @Description: {文件模块}
 * @date 2019/8/28 8:58
 */
@RestController
@RequestMapping("/api/entry/file")
@Api(value = "文件模块")
public class FileApiController {
    @Resource(name = "OssUtil")
    private OssUtil ossUtil;

    @PostMapping("/upload")
    @ApiOperation(value = "上传文件并返回对应url")
    public ResultVO upload(HttpServletRequest request) {
        //将request变成多部分request
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
        //获取multiRequest 中所有的文件名
        Iterator iter = multiRequest.getFileNames();
        List<String> urls = new ArrayList<>();
        while (iter.hasNext()) {
            //一次遍历所有文件
            MultipartFile file = multiRequest.getFile(iter.next().toString());
            if (file != null) {
                try {
                    String format = DateUtil.format(new Date(), "yyyyMMddHHmmss");
                    String year = DateUtil.format(new Date(), "yyyyMM");
                    // 获取源文件名称
                    String originalFilename = file.getOriginalFilename();
                    // 文件名如果包含以下内容的话:法院接收会出错导致上传法院失败
                    String fileType = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
                    String fileName = year + "/" + fileType + "/" + format + "." + fileType;
                    String url = ossUtil.putObject(file.getBytes(), fileName);
                    urls.add(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return ResultVO.success("上传完成", urls);
    }
}
