package com.kiss.form.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kiss.form.bean.HeritrixFileBean;
import com.kiss.form.constant.HeritrixConstant;
import com.kiss.form.util.FileUtils;
import com.kiss.form.util.curl.CurlGlue;
import com.kiss.form.util.curl.CurlWrite;
import com.kiss.user.service.ISystemService;
import com.kiss.util.MapUtils;
import com.kiss.web.spring3.controller.MultiActionControllerImpl;

/**
 * @author leyvi
 * @since 2013-8-13下午6:10:01
 */
@Controller
@RequestMapping("/heritrix")
public class HeritrixController extends MultiActionControllerImpl {

	@Autowired
	private ISystemService systemService;
	@RequestMapping("/heritrixmanage")
	public ModelAndView heritrix(String filePath, String up) {
		if (StringUtils.isBlank(filePath)) {
			filePath = systemService
					.getMySystemValueByCode(HeritrixConstant.HERITRIX_PATH
							.getValue());
		} else {
			filePath = FileUtils.decodeBase64(filePath);
		}
		List<HeritrixFileBean> files = FileUtils.getFileNames(filePath);
		Map<String, Object> params = MapUtils.getInstance();
		String parentFilePath = FileUtils.getParentFilePath(filePath);
		params.put("files", files);
		params.put("parentFilePath", parentFilePath);
		if (StringUtils.isBlank(parentFilePath)) {
			params.put("up", "2");
		} else {
			params.put("up", up);
		}
		return getModelAndView(params);
	}

	@RequestMapping("/toreviewfile")
	public ModelAndView reviewFile(String filePath, HttpServletResponse response) {
		FileInputStream fis = null;
		File file = null;
		ServletOutputStream sos = null;
		try {
			file = new File(FileUtils.decodeBase64(filePath));
			response.setHeader("Content-Disposition", "attachment; filename=\""
					+ new String(file.getName().getBytes(),"ISO-8859-1"));
			response.setContentType("application/octet-stream; charset=UTF-8");
			fis = new FileInputStream(file);
			sos = response.getOutputStream();
			BufferedInputStream bis = new BufferedInputStream(fis);
			byte[] b = new byte[1024];
			while (bis.read(b) != -1) {
				sos.write(b);
			}
			sos.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null)
					fis.close();
				if (sos != null)
					sos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	@RequestMapping("/rescanJob")
	public ModelAndView rescanJob(HttpServletResponse response){
		//curl -v -d "action=rescan" -k -u admin:admin --anyauth --location https://localhost:8443/engine
		CurlGlue cg = new CurlGlue();
		cg.setopt(CurlGlue.CURLOPT_WRITEFUNCTION, new CurlWrite() {
			@Override
			public int handleString(byte[] s) {
				System.out.println(s);
				return 0;
			}
		});
		cg.setopt(CurlGlue.CURLOPT_URL, "https://localhost:8443/engine");
        cg.setopt(CurlGlue.CURLOPT_SSLVERSION, 3);
        cg.setopt(CurlGlue.CURLOPT_SSL_VERIFYPEER, 1);
        cg.setopt(CurlGlue.CURLOPT_USERNAME, "admin");
        cg.setopt(CurlGlue.CURLOPT_USERPWD, "admin");
        cg.setopt(CurlGlue.CURLOPT_VERBOSE, 1);
        cg.setopt(CurlGlue.CURLOPT_FOLLOWLOCATION, 1);
        cg.setopt(CurlGlue.CURLOPT_COOKIEFILE, "cookie.txt");
        cg.setopt(CurlGlue.CURLOPT_POSTFIELDS, "action=rescan");
        cg.perform();
        // The cookie.txt file is actually created now
        cg.finalize();
		return getModelAndView();
	} 
}
