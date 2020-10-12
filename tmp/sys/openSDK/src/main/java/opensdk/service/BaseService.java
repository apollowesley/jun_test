package opensdk.service;

import opensdk.OpenClient;

/**
 * 基础service,其它service需要继承这个类
 * 
 */
public class BaseService {
	private OpenClient client = null;

	public BaseService(String appId, String secret,String serverCtx) {
		client = new OpenClient(appId, secret, serverCtx);
	}

	public OpenClient getClient() {
		return client;
	}
}
