package org.duomn.naive.web.upload.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("UploadByServletAction.action")
public class UploadByServletAction {
	
	private static final String CONTENT_DISPOSITION = "Content-Disposition";
	
	private static final String FILENAME_KEY = "filename=";
	
	/**
	 * 此处依赖了javax.servlet-api-jar(3.0以上的版本，应兼容以下的版本)
	 * 参考{@link org.springframework.web.multipart.support.StandardServletMultipartResolver}中获取文件名
	 * @return
	 */
	@RequestMapping(params="method=toUpload")
	public String toUpload() {
		return "jsp/upload/upload-by-servlet";
	}
	
	@RequestMapping(params="method=handleUpload")
	public String handleUpload(HttpServletRequest req, HttpServletResponse resp) {
		
		return "jsp/upload/upload-by-servlet";
	}

	public List<String> getUploadFileNames(HttpServletRequest req) throws Exception {
		List<String> uploadFileNames = new ArrayList<String>();
		Collection<Part> parts = req.getParts();
		for (Part part : parts) {
			String filename = extractFilename(part.getHeader(CONTENT_DISPOSITION));
			if (filename != null) {
				uploadFileNames.add(part.getName());
			}
		}
		return uploadFileNames;
	}
	
	private String extractFilename(String contentDisposition) {
		if (contentDisposition == null) {
			return null;
		}
		// TODO: can only handle the typical case at the moment
		int startIndex = contentDisposition.indexOf(FILENAME_KEY);
		if (startIndex == -1) {
			return null;
		}
		String filename = contentDisposition.substring(startIndex + FILENAME_KEY.length());
		if (filename.startsWith("\"")) {
			int endIndex = filename.indexOf("\"", 1);
			if (endIndex != -1) {
				return filename.substring(1, endIndex);
			}
		}
		else {
			int endIndex = filename.indexOf(";");
			if (endIndex != -1) {
				return filename.substring(0, endIndex);
			}
		}
		return filename;
	}
}
