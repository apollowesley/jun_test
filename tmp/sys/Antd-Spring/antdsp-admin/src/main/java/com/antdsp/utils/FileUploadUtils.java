package com.antdsp.utils;

import org.springframework.beans.factory.annotation.Value;

import com.qiniu.common.Zone;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;

public class FileUploadUtils {
	
	@Value("${qiniu.accessKey}")
	private static String accessKey ;
	@Value("${qiniu.secretKey}")
	private static String secretKey;
	@Value("${qiniu.bucket}")
	private static String bucket;
	
	private static String key;
	
	
	
	private final static Configuration cfg = new Configuration(Zone.zone0());
	
	public static String uploadImage() {
		
		UploadManager uploadManager = new UploadManager(cfg);
		
		System.out.println(FileUploadUtils.accessKey);
		
		return null;
	}

}
