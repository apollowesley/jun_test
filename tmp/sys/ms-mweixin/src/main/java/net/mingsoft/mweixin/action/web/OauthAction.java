package net.mingsoft.mweixin.action.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.mingsoft.base.entity.ResultJson;
import com.mingsoft.people.constant.e.SessionConstEnum;
import com.mingsoft.people.entity.PeopleEntity;
import com.mingsoft.util.StringUtil;
import com.mingsoft.weixin.biz.IWeixinBiz;
import com.mingsoft.weixin.entity.WeixinEntity;
import com.mingsoft.weixin.entity.WeixinPeopleEntity;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import net.mingsoft.base.exception.BusinessException;
import net.mingsoft.basic.util.BasicUtil;
import net.mingsoft.mweixin.biz.IWeixinPeopleBiz;
import net.mingsoft.weixin.service.PortalService;
	
/**
 * 微信网页2.0授权表管理控制层
 * @author 铭飞开发团队
 * @version 
 * 版本号：1.0.0<br/>
 * 创建日期：2017-5-25 22:04:59<br/>
 * 历史修订：<br/>
 */
@Controller("netOauthActionWeb")
@RequestMapping("/mweixin/oauth")
public class OauthAction extends com.mingsoft.weixin.action.BaseAction{
	@Resource(name="netWeixinPeopleBiz")
	private IWeixinPeopleBiz weixinPeopleBiz;
	@Autowired
	private PortalService wxService;
	@Autowired
	private IWeixinBiz weixinBiz;
	
	/**
	 * 授权登录
	 */
	@RequestMapping("/getUrl")
	public String getUrl(@RequestParam(required = false) String weixinNo,
						 @RequestParam(required = false) String url,
						 HttpServletResponse response,HttpServletRequest request){
		if(!StringUtil.isBlank(url)) {
			try {
				url = URLEncoder.encode(url,"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		if ((request.getHeader("accept").indexOf("application/json") > -1
				|| (request.getHeader("X-Requested-With") != null
						&& request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
			LOG.debug("ajax login request out err json");
			try {
				PrintWriter writer = response.getWriter();
				ResultJson result = new ResultJson();
				result.setResult(false);
				result.setResultMsg("login err");
				writer.write(JSONObject.toJSONString(result));
				writer.flush();
				writer.close();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		//获取微信服务
		WeixinEntity weixin = weixinBiz.getByWeixinNo(weixinNo);
		wxService = wxService.build(weixin);
		//组织重定向链接，通过链接进行授权
		String oauthUrl = BasicUtil.getUrl()+"/mweixin/oauth.do?weixinNo="+weixinNo+"&url="+url;
		return "redirect:"+wxService.oauth2buildAuthorizationUrl(oauthUrl, "snsapi_userinfo" , null);
		
	}
	
	/**
	 * 授权登录
	 */
	@ResponseBody
	@GetMapping(produces = "text/plain;charset=utf-8")
	public void login(HttpServletResponse response,HttpServletRequest request)throws ServletException, IOException {
		String weixinNo = request.getParameter("weixinNo"); //获取token
		WeixinEntity weixin = weixinBiz.getByWeixinNo(weixinNo);
		wxService = wxService.build(weixin);
		String code = request.getParameter("code"); //用户同意授权就可以获得
		String url = BasicUtil.getString("url");
		
		try {
			//通过code获取用户token
			WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxService.oauth2getAccessToken(code);
			//通过用户token获取用户信息
			WxMpUser user = wxService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
			weixinPeopleBiz.saveOrUpdate(user, weixin.getWeixinId());
			
			//将用户压入session:weixn_people_session
			WeixinPeopleEntity wpe = weixinPeopleBiz.getEntityByOpenIdAndAppIdAndWeixinId(user.getOpenId(), BasicUtil.getAppId(), weixin.getWeixinId());
			LOG.debug("微信授权设置用户session：" + wpe);
			BasicUtil.setSession(SessionConstEnum.PEOPLE_SESSION,wpe);
			//将微信实体压入session:weinxin_session
			this.setWeixinSession(request,com.mingsoft.weixin.constant.SessionConst.WEIXIN_SESSION,weixin);
			//重定向
			response.sendRedirect(url);
		} catch (WxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}