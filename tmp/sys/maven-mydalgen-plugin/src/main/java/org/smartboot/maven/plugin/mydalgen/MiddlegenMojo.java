package org.smartboot.maven.plugin.mydalgen;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.smartboot.maven.plugin.mydalgen.plugins.mybatis.IWalletPlugin;
import org.smartboot.maven.plugin.mydalgen.plugins.mybatis.config.IWalletConfig;
import org.smartboot.maven.plugin.mydalgen.plugins.mybatis.util.LogUtils;

@Mojo(name = "mybatis")
public class MiddlegenMojo extends AbstractMojo {

	/** 生成器 */
	private Middlegen middlegen;

	@Parameter(readonly = true, required = true)
	private FileProducer[] fileProducers;

	@Parameter(readonly = true, required = true)
	private MyDalgenConfig config;

	/**
	 * CTOR
	 */
	public MiddlegenMojo() {
		LogUtils.log("**********************************");
		LogUtils.log("* 开始启动dalgen任务 *");
		LogUtils.log("* " + this.getClass().getName() + " *");
		LogUtils.log("**********************************");

		middlegen = new Middlegen(this);
	}

	public void execute() throws MojoExecutionException {
		IWalletPlugin plugin = new IWalletPlugin();
		plugin.setName("iwallet");
		plugin.setMergedir(config.getMergedir());
		plugin.setPackage(config.getMiddlegenPackage());

		for (FileProducer fileProducer : fileProducers) {
			plugin.addConfiguredFileproducer(fileProducer);
		}
		middlegen.addPlugin(plugin);

		long start = System.currentTimeMillis();
		LogUtils.log("开始执行dalgen任务：" + this.getClass().getName());

		try {
			// 用来初始化dal-config.xml中的内容，_configFile就是dal-config.xml
			IWalletConfig.init(new File(config.getConfigFile()));

			Prefs.getInstance().init();

			MiddlegenPopulator populator = new MiddlegenPopulator(middlegen, config);

			// 用来验证dal-config.xml中的SEQ信息
			middlegen.validate();

			middlegen.getTableElements().clear();

			StringBuffer logText = new StringBuffer();
			int count = 0;

			// plugin.getAllTableNames()用来获得dal-config.xml中的所有表名信息
			for (String tableName : plugin.getAllTableNames()) {
				TableElement tableElement = new TableElement();
				tableElement.setName(tableName);

				middlegen.addTableElement(tableElement);

				// 如果当前表在指定范围内,提示给用户
				logText.append("(" + ++count + ")." + tableName + "\n");
			}

			if (logText.toString().length() == 0) {
				// this.log("指定的数据表/SEQ没有找到, 请重新操作.", Project.MSG_WARN);
				return;
			}

			if (middlegen.getTableElements().isEmpty()) {
				populator.addRegularTableElements();
			}

			populator.populate(middlegen.getTableElements());

			// Instantiate all plugins' decorators.
			middlegen.decorateAll();
			middlegen.writeSource();

		} catch (Throwable e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			long time = System.currentTimeMillis() - start;
			LogUtils.log("dalgen任务执行完成, 耗时[" + time + "]ms.");
		}
	}

}