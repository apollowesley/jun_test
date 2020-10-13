import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.ParseException;
import com.xlz.engine.common.config.Way;
import com.xlz.engine.common.model.Application;
import com.xlz.engine.common.model.ApplicationService;
import com.xlz.engine.common.model.Strategy;
import com.xlz.engine.common.result.Result;

public class JsonTest {

	public static void main(String[] args) {
		new JsonTest().init();
	}
	public void init(){
		String url = "http://100.66.153.186/gray/pullApplicationConfig/applicationConfig";
		String param = "applicationId=paygent&engineType=nginx";
		String json = sendGet( url,  param);
		try {
			Result map = JSON.parse(json, Result.class);
			Map<?,?> data = (Map<?,?>)map.getObj();
			
			Application application = new Application(data.get("name").toString(),
					data.get("applicationId").toString(), 
					data.get("param").toString(), 
					new Integer(data.get("status").toString()) );
			
			String instances[] = data.get("grayIp").toString().split(";");
			for(String instance : instances){
				application.getGrayInstance().add(instance);
			}
			
			application.setStrategy(buildStrategy(data));
			
			List<Map<?,?>> servicesMap = (List<Map<?,?>>)data.get("services");
			for(Map<?,?> service : servicesMap){
				ApplicationService applicationService = new ApplicationService (service.get("name").toString(), 
						service.get("version").toString() , 
						service.get("param").toString() , 
						new Integer(service.get("status").toString()) );
				applicationService.setStrategy(buildStrategy(service));
				applicationService.setWhitelists( buildWhitelists(service));
				
				application.getServices().put(applicationService.getName(), applicationService);
			}
			application.setWhitelists( buildWhitelists(data));
			
			System.out.println(application);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private Strategy buildStrategy(Map<?,?> data){
		Map<?,?> strategyMap = (Map<?,?>)data.get("strategy");
		Strategy strategy = new Strategy();
		if(strategyMap.get("name")!= null){
			strategy.setName(strategyMap.get("name").toString());
		}
		if(strategyMap.get("forwardReverse")!= null){
			strategy.setForwardReverse(new Integer(strategyMap.get("forwardReverse").toString()));
		}
		if(strategyMap.get("flowTatio")!= null){
			strategy.setFlowTatio(new Integer(strategyMap.get("flowTatio").toString()));
		}
		if(strategyMap.get("regular")!= null){
			strategy.setRegular(Pattern.compile(strategyMap.get("regular").toString()));
		}
		if(strategyMap.get("way")!= null){
			strategy.setWay(Way.valueOf(strategyMap.get("way").toString()));
		}
		return strategy;
	}
	
	private Set<String> buildWhitelists(Map<?,?> data){
		List<Map<?,?>> whitelistsMap = (List<Map<?,?>>) data.get("whitelists");
		Set<String> whitelists = new HashSet<String>();
		if(whitelistsMap == null){
			return whitelists;
		}
		for(Map<?,?> whitelist : whitelistsMap){
			String param = whitelist.get("param").toString();
			try {
				Map<String,String> map = (Map<String,String>)JSON.parse(param, Map.class);
				for(Map.Entry<String,String> entry : map.entrySet()){
					whitelists.add(entry.getKey()+":"+entry.getValue());
				}
			} catch (ParseException e) {
			}
			
		}
		return whitelists;
		
	}
	/**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
}
