package com.foo.common.base.pojo;

public class QuickCopyModel {
	// 项目名称：itmsCore或者itmsUI
	private String projectName;
	// Class的基本路径：D:\\zzNode\\itmsPlus\\bin
	private String classBaseDirOfWindows;
	// 类的层次结构：\\org\\com\\...
	private String classPatternOfWindows;
	// Java的基本路径:D:\\zzNode\\itmsPlus\\ITMS_DEV\\src
	private String javaBaseDirOfWindows;
	// java class的实际路径
	private String classPathOfWindows;
	// java 的实际路径
	private String javaPathOfWindows;

	public String getJavaPathOfWindows() {
		return javaPathOfWindows;
	}

	public void setJavaPathOfWindows(String javaPathOfWindows) {
		this.javaPathOfWindows = javaPathOfWindows;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getClassBaseDirOfWindows() {
		return classBaseDirOfWindows;
	}

	public void setClassBaseDirOfWindows(String classBaseDirOfWindows) {
		this.classBaseDirOfWindows = classBaseDirOfWindows;
	}

	public String getJavaBaseDirOfWindows() {
		return javaBaseDirOfWindows;
	}

	public void setJavaBaseDirOfWindows(String javaBaseDirOfWindows) {
		this.javaBaseDirOfWindows = javaBaseDirOfWindows;
	}

	public String getClassPathOfWindows() {

		String myClassPattern = getJavaPathOfWindows().replace(
				getJavaBaseDirOfWindows(), "");

		classPathOfWindows = (getClassBaseDirOfWindows() + myClassPattern)
				.replaceAll(".java", ".class");

		return classPathOfWindows;

	}

	public void setClassPathOfWindows(String classPathOfWindows) {
		this.classPathOfWindows = classPathOfWindows;
	}

	public String getClassPatternOfWindows() {

		this.classPatternOfWindows = getClassPathOfWindows().replace(
				getClassBaseDirOfWindows(), "");

		if (classPatternOfWindows.startsWith("\\")) {
			return classPatternOfWindows;
		} else {
			return "\\" + classPatternOfWindows;
		}
	}

	public void setClassPatternOfWindows(String classPatternOfWindows) {
		this.classPatternOfWindows = classPatternOfWindows;
	}

}
