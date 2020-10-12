package com.eeepay.epay.front.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.eeepay.epay.admin.utils.ReqUtil;
import com.eeepay.epay.admin.utils.RespUtil;

/**
 * 
 * @author junhu
 */
@Controller("Base64UploadContrller")
@Scope("prototype") // 多例
@RequestMapping("/admin/base64")
public class Base64UploadController {
	
	/**
	 * 图片通过base64上传
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/imageUpload.json", method = { RequestMethod.GET, RequestMethod.POST })
	public void imageUpload(HttpServletRequest request, HttpServletResponse response) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Map<String, Object> results = new HashMap<String, Object>();
		
		int baseint = 0;
		while(true){
			String b64image = ReqUtil.getString(request,"image"+(baseint==0?"":baseint),"");
			if(b64image.equals("")){
				break;
			}
			String filetype = "jpg";// 默认jpg格式
			Pattern pattern = Pattern.compile("data:image/([^;]*);base64,");
			Matcher matcher = pattern.matcher(b64image);
			if(matcher.find()){
				filetype = matcher.group(1);
			}
			String img = b64image.replaceFirst("data:image/([^;]*);base64,", "");
			
			byte[] imagebytes =  Base64Utils.decodeFromString(img);
			
			String uploadHeadStr = "/upload/"+ sdf.format(new Date());
			File uploadFile = new File(request.getSession().getServletContext().getRealPath(uploadHeadStr));
			if (!uploadFile.exists()) {
				uploadFile.mkdirs();
			}
	        UUID uuid = UUID.randomUUID();
			String tpath = uuid + "." + filetype;
			String path = uploadFile + "/" + tpath; // 绝对路径
	         
			FileOutputStream out = null;
			try {
				out = new FileOutputStream(path);
				out.write(imagebytes);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (out != null) {
						out.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			results.put("image"+(baseint==0?"":baseint), uploadHeadStr+"/" + tpath);
			baseint++;
		}
		
		JSONObject json = new JSONObject();
		try {
			json.put("success", true);
			json.put("data", results);
		} catch (Exception e) {
 			e.printStackTrace();
		}
		RespUtil.renderJson(response, json.toString());
	}
	
}
