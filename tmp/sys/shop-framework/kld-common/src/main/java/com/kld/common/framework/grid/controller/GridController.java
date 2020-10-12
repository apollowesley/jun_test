package com.kld.common.framework.grid.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kld.common.framework.dto.GridQueryPara;
import com.kld.common.framework.grid.service.IGridBizService;
import com.kld.common.util.GetSpringInstance;
import com.kld.common.util.ExcelUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping(value = "/cill/grid/")
@SuppressWarnings("rawtypes")
public class GridController {

	
	/**
	 * @param serviceName ,服务类实现的注解名称
	 * @param expportFlag,当前页与全部页的标识
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/exportExcel")
	public void exportExcel(String serviceName,String expportFlag,String excelName,GridQueryPara queryParam,HttpServletRequest request,HttpServletResponse response){
		
		String colModel = request.getParameter("colModel");
		if(StringUtils.isBlank(colModel)) return ;
		if(colModel.endsWith(";"))  colModel = colModel.substring(0, colModel.length()-1);

		String[] temp =  colModel.split(";");
		String[] propNames = new String[temp.length];
		String[] headStrs = new String[temp.length];
		
		String[] s = null;
		for(int i = 0 ; i< temp.length;i++){
			s = temp[i].split(",");
			propNames[i] = s[0];
			headStrs[i] = s[1];
		}

		IGridBizService gridData = (IGridBizService) GetSpringInstance.getBean(serviceName);
		List list = gridData.getGridData(queryParam, request);
		
		try {
			ExcelUtil.exportDataAs2007Excel(response, list, propNames, headStrs, excelName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/exportExcel1")  
	public void downloadFile(String fileName, HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
		try {
			InputStream inputStream = new FileInputStream("d:/abc.xls");
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[1024];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}
			inputStream.close();
			os.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}  
}
