package org.duomn.naive.common.upload;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.duomn.naive.common.exception.BaseException;
import org.duomn.naive.common.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApacheCommonsFileUploadImpl implements IFileUploader {

	private static Logger log = LoggerFactory.getLogger(ApacheCommonsFileUploadImpl.class);
	/** 单个文件的大小限制 */
	private long fileSizeMax;
	/** 上传文件的总大小设置 */
	private long maxUploadFileSize;
	/** 上传文件的目录 */
	private String uploadDirectory;
	/** 是否启用md5校验 */
	private boolean md5sum;
	/** 文件的默认编码 */
	private String encoding = "UTF-8";

	public long getFileSizeMax() {
		return fileSizeMax;
	}

	public void setFileSizeMax(long fileSizeMax) {
		this.fileSizeMax = fileSizeMax;
	}

	public long getMaxUploadFileSize() {
		return maxUploadFileSize;
	}

	public void setMaxUploadFileSize(long maxUploadFileSize) {
		this.maxUploadFileSize = maxUploadFileSize;
	}

	public String getUploadDirectory() {
		return uploadDirectory;
	}

	public void setUploadDirectory(String uploadDirectory) {
		this.uploadDirectory = uploadDirectory;
	}

	public boolean isMd5sum() {
		return md5sum;
	}

	public void setMd5sum(boolean md5sum) {
		this.md5sum = md5sum;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	private String filename;

	@Override
	public void upload(HttpServletRequest req, HttpServletResponse resp)
			throws BaseException {
		boolean isMultipart = ServletFileUpload.isMultipartContent(req);
		try {
			if (isMultipart) {
				FileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setFileSizeMax(fileSizeMax); // 单个文件的总大小
				upload.setSizeMax(maxUploadFileSize); // 上传文件的总大小
				upload.setHeaderEncoding(encoding); // 设置编码

				FileItemIterator fileItr = upload.getItemIterator(req);

				Map<String, String> formData = new HashMap<String, String>();
				while (fileItr.hasNext()) {
					FileItemStream fis = fileItr.next();
					if (fis.isFormField()) {
						// 1.解析表单里的参数，数据表单应在文件表单之前
						String fieldName = fis.getFieldName();
						String contentType = fis.getContentType(); // text/plain;
																	// charset=UTF-8
						String encoding = IOUtils.getEncoding(contentType,
								"UTF-8");
						if (IOUtils.isStringType(contentType)) {
							ByteArrayOutputStream baos = new ByteArrayOutputStream();
							InputStream in = null;
							String value = "";
							try {
								in = fis.openStream();
								IOUtils.copy(in, baos);
								baos.flush();
								value = new String(baos.toByteArray(), encoding);
							} finally {
								if (in != null) {
									in.close();
								}
								if (baos != null) {
									baos.close();
								}
							}
							formData.put(fieldName, value);
						}
					} else {
						log.debug("formdate:" + formData);
						String index = IOUtils.getJUploadFileIndexByName(fis
								.getFieldName()); // 添加对多文件上传的支持

						// 2.从数据库读取参数，并设置值
						long skipped = formData
								.get(IOUtils.BROKEN_RESUME_POSITION + index) == null ? -1
								: Long.parseLong(formData
										.get(IOUtils.BROKEN_RESUME_POSITION
												+ index));

						// 3.保存文件
						String srcFileName = fis.getName();
						String contentType = fis.getContentType();
						log.debug("srcFileName = [" + srcFileName
								+ "], contentType = [" + contentType + "]");
						// 　null值的判断用于测试
						String uploadFilename = uploadDirectory
								+ File.separatorChar
								+ (filename == null ? srcFileName : filename);

						File uploadFile = new File(uploadFilename);
						IOUtils.breakStreamToFile(fis.openStream(), uploadFile,
								skipped);
						log.debug("saved as [" + uploadFilename + "]");

						// MD5校验
						if (md5sum) {
							String initMd5 = formData.get("MD5SUM" + index);
							if (initMd5 == null)
								throw new BaseException("客户端需要上传原始的MD5值");
							String md5sum = IOUtils.calculateMD5Sum(uploadFile);
							if (md5sum.equals(initMd5)) {
								log.debug("check MD5SUM valid. formdata = ["
										+ formData + "]");
							} else {
								log.error("check MD5SUM invalid. formdata= ["
										+ formData + "]");
							}
						}
					}
				}
			} else {
				log.debug("Not a file upload request");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {}
	}

}
