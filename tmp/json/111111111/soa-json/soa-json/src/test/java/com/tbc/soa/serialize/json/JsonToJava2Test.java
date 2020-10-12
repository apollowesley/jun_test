package com.tbc.soa.serialize.json;

import static org.junit.Assert.assertEquals;


import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.tbc.soa.json.JSONDeserializer;
import com.tbc.soa.json.JSONSerializer;

public class JsonToJava2Test {
	String simpleJson = "{\"birthDay\":1282555532182,\"class\":\"com.tbc.soa.serialize.json.Person\",\"map\":{\"key\":\"value\"},\"user\":{\"class\":\"com.tbc.soa.serialize.json.User\",\"name\":\"myName\"}}";
	String complexJson = "{\"birthDay\":1282555532182,\"class\":\"com.tbc.soa.serialize.json.Person\",\"map\":{\"key\":\"value\"},\"phoneNumbers\":[\"abc\"],\"user\":{\"class\":\"com.tbc.soa.serialize.json.User\",\"name\":\"myName\"},\"users\":[{\"class\":\"com.tbc.soa.serialize.json.User\",\"name\":\"myName\"}]}";
	String complexJsonWithoutClassHint = "{\"birthDay\":1282555532182,\"map\":{\"key\":\"value\"},\"phoneNumbers\":[\"abc\"],\"user\":{\"name\":\"myName\"},\"users\":[{\"name\":\"myName\"}]}";

	String objectWithClassProperty = "{\"class\":\"com.tbc.soa.serialize.json.ObjectWithClassProperty\",\"type\":\"java.lang.Integer\",\"typeArray\":[\"java.lang.String\",\"java.lang.Integer\"]}";
	String mapProperty = "{\"class\":\"com.tbc.soa.serialize.json.Person\",\"map\":{\"Key\":\"Value\"}}";
	String mapStringToUserProperty = "{\"class\":\"com.tbc.soa.serialize.json.Person\",\"mapStringToUser\":{\"Key\":{\"class\":\"com.tbc.soa.serialize.json.User\"}}}";
	String enumProperty = "{\"class\":\"com.tbc.soa.serialize.json.Person\",\"myEnum\":\"A\"}";
	
	String tz = "{\"first\":1,\"total\":52,\"pageNo\":1,\"pageSize\":10,\"autoCount\":true,\"rows\":[{\"content\":\"\",\"userName\":\"系统管理员\",\"reason\":null,\"corpCode\":\"yuanyuan\",\"extMap\":null,\"createBy\":\"aaceb565f3da4288a0f52603f1c57a47\",\"createTime\":\"2012-02-07 13:05:22\",\"lastModifyBy\":\"aaceb565f3da4288a0f52603f1c57a47\",\"lastModifyTime\":\"2012-02-07 13:05:22\",\"optTime\":22143185,\"answer\":null,\"questionId\":\"89643205a2d54916a68e0a8c44b908bc\",\"ip\":null,\"isDelete\":false,\"operationType\":null,\"userFaceUrl\":\"http://dev.cne1n.net/sf-server/file/getFile/f7b86909df2facc31e0ad5aaa1aeaf46-S_1328593338906/4f0d35ed63d970b4f0bbef0d_0100/small\",\"modifyType\":null,\"title\":\"加加减减\",\"shareInWeibo\":false,\"answerCount\":0,\"answerList\":null,\"topic\":null,\"isFavorite\":false,\"isAnswerred\":null},{\"content\":\"asfsafsafsafsafasfsafsafsafsafasfsafsafsafsafasfsafsafsafsafasfsafsafsafsafasfsafsafsafsafasfsafsafsafsafasfsafsafsafsafasfsafsafsafsafasfsafsafsafsafasfsafsafsafsaf\",\"userName\":\"系统管理员\",\"reason\":null,\"corpCode\":\"yuanyuan\",\"extMap\":null,\"createBy\":\"aaceb565f3da4288a0f52603f1c57a47\",\"createTime\":\"2012-02-06 15:52:51\",\"lastModifyBy\":\"aaceb565f3da4288a0f52603f1c57a47\",\"lastModifyTime\":\"2012-02-07 13:01:46\",\"optTime\":22141912,\"answer\":{\"content\":\"33333\",\"userName\":\"系统管理员\",\"reason\":null,\"corpCode\":\"yuanyuan\",\"extMap\":null,\"createBy\":\"aaceb565f3da4288a0f52603f1c57a47\",\"createTime\":\"2012-02-06 16:26:08\",\"lastModifyBy\":\"aaceb565f3da4288a0f52603f1c57a47\",\"lastModifyTime\":\"2012-02-06 16:26:08\",\"optTime\":22141946,\"questionId\":\"620000d527b84847ab9ee76be7ec485e\",\"answerId\":\"464f66fd259445e1b8eba72d6e698ca1\",\"agreeCount\":0,\"ip\":null,\"isDelete\":false,\"operationType\":null,\"userFaceUrl\":null,\"answerAgreeList\":null,\"isAgree\":null},\"questionId\":\"620000d527b84847ab9ee76be7ec485e\",\"ip\":null,\"isDelete\":false,\"operationType\":null,\"userFaceUrl\":\"http://dev.cne1n.net/sf-server/file/getFile/f7b86909df2facc31e0ad5aaa1aeaf46-S_1328593338906/4f0d35ed63d970b4f0bbef0d_0100/small\",\"modifyType\":null,\"title\":\"好好学习天天向上。\",\"shareInWeibo\":false,\"answerCount\":1,\"answerList\":null,\"topic\":null,\"isFavorite\":false,\"isAnswerred\":null},{\"content\":\"\",\"userName\":\"系统管理员\",\"reason\":null,\"corpCode\":\"yuanyuan\",\"extMap\":null,\"createBy\":\"aaceb565f3da4288a0f52603f1c57a47\",\"createTime\":\"2012-02-06 15:52:30\",\"lastModifyBy\":\"aaceb565f3da4288a0f52603f1c57a47\",\"lastModifyTime\":\"2012-02-06 15:52:30\",\"optTime\":22141912,\"answer\":null,\"questionId\":\"364ffe6cc0754b3d8e9fb3aec776016d\",\"ip\":null,\"isDelete\":false,\"operationType\":null,\"userFaceUrl\":\"http://dev.cne1n.net/sf-server/file/getFile/f7b86909df2facc31e0ad5aaa1aeaf46-S_1328593338906/4f0d35ed63d970b4f0bbef0d_0100/small\",\"modifyType\":null,\"title\":\"fsadfsafa\",\"shareInWeibo\":false,\"answerCount\":0,\"answerList\":null,\"topic\":null,\"isFavorite\":false,\"isAnswerred\":null},{\"content\":\"\",\"userName\":\"系统管理员\",\"reason\":null,\"corpCode\":\"yuanyuan\",\"extMap\":null,\"createBy\":\"aaceb565f3da4288a0f52603f1c57a47\",\"createTime\":\"2012-02-06 15:51:37\",\"lastModifyBy\":\"aaceb565f3da4288a0f52603f1c57a47\",\"lastModifyTime\":\"2012-02-06 15:51:37\",\"optTime\":22141911,\"answer\":null,\"questionId\":\"824545ae3f43482ab24403229bebc099\",\"ip\":null,\"isDelete\":false,\"operationType\":null,\"userFaceUrl\":\"http://dev.cne1n.net/sf-server/file/getFile/f7b86909df2facc31e0ad5aaa1aeaf46-S_1328593338906/4f0d35ed63d970b4f0bbef0d_0100/small\",\"modifyType\":null,\"title\":\"fasfasf\",\"shareInWeibo\":false,\"answerCount\":0,\"answerList\":null,\"topic\":null,\"isFavorite\":false,\"isAnswerred\":null},{\"content\":\"\",\"userName\":\"系统管理员\",\"reason\":null,\"corpCode\":\"yuanyuan\",\"extMap\":null,\"createBy\":\"aaceb565f3da4288a0f52603f1c57a47\",\"createTime\":\"2012-02-06 15:51:25\",\"lastModifyBy\":\"aaceb565f3da4288a0f52603f1c57a47\",\"lastModifyTime\":\"2012-02-06 16:56:47\",\"optTime\":22141911,\"answer\":{\"content\":\"32332\",\"userName\":\"系统管理员\",\"reason\":null,\"corpCode\":\"yuanyuan\",\"extMap\":null,\"createBy\":\"aaceb565f3da4288a0f52603f1c57a47\",\"createTime\":\"2012-02-06 16:56:47\",\"lastModifyBy\":\"aaceb565f3da4288a0f52603f1c57a47\",\"lastModifyTime\":\"2012-02-06 16:56:47\",\"optTime\":22141976,\"questionId\":\"87a3710ba2de402eb26a5453baf855b9\",\"answerId\":\"322216b695334986813b5c4ef04c0451\",\"agreeCount\":0,\"ip\":null,\"isDelete\":false,\"operationType\":null,\"userFaceUrl\":null,\"answerAgreeList\":null,\"isAgree\":null},\"questionId\":\"87a3710ba2de402eb26a5453baf855b9\",\"ip\":null,\"isDelete\":false,\"operationType\":null,\"userFaceUrl\":\"http://dev.cne1n.net/sf-server/file/getFile/f7b86909df2facc31e0ad5aaa1aeaf46-S_1328593338906/4f0d35ed63d970b4f0bbef0d_0100/small\",\"modifyType\":null,\"title\":\"32323\",\"shareInWeibo\":false,\"answerCount\":1,\"answerList\":null,\"topic\":null,\"isFavorite\":false,\"isAnswerred\":null},{\"content\":\"\",\"userName\":\"系统管理员\",\"reason\":null,\"corpCode\":\"yuanyuan\",\"extMap\":null,\"createBy\":\"aaceb565f3da4288a0f52603f1c57a47\",\"createTime\":\"2012-02-06 15:51:12\",\"lastModifyBy\":\"aaceb565f3da4288a0f52603f1c57a47\",\"lastModifyTime\":\"2012-02-06 15:51:12\",\"optTime\":22141911,\"answer\":null,\"questionId\":\"89c0edf369e24ff790eed36daa62afb8\",\"ip\":null,\"isDelete\":false,\"operationType\":null,\"userFaceUrl\":\"http://dev.cne1n.net/sf-server/file/getFile/f7b86909df2facc31e0ad5aaa1aeaf46-S_1328593338906/4f0d35ed63d970b4f0bbef0d_0100/small\",\"modifyType\":null,\"title\":\"f3232\",\"shareInWeibo\":false,\"answerCount\":0,\"answerList\":null,\"topic\":null,\"isFavorite\":false,\"isAnswerred\":null},{\"content\":\"\",\"userName\":\"系统管理员\",\"reason\":null,\"corpCode\":\"yuanyuan\",\"extMap\":null,\"createBy\":\"aaceb565f3da4288a0f52603f1c57a47\",\"createTime\":\"2012-02-06 15:50:54\",\"lastModifyBy\":\"aaceb565f3da4288a0f52603f1c57a47\",\"lastModifyTime\":\"2012-02-06 15:50:54\",\"optTime\":22141910,\"answer\":null,\"questionId\":\"2b11f5795da24b8ea3f595891020e97a\",\"ip\":null,\"isDelete\":false,\"operationType\":null,\"userFaceUrl\":\"http://dev.cne1n.net/sf-server/file/getFile/f7b86909df2facc31e0ad5aaa1aeaf46-S_1328593338906/4f0d35ed63d970b4f0bbef0d_0100/small\",\"modifyType\":null,\"title\":\"fsafsaf\",\"shareInWeibo\":false,\"answerCount\":0,\"answerList\":null,\"topic\":null,\"isFavorite\":false,\"isAnswerred\":null},{\"content\":\"\",\"userName\":\"系统管理员\",\"reason\":null,\"corpCode\":\"yuanyuan\",\"extMap\":null,\"createBy\":\"aaceb565f3da4288a0f52603f1c57a47\",\"createTime\":\"2012-02-06 15:50:34\",\"lastModifyBy\":\"aaceb565f3da4288a0f52603f1c57a47\",\"lastModifyTime\":\"2012-02-06 15:50:34\",\"optTime\":22141910,\"answer\":null,\"questionId\":\"b8bbb8c0d80d4ab380cb5b6999aa6d64\",\"ip\":null,\"isDelete\":false,\"operationType\":null,\"userFaceUrl\":\"http://dev.cne1n.net/sf-server/file/getFile/f7b86909df2facc31e0ad5aaa1aeaf46-S_1328593338906/4f0d35ed63d970b4f0bbef0d_0100/small\",\"modifyType\":null,\"title\":\"33232323\",\"shareInWeibo\":false,\"answerCount\":0,\"answerList\":null,\"topic\":null,\"isFavorite\":false,\"isAnswerred\":null},{\"content\":\"\",\"userName\":\"系统管理员\",\"reason\":null,\"corpCode\":\"yuanyuan\",\"extMap\":null,\"createBy\":\"aaceb565f3da4288a0f52603f1c57a47\",\"createTime\":\"2012-02-06 15:49:24\",\"lastModifyBy\":\"aaceb565f3da4288a0f52603f1c57a47\",\"lastModifyTime\":\"2012-02-06 15:49:24\",\"optTime\":22141909,\"answer\":null,\"questionId\":\"d11e0366b9c442b981503f100f738722\",\"ip\":null,\"isDelete\":false,\"operationType\":null,\"userFaceUrl\":\"http://dev.cne1n.net/sf-server/file/getFile/f7b86909df2facc31e0ad5aaa1aeaf46-S_1328593338906/4f0d35ed63d970b4f0bbef0d_0100/small\",\"modifyType\":null,\"title\":\"safFSAF\",\"shareInWeibo\":false,\"answerCount\":0,\"answerList\":null,\"topic\":null,\"isFavorite\":false,\"isAnswerred\":null},{\"content\":\"\",\"userName\":\"系统管理员\",\"reason\":null,\"corpCode\":\"yuanyuan\",\"extMap\":null,\"createBy\":\"aaceb565f3da4288a0f52603f1c57a47\",\"createTime\":\"2012-02-06 15:49:01\",\"lastModifyBy\":\"aaceb565f3da4288a0f52603f1c57a47\",\"lastModifyTime\":\"2012-02-06 15:49:01\",\"optTime\":22141909,\"answer\":null,\"questionId\":\"c56dd682a1584b22beb0295dfc207cff\",\"ip\":null,\"isDelete\":false,\"operationType\":null,\"userFaceUrl\":\"http://dev.cne1n.net/sf-server/file/getFile/f7b86909df2facc31e0ad5aaa1aeaf46-S_1328593338906/4f0d35ed63d970b4f0bbef0d_0100/small\",\"modifyType\":null,\"title\":\"22\",\"shareInWeibo\":false,\"answerCount\":0,\"answerList\":null,\"topic\":null,\"isFavorite\":false,\"isAnswerred\":null}],\"totalPages\":6,\"hasNext\":true,\"nextPage\":2,\"hasPre\":false,\"prePage\":1,\"sortName\":\"createTime\",\"sortOrder\":\"desc\",\"autoPaging\":true}";
	

	@Test
	public void test() {
	
	}
/*
	@Test
	public void testJsonToJavaTZ() throws SecurityException, NoSuchMethodException {
		
		Method method = getClass().getMethod("myFun", new Class[]{});
		
		Type type = method.getGenericReturnType();
		com.tbc.paas.common.domain.Page<com.tbc.app.qa.question.domain.Question> p = (com.tbc.paas.common.domain.Page<com.tbc.app.qa.question.domain.Question>) new JSONDeserializer().deserialize(tz, type);
		System.out.println("------------");
		System.out.println(p.getRows());
		
		System.out.println(p.getRows().get(0).getCreateTime());
		
		JSONSerializer serializer = new JSONSerializer();
		String deepSerialize = serializer.deepSerialize(p);
		System.out.println(deepSerialize);
		//assertEquals(tz, deepSerialize);
		
		
		com.tbc.paas.common.domain.Page p2 = (com.tbc.paas.common.domain.Page) new JSONDeserializer().deserialize(deepSerialize, type);
		assertEquals(deepSerialize,serializer.deepSerialize(p2));
		
	}
	
	public com.tbc.paas.common.domain.Page<com.tbc.app.qa.question.domain.Question> myFun() {
		com.tbc.paas.common.domain.Page<com.tbc.app.qa.question.domain.Question> page = new com.tbc.paas.common.domain.Page<com.tbc.app.qa.question.domain.Question>();
		
		List<com.tbc.app.qa.question.domain.Question> list = new ArrayList<com.tbc.app.qa.question.domain.Question>();
		com.tbc.app.qa.question.domain.Question question= new com.tbc.app.qa.question.domain.Question();
		question.setCorpCode("helk");
		
		list.add(question);
		page.setRows(list);
		
		return page;
	}
	
	*/

	@Test
	public void testTimestamp() {
		
		System.out.println(new Date(1328584816000L));
		System.out.println(new Date(1328584848000L));
		
		System.out.println(new Date(1328584580000L));
		System.out.println(new Date(1328584816000L));
		System.out.println(new Date(1328584848000L));
		System.out.println(new Date(1328585236000L));
		System.out.println(new Date(1328584848000L));

		
		System.out.println(new Date(1328584580000L));
		System.out.println(new Date(1328584807000L));
		System.out.println(new Date(1328584807000L));
		System.out.println(new Date(1328584780000L));
		
	}
}
