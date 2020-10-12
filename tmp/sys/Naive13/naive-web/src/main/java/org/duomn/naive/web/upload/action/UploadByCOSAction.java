package org.duomn.naive.web.upload.action;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FilePart;
import com.oreilly.servlet.multipart.MultipartParser;
import com.oreilly.servlet.multipart.ParamPart;
import com.oreilly.servlet.multipart.Part;

@Controller
@RequestMapping("UploadByCOSAction.action")
public class UploadByCOSAction {

	private static final Logger log = LoggerFactory.getLogger(UploadByCOSAction.class);
	
	private String filename;
	
	private String uploadDirectory = "e:/upload";
	
//	@ResponseBody
	@RequestMapping(params = "method=handleUpload")
	public String handleUpload(HttpServletRequest req, HttpServletResponse resp) {
		try {
			MultipartRequest multipart = new MultipartRequest(req, uploadDirectory, Integer.MAX_VALUE);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*
		try {
			MultipartParser mutiParser = new MultipartParser(req, Integer.MAX_VALUE);
			mutiParser.setEncoding("UTF-8");
			Part part = null;

			Map<String, String> formData = new HashMap<String, String>();
			while ((part = mutiParser.readNextPart()) != null) {
				if (part.isParam()) {
					// 1.解析表单里的参数，数据表单应在文件表单之前
					ParamPart paramPart = (ParamPart) part;

					String fieldName = paramPart.getName();
					String value = paramPart.getStringValue();
					formData.put(fieldName, value);
					log.debug("fieldName = [" + fieldName + "], value = [" + value + "]");
				} else if (part.isFile()) {
					FilePart filePart = (FilePart) part;

					log.debug("upload form data: " + formData);
					// 2.从数据库读取参数，并设置值
					long skipped = formData.get(UploadUtil.BROKEN_RESUME_POSITION) == null ? -1
							: Long.parseLong(formData.get(UploadUtil.BROKEN_RESUME_POSITION));
					String imageFilePcid = formData.get(UploadUtil.BROKEN_RESUME_POSITION) == null ? ""
							: formData.get(UploadUtil.BROKEN_RESUME_POSITION);

					// 3.保存文件
					String srcFileName = filePart.getFileName();
					String contentType = filePart.getContentType();
					log.debug("srcFileName = [" + srcFileName + "], contentType = [" + contentType + "]");
					String uploadFilename = uploadDirectory + File.separatorChar
							+ (filename == null ? srcFileName : filename);

					File uploadFile = new File(uploadFilename);
					UploadUtil.breakStreamToFile(filePart.getInputStream(),
							uploadFile, skipped);
					log.debug("saved as [" + uploadFilename + "]");

					// MD5校验
					String initMd5 = "";
					String md5sum = UploadUtil.calculateMD5Sum(uploadFile);
					if (md5sum.equals(initMd5)) {
						log.debug("check MD5SUM valid. imageFilePcid = [" + imageFilePcid + "]");
					} else {
						log.debug("check MD5SUM invalid. imageFilePcid = [" + imageFilePcid + "]");
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
//			throw new BaseException(e);
			return "ERROR";
 		} finally {
		}
		*/
		String msg = "SUCCESS";
		
		PrintWriter pw = null;
		try {
			pw = resp.getWriter();
			pw.write(msg);
			pw.flush();
		} catch (IOException e) {
			log.error("Response the upload exception.", e);
		} finally {
			if (pw != null) {
				try {
					pw.close();
				} catch (Exception e) {
					log.error("Close the response outputSstream exception.", e);
				}
			}
		}
		
		return null;
	}

}
