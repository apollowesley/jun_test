package com.thx.file.util;
/**
 * excel结合资源管理导出图片默认实现类,导出方法直接传此类的实例即可
 * 
 * 建立日期 : 2014年8月20日 上午11:01:54<br>
 * 作者 : cys<br>
 * 模块 : <br>
 * 描述 : <br>
 * 修改历史: 序号 日期 修改人 修改原因 <br>
 */
public class ExcelExtendsImpl {
	/**
	 * 获取图片绝对路径
	 * @param pictureId
	 * @return
	 */
	public String getPicturePath(String pictureId){
		return WebFileUtil.getFileById(pictureId).getPath();
	}
}
