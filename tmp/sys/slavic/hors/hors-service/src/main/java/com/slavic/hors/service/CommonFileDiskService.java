package com.slavic.hors.service;

import com.slavic.hors.orm.entity.HorsCommonFileDisk;

public interface CommonFileDiskService {

    Long storeFile(String fileName, String content);

    HorsCommonFileDisk receiveFile(Long id);

}
