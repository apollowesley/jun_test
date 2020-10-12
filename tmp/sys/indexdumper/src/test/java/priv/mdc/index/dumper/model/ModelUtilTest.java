package priv.mdc.index.dumper.model;

import priv.mdc.index.dumper.filter.FilterBean;
import priv.mdc.index.dumper.filter.FilterBean.FieldBean;
import priv.mdc.index.dumper.model.IndexDocReq;
import priv.mdc.index.dumper.model.ModelRecord;
import priv.mdc.index.dumper.model.ModelUtil;
import priv.mdc.index.dumper.model.UpdateType;
import junit.framework.TestCase;

public class ModelUtilTest extends TestCase {

	public void testGenerateInsert(){
		FilterBean filterBean = new FilterBean();
		filterBean.setTable("USERS");
		filterBean.setIndexName("tongban");
		filterBean.setDocName("users");
		filterBean.setIdFieldName("USER_ID");
		filterBean.getDataFields().add(new FieldBean("NICK_NAME","text","nick_name"));
		filterBean.getDataFields().add(new FieldBean("POSITION","a_double","position"));
		
		ModelRecord record = new ModelRecord();
		record.setId("66927631516b290070ffbfa2");
		record.getFields().put("NICK_NAME", "jack brown");
		record.getFields().put("POSITION", "116.40435,39.906526");
		record.getFields().put("USER_NAME", "bark");
		
		UpdateType updateType = UpdateType.CREATE;
		
		IndexDocReq req = ModelUtil.generate(filterBean, record, updateType);
		System.out.println(req.getBatachReqBody());
		System.out.println("----------------------");
		System.out.println(req.getSingleReqMethod());
		System.out.println(req.getSingleReqUri());
		System.out.println(req.getReqBody());
	}
	
	public void testGenerateDelete(){
		FilterBean filterBean = new FilterBean();
		filterBean.setTable("USERS");
		filterBean.setIndexName("tongban");
		filterBean.setDocName("users");
		filterBean.setIdFieldName("USER_ID");
		filterBean.getDataFields().add(new FieldBean("NICK_NAME","text","nick_name"));
		filterBean.getDataFields().add(new FieldBean("POSITION","a_double","position"));
		
		ModelRecord record = new ModelRecord();
		record.setId("66927631516b290070ffbfa2");
		record.getFields().put("NICK_NAME", "jack brown");
		record.getFields().put("POSITION", "116.40435,39.906526");
		record.getFields().put("USER_NAME", "bark");
		
		UpdateType updateType = UpdateType.UPDATE;
		
		IndexDocReq req = ModelUtil.generate(filterBean, record, updateType);
		System.out.println(req.getBatachReqBody());
	}
	
}
