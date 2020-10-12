package nivalsoul.kettle.plugins.common;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;
import org.pentaho.di.core.row.RowMetaInterface;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ExecuteSQLPlugin extends CommonStepRunBase {
	//字段
	private String[] fields;
	//SQL字段
	String sqlField = null;

	String driver;
	String url;
	String krb5Path;
	String keytabPath;
	String user;

	Connection con = null;
	Statement stmt = null;

	@Override
	protected Object[] disposeRow(Object[] row) throws Exception {
		String sql = "";
		if(sqlField!=null){
			boolean flag = true;
			for (int i = 0; i < fields.length; i++) {
				if(fields[i].equals(sqlField)){
					sql = commonStep.environmentSubstitute(row[i].toString());
					flag = false;
				}
			}
			if(flag){
				throw new Exception("配置中指定了SQL字段，但是输入流中没有该字段！");
			}
		}else{
			sql = commonStep.environmentSubstitute(row[0].toString());
		}
		commonStep.logBasic("exec SQL "+sql+"...");
		stmt.execute(sql);

        return row;
    }
	
	@Override
	protected void init() throws Exception {
		super.init();
		RowMetaInterface rowMeta = commonStep.getInputRowMeta();
		fields = rowMeta.getFieldNames();
		sqlField = configInfo.getString("sqlField");
		driver = configInfo.getString("driver");
		url = configInfo.getString("url");
		krb5Path = configInfo.getString("krb5Path");
		keytabPath = configInfo.getString("keytabPath");
		user = configInfo.getString("user");
		//init jdbc config
		Class.forName(driver);
		if(configInfo.getBooleanValue("useKerberos")) {
			System.setProperty("java.security.krb5.conf", krb5Path);
			Configuration configuration = new Configuration();
			configuration.set("hadoop.security.authentication", "Kerberos");
			UserGroupInformation.setConfiguration(configuration);
			UserGroupInformation.loginUserFromKeytab(user, keytabPath);
		}
		con = DriverManager.getConnection(url);
		stmt = con.createStatement();
	}

	@Override
	protected void end() throws Exception {
		if(stmt != null){
			stmt.close();
		}
		if (con != null) {
			con.close();
		}
		super.end();
	}
}
