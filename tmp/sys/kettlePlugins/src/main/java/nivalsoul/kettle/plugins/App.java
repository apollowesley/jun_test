package nivalsoul.kettle.plugins;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.stuxuhai.jpinyin.ChineseHelper;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.google.common.collect.Maps;
import org.elasticsearch.action.DocWriteRequest;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
    	/*String filename = "F:\\FTP\\abc\\dd.xlsx";
		List<List<String>> lists = XLSX2CSV.readToList(new FileInputStream(filename));
		for (List<String> list : lists) {
			System.out.println(list);
		}*/
		/*JSONObject jsonObject = new JSONObject();
		System.out.println(jsonObject.getJSONArray("aa").size());*/

		Settings settings = Settings.builder()
				.put("cluster.name", "elasticsearch").build();
		TransportClient client = new PreBuiltTransportClient(settings);
		client.addTransportAddress(new TransportAddress(InetAddress.getByName("localhost"), 9300));
		
		List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = Maps.newHashMap();
		map.put("k1", "adf");
		map.put("k2", 234);
		list.add(map);
		map = Maps.newHashMap();
		map.put("k3", "ddd");
		map.put("k4", 123);
		list.add(map);
		System.out.println(JSON.toJSONString(list));
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> stringObjectMap =  list.get(i);
			IndexRequestBuilder req = client.prepareIndex("t2", "userinfo");
			req.setOpType(DocWriteRequest.OpType.CREATE);
			//req.setId("");
			req.setSource(stringObjectMap);
			bulkRequest.add(req);
		}
		BulkResponse bulkResponse = bulkRequest.get();
		if (bulkResponse.hasFailures()) {
			System.out.println(bulkResponse.buildFailureMessage());
		}


		String str = "你好世界";
		//System.out.println(PinyinHelper.convertToPinyinString(str, ",", PinyinFormat.WITH_TONE_MARK)); // nǐ,hǎo,shì,jiè
		//System.out.println(PinyinHelper.convertToPinyinString(str, ",", PinyinFormat.WITH_TONE_NUMBER)); // ni3,hao3,shi4,jie4
		System.out.println(PinyinHelper.convertToPinyinString(str, "", PinyinFormat.WITHOUT_TONE)); // ni,hao,shi,jie

		System.out.println(ChineseHelper.convertToTraditionalChinese("用笔记本电脑写程序"));
		System.out.println(ChineseHelper.convertToSimplifiedChinese("「以後等妳當上皇后，就能買士多啤梨慶祝了」"));
    }
}
