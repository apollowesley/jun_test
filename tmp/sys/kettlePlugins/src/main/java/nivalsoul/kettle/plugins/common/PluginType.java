package nivalsoul.kettle.plugins.common;

public enum PluginType {
	Custom("自定义", "")
	,CustomInput("自定义输入类型", "nivalsoul.kettle.plugins.common.CustomInputPlugin")
	,JsonOutput("输出到json文件", "nivalsoul.kettle.plugins.common.JsonOutputPlugin")
	,DataChange("中文转换相关", "nivalsoul.kettle.plugins.common.DataChangePlugin")
    ,ESOutput("输出到es表", "")
    ,HdfsInput("读取HDFS文件", "nivalsoul.kettle.plugins.common.HdfsInputPlugin")
    ,HdfsHiveOutput("输出到hive表", "nivalsoul.kettle.plugins.common.HdfsHiveOutputPlugin")
    ,ExecuteSQL("执行SQL语句", "nivalsoul.kettle.plugins.common.ExecuteSQLPlugin")
    ,DbfOutput("输出到dbf文件", "nivalsoul.kettle.plugins.common.DbfOutputPlugin")
	,HbaseOutput("输出到Hbase", "")
	;

    private String value;
    private String className;

    private PluginType(String value, String className) {
        this.value = value;
        this.className = className;
    }

    public String getValue() {
        return value;
    }
    
    public String getClassName() {
		return className;
	}

    public static PluginType parse(String type) {
    	PluginType one = null;
        for (PluginType obj : PluginType.values()) {
            if (obj.getValue().equals(type)) {
            	one = obj;
                break;
            }
        }
        return one;
    }

}
