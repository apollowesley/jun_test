package codeGenerate.mybatis.tempalte;

import java.util.Map;

import codeGenerate.mybatis.vo.ConfigVo;


/**
 * 模版路径抽象
 * @author Administrator
 *
 */
public abstract class TemPathAbstract {

	/**
	 * @description: 根据配置生成模版对应的文件路径映射map，其中key为模版路径，value为对应生成文件后的路径
	 * @creator: dw-fu1
	 * @createDate: 2018年2月26日 
	 * @modifier:
	 * @modifiedDate:
	 * @param configVo
	 * @return
	 */
	public abstract Map<String,String> getPathMap(ConfigVo configVo);
}
