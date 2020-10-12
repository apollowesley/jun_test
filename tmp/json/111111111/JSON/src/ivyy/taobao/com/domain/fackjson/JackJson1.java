package ivyy.taobao.com.domain.fackjson;

import ivyy.taobao.com.entity.About;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *@Author:liangjl
 *@Date:2015-1-5
 *@Version:1.0
 *@Description:jackjson��֧��'��:
 *��ȷ��jsonStr="[{\"age\":22,\"sex\":\"��\",\"userName\":\"xiaoliang\"},{\"age\":22,\"sex\":\"��\",\"userName\":\"xiaoliang\"}]";
 *����jsonStr="[{'age':22,'sex':'��','userName':'xiaoliang'},{'age':22,'sex':'��','userName':'xiaoliang'}]";
 */
public class JackJson1 {
	private static ObjectMapper mapper=new ObjectMapper();
	public static void main(String[] args)throws Exception {
		//json1.json
		
		StringBuffer buff=new StringBuffer();
		
		buff.append("[");
			buff.append("{");
				buff.append("\"weixin\"").append(":").append("\"YFDSBUYI\"").append(",");
				buff.append("\"weibo\"").append(":").append("\"http://weibo.com/resourceljl\"").append(",");
				buff.append("\"qq\"").append(":").append("\"1302128216\"").append(",");
				buff.append("\"email\"").append(":").append("\"buyee_hr@126.com\"").append(",");
				buff.append("\"address\"").append(":").append("\"�㶫ʡ�Ƹ����Ƴ�����ݺ·39��\"").append("");
			buff.append("}");
			buff.append(",");//��һ�������β
			buff.append("{");
				buff.append("\"weixin\"").append(":").append("\"YFDSBUYI\"").append(",");
				buff.append("\"weibo\"").append(":").append("\"http://weibo.com/resourceljl\"").append(",");
				buff.append("\"qq\"").append(":").append("\"1302128216\"").append(",");
				buff.append("\"email\"").append(":").append("\"buyee_hr@126.com\"").append(",");
				buff.append("\"address\"").append(":").append("\"�㶫ʡ�Ƹ����Ƴ�����ݺ·39��\"").append("");
			buff.append("}");
		buff.append("]");
		
		String  jsonStr=buff.toString();
		
		System.out.println(jsonStr);
		//jsonStr=mapper.writeValueAsString(jsonStr);
		
		JsonFactory factory=new JsonFactory();
		
		JsonParser parser=factory.createJsonParser(jsonStr);
		
		parser.nextToken();
		while (parser.nextToken()==JsonToken.START_OBJECT) {
			About a=mapper.readValue(parser, About.class);
			System.out.println(a.getAddress());
			
		}
		
	}
}
