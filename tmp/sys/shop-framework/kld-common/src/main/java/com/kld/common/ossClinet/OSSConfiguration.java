package com.kld.common.ossClinet;

import com.aliyun.oss.ClientConfiguration;

public class OSSConfiguration {
    public ClientConfiguration conf = new ClientConfiguration();
    /**
     * Provide OSS endpoint
     */
    private String endpoint;
    /**
     * Provide your AccessKeyId
     */
    private String accessKeyId;
    /**
     * Provide your AccessKeySecret
     */
    private String accessKeySecret;

    /**
     * 设置用户代理。
     *
     * @param userAgent 用户代理。
     */
    public void setUserAgent(String userAgent) {
        conf.setUserAgent(userAgent);
    }

    /**
     * 设置代理服务器主机地址。
     *
     * @param proxyHost 代理服务器主机地址。
     */
    public void setProxyHost(String proxyHost) {

        conf.setProxyHost(proxyHost);
    }


    /**
     * 设置代理服务器端口。
     *
     * @param proxyPort 代理服务器端口。
     */
    public void setProxyPort(int proxyPort) {

        conf.setProxyPort(proxyPort);
    }


    /**
     * 设置代理服务器验证的用户名。
     *
     * @param proxyUsername 用户名。
     */
    public void setProxyUsername(String proxyUsername) {

        conf.setProxyUsername(proxyUsername);
    }


    /**
     * 设置代理服务器验证的密码。
     *
     * @param proxyPassword 密码。
     */
    public void setProxyPassword(String proxyPassword) {

        conf.setProxyPassword(proxyPassword);
    }

    /**
     * 设置访问NTLM验证的代理服务器的Windows域名（可选）。
     *
     * @param proxyDomain 域名。
     */
    public void setProxyDomain(String proxyDomain) {

        conf.setProxyDomain(proxyDomain);
    }

    /**
     * 设置NTLM代理服务器的Windows工作站名称。
     * （可选，如果代理服务器非NTLM，不需要设置该参数）。
     *
     * @param proxyWorkstation NTLM代理服务器的Windows工作站名称。
     */
    public void setProxyWorkstation(String proxyWorkstation) {

        conf.setProxyWorkstation(proxyWorkstation);
    }

    /**
     * 设置允许打开的最大HTTP连接数。
     *
     * @param maxConnections 最大HTTP连接数。
     */
    public void setMaxConnections(int maxConnections) {

        conf.setMaxConnections(maxConnections);
    }


    /**
     * 设置通过打开的连接传输数据的超时时间（单位：毫秒）。
     * 0表示无限等待（但不推荐使用）。
     *
     * @param socketTimeout 通过打开的连接传输数据的超时时间（单位：毫秒）。
     */
    public void setSocketTimeout(int socketTimeout) {

        conf.setSocketTimeout(socketTimeout);
    }

    /**
     * 设置建立连接的超时时间（单位：毫秒）。
     *
     * @param connectionTimeout 建立连接的超时时间（单位：毫秒）。
     */
    public void setConnectionTimeout(int connectionTimeout) {

        conf.setConnectionTimeout(connectionTimeout);
    }


    /**
     * 设置一个值表示当可重试的请求失败后最大的重试次数。（默认值为3）
     *
     * @param maxErrorRetry 当可重试的请求失败后最大的重试次数。
     */
    public void setMaxErrorRetry(int maxErrorRetry) {

        conf.setMaxErrorRetry(maxErrorRetry);
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }
}
