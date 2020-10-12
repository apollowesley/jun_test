package com.kld.common.ossClinet;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;

import com.kld.common.util.BeanToMapUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class OSSClientServer implements IOSSClient {
    private static String AGREEMENT = "http://";

    private OSSClient client = null;


    public static IOSSClient getInstance(OSSConfiguration configuration) throws Exception {
        return new OSSClientServer(configuration);
    }

    public OSSClientServer(OSSConfiguration configuration) throws Exception {
        if (!StringUtils.isNotBlank(configuration.getAccessKeyId()) && !StringUtils.isNotBlank(configuration.getAccessKeySecret()) && !StringUtils.isNotBlank(configuration.getEndpoint())) {
            throw new Exception("Error of parameter");
        }
        if (client == null) {
            client = new OSSClient(AGREEMENT + configuration.getEndpoint(), configuration.getAccessKeyId(), configuration.getAccessKeySecret(), configuration.conf);
        }
    }

    public void putObject(String bucketName, String key, String filePath) {
        // client.putObject(bucketName, key, inputStream);
        System.out.println("Uploading a new object to OSS from a file\n");
        client.putObject(new PutObjectRequest(bucketName, key, new File(filePath)));
    }

    public void putObject(String bucketName, String key, File file) {
        // client.putObject(bucketName, key, inputStream);
        System.out.println("Uploading a new object to OSS from a file\n");
        client.putObject(new PutObjectRequest(bucketName, key, file));
    }

    public void putObject(String bucketName, String key, InputStream inputStream) {
        System.out.println("Uploading a new object to OSS from a file\n");
        client.putObject(bucketName, key, inputStream, null);
    }

    public void creatFolder(String bucketName, String objectName) throws IOException {
        objectName = objectName + "/";
        ObjectMetadata objectMeta = new ObjectMetadata();
        //这里的size为0,注意OSS本身没有文件夹的概念,这里创建的文件夹本质上是一个size为0的Object,dataStream仍然可以有数据
        byte[] buffer = new byte[0];
        ByteArrayInputStream in = new ByteArrayInputStream(buffer);
        objectMeta.setContentLength(0);
        try {
            client.putObject(bucketName, objectName, in, objectMeta);
        } finally {
            in.close();
        }
    }

    public void appendObject(String fileToAppen, String bucketName, String key) {

        // 发起首次追加Object请求，注意首次追加需要设置追加位置为0
        final String fileToAppend = "<file to append at first time>";
        AppendObjectRequest appendObjectRequest = new AppendObjectRequest(bucketName, key, new File(fileToAppend));

        // 设置content-type，注意设置object meta只能在使用Append创建Object设置
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentType("image/jpeg");
        appendObjectRequest.setMetadata(meta);

        // 设置追加位置为0,发送追加Object请求
        appendObjectRequest.setPosition(0L);
        AppendObjectResult appendObjectResult = client.appendObject(appendObjectRequest);

        // 发起第二次追加Object请求，追加位置为第一次追加后的Object长度
        final String fileToAppend2 = "<file to append at second time>";
        appendObjectRequest = new AppendObjectRequest(bucketName, key, new File(fileToAppend2));

        // 设置追加位置为前一次追加文件的大小,发送追加Object请求
        appendObjectRequest.setPosition(appendObjectResult.getNextPosition());
        appendObjectResult = client.appendObject(appendObjectRequest);

        OSSObject o = client.getObject(bucketName, key);
        // 当前该Object的大小为两次追加文件的大小总和
        System.out.println(o.getObjectMetadata().getContentLength());
        // 下一个追加位置为前两次追加文件的大小总和
        System.err.println(appendObjectResult.getNextPosition().longValue());
    }

    public void multipartUpload(String bucketName, String key, String localFilePath) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        List<PartETag> partETags = Collections.synchronizedList(new ArrayList<PartETag>());
        try {
            /*
             * Claim a upload id firstly
             */
            String uploadId = claimUploadId(bucketName, key);
            System.out.println("Claiming a new upload id " + uploadId + "\n");

            /*
             * Calculate how many parts to be divided
             */
            final long partSize = 5 * 1024 * 1024L;   // 5MB
            final File sampleFile = new File(localFilePath);
            long fileLength = sampleFile.length();
            int partCount = (int) (fileLength / partSize);
            if (fileLength % partSize != 0) {
                partCount++;
            }
            if (partCount > 10000) {
                throw new RuntimeException("Total parts count should not exceed 10000");
            } else {
                System.out.println("Total parts count " + partCount + "\n");
            }

            /*
             * Upload multiparts to your bucket
             */
            System.out.println("Begin to upload multiparts to OSS from a file\n");
            for (int i = 0; i < partCount; i++) {
                long startPos = i * partSize;
                long curPartSize = (i + 1 == partCount) ? (fileLength - startPos) : partSize;
                executorService.execute(new PartUploader(key, bucketName, sampleFile, startPos, curPartSize, i + 1, uploadId, partETags, client));
            }

            /*
             * Waiting for all parts finished
             */
            executorService.shutdown();
            while (!executorService.isTerminated()) {
                try {
                    executorService.awaitTermination(5, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            /*
             * Verify whether all parts are finished
             */
            if (partETags.size() != partCount) {
                throw new IllegalStateException("Upload multiparts fail due to some parts are not finished yet");
            } else {
                System.out.println("Succeed to complete multiparts into an object named " + key + "\n");
            }

            /*
             * View all parts uploaded recently
             */
            listAllParts(bucketName, key, uploadId);

            /*
             * Complete to upload multiparts
             */
            completeMultipartUpload(bucketName, key, uploadId, partETags);


        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message: " + oe.getErrorCode());
            System.out.println("Error Code:       " + oe.getErrorCode());
            System.out.println("Request ID:      " + oe.getRequestId());
            System.out.println("Host ID:           " + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ce.getMessage());
        } finally {
            /*
             * Do not forget to shut down the client finally to release all allocated resources.
             */
            if (client != null) {
                client.shutdown();
            }
        }

    }

    private String claimUploadId(String bucketName, String key) {
        InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucketName, key);
        InitiateMultipartUploadResult result = client.initiateMultipartUpload(request);
        return result.getUploadId();
    }

    public InputStream getObject(String bucketName, String key) {
         /*
             * Fetch the object that newly created at the step below.
             */
        System.out.println("Downloading an object");
        OSSObject object = client.getObject(new GetObjectRequest(bucketName, key));
        System.out.println("Content-Type: " + object.getObjectMetadata().getContentType());
        return object.getObjectContent();
    }

    public ObjectMetadata getObject(String bucketName, String key, String filePath) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        System.out.println("Fetching an object");
        ObjectMetadata objectMetadata = client.getObject(new GetObjectRequest(bucketName, key), new File(filePath));
        return objectMetadata;
    }

    @Override
    public List<Map<String, Object>> listBuckets() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        System.out.println("Listing buckets");
        List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
        // 遍历Bucket
        for (Bucket bucket : client.listBuckets()) {
            listMap.add(BeanToMapUtil.toMapObject(bucket));
            System.out.println(" - " + bucket.getName());
        }
        return listMap;
    }


    @Override
    public String getBucketLocation(String bucketName) {
        return client.getBucketLocation(bucketName);
    }

    public void shutdown() {
        client.shutdown();
    }

    private void completeMultipartUpload(String bucketName, String key, String uploadId, List<PartETag> partETags) {
        // Make part numbers in ascending order
        Collections.sort(partETags, new Comparator<PartETag>() {

            @Override
            public int compare(PartETag p1, PartETag p2) {
                return p1.getPartNumber() - p2.getPartNumber();
            }
        });

        System.out.println("Completing to upload multiparts\n");
        CompleteMultipartUploadRequest completeMultipartUploadRequest =
                new CompleteMultipartUploadRequest(bucketName, key, uploadId, partETags);
        client.completeMultipartUpload(completeMultipartUploadRequest);
    }

    private void listAllParts(String bucketName, String key, String uploadId) {
        System.out.println("Listing all parts......");
        ListPartsRequest listPartsRequest = new ListPartsRequest(bucketName, key, uploadId);
        PartListing partListing = client.listParts(listPartsRequest);

        int partCount = partListing.getParts().size();
        for (int i = 0; i < partCount; i++) {
            PartSummary partSummary = partListing.getParts().get(i);
            System.out.println("\tPart#" + partSummary.getPartNumber() + ", ETag=" + partSummary.getETag());
        }
        System.out.println();
    }
}
