package com.thx.file.service;

import com.thx.common.service.BaseManager;
import com.thx.file.model.File;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class FileManager extends BaseManager<File> {
	
}
