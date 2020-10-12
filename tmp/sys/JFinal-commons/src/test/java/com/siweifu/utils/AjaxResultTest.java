package com.siweifu.utils;

import com.siweifu.vo.AjaxResult;

public class AjaxResultTest {

	public static void main(String[] args) {
		AjaxResult r = new AjaxResult();
		r.addConfirmError("请先登陆");

		System.out.println(r.toString());
	}

}
