/**
 * 
 */
package com.laycms.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.ObjectMetadata;

/**
 * @author <p>Turbidsoul Chen 于 2016年11月8日 上午9:45:05</p>
 *
 */
public class FileuploadUtils {
	
	private static final String[] IMAGE_EXTS = new String[] {"png", "jpg", "jpeg","doc","docx","txt","xlsx","xls","ppt","pdf"};
	private static final String[] DOC_EXTS = new String[] {"doc","docx","txt","xlsx","xls","ppt","pdf"};

	public static final String PREFIX_DIR = "hw";
	
	
	public static OSSClient getNewOSSClient(){
		OSSClient client = new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
		return client;
	}
	public static List<String> uploadToAliyunOss(HttpServletRequest request, String prefix) throws OSSException, ClientException, IOException, FileUploadException {
		OSSClient client = new OSSClient(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
		List<FileItem> imageItems = getImageTempFile(request);
		List<String> plist = new ArrayList<>();
		for (FileItem item : imageItems) {
			String[] fileinfos = item.getName().split("\\.");
			String name = RandomStringUtils.randomAlphanumeric(10) + "." + fileinfos[fileinfos.length - 1];
			String path = prefix + "/" + name;
			client.putObject(BUCKET_NAME, path, item.getInputStream());
			plist.add(path);
		}
		client.shutdown();
		return plist;
	}
	
	
	public static String uploadFileToAliyunOss(FileItem item, String prefix,OSSClient client) throws OSSException, ClientException, IOException, FileUploadException {
		String[] fileinfos = item.getName().split("\\.");
		String name = RandomStringUtils.randomAlphanumeric(10) + "." + fileinfos[fileinfos.length - 1];
		String path = prefix + "/" + name;
		client.putObject(BUCKET_NAME, path, item.getInputStream());
		return path;
	}
	
	public static List<FileItem> getImageTempFile(HttpServletRequest request) throws FileUploadException {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		File repository = (File) request.getServletContext().getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> items = upload.parseRequest(request);
		List<FileItem> imageItems = new ArrayList<>();
		for (FileItem fileItem : items) {
			if(StringUtils.isNotEmpty(fileItem.getName()) && ArrayUtils.contains(IMAGE_EXTS, fileItem.getName().split("\\.")[1])) {
				imageItems.add(fileItem);
			}
		}
		return imageItems;
	}
	public static List<FileItem> getDocTempFile(HttpServletRequest request) throws FileUploadException {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		File repository = (File) request.getServletContext().getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);
		ServletFileUpload upload = new ServletFileUpload(factory);
		List<FileItem> items = upload.parseRequest(request);
		List<FileItem> imageItems = new ArrayList<>();
		
		
		for (FileItem fileItem : items) {
			String name = fileItem.getName();
			if(StringUtils.isNotEmpty(name)) {
				
				String filePix = name.substring(name.lastIndexOf(".")+1);
				if (ArrayUtils.contains(DOC_EXTS, filePix)) {
					imageItems.add(fileItem);
				}
			}
		}
		return imageItems;
	}
	

}
