package com.luoqy.speedy.util;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
/**
 *  云存储
 * */
public class Oss {
	public static void main(String[] args) {
		qiniuyun("D:\\qiniu\\test.png");
	}
    /**
     *  七牛云
     * */
    public static String qiniuyun(String fileUrl){
    	String accessKey = "wH9q8p9NhocV5ncjvRKjYtkFdlwrcg-N3WkXygQv";
    	String secretKey = "Sw42gjm81OaKU_WAuDdiqSMZ_uiTo5-64BDtXRHA";
    	String bucket = "lumberer";
    	Auth auth = Auth.create(accessKey, secretKey);
    	String upToken = auth.uploadToken(bucket);
    	
    	//构造一个带指定 Region 对象的配置类
    	Configuration cfg = new Configuration(Region.region2());
    	//...其他参数参考类注释
    	UploadManager uploadManager = new UploadManager(cfg);
    	//如果是Windows情况下，格式是 D:\\qiniu\\test.png
    	String localFilePath =fileUrl;
    	//默认不指定key的情况下，以文件内容的hash值作为文件名
    	String key = null;
    	try {
    	    Response response = uploadManager.put(localFilePath, key, upToken);
    	    //解析上传成功的结果
    	    DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
    	    System.out.println(putRet.key);
    	    System.out.println(putRet.hash);
    	    return putRet.key;
    	} catch (QiniuException ex) {
    	    Response r = ex.response;
    	    System.err.println(r.toString());
    	    
    	    try {
    	        System.err.println(r.bodyString());
    	        return r.bodyString();
    	    } catch (QiniuException ex2) {
    	        //ignore
    	    	return ex2.error();
    	    }
    	}
		
    }
    /**
     *  阿里云
     * */
    public static void aliyun(){

    }
}
