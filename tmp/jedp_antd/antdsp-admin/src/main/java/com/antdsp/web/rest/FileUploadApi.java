package com.antdsp.web.rest;

import java.io.IOException;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.antdsp.common.AntdspResponse;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

/**
 * 文件上传
 * <p>title:FileUploadApi</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2019</p>
 *
 * @author lijiantao
 * @date 2019年6月24日
 * @email a496401006@qq.com
 *
 */
@RestController
@RequestMapping("/fileupload")
public class FileUploadApi {
	
	private final String GROUP = "public";
	
	@Value("${qiniu.accessKey}")
	private String accessKey;
	
	@Value("${qiniu.secretKey}")
	private String secretKey;
	
	@Value("${qiniu.bucket}")
	private String bucket;
	
	private Auth auth ;
	
	private String upToken;
	
	private UploadManager uploadManager;
	
	@PostConstruct
	private void init() {
		
		uploadManager = new UploadManager(new Configuration(Zone.zone0()));
		auth = Auth.create(accessKey, secretKey);
		upToken = auth.uploadToken(bucket);
	}

	/**
	 * 上传文件
	 * @param group
	 * @param filename
	 * @return
	 */
	@PostMapping("/{group}")
	public AntdspResponse upload(@PathVariable("group") String group, @RequestParam("file") MultipartFile file) {
	
		if(StringUtils.isEmpty(group)) {
			group = GROUP;
		}
		if(file == null || file.getSize() <= 0) {
			return AntdspResponse.error("未找到上传文件，请重试！");
		}
		
		try {

			String key = fileName(group , file.getOriginalFilename());
			Response response = uploadManager.put(file.getBytes(), key, upToken);
			if(response.isOK()) {
				return AntdspResponse.success(key);
			}else {
				return AntdspResponse.error("上传文件失败，请重试或联系管理员！");
			}
			
		}catch(IOException e) {
			return AntdspResponse.error("上传文件异常，请联系管理员！");
		}
		
	}
	
	private String fileName(String group , String originalFilename) {
		
		String fileSuffix = originalFilename.substring(originalFilename.lastIndexOf("."), originalFilename.length());
		String newFileName = UUID.randomUUID().toString().replaceAll("-", "") + fileSuffix;
		return group +"/"+ newFileName;
	}
	
}
