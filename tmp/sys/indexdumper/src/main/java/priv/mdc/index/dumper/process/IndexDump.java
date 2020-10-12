package priv.mdc.index.dumper.process;

import java.io.IOException;
import java.nio.charset.CodingErrorAction;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import priv.mdc.index.dumper.model.IndexDocReq;
import priv.mdc.index.dumper.util.ConfigInfo;



public class IndexDump {

	final static private Logger logger = LoggerFactory.getLogger(IndexDump.class);
	
	private static PoolingHttpClientConnectionManager cm;
	
	private static CloseableHttpClient httpclient;
	
	private static List<String> targetList = new ArrayList<String>();
	
	/**
	 * 初始化
	 */
	public static void init(){
		int conn_max = ConfigInfo.getInt("es.server.conn.max");
		int conn_max_per_route = ConfigInfo.getInt("es.server.conn.max_per_route");
		int timeout = ConfigInfo.getInt("es.server.timeout");
		int conn_timeout = ConfigInfo.getInt("es.server.timeout.connect");
		cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(conn_max);
		cm.setDefaultMaxPerRoute(conn_max_per_route);
        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setMalformedInputAction(CodingErrorAction.IGNORE)
                .setUnmappableInputAction(CodingErrorAction.IGNORE)
                .setCharset(Consts.UTF_8)
                .build();
		cm.setDefaultConnectionConfig(connectionConfig);
        SocketConfig socketConfig = SocketConfig.custom()
                .setTcpNoDelay(true)
                .build();
		cm.setDefaultSocketConfig(socketConfig);
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(timeout*1000)
                .setConnectTimeout(conn_timeout*1000)
                .setConnectionRequestTimeout(conn_timeout*1000)
                .build();
		httpclient = HttpClients.custom()
                .setConnectionManager(cm)
                .setDefaultRequestConfig(requestConfig)
                .build();
		//找出全部的可访问服务器地址
		String esServerList = ConfigInfo.getString("es.server.list");
		String[] addrArray = esServerList.split(",");
		for(int i=0; i<addrArray.length; ++i){
			String addr = addrArray[i];
			targetList.add(addr);
		}
	}
	
	/**
	 * 对ES执行一个动作
	 * @param action
	 * @param url
	 * @param body
	 */
	public static void executeAction(String action, String uri, String body){
		String url = null;
		if(!uri.startsWith("/")){
			url = "http://"+ targetList.get(0) + "/" + uri;
		}else{
			url = "http://"+ targetList.get(0) + uri;
		}
		HttpRequestBase httpMethod = null;
		if("DELETE".equals(action)){
			logger.info("DELETE : {}",url);
			httpMethod = new HttpDelete(url);
		}
		if("POST".equals(action)){
			logger.info("POST : {}",url);
			httpMethod = new HttpPost(url);
            StringEntity reqEntity = new StringEntity(body,"UTF-8");
            reqEntity.setContentType("application/json"); 
            ((HttpPost)httpMethod).setEntity(reqEntity);
		}
		if(httpMethod==null){
			logger.warn("Unknow action type : {}",action);
			return;
		}
        CloseableHttpResponse httpResponse = null;
        try {
			httpResponse = httpclient.execute(httpMethod);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			if(statusCode >= 200 && statusCode < 300){
				logger.info("Success");
			}else{
				logger.error("Failed");
			}
		} catch (ClientProtocolException ex) {
			logger.error(ex.getMessage(), ex);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		} finally {
			try {
				if(httpResponse!=null) httpResponse.close();
			} catch (IOException e) {
			}
        }
	}
	
	/**
	 * 灌索引文档
	 * @param indexDocReqList
	 */
	public static void dump(List<IndexDocReq> indexDocReqList){
		if(indexDocReqList==null ||indexDocReqList.isEmpty()){
			return;
		}
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<indexDocReqList.size(); ++i){
			sb.append(indexDocReqList.get(i).getBatachReqBody());
		}
		String reqBody = sb.toString();
		//向ES服务器发送请求，如果第一个ES服务器失败，则向第二个ES服务重试，直至成功，或者最终失败
		boolean updateSuccess = false;
		String updateSuccessUrl = null;
		for(int i=0; i<targetList.size(); ++i){
			String url = "http://"+ targetList.get(i) + "/_bulk";
			logger.info("Using target : {}",url);
			HttpPost httpPost = new HttpPost(url);
            StringEntity reqEntity = new StringEntity(reqBody,"UTF-8");
            reqEntity.setContentType("application/json"); 
            httpPost.setEntity(reqEntity);
            CloseableHttpResponse httpResponse = null;
            try {
				httpResponse = httpclient.execute(httpPost);
				int statusCode = httpResponse.getStatusLine().getStatusCode();
				if(statusCode >= 200 && statusCode < 300){
					//Success
					updateSuccess = true;
					updateSuccessUrl = url;
					break;
				}else{
					logger.error("DumpFailed : {}",url);
					continue;
				}
			} catch (ClientProtocolException ex) {
				logger.error(ex.getMessage(), ex);
			} catch (Exception ex) {
				logger.error(ex.getMessage(), ex);
			} finally {
				try {
					if(httpResponse!=null) httpResponse.close();
				} catch (IOException e) {
				}
            }
		}
		if(updateSuccess){
			logger.info("DumpSuccess : url = {}\n{}\n",updateSuccessUrl,reqBody);
		}else{
			logger.error("DumpFailed : {}\n",reqBody);
		}
	}
	
}
