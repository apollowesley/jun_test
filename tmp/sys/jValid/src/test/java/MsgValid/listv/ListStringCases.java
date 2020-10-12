package MsgValid.listv;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import test.model.Cup;
import validator.MsgValidator;

import com.alibaba.fastjson.JSONArray;

/**
 * 集合验证
 */
public class ListStringCases {
	@Test
	public void list1(){
//		1.required - 不能为空
		Cup cup = new Cup();
		Cup cup2 = new Cup("aaa");
		List cs  = new ArrayList();
		cs.add(cup);
		cs.add(cup2);
		String cfgs = "{name:'required'}";
		assertEquals(false, MsgValidator.valid(cs, cfgs));
		System.out.println("msg:"+MsgValidator.msg());
	}
	
	//JsonArray的验证：
	@Test
	public void jsonArray(){
//		1.required - 不能为空
		String css = "[{name:'aa'},{name:'bb'},{name:'cc'},{name:''}]";
		JSONArray cs = JSONArray.parseArray(css);
		String cfgs = "{name:'required'}";
		assertEquals(false, MsgValidator.valid(cs, cfgs));
		System.out.println("msg:"+MsgValidator.msg());
	}
	
}
