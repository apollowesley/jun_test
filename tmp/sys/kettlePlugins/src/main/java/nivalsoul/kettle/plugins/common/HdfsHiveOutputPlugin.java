package nivalsoul.kettle.plugins.common;

import nivalsoul.kettle.plugins.util.bigdata.hive.DataWriter;
import nivalsoul.kettle.plugins.util.bigdata.hive.WriterConfig;
import org.pentaho.di.core.row.RowMetaInterface;

import java.util.UUID;

public class HdfsHiveOutputPlugin extends CommonStepRunBase {
	DataWriter dataWriter;
	int num = 0;

	@Override
	protected Object[] disposeRow(Object[] row) throws Exception {
		dataWriter.write(row);
        num++;
        return row;
    }
	
	@Override
	protected void init() throws Exception {
		super.init();
		RowMetaInterface rowMeta = commonStep.getInputRowMeta();
		String[] fields = rowMeta.getFieldNames();
		String[] types = new String[fields.length];
		commonStep.logBasic("field size=="+fields.length);
		for(int i=0;i<fields.length;i++){
			switch (rowMeta.getValueMeta(i).getType()){
				case 5:
					types[i] = "long";
					break;
				case 1:
				case 6:
					types[i] = "double";
					break;
				case 4:
					types[i] = "boolean";
					break;
				case 2:
				case 3:
				default:
					types[i] = "string";
			}
		}
		WriterConfig config = WriterConfig.builder()
				.hadoopUserName(configInfo.getString("hadoopUserName"))
				.hiveDriver(configInfo.getString("hiveDriver"))
				.hiveUrl(configInfo.getString("hiveUrl"))
				.hiveUser(configInfo.getString("hiveUser"))
				.hivePassword(configInfo.getString("hivePassword"))
				.hiveTable(configInfo.getString("hiveTable"))
				.tableType(configInfo.getString("tableType"))
				.overwrite(configInfo.getString("overwrite"))
				.createTable(configInfo.getString("createTable"))
				.partitionField(configInfo.getJSONArray("partitionField"))
				.partitionValue(configInfo.getJSONArray("partitionValue"))
				.fieldNames(fields)
				.fieldTypes(types)
				.hdfsUrls(configInfo.getString("hdfsUrls"))
				.hdfsFileName(configInfo.getString("hdfsFileName"))
				.fieldSeparator(configInfo.getString("fieldSeparator"))
				.lineSeparator(configInfo.getString("lineSeparator"))
				.build();
		dataWriter = new DataWriter(commonStep, config);
	}
	
	@Override
	protected void end() throws Exception {
		dataWriter.finish();
		commonStep.logBasic("write data finish!");
		super.end();
	}
}
