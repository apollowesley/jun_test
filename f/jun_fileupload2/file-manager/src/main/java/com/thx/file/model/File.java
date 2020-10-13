package com.thx.file.model;

import com.thx.common.Constant;
import com.thx.common.util.AppContext;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.regex.Matcher;

/**
 * 
 * 建立日期 : 2014年6月3日 下午5:16:58<br>
 * 作者 : shiyan<br>
 * 模块 : <br>
 * 描述 : <br>
 * 修改历史: 序号 日期 修改人 修改原因 <br>
 */
@Entity
@Table(name = "sys_file")
public class File {
	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "uuid")
	private String fileId;
	
	private String oldName;
	
	private String newName;
	
	@Column(name = "path_")
	private String path;
	
	@Column(name = "version_")
	private String version;
	
	private Date uploadTime;
	
	private String userId;
    private String fileSize;
    @Transient
	private long fileBytes;
	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getOldName() {
		return oldName;
	}

	public void setOldName(String oldName) {
		this.oldName = oldName;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public String getPath() {
		
		String savePathType = AppContext.getProperties(Constant.SAVE_PATH_TYPE_KEY);
		if(StringUtils.isNotBlank(savePathType)&&!Boolean.parseBoolean(savePathType)){
			String basePath = AppContext.getProperties(Constant.UPLOAD_PATH_KEY);
			if(StringUtils.isNotBlank(basePath)){			 
				basePath = basePath.replaceAll("/", Matcher.quoteReplacement(java.io.File.separator));
				return basePath+path;
			}
		}
		return path;
	}

	public void setPath(String path) {
		String savePathType = AppContext.getProperties(Constant.SAVE_PATH_TYPE_KEY);
		if(StringUtils.isNotBlank(savePathType)&&!Boolean.parseBoolean(savePathType)){
			String basePath = AppContext.getProperties(Constant.UPLOAD_PATH_KEY);
			if(StringUtils.isNotBlank(basePath)){
				basePath = basePath.replaceAll("/", Matcher.quoteReplacement(java.io.File.separator));
				if(path.indexOf(basePath)>=0){
					this.path = path.substring(path.indexOf(basePath)+basePath.length());
					return ;
				}				
			}
		}
		this.path = path;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public long getFileBytes() {
		return fileBytes;
	}

	public void setFileBytes(long fileBytes) {
		this.fileBytes = fileBytes;
	}
	
	
}
