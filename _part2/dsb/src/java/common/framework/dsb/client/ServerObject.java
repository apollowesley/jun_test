package common.framework.dsb.client;

import java.io.Serializable;

import common.framework.dsb.proxy.ServiceProxy;

public class ServerObject implements Serializable {
	/**
	 * Service 代理，属于远程接口,代理服务端所有service调用
	 */
	ServiceProxy serviceProxy = null;
	/**
	 * 服务端IP地址
	 */
	String host = null;
	/**
	 * 服务端端口
	 */
	int port;
}
