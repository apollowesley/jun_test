package org.gaofeng.sqltodto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.gaofeng.common.CommonFunction;
import org.gaofeng.common.MappingRule;
import org.gaofeng.common.properties.PropertiesTool;
import org.gaofeng.domain.GroupTableViewModel;
import org.gaofeng.domain.TableViewModel;

/**
 * 根据字段名 类型 组织sqlMap格式
 * 
 * @author gf
 *
 */
public class MakeSqlMap  extends CommonFunction{

	public static void makeSqlMap(GroupTableViewModel mapListCol, String tableName)
			throws IOException {
		
		boolean onlyOneKey =false;
		//主键dto或者java类型
		String keyDomainName = PropertiesTool.DOMAINPACKAGENAME.replace(";", "") + "."+PropertiesTool.BEANSREFIXNAME+tableNameForClassName+"Key"+PropertiesTool.BEANSUFFIXNAME;
		if(mapListCol.getListKey().size()==1){
			onlyOneKey=true;
			keyDomainName= "java.lang."+mapListCol.getListKey().get(0).getJavaType();
		}
		
		
		List<TableViewModel> listKey=mapListCol.getListKey();
		List<TableViewModel> listUnKey=mapListCol.getListUnKey();
		List<TableViewModel> listAll=new ArrayList<TableViewModel>();
		listAll.addAll(listKey);
		listAll.addAll(listUnKey);
		
		StringBuffer sb = new StringBuffer();
		sb.append(makeTittle());
		sb.append(makeResultMap(listKey,listUnKey));
//		sb.append(makeDynWhereWhereClause());
		sb.append(makeWhereList(listAll));
//		sb.append(makeUpdateByDynWhereWhereClause());
		sb.append(makeBaseColumnList(listAll));
//		sb.append(makeSelectByDynWhere("selectListByDynWhere"));
//		sb.append(makeSelectByDynWhere("selectSingleByDynWhere"));
		sb.append(makeSelectByPrimaryKey(listKey,keyDomainName));
		sb.append(makeDeleteByPrimaryKey(listKey,keyDomainName));
//		sb.append(makeDeleteByDynWhere());
		sb.append(makeInsert(listAll));
		sb.append(makeInsertSelective(listAll));
//		sb.append(makeCountByDynWhere());
//		sb.append(makeUpdateByDynWhereSelective(listUnKey));
//		sb.append(makeUpdateByDynWhere(listAll));
		sb.append(makeUpdateByPrimaryKeySelective(listKey,listUnKey));
		sb.append(makeUpdateByPrimaryKey(listKey,listUnKey));
		sb.append(makeCountByWhere(listAll));
		sb.append(makeSelectByWhere(listAll));
		sb.append(makeSelectSingleByWhere(listAll));
		sb.append("</mapper>\n");
		sysLog(sb.toString(), INFO);
		MappingRule.makeFile(
				MappingRule.sqlMapPath + "\\"
						+ PropertiesTool.BEANSREFIXNAME+tableNameForClassName
						+ "Mapper.xml", sb.toString());

	}

	private static String makeSelectSingleByWhere(List<TableViewModel> listAll) {
		StringBuffer sb=new StringBuffer();
		sb.append("	<select id=\"selectSingleByWhere\" parameterType=\""+PropertiesTool.DOMAINPACKAGENAME.replace(";", "") + "."
				+ PropertiesTool.BEANSREFIXNAME+tableNameForClassName+PropertiesTool.BEANSUFFIXNAME+"\" resultMap=\"BaseResultMap\" >\n");
		sb.append("		select \n");
		sb.append(" <include refid=\"Base_Column_List\" /> \n") ;
		sb.append("  from "+tableName+" t  \n");
		sb.append("		<where> \n");
		sb.append("			<include refid=\"where_List\" />\n");
		sb.append("		</where>\n");
		sb.append("	</select>\n");
		return sb.toString();
	}

	private static String makeSelectByWhere(List<TableViewModel> listAll) {
		StringBuffer sb=new StringBuffer();
		sb.append("	<select id=\"selectListByWhere\" parameterType=\""+PropertiesTool.DOMAINPACKAGENAME.replace(";", "") + "."
				+ PropertiesTool.BEANSREFIXNAME+tableNameForClassName+PropertiesTool.BEANSUFFIXNAME+"\" resultMap=\"BaseResultMap\" >\n");
		sb.append("		select \n");
		sb.append(" <include refid=\"Base_Column_List\" /> \n") ;
		sb.append("  from "+tableName+" t  \n");
		sb.append("		<where> \n");
		sb.append("			<include refid=\"where_List\" />\n");
		sb.append("		</where>\n");
		sb.append("		<if test=\"strSqlOrderBy != null\" >\n");
		sb.append("            order by ${strSqlOrderBy}\n");
		sb.append("		</if>\n");
		sb.append("		<if test=\"limitStart != null and limitEnd != null\" >\n");
		sb.append("            LIMIT ${limitStart},${limitEnd} \n");
		sb.append("		</if>\n");
		sb.append("	</select>\n");
		
		return sb.toString();
	}

	private static String makeCountByWhere(List<TableViewModel> listAll) {
		StringBuffer sb=new StringBuffer();
		sb.append("	<select id=\"selectCountByWhere\" parameterType=\""+PropertiesTool.DOMAINPACKAGENAME.replace(";", "") + "."
				+ PropertiesTool.BEANSREFIXNAME+tableNameForClassName+PropertiesTool.BEANSUFFIXNAME+"\" resultType=\"java.lang.Integer\">\n");
		sb.append("		select count(*) from "+tableName+" t \n");
		sb.append("		<where> \n");
		sb.append("			<include refid=\"where_List\" />\n");
		sb.append("		</where>\n");
		sb.append("	</select>\n");
		return sb.toString();
	}

	private static String makeWhereList(List<TableViewModel> listAll) {
		StringBuffer sb=new StringBuffer();
		sb.append("	<sql id=\"where_List\">\n");
		for(TableViewModel tb :listAll){
			makeWhereList(sb, tb,"","=");
			if("Date".equals(tb.getJavaType())){
				makeWhereList(sb, tb,"Start",">=");
				makeWhereList(sb, tb,"End","<");
			}
			
		}
		sb.append("		<if test=\"strSqlWhere != null\" >\n");
		sb.append("            ${strSqlWhere} \n");
		sb.append("		</if>\n");
		sb.append("	</sql>\n");
		
		return sb.toString();
	}

	private static void makeWhereList(StringBuffer sb, TableViewModel tb,String dateAdd,String fuhao) {
		sb.append("		<if test=\""+tb.getPropertyName()+dateAdd+" != null ");
		if("String".equals(tb.getJavaType())){
			sb.append("and "+tb.getPropertyName()+" !=''");
		}
		sb.append("\">\n");
		
		sb.append("            <![CDATA[ and t."+tb.getFieldName()+" "+fuhao+" #{"+tb.getPropertyName()+dateAdd+",jdbcType="+tb.getMyBatisType()+"} ]]>\n");
		sb.append("		</if>\n");
	}

	private static String makeUpdateByPrimaryKey(List<TableViewModel> listKey,List<TableViewModel> listUnKey) {
		StringBuffer sb=new StringBuffer();
		sb.append("  <update id=\"updateByPrimaryKey\" parameterType=\""+PropertiesTool.DOMAINPACKAGENAME.replace(";", "") + "."
				+ PropertiesTool.BEANSREFIXNAME+tableNameForClassName+PropertiesTool.BEANSUFFIXNAME+"\" >\n");
		sb.append("    update "+tableName+"\n");
		sb.append("    set \n");
		for(int i=0;i<listUnKey.size();i++){
			sb.append("      "+listUnKey.get(i).getFieldName()+" = #{"+listUnKey.get(i).getPropertyName()+",jdbcType="+listUnKey.get(i).getMyBatisType()+"}");
			if(i!=listUnKey.size()-1){
				sb.append(",");
			}
			sb.append("\n");
		}
		for (int i = 0; i < listKey.size(); i++) {
			if(i==0){
				sb.append("    where "+listKey.get(i).getFieldName()+" = #{"+listKey.get(i).getPropertyName()+",jdbcType="+listKey.get(i).getMyBatisType()+"}\n");
			}else{
				sb.append("      and "+listKey.get(i).getFieldName()+" = #{"+listKey.get(i).getPropertyName()+",jdbcType="+listKey.get(i).getMyBatisType()+"}\n");
			}
		}
		sb.append("  </update>\n");
		return sb.toString();
	}

	private static String makeUpdateByPrimaryKeySelective(List<TableViewModel> listKey,List<TableViewModel> listUnKey) {
		StringBuffer sb=new StringBuffer();
		sb.append("  <update id=\"updateByPrimaryKeySelective\" parameterType=\""+PropertiesTool.DOMAINPACKAGENAME.replace(";", "") + "."
				+ PropertiesTool.BEANSREFIXNAME+tableNameForClassName+PropertiesTool.BEANSUFFIXNAME+"\" >\n");
		sb.append("    update "+tableName+"\n");
		sb.append("    <set >\n");
		for(TableViewModel tb:listUnKey){
			sb.append("      <if test=\""+tb.getPropertyName()+" != null\" >\n");
			sb.append("        "+tb.getFieldName()+" = #{"+tb.getPropertyName()+",jdbcType="+tb.getMyBatisType()+"},\n");
			sb.append("      </if>\n");
		}
		sb.append("    </set>\n");
		for(int i=0;i<listKey.size();i++){
			if(i==0){
				sb.append("    where "+listKey.get(i).getFieldName()+" = #{"+listKey.get(i).getPropertyName()+",jdbcType="+listKey.get(i).getMyBatisType()+"}\n");
			}else{
				sb.append("      and "+listKey.get(i).getFieldName()+" = #{"+listKey.get(i).getPropertyName()+",jdbcType="+listKey.get(i).getMyBatisType()+"}\n");
			}
		}
		
		sb.append("  </update>\n");
		return sb.toString();
	}

	private static String makeUpdateByDynWhere(List<TableViewModel> listAll) {
		StringBuffer sb=new StringBuffer();
		sb.append("  <update id=\"updateByDynWhere\" parameterType=\"map\" >\n");
		sb.append("    update "+tableName+"\n");
		sb.append("    set \n");
		for(int i=0;i<listAll.size();i++){
			sb.append("      "+listAll.get(i).getFieldName()+" = #{record."+listAll.get(i).getPropertyName()+",jdbcType="+listAll.get(i).getMyBatisType()+"}");
			if (i != listAll.size() - 1) {
				sb.append(",");
			}
			sb.append("\n");
		}
		sb.append("    <if test=\"_parameter != null\" >\n");
		sb.append("      <include refid=\"Update_By_DynWhere_Where_Clause\" />\n");
		sb.append("    </if>\n");
		sb.append("  </update>\n");
		return sb.toString();
	}

	private static String makeUpdateByDynWhereSelective(List<TableViewModel> listUnKey) {
		StringBuffer sb=new StringBuffer();
		sb.append("  <update id=\"updateByDynWhereSelective\" parameterType=\"map\" >\n");
		sb.append("    update "+tableName+"\n");
		sb.append("    <set >\n");
		for(TableViewModel tb:listUnKey){
			sb.append("      <if test=\"record."+tb.getPropertyName()+" != null\" >\n");
			sb.append("        id = #{record."+tb.getPropertyName()+",jdbcType="+tb.getMyBatisType()+"},\n");
			sb.append("      </if>\n");
		}
		sb.append("    </set>\n");
		sb.append("    <if test=\"_parameter != null\" >\n");
		sb.append("      <include refid=\"Update_By_DynWhere_Where_Clause\" />\n");
		sb.append("    </if>\n");
		sb.append("  </update>\n");
		return sb.toString();
	}

	private static String makeCountByDynWhere() {
		StringBuffer sb=new StringBuffer();
		sb.append("  <select id=\"selectCountByDynWhere\" parameterType=\""+PropertiesTool.DOMAINPACKAGENAME.replace(";", "") + "."
				+ PropertiesTool.BEANSREFIXNAME+tableNameForClassName+"DynWhere"+PropertiesTool.BEANSUFFIXNAME+"\" resultType=\"java.lang.Integer\" >\n");
		sb.append("    select count(*) from "+tableName+"\n");
		sb.append("    <if test=\"_parameter != null\" >\n");
		sb.append("      <include refid=\"DynWhere_Where_Clause\" />\n");
		sb.append("    </if>\n");
		sb.append("  </select>\n");
		return sb.toString();
	}

	private static String makeInsertSelective(List<TableViewModel> listAll) {
		StringBuffer sb=new StringBuffer();
		sb.append("  <insert id=\"insertSelective\" parameterType=\""+PropertiesTool.DOMAINPACKAGENAME.replace(";", "") + "."
				+ PropertiesTool.BEANSREFIXNAME+tableNameForClassName+PropertiesTool.BEANSUFFIXNAME+"\" >\n");
		sb.append("    insert into "+tableName+"\n");
		sb.append("    <trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\" >\n");
		for(TableViewModel tb:listAll){
			sb.append("      <if test=\""+tb.getPropertyName()+" != null\" >\n");
			sb.append("        "+tb.getFieldName()+",\n");
			sb.append("      </if>\n");
		}
		sb.append("    </trim>\n");
		sb.append("    <trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\" >\n");
		for(TableViewModel tb:listAll){
			sb.append("      <if test=\""+tb.getPropertyName()+" != null\" >\n");
			sb.append("        #{"+tb.getPropertyName()+",jdbcType="+tb.getMyBatisType()+"},\n");
			sb.append("      </if>\n");
		}
		sb.append("    </trim>\n");
		sb.append("  </insert>\n");
		return sb.toString();
	}

	private static String makeInsert(List<TableViewModel> listAll) {
		StringBuffer sb=new StringBuffer();
		sb.append("  <insert id=\"insert\" parameterType=\""+PropertiesTool.DOMAINPACKAGENAME.replace(";", "") + "."
				+ PropertiesTool.BEANSREFIXNAME+tableNameForClassName+PropertiesTool.BEANSUFFIXNAME+"\" >\n");
		sb.append("    insert into "+tableName+" ( <include refid=\"Base_Column_List\" /> )\n");
		sb.append("    values (");
		for(int i=0;i<listAll.size();i++){
			sb.append("#{"+listAll.get(i).getPropertyName()+",jdbcType="+listAll.get(i).getMyBatisType()+"}");
			if(i!=listAll.size()-1){
				sb.append(",");
			}
			if((i+1)%4==0){
				sb.append("\n      ");
			}
		}
		sb.append(")\n");
		sb.append("  </insert>\n");
		return sb.toString();
	}

	private static String makeDeleteByDynWhere() {
		StringBuffer sb=new StringBuffer();
		sb.append("  <delete id=\"deleteByDynWhere\" parameterType=\""+PropertiesTool.DOMAINPACKAGENAME.replace(";", "") + "."
				+ PropertiesTool.BEANSREFIXNAME+tableNameForClassName+"DynWhere"+PropertiesTool.BEANSUFFIXNAME+"\" >\n");
		sb.append("    delete from "+tableName+"\n");
		sb.append("    <if test=\"_parameter != null\" >\n");
		sb.append("      <include refid=\"DynWhere_Where_Clause\" />\n");
		sb.append("    </if>\n");
		sb.append("  </delete>\n");
		return sb.toString();
	}

	private static String makeDeleteByPrimaryKey(List<TableViewModel> listKey , String keyDomainName) {
		StringBuffer sb=new StringBuffer();
		sb.append("  <delete id=\"deleteByPrimaryKey\" parameterType=\""+ keyDomainName +"\" >\n");
		sb.append("    delete from "+tableName+"\n");
		for(int i=0;i<listKey.size();i++){
			if(i==0){
				sb.append("    where "+listKey.get(i).getFieldName()+" = #{"+listKey.get(i).getPropertyName()+",jdbcType="+listKey.get(i).getMyBatisType()+"}\n");
			}else{
				sb.append("      and "+listKey.get(i).getFieldName()+" = #{"+listKey.get(i).getPropertyName()+",jdbcType="+listKey.get(i).getMyBatisType()+"}\n");
			}
		}
		sb.append("  </delete>\n");
		return sb.toString();
	}

	private static String makeSelectByPrimaryKey(List<TableViewModel> listKey, String keyDomainName) {
		StringBuffer sb=new StringBuffer();
		sb.append("  <select id=\"selectByPrimaryKey\" resultMap=\"BaseResultMap\" parameterType=\""+ keyDomainName +"\" >\n");
		sb.append("    select \n");
		sb.append("    <include refid=\"Base_Column_List\" />\n");
		sb.append("    from "+tableName+"\n");
		for(int i=0;i<listKey.size();i++){
			if(i==0){
				sb.append("    where "+listKey.get(i).getFieldName()+" = #{"+listKey.get(i).getPropertyName()+",jdbcType="+listKey.get(i).getMyBatisType()+"}\n");
			}else{
				sb.append("      and "+listKey.get(i).getFieldName()+" = #{"+listKey.get(i).getPropertyName()+",jdbcType="+listKey.get(i).getMyBatisType()+"}\n");
			}
		}
		sb.append("  </select>\n");
		return sb.toString();
	}

	private static String makeSelectByDynWhere(String stringSqlName) {
		StringBuffer sb=new StringBuffer();
		sb.append("  <select id=\""+stringSqlName+"\" resultMap=\"BaseResultMap\" parameterType=\""+PropertiesTool.DOMAINPACKAGENAME.replace(";", "") + "."
				+ PropertiesTool.BEANSREFIXNAME+tableNameForClassName+"DynWhere"+PropertiesTool.BEANSUFFIXNAME+"\" >\n");
		sb.append("    select\n");
		sb.append("    <if test=\"distinct\" >\n");
		sb.append("      distinct\n");
		sb.append("    </if>\n");
		sb.append("    <include refid=\"Base_Column_List\" />\n");
		sb.append("    from "+tableName+" \n");
		sb.append("    <if test=\"_parameter != null\" >\n");
		sb.append("      <include refid=\"DynWhere_Where_Clause\" />\n");
		sb.append("    </if>\n");
		sb.append("    <if test=\"strSqlOrderBy != null\" >\n");
		sb.append("      order by ${strSqlOrderBy}\n");
		sb.append("    </if>\n");
		sb.append("    <if test=\"limitStart != null and limitEnd != null\" >\n");
		sb.append("       LIMIT ${limitStart},${limitEnd} \n");
		sb.append("    </if>\n");
		sb.append("  </select>\n");
		return sb.toString();
	}

	private static String makeBaseColumnList(List<TableViewModel> listAll) {
		StringBuffer sb=new StringBuffer();
		sb.append("  <sql id=\"Base_Column_List\" >\n");
		sb.append("    ");
		for(int i=0;i<listAll.size();i++){
			sb.append(listAll.get(i).getFieldName());
			if(i!=listAll.size()-1){
				sb.append(",");
			}
			if((i+1)%10==0){
				sb.append("\n    ");
			}
		}
		sb.append("  </sql>\n");
		return sb.toString();
	}

	private static String makeUpdateByDynWhereWhereClause() {
		StringBuffer sb=new StringBuffer();
		sb.append("  <sql id=\"Update_By_DynWhere_Where_Clause\" >\n");
		sb.append("    <where >\n");
		sb.append("      <foreach collection=\"dynWhere.oredCriteria\" item=\"criteria\" separator=\"or\" >\n");
		sb.append("        <if test=\"criteria.valid\" >\n");
		sb.append("          <trim prefix=\"(\" suffix=\")\" prefixOverrides=\"and\" >\n");
		sb.append("            <foreach collection=\"criteria.criteria\" item=\"criterion\" >\n");
		sb.append("              <choose >\n");
		sb.append("                <when test=\"criterion.noValue\" >\n");
		sb.append("                  and ${criterion.condition}\n");
		sb.append("                </when>\n");
		sb.append("                <when test=\"criterion.singleValue\" >\n");
		sb.append("                  and ${criterion.condition} #{criterion.value}\n");
		sb.append("                </when>\n");
		sb.append("                <when test=\"criterion.betweenValue\" >\n");
		sb.append("                  and ${criterion.condition} #{criterion.value} and #{criterion.secondValue}\n");
		sb.append("                </when>\n");
		sb.append("                <when test=\"criterion.listValue\" >\n");
		sb.append("                  and ${criterion.condition}\n");
		sb.append("                  <foreach collection=\"criterion.value\" item=\"listItem\" open=\"(\" close=\")\" separator=\",\" >\n");
		sb.append("                    #{listItem}\n");
		sb.append("                  </foreach>\n");
		sb.append("                </when>\n");
		sb.append("              </choose>\n");
		sb.append("            </foreach>\n");
		sb.append("          </trim>\n");
		sb.append("        </if>\n");
		sb.append("      </foreach>\n");
		sb.append("    </where>\n");
		sb.append("  </sql>\n");
		return sb.toString();
	}

	private static String makeDynWhereWhereClause() {
		StringBuffer sb =new StringBuffer();
		sb.append("  <sql id=\"DynWhere_Where_Clause\" >\n");
		sb.append("    <where >\n");
		sb.append("      <foreach collection=\"oredCriteria\" item=\"criteria\" separator=\"or\" >\n");
		sb.append("        <if test=\"criteria.valid\" >\n");
		sb.append("          <trim prefix=\"(\" suffix=\")\" prefixOverrides=\"and\" >\n");
		sb.append("            <foreach collection=\"criteria.criteria\" item=\"criterion\" >\n");
		sb.append("              <choose >\n");
		sb.append("                <when test=\"criterion.noValue\" >\n");
		sb.append("                  and ${criterion.condition}\n");
		sb.append("                </when>\n");
		sb.append("                <when test=\"criterion.singleValue\" >\n");
		sb.append("                  and ${criterion.condition} #{criterion.value}\n");
		sb.append("                </when>\n");
		sb.append("                <when test=\"criterion.betweenValue\" >\n");
		sb.append("                  and ${criterion.condition} #{criterion.value} and #{criterion.secondValue}\n");
		sb.append("                </when>\n");
		sb.append("                <when test=\"criterion.listValue\" >\n");
		sb.append("                  and ${criterion.condition}\n");
		sb.append("                  <foreach collection=\"criterion.value\" item=\"listItem\" open=\"(\" close=\")\" separator=\",\" >\n");
		sb.append("                    #{listItem}\n");
		sb.append("                  </foreach>\n");
		sb.append("                </when>\n");
		sb.append("              </choose>\n");
		sb.append("            </foreach>\n");
		sb.append("          </trim>\n");
		sb.append("        </if>\n");
		sb.append("      </foreach>\n");
		sb.append("    </where>\n");
		sb.append("  </sql>\n");
		return sb.toString();
	}

	private static String makeResultMap(List<TableViewModel> listKey, List<TableViewModel> listUnKey) {
		StringBuffer sb=new StringBuffer();
		sb.append("  <resultMap id=\"BaseResultMap\" type=\""+PropertiesTool.DOMAINPACKAGENAME.replace(";", "") + "."
				+ PropertiesTool.BEANSREFIXNAME+tableNameForClassName+PropertiesTool.BEANSUFFIXNAME+"\" >\n");
		
		for(TableViewModel pri:listKey){
			sb.append("    <id column=\""+pri.getFieldName()+"\" property=\""+pri.getPropertyName()+"\" jdbcType=\""+pri.getMyBatisType()+"\" />\n");
		}
		for(TableViewModel unPri:listUnKey){
			sb.append("    <result column=\""+unPri.getFieldName()+"\" property=\""+unPri.getPropertyName()+"\" jdbcType=\""+unPri.getMyBatisType()+"\" />\n");
		}
		sb.append("  </resultMap>\n");
		return sb.toString();
	}

	private static String makeTittle() {
		StringBuffer sb=new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
		sb.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >\n");
		sb.append("<mapper namespace=\""
				+ PropertiesTool.MAPPERPACKAGENAME.replace(";", "") + "."
				+ PropertiesTool.BEANSREFIXNAME+tableNameForClassName
				+ "Mapper\">\n");
		return sb.toString();
	}

}
