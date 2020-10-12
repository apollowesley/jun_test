package nivalsoul.kettle.plugins.common;

import java.util.Map;

import org.pentaho.di.core.row.RowDataUtil;
import org.pentaho.di.core.row.RowMeta;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.row.ValueMeta;
import org.pentaho.di.core.variables.VariableSpace;
import org.pentaho.di.trans.step.StepMeta;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;

import nivalsoul.kettle.plugins.util.HttpTool;

public class DbfOutputPlugin extends CommonStepRunBase {

	@Override
	protected Object[] disposeRow(Object[] outputRow) throws Exception {
		return outputRow;
    }
	
}
