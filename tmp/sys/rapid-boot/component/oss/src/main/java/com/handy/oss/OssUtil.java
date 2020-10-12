package com.handy.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 * @author handy
 * @Description: {阿里云oss服务}
 * @date 2019/8/27 20:48
 */
@Repository("OssUtil")
public class OssUtil {
    @Value("${oss.endpoint}")
    private String endpoint;
    @Value("${oss.accessKeyId}")
    private String accessKeyId;
    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;
    @Value("${oss.bucketName}")
    private String bucketName;

    public static void main(String[] args) {
        OssUtil ossUtil = new OssUtil();
        String s = ossUtil.putObject("https://static.lsbj365.com/resource/template/受理通知书.docx", "网络文件.docx");
        System.out.println(s);
    }

    public Boolean createBucket() {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        if (ossClient.doesBucketExist(bucketName)) {
            ossClient.shutdown();
            return false;
        } else {
            ossClient.createBucket(bucketName);
            ossClient.shutdown();
            return true;
        }
    }

    /**
     * 上传流并返回文件路径
     *
     * @param inputStream
     * @param fileName
     */
    public String putObject(InputStream inputStream, String fileName) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName, fileName, inputStream);
        String url = getUrl(ossClient, fileName);
        ossClient.shutdown();
        return url;
    }

    /**
     * 上传bytes
     *
     * @param bytes
     * @param fileName
     */
    public String putObject(byte[] bytes, String fileName) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ossClient.putObject(bucketName, fileName, new ByteArrayInputStream(bytes));
        String url = getUrl(ossClient, fileName);
        ossClient.shutdown();
        return url;
    }

    /**
     * 上传网络文件
     *
     * @param url
     * @param fileName
     */
    public String putObject(String url, String fileName) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try {
            ossClient.putObject(bucketName, fileName, new URL(url).openStream());
            String urlStr = getUrl(ossClient, fileName);
            ossClient.shutdown();
            return urlStr;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }
        return null;
    }

    /**
     * 普通下载
     *
     * @param fileName
     * @return
     */
    public InputStream download(String fileName) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        OSSObject ossObject = ossClient.getObject(bucketName, fileName);
        ossClient.shutdown();
        return ossObject.getObjectContent();
    }

    /**
     * 获得url链接
     *
     * @param key
     * @return
     */
    public String getUrl(OSS ossClient, String key) {
        if (ossClient == null) {
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        }
        // 设置URL过期时间为10年 3600l* 1000*24*365*10
        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
        // 生成URL
        URL url = ossClient.generatePresignedUrl(bucketName, key, expiration);
        if (url != null) {
            return url.toString();
        }
        return null;
    }
}
