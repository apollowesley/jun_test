package com.foo.common.base.utils;

public class ClassMetaHelper {

	// public static void main(String[] args) {
	// Class<MyExample> metaClazz = MyExample.class;
	// ClassFieldGetAndSetForMustache fieldGetAndSetObject;
	//
	// boolean hasDateField = false;
	// String myFieldName;
	// String myFieldNameFirstUpper;
	// String myFieldType;
	// String myColumnName;
	// boolean myNullable;
	// String myFieldTypeInitExp;
	// String myLengthExp;
	//
	// List<ClassFieldGetAndSetForMustache> fieldsGetAndSet = Lists
	// .newArrayList();
	//
	// for (Field myField : metaClazz.getDeclaredFields()) {
	//
	// if (myField.getAnnotation(Column.class) != null) {
	// logger.info(myField.getName());
	// }
	//
	// myFieldName = myField.getName();
	//
	// // TODOO 这里默认id为主键，所以不进行id的解析了
	// if (myFieldName.toLowerCase().equals("id")) {
	// continue;
	// }
	//
	// myFieldNameFirstUpper = StringUtils.capitalize(myFieldName);
	//
	// //
	// // myFieldType = myField.getType();
	// // if (myFieldType.equals("Date")) {
	// // hasDateField = true;
	// // }
	//
	// fieldGetAndSetObject = new ClassFieldGetAndSetForMustache();
	// fieldGetAndSetObject.setMyFieldName(myFieldName);
	// fieldGetAndSetObject
	// .setMyFieldNameFirstUpper(myFieldNameFirstUpper);
	// // fieldGetAndSetObject.setMyFieldType(myFieldType);
	// fieldsGetAndSet.add(fieldGetAndSetObject);
	// }
	// }
	//
	// public static void main2(String[] args) throws Exception {
	// // Class<MyExample> metaClazz = MyExample.class;
	// // for (Field myField : metaClazz.getDeclaredFields()) {
	// // if (myField.getAnnotation(Column.class) != null) {
	// // logger.info(myField.getName());
	// // }
	// // }
	//
	// ASTVisitor astVisitor = new ASTVisitor() {
	// @Override
	// public boolean visit(FieldDeclaration node) {
	// for (Object obj : node.fragments()) {
	// VariableDeclarationFragment v = (VariableDeclarationFragment) obj;
	// // System.out.println(v.toString());
	// }
	// return true;
	// }
	//
	// // @Override
	// // public boolean visit(AnnotationTypeDeclaration node) {
	// // System.out.println(node.toString());
	// // return true;
	// // }
	//
	// @Override
	// public boolean visit(NormalAnnotation node) {
	// for (Object string : node.values()) {
	// System.out.println(string.toString());
	// }
	// return true;
	// }
	//
	// // @Override
	// // public boolean visit(SingleMemberAnnotation annotation) {
	// // System.out.println("here2");
	// // System.out.println(annotation.toString());
	// // return true;
	// // }
	// //
	// // @Override
	// // public boolean visit(MarkerAnnotation annotation) {
	// // System.out.println("here3");
	// // System.out.println(annotation.toString());
	// // return true;
	// // }
	// };
	//
	// CompilationUnit compilationUnit = getCompilationUnit();
	// compilationUnit.accept(astVisitor);
	//
	// }
	//
	// /**
	// * get compilation unit of source code
	// *
	// * @param javaFilePath
	// * @return CompilationUnit
	// * @throws IOException
	// */
	// public static CompilationUnit getCompilationUnit() throws IOException {
	//
	// String source = FileUtils.readFileToString(
	// new File(
	// "D:\\tools\\myGit\\feintek-platform\\industry-platform\\src\\main\\java\\com\\feiynn\\groupbuying\\model\\SysAnnounce.java"),
	// "utf-8");
	//
	// ASTParser astParser = ASTParser.newParser(4);
	// astParser.setSource(source.toCharArray());
	// astParser.setKind(ASTParser.K_COMPILATION_UNIT);
	//
	// CompilationUnit result = (CompilationUnit) (astParser.createAST(null));
	//
	// return result;
	// }
}
