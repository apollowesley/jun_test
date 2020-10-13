/**
 * 
 */
package com.thx.file.util;

import com.thx.common.Constant;
import com.thx.common.spring.BeanFactory;
import com.thx.common.util.AppContext;
import com.thx.common.util.FileUtil;
import com.thx.common.util.ThreadLocalContext;
import com.thx.common.web.SessionInfo;
import com.thx.common.web.WebConstant;
import com.thx.file.model.File;
import com.thx.file.service.FileManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;

/**
 * 
 * 建立日期 : 2014年6月5日 下午5:57:13<br>
 * 作者 : shiyan<br>
 * 模块 : <br>
 * 描述 : <br>
 * 修改历史: 序号 日期 修改人 修改原因 <br>
 */
public class WebFileUtil {

	private static Logger log = LoggerFactory.getLogger(WebFileUtil.class);
	/**
	 * 根据文件流保存单个文件
	 * @param fileName
	 * @param fileStream
	 * @return
	 */
	public static File uploadAndSaveFile(String fileName,byte[] fileStream) {
		try {
			if(fileStream==null||fileStream.length<=0)
				return null;
			String path = getFilePath(null);
			String newFileName = FileUtil.rename(fileName);
			path = path+java.io.File.separator+newFileName;
			
			if(!FileUtil.createFileByStream( path, fileStream))
				return null;
			File f = new File();
			f.setNewName(newFileName);
			f.setOldName(fileName);
			f.setPath(path);
			f.setUploadTime(new Date());
		    f.setFileSize(FileUtil.formatSize(fileStream.length));
			SessionInfo user = (SessionInfo) ThreadLocalContext.get(WebConstant.KEY_USER);
			if (user == null ){
				HttpServletRequest request = getRequest();
				if(request!=null)
					user = (SessionInfo) (request.getSession()
							.getAttribute(WebConstant.KEY_USER));
			}
			if (user != null) {
				f.setUserId(user.getUserId());
			}
			FileManager fileManager = (FileManager) BeanFactory.getBean("fileManager");
			fileManager.save(f);
			return f;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	/**
	 * 根据文件流保存单个文件
	 * @param fileName
	 * @param fileStream
	 * @param modPath,模块名
	 * @return
	 */
	public static File uploadAndSaveFile(String fileName,byte[] fileStream,String modPath) {
		try {
			if(fileStream==null||fileStream.length<=0)
				return null;
			String path = getFilePath(modPath);
			String newFileName = FileUtil.rename(fileName);
			path = path+java.io.File.separator+newFileName;
			
			if(!FileUtil.createFileByStream(path, fileStream))
				return null;
			File f = new File();
			f.setNewName(newFileName);
			f.setOldName(fileName);
			f.setPath(path);
			f.setUploadTime(new Date());
		    f.setFileSize(FileUtil.formatSize(fileStream.length));
			SessionInfo user = (SessionInfo) ThreadLocalContext.get(WebConstant.KEY_USER);
			if (user == null ){
				HttpServletRequest request = getRequest();
				if(request!=null)
					user = (SessionInfo) (request.getSession()
							.getAttribute(WebConstant.KEY_USER));
			}
			if (user != null) {
				f.setUserId(user.getUserId());
			}
			FileManager fileManager = (FileManager) BeanFactory.getBean("fileManager");
			fileManager.save(f);
			return f;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	/**
	 * 上传保存单个文件
	 * @param fileName
	 * @param file
	 * @return
	 */
	public static File uploadAndSaveFile( String fileName,java.io.File file) {
		try {
			if(file==null)
				return null;
			String path = getFilePath(null);
			java.io.File dir = new java.io.File(path);
			if(!dir.exists()){
				dir.mkdirs();
			}
			String newFileName = FileUtil.rename(fileName);
			path = path+java.io.File.separator+newFileName;
			if(!FileUtil.copyFile(file.getAbsolutePath(), path, true))
				return null;
			File f = new File();
			f.setNewName(newFileName);
			f.setOldName(fileName);
			f.setPath(path);
			f.setUploadTime(new Date());
			f.setFileSize(FileUtil.formatSize(file.length()));
			SessionInfo user = (SessionInfo) ThreadLocalContext.get(WebConstant.KEY_USER);
			if (user == null ){
				HttpServletRequest request = getRequest();
				if(request!=null)
					user = (SessionInfo) (request.getSession()
							.getAttribute(WebConstant.KEY_USER));
			}
			if (user != null) {
				f.setUserId(user.getUserId());
			}
			FileManager fileManager = (FileManager) BeanFactory.getBean("fileManager");
			fileManager.save(f);
			return f;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 上传保存单个文件
	 * @param fileName
	 * @param file
	 * @param modPath,模块名
	 * @return
	 */
	public static File uploadAndSaveFile( String fileName,java.io.File file,String modPath) {
		try {
			if(file==null)
				return null;
			String path = getFilePath(modPath);
			java.io.File dir = new java.io.File(path);
			if(!dir.exists()){
				dir.mkdirs();
			}
			String newFileName = FileUtil.rename(fileName);
			path = path+java.io.File.separator+newFileName;
			if(!FileUtil.copyFile(file.getAbsolutePath(), path, true))
				return null;
			File f = new File();
			f.setNewName(newFileName);
			f.setOldName(fileName);
			f.setPath(path);
			f.setUploadTime(new Date());
			f.setFileSize(FileUtil.formatSize(file.length()));
			SessionInfo user = (SessionInfo) ThreadLocalContext.get(WebConstant.KEY_USER);
			if (user == null ){
				HttpServletRequest request = getRequest();
				if(request!=null)
					user = (SessionInfo) (request.getSession()
							.getAttribute(WebConstant.KEY_USER));
			}
			if (user != null) {
				f.setUserId(user.getUserId());
			}
			FileManager fileManager = (FileManager) BeanFactory.getBean("fileManager");
			fileManager.save(f);
			return f;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 *上传和保存多个文件 
	 * @param fileNames
	 * @param files
	 * @return
	 */
	public static List<File> uploadAndSaveFile( String[] fileNames,java.io.File[] files) {
		try {
			if(files==null)
				return null;
			if(fileNames==null)
				return null;
			List<File> result =  new ArrayList<File>();
			for(int i = 0 ; i < files.length ; i++){
				result.add(uploadAndSaveFile(fileNames[i], files[i]));
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 *上传和保存多个文件 
	 * @param fileNames
	 * @param files
	 * @param modPath,模块名
	 * @return
	 */
	public static List<File> uploadAndSaveFile( String[] fileNames,java.io.File[] files,String modPath) {
		try {
			if(files==null)
				return null;
			if(fileNames==null)
				return null;
			List<File> result =  new ArrayList<File>();
			for(int i = 0 ; i < files.length ; i++){
				result.add(uploadAndSaveFile(fileNames[i], files[i],modPath));
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static File getFileById(String id){
		FileManager fileManager = (FileManager) BeanFactory.getBean("fileManager");
		return fileManager.get(id);
	}
	public static File updateFile(File file){
		FileManager fileManager = (FileManager) BeanFactory.getBean("fileManager");
		fileManager.update(file);
		return file;
	}


	/**
	 * 删除文件
	 * @param id
	 */
	public static boolean deleteFile(String id){
		File file = getFileById(id);
		if(file!=null){
			java.io.File ioFile =new java.io.File(file.getPath());
			if(ioFile!=null){
				FileManager fileManager = (FileManager) BeanFactory.getBean("fileManager");
				fileManager.delete(id);
				ioFile.delete();
				return true;
			}
		}
		return false;
	}
	/**
	 * 保存文件至数据库，不上传至服务器
	 *
	 * @param file,
	 * @param oldFileName
	 */
	public static File saveFile(java.io.File file,String oldFileName){
		File f = new File();
		f.setNewName(file.getName());
		f.setOldName(oldFileName);
		f.setPath(file.getAbsolutePath());
		f.setUploadTime(new Date());
		f.setFileSize(FileUtil.formatSize(file.length()));
		SessionInfo user = (SessionInfo) ThreadLocalContext.get(WebConstant.KEY_USER);
		if (user == null ){
			HttpServletRequest request = getRequest();
			if(request!=null)
				user = (SessionInfo) (request.getSession()
						.getAttribute(WebConstant.KEY_USER));
		}
		if (user != null) {
			f.setUserId(user.getUserId());
		}
		FileManager fileManager = (FileManager) BeanFactory.getBean("fileManager");
		fileManager.save(f);
		return f;
	}

	/**
	 * 获取request对象
	 *
	 * @return
	 */
	private static HttpServletRequest getRequest() {

		HttpServletRequest request = (HttpServletRequest) ThreadLocalContext.get(WebConstant.CURRENT_REQUEST);
		log.debug("线程绑定的变量：" + WebConstant.CURRENT_REQUEST + "=" + (request == null ? "null" : request.getRequestedSessionId()));

//		去掉 Struts2 的依赖项, 改用 Spring, 需要配置
//		<listener>
//		  <listener-class>org.springframework.web.context.request.RequestContextListener</listener-class>
//		</listener>
//		if (request == null)
//			request = ServletActionContext.getRequest();

		if (request == null) {
			request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		}

		return request;
	}

	/**
	 * 获取上传路径，如果模块路径为空，则创建年月文件夹，返回路径
	 * @param modPath,模块路径
	 */
	public static String getFilePath(String modPath){
		String path = AppContext.getProperties(Constant.UPLOAD_PATH_KEY);

		if(StringUtils.isBlank(path)){
			throw new IllegalStateException("配置文件未配置上传路径," + Constant.UPLOAD_PATH_KEY);
		}

		if(StringUtils.isNotBlank(modPath)){
			path += java.io.File.separator+modPath.replaceAll("/", Matcher.quoteReplacement(java.io.File.separator));
		}else{
			SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMM");  
			String nowTimeStr = sDateFormat.format(new Date()); 
			path +=java.io.File.separator+nowTimeStr;
		}

		java.io.File dir = new java.io.File(path);
		if(!dir.exists()){
			dir.mkdirs();
		}

		return path;
	}
}
