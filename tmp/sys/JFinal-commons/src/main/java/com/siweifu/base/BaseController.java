package com.siweifu.base;

import com.jfinal.core.Controller;
import com.jfinal.log.Log;

/**
 * 通用控制器，用于添加通用方法
 * @title BaseController.java
 * @description 
 * @company 北京思维夫网络科技有限公司
 * @author 卢春梦
 * @version 1.0
 * @created 2015年5月18日上午11:20:17
 */
public abstract class BaseController extends Controller {

	protected final Log logger = Log.getLog(getClass());

	/**
	 * 跳转错误页
	 */
	protected void renderError(String url, String msg, Integer... time) {
		this.setAttr("url", url);
		this.setAttr("error", msg);
		this.setAttr("the_time", time.length==0 ? 6 : time[0].intValue()); //默认6秒
		render("/commons/error.html");
	}

}
