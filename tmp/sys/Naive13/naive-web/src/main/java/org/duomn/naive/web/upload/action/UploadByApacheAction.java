package org.duomn.naive.web.upload.action;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("UploadByApacheAction.action")
public class UploadByApacheAction {
	
	public static final Logger log = LoggerFactory.getLogger(UploadByApacheAction.class);
	
	int sizeThresholdUploadFileMemory = 1;
	// maximum size before a FileUploadException will be thrown
	int maxUploadFileSize = 15*1000*1024*1024;
	// save the file to the root of the drive where web server is
	// running; change as appropriate
	String COMPLETE_UPLOAD_DIRECTORY = "e:/upload/";
	// the location for temporary saving uploaded files that exceed 
	// the threshold sizeThresholdUploadFileMemory 
	String tempDirectory = "/temp";
	
	String filename;

	@RequestMapping(params="method=handleUpload")
	public String handleUpload(HttpServletRequest req, HttpServletResponse resp) {
		boolean isMultipart = ServletFileUpload.isMultipartContent(req);
		try {
			if (isMultipart) {
				FileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setFileSizeMax(10 * 1024 * 1024); // 单个文件的总大小
				upload.setSizeMax(maxUploadFileSize); // 上传文件的总大小
				upload.setHeaderEncoding("UTF-8"); // 设置编码
				
				FileItemIterator fileItr = upload.getItemIterator(req);
				
				// 1.解析表单里的参数
				Map<String, String> formData = parseFormData(fileItr);
				log.debug("formData:" + formData);
				
				// 2.从数据库读取参数，并设置值
				long skipped = formData.get(UploadUtil.BROKEN_RESUME_POSITION) == null ? -1 : 
					Long.parseLong(formData.get(UploadUtil.BROKEN_RESUME_POSITION));
				String imageFilePcid = formData.get(UploadUtil.BROKEN_RESUME_POSITION) == null ? "" :
					formData.get(UploadUtil.BROKEN_RESUME_POSITION);
//				Map<String, Object> imageFileData = imageDao.queryUploadFileByDetailPcid(imageFilePcid);
//				this.COMPLETE_UPLOAD_DIRECTORY = (String) imageFileData.get("PKG_PATH");
//				this.filename = (String) imageFileData.get("IMAGE_FILE_NAME");
				
				// 3.保存文件，并进行MD5校验
//				String initMd5 = (String) imageFileData.get("MD5_SUM");
				String initMd5 = "";
				receiveUploadFile(fileItr, imageFilePcid, skipped, initMd5);
			} else {
				System.out.println("无指定上传文件");
			}
		} catch (Exception e) {
			
		}
		return null;
	}
	
	/**
	 * 解析表单中的数据
	 * @param fileItr Apache文件上传FileItemIterator迭代器
	 * @throws IOException
	 * @throws FileUploadException
	 * @return 包含表单中请求参数的Map<String, String>
	 */
	private Map<String, String> parseFormData(FileItemIterator fileItr) throws IOException, FileUploadException {
		Map<String, String> formData = new HashMap<String, String>();
		while (fileItr.hasNext()) {
			FileItemStream fis = fileItr.next();
			if (!fis.isFormField()) continue;  
			
			String fieldName = fis.getFieldName();
			String contentType = fis.getContentType(); // text/plain; charset=UTF-8
			String encoding = UploadUtil.getEncoding(contentType, "UTF-8");
			if (UploadUtil.isStringType(contentType)) {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				InputStream in = null;
				String value = "";
				try {
					in = fis.openStream();
					UploadUtil.copy(in, baos);
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
				log.debug("fieldName = ["+fieldName+"], value = [" + value + "]");
			}
		}
		return formData;
	}
	
	/**
	 * 接受上传的文件，并进行MD5校验
	 * @param fileItr fileItr Apache文件上传FileItemIterator迭代器
	 * @param imageFilePcid 上传文件的pcid
	 * @param skipped 断点续传的起始位置，非断点续传为-1
	 * @param initMd5 原始文件的MD5值
	 * @throws FileUploadException
	 * @throws IOException
	 */
	private void receiveUploadFile(FileItemIterator fileItr, String imageFilePcid, long skipped, String initMd5) throws FileUploadException, IOException {
		while (fileItr.hasNext()) {
			FileItemStream fis = fileItr.next();
			if (fis.isFormField()) continue; 
			
			String srcFileName = fis.getName();
			String contentType = fis.getContentType();
			log.debug("srcFileName = [" + srcFileName + "], contentType = [" + contentType + "]");
			String uploadFilename = COMPLETE_UPLOAD_DIRECTORY + File.pathSeparator + (filename == null ? srcFileName : filename);
			
			File uploadFile = new File(uploadFilename);
			UploadUtil.breakStreamToFile(fis.openStream(), uploadFile, skipped);
			log.debug("saved as ["+uploadFilename+"]");
			
			// MD5校验
			String md5sum = UploadUtil.calculateMD5Sum(uploadFile);
			if (md5sum.equals(initMd5)) { 
//				imageDao.updateUploadFileMd5Sum(imageFilePcid, "1");
				log.debug("");
			} else {
//				imageDao.updateUploadFileMd5Sum(imageFilePcid, "0");
			}
		}
	}
	
}
