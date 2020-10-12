package com.zyguo.tools.push;





public class IOSPush extends AbstractAndroidPush{
	private String appKey = "3aIfN4ILnQV8mDMUyE24fw=="; // 小米
	private String packageName = "com.bilin.huijiao.activity"; // 小米

	@Override
	protected String dealPushResponse(Object response, PushBody pushBody) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object pushBody2message(PushBody pushBody, boolean isToApp ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Object sendPush(Object msg, String token, boolean isToApp ) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	

	
}
