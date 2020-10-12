package org.duomn.naive.web.upload.action;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
@RequestMapping("UploadBySpringAction.action")
public class UploadBySpringAction {

	private static final String UPLOAD_PATH = "E:\\upload";
	
	/**
	 * 使用Apache commons-fileupload进行上传，上传成功后跳转到当前页面。
	 * 好处:可以避免依赖servlet-api.jar，文件上传的处理在{@link org.springframework.web.servlet.DispatcherServlet}中
	 * 缺点:Spring对所有的请求都会检查是否包含多媒体信息
	 * @return
	 */
	@RequestMapping(params="method=toUpload")
	public String toUpload() {
		return "jsp/upload/upload-by-spring";
	}
	
	/**
	 * Apache commons-fileupload文件上传完成后的处理类<br/>
	 * 需要在applicationContext.xml中配置{@link org.springframework.web.multipart.commons.CommonsMultipartResolver},
	 * 文件上传交给Spring处理，包括文件大小检查，此方法为文件上传完成后的处理方法，可以从服务器获取到上传的文件。
	 * @param model
	 * @param req
	 * @return
	 */
	@RequestMapping(params="method=handleUpload")
	public String handleUpload(ModelMap model, MultipartHttpServletRequest req) {
		try {
			List<MultipartFile> files = req.getFiles("files");
			if (files != null) {
				for (MultipartFile multipartFile : files) {
					String fileName = multipartFile.getOriginalFilename();
					File dist = new File(UPLOAD_PATH + File.pathSeparator + fileName);
					multipartFile.transferTo(dist);
				}
			}
			model.put("msg", "上传成功");
		} catch (Exception e) {
			e.printStackTrace();
			model.put("msg", "上传失败");
		}
		return "jsp/upload/upload-by-spring";
	}
	
}
