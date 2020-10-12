package nivalsoul.kettle.plugins.step;

import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.Shell;
import org.pentaho.di.core.CheckResult;
import org.pentaho.di.core.CheckResultInterface;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.Counter;
import org.pentaho.di.core.annotations.Step;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.exception.KettleStepException;
import org.pentaho.di.core.exception.KettleValueException;
import org.pentaho.di.core.exception.KettleXMLException;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.row.ValueMeta;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.core.row.value.ValueMetaFactory;
import org.pentaho.di.core.variables.VariableSpace;
import org.pentaho.di.core.xml.XMLHandler;
import org.pentaho.di.repository.ObjectId;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStepMeta;
import org.pentaho.di.trans.step.StepDataInterface;
import org.pentaho.di.trans.step.StepDialogInterface;
import org.pentaho.di.trans.step.StepInterface;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;
import org.pentaho.di.trans.steps.textfileinput.TextFileInputField;
import org.pentaho.metastore.api.IMetaStore;
import org.w3c.dom.Node;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


@Step(
	id = "CommonStepPlugin", 
	image = "nivalsoul/kettle/plugins/common/commonPlugin.png", 
	i18nPackageName = "nivalsoul.kettle.plugins.step", 
	name = "Plugin.Name", 
	description = "Plugin.Description", 
	categoryDescription = "i18n:org.pentaho.di.trans.step:BaseStep.Category.Transform",
	documentationUrl = "https://my.oschina.net/nivalsoul/blog"
)
public class CommonStepMeta extends BaseStepMeta implements StepMetaInterface {

	private static Class<?> PKG = CommonStepMeta.class; // for i18n purposes

	private String pluginType = "自定义";
    private String className = "";
	private String configInfo = "{}";
	
	private JSONArray outputFields = new JSONArray();

	public CommonStepMeta() {
		super(); 
	}
	
	public void setDefault() {
		this.pluginType = "自定义";
		this.className = "";
		this.configInfo = "{}";
	}

    public String getXML() throws KettleValueException {
    	StringBuilder retval = new StringBuilder();
		retval.append("    ").append(XMLHandler.addTagValue("pluginType", this.pluginType));
		retval.append("    ").append(XMLHandler.addTagValue("className", this.className));
		retval.append("    ").append(XMLHandler.addTagValue("configInfo", this.configInfo));
		
		return retval.toString();
	}
    
    public void loadXML(Node stepnode, List<DatabaseMeta> databases, Map<String, Counter> counters) throws KettleXMLException {
    	setPluginType(XMLHandler.getTagValue(stepnode, "pluginType"));
		setClassName(XMLHandler.getTagValue(stepnode, "className"));
		setConfigInfo(XMLHandler.getTagValue(stepnode, "configInfo"));
	}
    
    public void saveRep(Repository rep, ObjectId id_transformation, ObjectId id_step) throws KettleException {
		rep.saveStepAttribute(id_transformation, id_step, "pluginType", this.pluginType);
		rep.saveStepAttribute(id_transformation, id_step, "className", this.className);
		rep.saveStepAttribute(id_transformation, id_step, "configInfo", this.configInfo);
	}

	public void readRep(Repository rep, ObjectId id_step, List<DatabaseMeta> databases, Map<String, Counter> counters)
			throws KettleException {
		setPluginType(rep.getStepAttributeString(id_step, "pluginType"));
		setClassName(rep.getStepAttributeString(id_step, "className"));
		setConfigInfo(rep.getStepAttributeString(id_step, "configInfo"));
	}
	
	/**
	 * 该方法可以让后续步骤读到字段列表
	 */
	public void getFields( RowMetaInterface rowMeta, String origin, RowMetaInterface[] info, StepMeta nextStep,
			    VariableSpace space, Repository repository, IMetaStore metaStore ) throws KettleStepException {
		try {
			//如果指定了输出字段，则清楚原有的后新加字段
			if(outputFields.size()>0) {
				rowMeta.clear(); // Start with a clean slate, eats the input
			}

			for (int i = 0; i < outputFields.size(); i++) {
				JSONObject field = outputFields.getJSONObject(i);
				ValueMetaInterface valueMeta = ValueMetaFactory.createValueMeta(field.getString("name"), 
						ValueMetaFactory.getIdForValueMeta(field.getString("type")));
				valueMeta.setOrigin(origin);

				rowMeta.addValueMeta(valueMeta);
			}
		} catch (Exception e) {
			throw new KettleStepException(e);
		}
	}

    
	public Object clone() {
		Object retval = super.clone();
		return retval;
	}

	public void check(List<CheckResultInterface> remarks, TransMeta transmeta, StepMeta stepMeta, RowMetaInterface prev, String input[], String output[], RowMetaInterface info) {
		CheckResult cr;

		// See if we have input streams leading to this step!
		if (input.length > 0) {
			cr = new CheckResult(CheckResult.TYPE_RESULT_OK, "Step is receiving info from other steps.", stepMeta);
			remarks.add(cr);
		} else {
			cr = new CheckResult(CheckResult.TYPE_RESULT_ERROR, "No input received from other steps!", stepMeta);
			remarks.add(cr);
		}	
	}

	public StepDialogInterface getDialog(Shell shell, StepMetaInterface meta, TransMeta transMeta, String name) {
		return new CommonStepDialog(shell, meta, transMeta, name);
	}

	public StepInterface getStep(StepMeta stepMeta, StepDataInterface stepDataInterface, int cnr, TransMeta transMeta, Trans disp) {
		return new CommonStep(stepMeta, stepDataInterface, cnr, transMeta, disp);
	}

	public StepDataInterface getStepData() {
		return new CommonStepData();
	}

	//////////////////////////////
	
	public String getPluginType() {
		return pluginType;
	}
	public void setPluginType(String pluginType) {
		this.pluginType = pluginType;
	}
	
	public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }

    public String getConfigInfo() {
        return configInfo;
    }
    public void setConfigInfo(String configInfo) {
        this.configInfo = configInfo;
        JSONObject conf = JSON.parseObject(configInfo);
		if(conf.containsKey("outputFields")) {
			outputFields = conf.getJSONArray("outputFields");
		}
    }
    
	public JSONArray getOutputFields() {
		return outputFields;
	}

	public void setOutputFields(JSONArray outputFields) {
		this.outputFields = outputFields;
	}
}
