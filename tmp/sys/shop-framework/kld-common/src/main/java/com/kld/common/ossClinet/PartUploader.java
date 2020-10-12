package com.kld.common.ossClinet;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.PartETag;
import com.aliyun.oss.model.UploadPartRequest;
import com.aliyun.oss.model.UploadPartResult;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class PartUploader implements Runnable {
    private String bucketName;
    private String key;
    private File localFile;
    private long startPos;
    private long partSize;
    private int partNumber;
    private String uploadId;
    private List<PartETag> partETags;
    OSSClient client;

    public PartUploader(String key, String bucketName, File localFile, long startPos, long partSize, int partNumber, String uploadId, List<PartETag> partETags, OSSClient client) {
        this.key = key;
        this.bucketName = bucketName;
        this.localFile = localFile;
        this.startPos = startPos;
        this.partSize = partSize;
        this.partNumber = partNumber;
        this.uploadId = uploadId;
        this.partETags = partETags;
        this.client = client;
    }

    @Override
    public void run() {
        InputStream instream = null;
        try {
            instream = new FileInputStream(this.localFile);
            instream.skip(this.startPos);

            UploadPartRequest uploadPartRequest = new UploadPartRequest();
            uploadPartRequest.setBucketName(this.bucketName);
            uploadPartRequest.setKey(this.key);
            uploadPartRequest.setUploadId(this.uploadId);
            uploadPartRequest.setInputStream(instream);
            uploadPartRequest.setPartSize(this.partSize);
            uploadPartRequest.setPartNumber(this.partNumber);

            UploadPartResult uploadPartResult = client.uploadPart(uploadPartRequest);
            System.out.println("Part#" + this.partNumber + " done\n");
            synchronized (partETags) {
                partETags.add(uploadPartResult.getPartETag());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (instream != null) {
                try {
                    instream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
