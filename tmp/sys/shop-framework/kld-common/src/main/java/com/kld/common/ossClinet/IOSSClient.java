package com.kld.common.ossClinet;

import com.aliyun.oss.model.ObjectMetadata;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;


public interface IOSSClient {
    /**
     * 创建模拟文件夹
     *
     * @param bucketName 文件夹名称
     * @param objectName 创建文件夹名称
     * @throws IOException
     */
    public void creatFolder(String bucketName, String objectName)
            throws IOException;

    /**
     * 列出用户所有的BucketName
     *
     * @return
     */
    public List<Map<String, Object>> listBuckets() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException;

    /**
     * 获取 bucketLocation 地址
     *
     * @param bucketName
     * @return
     */
    public String getBucketLocation(String bucketName);

    /**
     * 上传本地文件
     *
     * @param bucketName Bucket名称
     * @param key        上传文件名称
     * @param filePath   上传文件地址
     */
    public void putObject(String bucketName, String key, String filePath);

    /**
     * 上传流文件
     *
     * @param bucketName  Bucket名称
     * @param key         上传文件名称
     * @param inputStream 上传流文件
     */
    public void putObject(String bucketName, String key, InputStream inputStream);

    /**
     * 上传流文件
     *
     * @param bucketName Bucket名称
     * @param key        上传文件名称
     * @param file       上传文件
     */
    public void putObject(String bucketName, String key, File file);

    /**
     * 追加上传
     *
     * @param fileToAppen
     * @param bucketName
     * @param key         object的名字
     */
    public void appendObject(String fileToAppen, String bucketName, String key);

    /**
     * 分片上传文件
     *
     * @param bucketName
     * @param key
     */
    public void multipartUpload(String bucketName, String key, String localFilePath);

    /**
     * 下载一个Object
     *
     * @param bucketName Bucket名称
     * @param key        上传文件名称包含skong/design/test.txt
     * @return 返回一个流文件
     */
    public InputStream getObject(String bucketName, String key);

    /**
     * 下载文件到本地文件夹
     *
     * @param bucketName
     * @param key
     * @param filePath
     * @return
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public ObjectMetadata getObject(String bucketName, String key, String filePath) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException;

    /**
     * 操作完毕后要关闭oss资源
     */
    public void shutdown();

}
