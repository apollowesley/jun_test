package com.kiss.freemarker.generatemain;

/**
 * 
 * @author yong_jiang
 *   主类 利用模版生成所有的Service，serviceImpl Logic，LogicImpl， Dao,DaoImpl,及serviceTest;
 *   用来快速开发项目，提升开发效率。	 
 */

public class AllGenerator extends Generator{
	private DaoGenerator dao = new DaoGenerator();
	private DaoImplGenerator daoimpl = new DaoImplGenerator();
	private ServiceGenerator service = new ServiceGenerator();
	private ServiceImplGenerator serviceImpl = new ServiceImplGenerator();

	@Override
	public boolean generate() {

		boolean serviceResult = generatorService();
		boolean serviceImplResult = generatorServiceImpl();
		boolean daoResult = generatorDao();
		boolean daoimplResult = generatordaoImpl();
		return serviceResult & serviceImplResult & daoResult & daoimplResult;
	}

	/***
	 * service生成
	 * @return 
	 */
	private boolean generatorService() {
		//service生成开始
		service.setEntity(entity);//实体
		service.setOrgName(orgName);//公司名
		service.setModuleName(moduleName); //模块名
		service.setPackageName(packageName);//包名
		service.setTargetDir(targetDir);//生成目标的路径
		service.setTemplateDir(templateDir); //模版路径
		
		boolean serviceResult = service.generate();
		//service生成结束
		return serviceResult;
	}

	/***
	 * serviceImpl生成
	 * @return 
	 */
	private boolean generatorServiceImpl() {
		//serviceImpl实现类生成开始
		serviceImpl.setEntity(entity);
		serviceImpl.setOrgName(orgName);
		serviceImpl.setModuleName(moduleName);
		serviceImpl.setPackageName(packageName);
		serviceImpl.setTargetDir(targetDir);
		serviceImpl.setTemplateDir(templateDir);
		
		boolean serviceImplResult = serviceImpl.generate();
		//serviceImpl实现类生成结束
		return serviceImplResult;
	}

	/**
	 * dao生成
	 * @return
	 */
	private boolean generatorDao() {
		//生成dao
		dao.setEntity(entity);//实体
		dao.setOrgName(orgName);//公司名
		dao.setModuleName(getDaomodelName()); //模块名
		dao.setPackageName(packageName);//包名
		String _targetDirtest2 = targetDir.replace(application, sharedProject);//替换生成到persist工程下面
		System.out.println(_targetDirtest2);
		dao.setTargetDir(_targetDirtest2);
		dao.setTemplateDir(templateDir); //模版路径
		boolean daoResult = dao.generate();
		//生成dao结束
		return daoResult;
	}

	/**
	 * dao实现类生成
	 * @return
	 */
	private boolean generatordaoImpl() {
		//生成daoImpl
		daoimpl.setEntity(entity);//实体
		daoimpl.setOrgName(orgName);//公司名
		daoimpl.setModuleName(getDaomodelName()); //模块名
		daoimpl.setPackageName(packageName);//包名
		String _targetDirtest3 = targetDir.replace(application, sharedProject);//替换生成到persist工程下面
		System.out.println(_targetDirtest3);
		daoimpl.setTargetDir(_targetDirtest3);
		daoimpl.setTemplateDir(templateDir); //模版路径
		boolean daoimplResult = daoimpl.generate();
		//生成daoImpl结束
		return daoimplResult;
	}

	@Override
	public String getTemplateName() {
		return null;
	}
	
}
