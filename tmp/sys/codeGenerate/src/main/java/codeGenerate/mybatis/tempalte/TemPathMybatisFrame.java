
package codeGenerate.mybatis.tempalte;

import java.util.LinkedHashMap;
import java.util.Map;

import codeGenerate.mybatis.vo.ConfigVo;

public class TemPathMybatisFrame extends TemPathAbstract {

	/**
	 * 模版相对根路径
	 */
	private static final String RELATIVE_PATH_ROOT = "/mybatis_frame";

	@Override
	public Map<String, String> getPathMap(ConfigVo configVo) {

		Map<String, String> fileMap = new LinkedHashMap<String, String>();
		//后台代码
		String srcPath = configVo.getGenPath() + "/src/" + configVo.getPack().replace(".", "/");
		fileMap.put(RELATIVE_PATH_ROOT + "/src/common/constants.ftl", srcPath + "/common/" + configVo.getBeanName() + "Constants.java");
		fileMap.put(RELATIVE_PATH_ROOT + "/src/entity/pojo.ftl", srcPath + "/entity/" + configVo.getBeanName() + ".java");
		fileMap.put(RELATIVE_PATH_ROOT + "/src/entity/pojoQuery.ftl", srcPath + "/entity/" + configVo.getBeanName() + "Qo.java");
		fileMap.put(RELATIVE_PATH_ROOT + "/src/entity/pojoShow.ftl", srcPath + "/entity/" + configVo.getBeanName() + "Vo.java");
		fileMap.put(RELATIVE_PATH_ROOT + "/src/mappers/interfaceMapper.java.ftl", srcPath + "/mappers/" + configVo.getBeanName() + "Mapper.java");
		fileMap.put(RELATIVE_PATH_ROOT + "/src/mappers/mapperBase.xml.ftl", srcPath + "/mappers/" + configVo.getBeanName() + "MapperBase.xml");
		fileMap.put(RELATIVE_PATH_ROOT + "/src/mappers/mapperExtend.xml.ftl", srcPath + "/mappers/" + configVo.getBeanName() + "MapperExtend.xml");
		fileMap.put(RELATIVE_PATH_ROOT + "/src/service/impl/serviceImpl.ftl", srcPath + "/service/impl/" + configVo.getBeanName() + "ServiceImpl.java");
		fileMap.put(RELATIVE_PATH_ROOT + "/src/service/service.ftl", srcPath + "/service/" + configVo.getBeanName() + "Service.java");
		fileMap.put(RELATIVE_PATH_ROOT + "/src/web/controller.ftl", srcPath + "/web/" + configVo.getBeanName() + "Controller.java");
		//前台代码
		String jspFileName = configVo.getBeanName().substring(0, 1).toLowerCase() + configVo.getBeanName().substring(1);
		String jspPath = configVo.getGenPath() + "/jsp/" + jspFileName;
		fileMap.put(RELATIVE_PATH_ROOT + "/jsp/view.ftl", jspPath + "/view.jsp");
		fileMap.put(RELATIVE_PATH_ROOT + "/jsp/list.ftl", jspPath + "/list.jsp");
		fileMap.put(RELATIVE_PATH_ROOT + "/jsp/edit.ftl", jspPath + "/edit.jsp");
		fileMap.put(RELATIVE_PATH_ROOT + "/jsp/add.ftl", jspPath + "/add.jsp");
		return fileMap;
	}

}
