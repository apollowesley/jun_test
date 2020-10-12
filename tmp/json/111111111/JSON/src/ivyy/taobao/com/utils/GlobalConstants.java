package ivyy.taobao.com.utils;

import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 *@Author:liangjilong
 *@Date:2015-1-4
 *@Email:jilongliang@sina.com
 *@Version:1.0
 *@Description
 */
public class GlobalConstants {
	
	/***
	 * ��ȡurl����
	 * @param page�ڼ�ҳ
	 * @param format��ʽ��XML��JSON��
	 * @return
	 */
	public static String getSinaNewUrl(Integer page,String format){
		StringBuffer buffer=new StringBuffer("http://api.roll.news.sina.com.cn/zt_list?channel=news");
		String url="";
		buffer.append("&cat_1=shxw");//��ʾ����
		buffer.append("&cat_2==zqsk||=qwys||=shwx||=fz-shyf");
		buffer.append("&level==1||=2");//����
		buffer.append("&show_ext=1");
		buffer.append("&show_all=1");//��ʾ����
		buffer.append("&show_num=22");//��ʾ������
		buffer.append("&tag=1");
		buffer.append("&format="+format);
		buffer.append("&page="+page);
		buffer.append("&callback=newsloader");
		url=buffer.toString();
		return url;
	}
	
	/***
	 * ��ȡ�ٶȵ�ͼ��url
	 * @param ak��Կ�����ڰٶ������key���÷��ʵ�ͼ��key
	 * @param location�ط��ľ�γ��
	 * @param format��ʽ��json����xml��
	 * @return
	 */
	public static String getBaiduMapUrl(String ak,String location,String format){
		StringBuffer buffer=new StringBuffer("http://api.map.baidu.com/geocoder/v2/?");
		String url="";
		buffer.append("ak="+ak);
		buffer.append("&callback=renderReverse");
		buffer.append("&location="+location);
		buffer.append("&output="+format);
		buffer.append("&pois=1");
		url=buffer.toString();
		
		return url;
	}
	
	/***
	 * ��ȡ���µ�����
	 * �����˵���ҳ������ͨ������body��id�Ϳ����õ���Ӧ����������..
	 * @param url
	 * @return
	 */
	public static String getNewsContent(String url) throws Exception{
		Document doc=Jsoup.parse(new URL(url), 3000);
		if(doc!=null){
			String artibody=doc.getElementById("artibody").html();//ͨ����ҳ��html��idȥ�õ���������artibody
			return artibody;
		}else{
			return "�����쳣";
		}
	}
}
