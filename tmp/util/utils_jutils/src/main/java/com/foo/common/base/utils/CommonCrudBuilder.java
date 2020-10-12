package com.foo.common.base.utils;

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

import com.foo.common.base.annotations.ColumnCrud;
import com.foo.common.base.annotations.EntityCrud;
import com.foo.common.base.annotations.JspCrud;
import com.foo.common.base.annotations.MgrCrud;
import com.foo.common.base.annotations.SelectCrud;
import com.foo.common.base.enums.FormFieldType;
import com.foo.common.base.enums.FormFieldValidation;
import com.foo.common.base.enums.JavaFieldType;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.google.common.base.CaseFormat;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * Crud类，请不要编辑此类任何东西，修改CommonCrudModel类即可。
 *
 */
public enum CommonCrudBuilder {

	INSTANCE;
	private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory
			.getLogger(CommonCrudBuilder.class);
	private String modelUppercase;// model名称，大写字母开头的驼峰格式。
	private String jspTitleName;// jsp模块中文名称，用于页面显示，页面title依赖该字段的值
	private final String global_auto_generated_directory_str = FooUtils
			.getGlobalAutoGeneratedDirectoryStr();
	private final String file_seprator = FooUtils.getFileSeparator();

	private static String actionPath = "";// action对应jsp的存放位置，如果不指定，那么程序会自动生成。比如/edu/course，那么jsp应该放在目录：/WEB-INF/pages/edu/course/目录下

	private final String modelDirectoryStr = global_auto_generated_directory_str
			+ file_seprator + "model";
	private final String actionDirectoryStr = global_auto_generated_directory_str
			+ file_seprator + "action";
	private final String mgrDirectoryStr = global_auto_generated_directory_str
			+ file_seprator + "mgr";

	private String template_crud_java_action_mustache = "crud/template-crud-java-action.mustache";
	private String template_crud_java_model_mustache = "crud/template-crud-java-model.mustache";
	private String template_crud_java_mgr_mustache = "crud/template-crud-java-mgr.mustache";
	private String template_crud_java_mgr_impl_mustache = "crud/template-crud-java-mgr-impl.mustache";
	private String template_crud_jsp_list_mustache = "crud/template-crud-list.jsp";
	private String template_crud_jsp_detail_mustache = "crud/template-crud-detail.jsp";
	private String jspFileName;
	private String modelPrefixUppercase;
	private String modelPrefixLowercase;

	public static void main(String[] args) throws Exception {
		CommonCrudBuilder.INSTANCE.execute();
	}

	public void execute() {

		File targetDir = new File(global_auto_generated_directory_str);

		try {
			// 初始化template所有参数
			Map<String, Object> source = initCommonScpoes();

			// 检查所有参数是否合乎规范
			checkParams();

			FooUtils.cleanDirectory(targetDir);

			generateJavaMgrWithMustache(source);
			generateJavaModelWithMustache(source);
			generateJavaActionWithMustache(source);
			generateJspWithMustache(source);

			logger.info("you should place all java class under dir:{}",
					"/src/main/java/com/feiynn/"
							+ source.get("modelPrefixLowercase"));

			logger.info("you should place all jsp under dir:{}",
					"/src/main/webapp/WEB-INF/pages" + actionPath);

		} catch (Exception e) {
			logger.error("Crud error on:{},clean directory now.", e);
			FileUtils.deleteQuietly(targetDir);
		}

	}

	private static void checkParams() {

	}

	/**
	 * 初始化公共模板
	 * 
	 * @return
	 */
	private <T> Map<String, Object> initCommonScpoes() {
		Preconditions.checkArgument(
				CommonCrudHelper.class.isAnnotationPresent(EntityCrud.class),
				"必须在类层次配置@EntityCrud注解");
		EntityCrud entityCrud = CommonCrudHelper.class
				.getAnnotation(EntityCrud.class);
		modelUppercase = entityCrud.modelUpperCamelName();
		jspTitleName = entityCrud.jspTitleName();

		actionPath = "/" + StringUtils.replace(CaseFormat.UPPER_CAMEL
				.to(CaseFormat.LOWER_UNDERSCORE, modelUppercase), "_", "/");

		jspFileName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,
				modelUppercase);

		String prefix = Splitter.on("/").omitEmptyStrings().limit(2)
				.splitToList(actionPath).get(0);
		modelPrefixUppercase = prefix;
		modelPrefixLowercase = prefix.toLowerCase();

		Map<String, Object> scopes = Maps.newHashMap();

		scopes.put("modelUppercase", modelUppercase);
		scopes.put("modelLowercase", CaseFormat.UPPER_CAMEL
				.to(CaseFormat.LOWER_CAMEL, modelUppercase));
		scopes.put("jspTitleName", jspTitleName);
		scopes.put("jspFileName", jspFileName);
		scopes.put("actionPath", actionPath);
		scopes.put("modelPrefixUppercase", modelPrefixUppercase);
		scopes.put("modelPrefixLowercase", modelPrefixLowercase);
		return scopes;

	}

	/**
	 * Generate mgr and mgr.impl
	 * 
	 * @param scopes
	 * @throws Exception
	 */
	private void generateJavaMgrWithMustache(Map<String, Object> scopes)
			throws Exception {
		MustacheFactory mf = new DefaultMustacheFactory();
		Mustache mustache;
		Writer writer = new StringWriter();
		File myFile;
		String result;

		if (CommonCrudHelper.class.isAnnotationPresent(MgrCrud.class)) {
			scopes.put("id2bean", true);
		} else {
			scopes.put("id2bean", false);
		}
		mustache = mf.compile(template_crud_java_mgr_mustache);
		writer = new StringWriter();
		mustache.execute(writer, scopes);
		writer.flush();
		result = writer.toString();
		myFile = new File(
				mgrDirectoryStr + file_seprator + modelUppercase + "Mgr.java");
		FooUtils.writeStringToFile(myFile, result);
		logger.info("generate java mgr file :{} on path:{}", myFile.getName(),
				myFile.getAbsolutePath());

		scopes.put("UPPER_UNDERSCORE", CaseFormat.UPPER_CAMEL
				.to(CaseFormat.UPPER_UNDERSCORE, modelUppercase));
		mustache = mf.compile(template_crud_java_mgr_impl_mustache);
		writer = new StringWriter();
		mustache.execute(writer, scopes);
		writer.flush();
		result = writer.toString();
		myFile = new File(mgrDirectoryStr + file_seprator + modelUppercase
				+ "MgrImpl.java");
		FooUtils.writeStringToFile(myFile, result);
		logger.info("generate java mgr-impl file :{} on path:{}",
				myFile.getName(), myFile.getAbsolutePath());

	}

	private void generateJavaModelWithMustache(Map<String, Object> scopes)
			throws Exception {

		MustacheFactory mf = new DefaultMustacheFactory();
		List<Map<String, Object>> fields_list = Lists.newArrayList();
		Map<String, Object> fields_map = Maps.newHashMap();
		List<Map<String, Object>> fields_get_and_set_list = Lists
				.newArrayList();
		List<Map<String, Object>> transform_list = Lists.newArrayList();
		Mustache mustache;
		Writer writer = new StringWriter();
		String result;
		File myFile;

		ColumnCrud columnCrud;

		String fieldName;
		String fieldNameTf;
		String fieldType;
		String initValue;
		String fieldNameUpperCamel;

		Set<String> permittedFieldTypes = Sets.newHashSet("Date", "String",
				"int", "double");

		Set<String> permittedFieldNames = Sets.newHashSet("id", "state",
				"createTime");

		Map<String, Object> javaModelScopes = Maps.newHashMap();
		for (Field field : FieldUtils.getAllFields(CommonCrudHelper.class)) {

			fieldName = field.getName();
			fieldType = field.getType().getSimpleName();
			fieldNameUpperCamel = StringUtils.capitalize(fieldName);

			// Common check,Start

			Preconditions.checkArgument(permittedFieldTypes.contains(fieldType),
					"allow only these types:" + permittedFieldTypes.toString());

			Preconditions.checkArgument(
					!permittedFieldTypes.contains(fieldName),
					"allow only these fieldNames:"
							+ permittedFieldNames.toString());

			Preconditions.checkArgument(
					field.isAnnotationPresent(ColumnCrud.class),
					"filed missing ColumnCrud annotation" + fieldName);
			// Common check,End

			columnCrud = field.getAnnotation(ColumnCrud.class);

			if (field.isAnnotationPresent(SelectCrud.class)) {
				fieldNameTf = fieldName + "Tf";
				transform_list.add(ImmutableMap.<String, Object> of("fieldName",
						fieldNameTf));
				fields_get_and_set_list.add(ImmutableMap.<String, Object> of(
						"fieldNameUpperCamel",
						StringUtils.capitalize(fieldNameTf), "fieldName",
						fieldNameTf, "fieldType", JavaFieldType.String));
			}

			// construct fields_list,Start
			fields_map = Maps.newHashMap();
			fields_map.put("fieldName", fieldName);
			fields_map.put("name", CaseFormat.UPPER_CAMEL
					.to(CaseFormat.LOWER_UNDERSCORE, fieldName).toUpperCase());
			fields_map.put("nullable", true);
			fields_map.put("length", columnCrud.length());
			fields_map.put("comment", columnCrud.comment());
			fields_map.put("needLength", false);
			fields_map.put("needColumnDefinition", false);
			fields_map.put("fieldType", fieldType);

			if (fieldType.equals("int")) {
				initValue = "0";
				fields_map.put("needColumnDefinition", true);
				fields_map.put("columnDefinition", "\"INT default 0\"");
			} else if (fieldType.equals("double")) {
				initValue = "0";
				fields_map.put("needColumnDefinition", true);
				fields_map.put("columnDefinition", "\"DECIMAL(12,4)\"");
			} else if (fieldType.equals("Date")) {
				initValue = "new Date()";
			} else {
				initValue = "\"\"";
				fields_map.put("needLength", true);
			}
			fields_map.put("initValue", initValue);
			fields_list.add(fields_map);
			// construct fields_list,End

			// construct fields_get_and_set_list,Start
			fields_get_and_set_list.add(ImmutableMap.<String, Object> of(
					"fieldNameUpperCamel", fieldNameUpperCamel, "fieldName",
					fieldName, "fieldType", fieldType));

			// construct fields_get_and_set_list,End

		}
		javaModelScopes.put("transform_list", transform_list);
		// 当有其它transform的时候，在这里进行逻辑判定是否引入
		javaModelScopes.put("need_transform",
				transform_list.size() > 0 ? true : false);

		javaModelScopes.put("fields_get_and_set_list", fields_get_and_set_list);
		javaModelScopes.put("fields_list", fields_list);
		javaModelScopes.put("tableName", CaseFormat.UPPER_CAMEL
				.to(CaseFormat.LOWER_UNDERSCORE, modelUppercase).toUpperCase());
		javaModelScopes.put("modelUppercase", modelUppercase);
		javaModelScopes.put("modelPrefixLowercase", modelPrefixLowercase);

		mustache = mf.compile(template_crud_java_model_mustache);
		writer = new StringWriter();
		mustache.execute(writer, javaModelScopes);
		writer.flush();
		result = writer.toString();
		myFile = new File(
				modelDirectoryStr + file_seprator + modelUppercase + ".java");
		FooUtils.writeStringToFile(myFile, result);
		logger.info("generate java model file :{} on path:{}", myFile.getName(),
				myFile.getAbsolutePath());
	}

	private void generateJavaActionWithMustache(Map<String, Object> scopes)
			throws Exception {

		MustacheFactory mf = new DefaultMustacheFactory();

		List<Map<String, Object>> get_fileds_from_request_list = Lists
				.newArrayList();
		List<Map<String, Object>> set_fileds_from_request_list = Lists
				.newArrayList();
		List<Map<String, Object>> transform_list = Lists.newArrayList();

		String fieldName;
		String fieldType;

		Set<String> permittedFieldTypes = Sets.newHashSet("Date", "String",
				"int", "double");

		Set<String> permittedFieldNames = Sets.newHashSet("id", "state",
				"createTime");
		String fieldNameUpperCamel;
		String fieldValueAssignStatement = "";

		Map<String, Object> javaActionScopes = Maps.newHashMap(scopes);
		for (Field field : FieldUtils.getAllFields(CommonCrudHelper.class)) {

			fieldName = field.getName();
			fieldType = field.getType().getSimpleName();
			fieldNameUpperCamel = StringUtils.capitalize(fieldName);

			// Common check,Start

			Preconditions.checkArgument(permittedFieldTypes.contains(fieldType),
					"allow only these types:" + permittedFieldTypes.toString());

			Preconditions.checkArgument(
					!permittedFieldTypes.contains(fieldName),
					"allow only these fieldNames:"
							+ permittedFieldNames.toString());

			Preconditions.checkArgument(
					field.isAnnotationPresent(ColumnCrud.class),
					"filed missing ColumnCrud annotation" + fieldName);

			// Common check,End

			// construct get_fileds_from_request_list,Start
			if (fieldType.equals("String")) {
				fieldValueAssignStatement = "Strings.nullToEmpty(request.getParameter(\""
						+ fieldName + "\"));";
			} else if (fieldType.equals("int")) {
				fieldValueAssignStatement = "Integer.parseInt(Strings.nullToEmpty(request.getParameter(\""
						+ fieldName + "\")));";
			} else if (fieldType.equalsIgnoreCase("double")) {
				fieldValueAssignStatement = "Double.parseDouble(Strings.nullToEmpty(request.getParameter(\""
						+ fieldName + "\")));";
			} else {
				logger.warn("date do not suppport now for field:{}", fieldName);
			}
			get_fileds_from_request_list.add(ImmutableMap.<String, Object> of(
					"fieldName", fieldName, "fieldType", fieldType,
					"fieldValueAssignStatement", fieldValueAssignStatement));

			// construct get_fileds_from_request_list,End

			// construct set_fileds_from_request_list,Start
			set_fileds_from_request_list.add(
					ImmutableMap.<String, Object> of("fieldName", fieldName,
							"fieldNameUpperCamel", fieldNameUpperCamel));
			// construct set_fileds_from_request_list,End

		}

		javaActionScopes.put("get_fileds_from_request_list",
				get_fileds_from_request_list);
		javaActionScopes.put("set_fileds_from_request_list",
				set_fileds_from_request_list);

		javaActionScopes.put("transform_list", transform_list);
		javaActionScopes.put("need_transform",
				transform_list.size() > 0 ? true : false);

		Mustache mustache = mf.compile(template_crud_java_action_mustache);
		Writer writer = new StringWriter();
		mustache.execute(writer, javaActionScopes);
		writer.flush();
		String result = writer.toString();
		File myFile = new File(actionDirectoryStr + file_seprator
				+ modelUppercase + "Action.java");
		FooUtils.writeStringToFile(myFile, result);
		logger.info("generate java action file :{} on path:{}",
				myFile.getName(), myFile.getAbsolutePath());

	}

	private void generateJspWithMustache(Map<String, Object> scopes)
			throws Exception {

		JspCrud jspCrud;
		List<Map<String, Object>> drawDetail_list = Lists.newArrayList();

		final Field[] allFields = FieldUtils
				.getAllFields(CommonCrudHelper.class);

		// 用于dataTables显示的list,目前只用于list.jsp
		List<Map<String, Object>> dataTables_list = Lists.newArrayList();

		// form表单字段,目前只支持单列或者双列表单布局,目前只用于detail.jsp
		List<Map<String, Object>> form_text_list = Lists.newArrayList();

		// form表单验证规则字段，key为表单field name，value为拼接好的validation验证规则,目前只用于detail.jsp
		List<String> form_validation_rules = Lists.newArrayList();

		// text的form为2个或单个一组，所以需要重新构造数据结构,目前只用于detail.jsp
		Map<String, Object> form_text_list_map = Maps.newHashMap();

		// 构造mustache需要的jsp form原始数据，Start
		String rules = "";
		String fieldName;
		String cnName;
		String eachFieldValidationDesc = "";
		boolean needNewNearestMap = true;
		FormFieldType formFieldType;
		for (Field tmpField : allFields) {
			jspCrud = tmpField.getAnnotation(JspCrud.class);
			if (!jspCrud.displayOnForm()) {
				continue;
			}
			cnName = jspCrud.cnName();
			fieldName = tmpField.getName();
			formFieldType = jspCrud.formFieldType();

			dataTables_list.add(ImmutableMap.<String, Object> of("cnName",
					cnName, "fieldName", fieldName));
			drawDetail_list.add(
					ImmutableMap.<String, Object> of("fieldName", fieldName));

			if (formFieldType == FormFieldType.textarea) {
				needNewNearestMap = true;
			} else {
				if (form_text_list_map.size() > 2) {
					needNewNearestMap = true;
				} else {
					needNewNearestMap = false;
				}
			}

			if (needNewNearestMap) {
				form_text_list_map = Maps.newHashMap();
				form_text_list.add(form_text_list_map);
			} else {
				if (form_text_list.size() == 0) {
					form_text_list_map = Maps.newHashMap();
					form_text_list.add(form_text_list_map);
				}
				form_text_list_map = form_text_list
						.get(form_text_list.size() - 1);
			}

			if (formFieldType == FormFieldType.textarea) {
				form_text_list_map.put("oneColumn", true);
				form_text_list_map.put("left", ImmutableMap.<String, Object> of(
						"cnName", jspCrud.cnName(), "fieldName", fieldName));
				form_text_list_map.put("required", null);// todoo required validation rule will need this.
			} else if (formFieldType == FormFieldType.text) {
				if (form_text_list_map.get("left") != null) {
					form_text_list_map.put("oneColumn", false);
					form_text_list_map.put("right",
							ImmutableMap.<String, Object> of("cnName",
									jspCrud.cnName(), "fieldName", fieldName));
				} else {
					form_text_list_map.put("left",
							ImmutableMap.<String, Object> of("cnName",
									jspCrud.cnName(), "fieldName", fieldName));
					form_text_list_map.put("oneColumn", true);
				}
			} else {
				// TODOO
			}

			FormFieldValidation[] validations = jspCrud.validation();

			List<String> validationRulesDesc = Lists.newArrayList();
			formFieldValidationLoop: for (FormFieldValidation formFieldValidation : validations) {
				if (formFieldValidation == null) {
					continue formFieldValidationLoop;
				}
				if (formFieldValidation == FormFieldValidation.empty) {
					eachFieldValidationDesc = "";
					break formFieldValidationLoop;
				}
				validationRulesDesc
						.add(formFieldValidation.toString() + ":true");
				eachFieldValidationDesc = tmpField.getName() + ":{"
						+ Joiner.on(",").skipNulls().join(validationRulesDesc)
						+ "}";
			}
			if (!eachFieldValidationDesc.equals("")) {
				form_validation_rules.add(eachFieldValidationDesc);
			}
		}
		// 构造mustache需要的jsp form原始数据，End

		rules = Joiner.on(",").skipNulls().join(form_validation_rules);
		// logger.info("rules here is:{}", rules);

		scopes.put("rules", rules);// detail.jsp界面使用的验证规则
		scopes.put("jspFileName", jspFileName);
		scopes.put("drawDetail_list", drawDetail_list);
		scopes.put("dataTables_list", dataTables_list);
		scopes.put("form_text_list", form_text_list);
		scopes.put("actionPath", actionPath);

		logger.info("Generate list_jsp now.");
		String jsp_list_data = FooUtils.generateDataWithMustache(scopes,
				template_crud_jsp_list_mustache);
		// logger.info("result is: \n\n\n {}", result);

		File jsp_list_file = new File(global_auto_generated_directory_str
				+ file_seprator + jspFileName + "_list.jsp");

		FooUtils.writeStringToFile(jsp_list_file, jsp_list_data);

		logger.info("generate jsp_list_file:{} on path:{}",
				jsp_list_file.getName(), global_auto_generated_directory_str);

		logger.info("Generate detail_jsp now.");
		String jsp_detail_data = FooUtils.generateDataWithMustache(scopes,
				template_crud_jsp_detail_mustache);
		// logger.info("result is: \n\n\n {}", result);

		File jsp_detail_file = new File(global_auto_generated_directory_str
				+ file_seprator + jspFileName + "_detail.jsp");

		FooUtils.writeStringToFile(jsp_detail_file, jsp_detail_data);

		logger.info("generate jsp_detail_file:{} on path:{}",
				jsp_detail_file.getName(), global_auto_generated_directory_str);

	}

}