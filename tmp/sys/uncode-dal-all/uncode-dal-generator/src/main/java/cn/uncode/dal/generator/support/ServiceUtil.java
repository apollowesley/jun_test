package cn.uncode.dal.generator.support;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class ServiceUtil {

	/**
	 * 返回接口service的包信息
	 * @param tbName
	 * @param infoMap
	 * @return
	 */
	public StringBuilder getInterfacePackage(String tbName, Map<String, String> infoMap){
		StringBuilder result = new StringBuilder("");
		String servicePackName = infoMap.get("servicePackName").toString();
		if(StringUtils.isNotBlank(servicePackName)){
			result.append("package ").append(servicePackName).append(";\n\n");
		}
		return result;
	}
	/**
	 * 返回接口service的Import类信息
	 * @param tbName
	 * @param infoMap
	 * @return
	 */
	public StringBuilder getInterfaceImport(String tbName, Map<String, String> infoMap){
		StringBuilder result = new StringBuilder("");
		String packName = infoMap.get("packName").toString();
		if(StringUtils.isNotBlank(packName)){
			result.append("import ").append(packName).append(".").append(StringUtil.upperFirestChar(tbName)).append(";\n\n");
		}
		result.append("import cn.uncode.dal.service.BaseService;\n");
		return result;
	}
	/**
	 * 返回接口service的类声明信息
	 * @param tbName
	 * @param infoMap
	 * @return
	 */
	public StringBuilder getInterfaceClass(String tbName, String fileName){
		StringBuilder result = new StringBuilder("");
		result.append(" /**\n");
		result.append(" * service接口类,此类由Uncode自动生成\n");
		result.append(" * @author uncode\n");
		result.append(" * @date").append(new SimpleDateFormat(" yyyy-MM-dd").format(new Date())).append("\n");
		result.append(" */\n");
		result.append("public interface ").append(fileName).append(" extends BaseService<").append(StringUtil.upperFirestChar(tbName)).append("> {\n\n}");
		return result;
	}

	/**
	 * 返回接口service的包信息
	 * @param tbName
	 * @param infoMap
	 * @return
	 */
	public StringBuilder getPackage(String tbName, Map<String, String> infoMap){
		StringBuilder result = new StringBuilder("");
		String servicePackName = infoMap.get("servicePackName").toString();
		if(StringUtils.isNotBlank(servicePackName)){
			result.append("package ").append(servicePackName).append(".impl;\n\n");
		}
		return result;
	}
	/**
	 * 返回接口service的Import类信息
	 * @param tbName
	 * @param infoMap
	 * @return
	 */
	public StringBuilder getImport(String tbName, Map<String, String> infoMap,String interfaceName){
		StringBuilder result = new StringBuilder("");
		String packName = infoMap.get("packName").toString();
		if(StringUtils.isNotBlank(packName)){
			result.append("import ").append(packName).append(".").append(StringUtil.upperFirestChar(tbName)).append(";\n");
		}
		String servicePackName = infoMap.get("servicePackName").toString();
		if(StringUtils.isNotBlank(servicePackName) && StringUtils.isNotBlank(interfaceName)){
			result.append("import ").append(servicePackName).append(".").append(interfaceName).append(";\n");
		}
		result.append("import org.springframework.stereotype.Service;\n\n");
		result.append("import cn.uncode.dal.service.AbstractBaseService;\n\n");
		return result;
	}
	/**
	 * 返回接口service的类声明信息
	 * @param tbName
	 * @param infoMap
	 * @return
	 */
	public StringBuilder getClass(String tbName, String fileName,String interfaceName){
		StringBuilder result = new StringBuilder("");
		result.append(" /**\n");
		result.append(" * service类,此类由Uncode自动生成\n");
		result.append(" * @author uncode\n");
		result.append(" * @date").append(new SimpleDateFormat(" yyyy-MM-dd").format(new Date())).append("\n");
		result.append(" */\n");
		result.append("@Service\n");
		result.append("public class ").append(fileName).append(" extends AbstractBaseService<").append(StringUtil.upperFirestChar(tbName))
		.append("> implements ").append(interfaceName).append(" {\n\n}");
		return result;
	}
	
	
	/**
	 * 创建Service文件
	 * @param tbName 表名，全小写
	 * @param collist
	 * @param infoMap
	 * @return
	 */
	public String createService(String tbName, Map<String, String> infoMap) {
		try {
			//生成接口文件
			String interfaceName = StringUtil.upperFirestChar(tbName) + "Service";
			File interfaceFile = new File(infoMap.get("catName") + File.separator, interfaceName + ".java");
			StringBuilder interfaceInfo = new StringBuilder("");
			interfaceInfo.append(getInterfacePackage(tbName, infoMap));
			interfaceInfo.append(getInterfaceImport(tbName, infoMap));
			interfaceInfo.append(getInterfaceClass(tbName,interfaceName));
			ReadWriteFileWithEncode.write(interfaceFile.getAbsolutePath(), interfaceInfo.toString(), "UTF-8");
			
			//生成实现类文件
			String serviceName = StringUtil.upperFirestChar(tbName) + "ServiceImpl";
			File serviceFile = new File(infoMap.get("catName") + File.separator, serviceName + ".java");
			StringBuilder serviceInfo = new StringBuilder("");
			serviceInfo.append(getPackage(tbName, infoMap));
			serviceInfo.append(getImport(tbName, infoMap,interfaceName));
			serviceInfo.append(getClass(tbName,serviceName,interfaceName));
			ReadWriteFileWithEncode.write(serviceFile.getAbsolutePath(), serviceInfo.toString(), "UTF-8");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
