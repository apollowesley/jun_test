package com.site.dto;

import java.io.Serializable;

public class DownloadBusiLogDto implements Serializable {

    private static final long serialVersionUID = 7927522993078015230L;

    private String fileName;

    private byte[] bytes;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

}
