package com.luoqy.speedy.modular.web.controller;

import com.luoqy.speedy.data.MySqldbUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author luoqy
 * @date 2019年8月30日
 *	页面跳转控制器 
 *
 */
@Controller
public class ViewController {
	/**
	 *  动态加载页面时跳转控制,不添加任何非公共的静态属性
	 * */
	@RequestMapping("/*.html")
	public String ajaxWebFile(HttpServletRequest req,Model model){
		try{
			Object base= MySqldbUtil.mysqlSelect("select * from speedy_base","none",null);
			model.addAttribute("base",base);
			String fileHtml=req.getServletPath();
			return "view"+fileHtml;
		}catch (Exception e){
			return "error/404.html";
		}

	}
    /**
     *  文章详情页
     * */
    @RequestMapping("/detail.html")
    public String webFile(String id, Model model){
        Object base= MySqldbUtil.mysqlSelect("select * from speedy_base","none",null);
        model.addAttribute("base",base);
        return "view/article/"+id;
    }
	/**
	 *   首页跳转
	 * */
	@RequestMapping("/index.html")
	public String index(Model model){
		Object base=MySqldbUtil.mysqlSelect("select * from speedy_base","none",null);
		model.addAttribute("base",base);
		//推荐5条
		model.addAttribute("sticks",MySqldbUtil.mysqlSelect("select id,title,createtime,keywords from speedy_article where stick=1 and type=1 and tag is null order by createtime limit 0,5","list",null));
		//要点5条
		model.addAttribute("homes",MySqldbUtil.mysqlSelect("select id,title,createtime,keywords from speedy_article where type=1 and home=1 and tag is null order by createtime limit 0,5","list",null));
		//最新9条
		Object news=MySqldbUtil.mysqlSelect("select *,(select name from speedy_articletype where speedy_articletype.id=speedy_article.columntype) columntype from speedy_article where type=1 and tag is null order by createtime limit 0,9","list",null);
		model.addAttribute("news",news);
		return "view/index.html";
	}
	/**
	 *   关于我 跳转
	 * */
	@RequestMapping("/about.html")
	public String about(Model model){
		Object base=MySqldbUtil.mysqlSelect("select * from speedy_base","none",null);
		model.addAttribute("base",base);
		//"++"
		String sql="select * from speedy_article where tag='about'";
		model.addAttribute("content",MySqldbUtil.mysqlSelect(sql,"none",null));
		//推荐5条
		model.addAttribute("sticks",MySqldbUtil.mysqlSelect("select id,title,createtime,keywords from speedy_article where stick=1 and type=1 and tag is null order by createtime limit 0,5","list",null));
		//要点5条
		model.addAttribute("homes",MySqldbUtil.mysqlSelect("select id,title,createtime,keywords from speedy_article where type=1 and home=1 and tag is null order by createtime limit 0,5","list",null));
		return "view/about.html";
	}
	/**
	 *   业务跳转
	 * */
	@RequestMapping("symbiosis.html")
	public String symbiosis(Model model){
		Object base=MySqldbUtil.mysqlSelect("select * from speedy_base","none",null);
		model.addAttribute("base",base);
		String sql="select * from speedy_article where tag='symbiosis'";
		model.addAttribute("content",MySqldbUtil.mysqlSelect(sql,"none",null));
		//推荐5条
		model.addAttribute("sticks",MySqldbUtil.mysqlSelect("select id,title,createtime,keywords from speedy_article where stick=1 and type=1 and tag is null order by createtime limit 0,5","list",null));
		//要点5条
		model.addAttribute("homes",MySqldbUtil.mysqlSelect("select id,title,createtime,keywords from speedy_article where type=1 and home=1 and tag is null order by createtime limit 0,5","list",null));
		return "view/symbiosis.html";
	}
}
