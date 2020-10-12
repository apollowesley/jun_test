package org.gaofeng.sqltodto;

import java.io.IOException;

import org.gaofeng.common.CommonFunction;
import org.gaofeng.common.MappingRule;
import org.gaofeng.common.properties.PropertiesTool;
import org.gaofeng.domain.GroupTableViewModel;

/**
 * 根据字段名 类型 组织mapper格式
 * 
 * @author gf
 *
 */
public class MakeMapper  extends CommonFunction{

	public static void makeMapper(GroupTableViewModel groupTableViewModel,
			String tableName) throws IOException {
		boolean onlyOneKey =false;
		//主键dto或者java类型
		String keyDomainName = PropertiesTool.BEANSREFIXNAME+tableNameForClassName+"Key"+PropertiesTool.BEANSUFFIXNAME;
		String keyName ="key";
		
		if(groupTableViewModel.getListKey().size()==1){
			onlyOneKey=true;
			keyName=groupTableViewModel.getListKey().get(0).getPropertyName();
			keyDomainName= "@Param(\""+keyName+"\") " + groupTableViewModel.getListKey().get(0).getJavaType();
		}

		StringBuffer sb = new StringBuffer();
		if (PropertiesTool.MAPPERPACKAGENAME != null
				&& !"".equals(PropertiesTool.MAPPERPACKAGENAME)) {
			sb.append("package "
					+ PropertiesTool.MAPPERPACKAGENAME.replaceAll(";", "")
					+ ";");
			sb.append("\n");
			sb.append("\n");
		}
		String domainName =PropertiesTool.BEANSREFIXNAME+tableNameForClassName+PropertiesTool.BEANSUFFIXNAME;
		sb.append("import "+PropertiesTool.DOMAINPACKAGENAME.replaceAll(";", "")+"."+domainName+";\n");
//		sb.append("import "+PropertiesTool.DOMAINPACKAGENAME.replaceAll(";", "")+"."+PropertiesTool.BEANSREFIXNAME+tableNameForClassName+"DynWhere"+PropertiesTool.BEANSUFFIXNAME+";\n");
		if(!onlyOneKey){
			sb.append("import "+PropertiesTool.DOMAINPACKAGENAME.replaceAll(";", "")+"."+keyDomainName+";\n");
		}
		sb.append("\n");
		sb.append("import java.util.List;\n");
//		sb.append("\n");
		if(onlyOneKey){
			sb.append("import org.apache.ibatis.annotations.Param;\n");
		}
		sb.append("\n");
		sb.append("public interface "+PropertiesTool.BEANSREFIXNAME+tableNameForClassName+"Mapper {\n");
		
		sb.append("	int insert("+domainName+" record);	\n");
		sb.append("	int insertSelective("+domainName+" record);\n");
		
//		sb.append("	int deleteByDynWhere("+PropertiesTool.BEANSREFIXNAME+tableNameForClassName+"DynWhere"+PropertiesTool.BEANSUFFIXNAME+" dynWhere);\n");
		sb.append("	int deleteByPrimaryKey("+ keyDomainName +" "+keyName+");\n");
		
//		sb.append("	int updateByDynWhereSelective(@Param(\"record\") "+domainName+" record, @Param(\"dynWhere\") "+PropertiesTool.BEANSREFIXNAME+tableNameForClassName+"DynWhere"+PropertiesTool.BEANSUFFIXNAME+" dynWhere);\n");
//		sb.append("	int updateByDynWhere(@Param(\"record\") "+domainName+" record, @Param(\"dynWhere\") "+PropertiesTool.BEANSREFIXNAME+tableNameForClassName+"DynWhere"+PropertiesTool.BEANSUFFIXNAME+" dynWhere);\n");
		sb.append("	int updateByPrimaryKeySelective("+domainName+" record);\n");
		sb.append("	int updateByPrimaryKey("+domainName+" record);\n");
		
		sb.append("	int selectCountByWhere("+domainName+" Entity);\n");
//		sb.append("	int selectCountByDynWhere("+PropertiesTool.BEANSREFIXNAME+tableNameForClassName+"DynWhere"+PropertiesTool.BEANSUFFIXNAME+" dynWhere);\n");
		
		sb.append("	"+domainName+" selectByPrimaryKey("+keyDomainName+" "+keyName+");\n");
		sb.append("	"+domainName+" selectSingleByWhere("+domainName+" Entity);\n");
//		sb.append("	"+domainName+" selectSingleByDynWhere("+PropertiesTool.BEANSREFIXNAME+tableNameForClassName+"DynWhere"+PropertiesTool.BEANSUFFIXNAME+" dynWhere);\n");
		sb.append("	List<"+domainName+"> selectListByWhere("+domainName+" Entity);\n");
//		sb.append("	List<"+domainName+"> selectListByDynWhere("+PropertiesTool.BEANSREFIXNAME+tableNameForClassName+"DynWhere"+PropertiesTool.BEANSUFFIXNAME+" dynWhere);\n");
		
		sb.append("}\n");
		
		sysLog(sb.toString(), INFO);
		MappingRule.makeFile(
				MappingRule.mapperPath + "\\"
						+ PropertiesTool.BEANSREFIXNAME+tableNameForClassName
						+ "Mapper.java", sb.toString());

	}
}
