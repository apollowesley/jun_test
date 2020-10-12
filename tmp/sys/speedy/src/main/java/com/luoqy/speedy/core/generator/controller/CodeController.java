package com.luoqy.speedy.core.generator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luoqy.speedy.common.CodeGenerate;
import com.luoqy.speedy.core.generator.config.CodeConfig;
import com.luoqy.speedy.data.MySqldbUtil;
import com.luoqy.speedy.util.Result;
/**
 * @author luoqy
 * @date 2019年7月10日
 * 代码生成控制类
 */
@Controller
@RequestMapping("/code")
public class CodeController {
	@RequestMapping("")
	public String index(Model model){
		//这里加载所有的表和注释，
		Object tables=MySqldbUtil.mysqlSelect("SELECT TABLE_NAME AS tablename,TABLE_COMMENT AS comment FROM information_schema. TABLES WHERE table_type = 'BASE TABLE' AND table_schema = DATABASE ();", "list",null);
		model.addAttribute("tables", tables);
		return "WEB-INF/code/code";
	}
	@RequestMapping("/generate")
	@ResponseBody
	public Result generate(CodeConfig condeConig){
		Result result=new Result();
		String moduleName=condeConig.getTablename().replace(condeConig.getIgnoreTabelPrefix(), "");
		condeConig.setModuleName(moduleName);
		if(null==condeConig.getProjectPackage()||"".equals(condeConig.getProjectPackage())){
			condeConig.setProjectPackage("com.luoqy.speedy.modular.system");
		}
		CodeGenerate.codeGenerate(condeConig);
		result.setData(new String[0]);
		result.setMessage("生成成功");
		result.setState(1);
		return result;
	}
}
