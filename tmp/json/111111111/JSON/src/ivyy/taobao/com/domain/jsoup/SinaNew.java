package ivyy.taobao.com.domain.jsoup;

import ivyy.taobao.com.utils.GlobalConstants;

import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *@Author:liangjilong
 *@Date:2015-1-4
 *@Email:jilongliang@sina.com
 *@Version:1.0
 *@Description�����ͨ��jsoup�����
 */
public class SinaNew {
	
	public static void main(String[] args)throws Exception {
		String requestURL = GlobalConstants.getSinaNewUrl(2, "xml");
	    org.jsoup.nodes.Document doc=Jsoup.parse(new URL(requestURL), 3000);
		// String html=doc.html();
	    Elements items=doc.select("item");//��ȡitem��item���ж���ڵ㣩
	    
	    String title = "", url = "", keywords = "", img = "", media_name = "";
	    int i=1;
	    for (Element its : items) {
			
	    	title=its.select("title").html();
	    	url=its.select("url").html();
	    	keywords=its.select("keywords").html();
	    	img=its.select("img").html();
	    	media_name=its.select("media_name").html();
	    	
	    	String newsText=GlobalConstants.getNewsContent(url);//������������
	    	
	    	//System.out.println(title + "\n" + url + "\n" + keywords + "\n"+ url + "\n" + media_name);
	    	
	    	System.out.println("==================��"+i+"ƪ=================="+newsText);
	    	i++;
		}
	}
}
