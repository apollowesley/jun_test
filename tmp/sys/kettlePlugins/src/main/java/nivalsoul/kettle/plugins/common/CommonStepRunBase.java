package nivalsoul.kettle.plugins.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pentaho.di.core.row.RowDataUtil;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.row.ValueMeta;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.core.row.value.ValueMetaFactory;
import org.pentaho.di.core.variables.VariableSpace;
import org.pentaho.di.job.Job;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.step.StepMeta;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;

import nivalsoul.kettle.plugins.step.CommonStep;
import nivalsoul.kettle.plugins.step.CommonStepData;
import nivalsoul.kettle.plugins.step.CommonStepMeta;

public abstract class CommonStepRunBase {
    public static final String HELP_INFO = "帮助信息";
    protected Log log = LogFactory.getLog(getClass());

    protected CommonStep commonStep;
    protected CommonStepMeta meta;
    protected CommonStepData data;

    protected JSONObject configInfo;
 
    public CommonStepRunBase() {
    }
    
    /**
     * 获取本步骤的输出字段 ，同时可以新增字段<br/>
     * @param r
     * @param origin
     * @param info
     * @param nextStep
     * @param space
     */
    public void getFields(RowMetaInterface r, String origin, RowMetaInterface[] info, StepMeta nextStep,
			VariableSpace space) throws Exception {
		// 可以新增字段
        /*ValueMetaInterface valueMeta = ValueMetaFactory.createValueMeta("fieldname1",
                ValueMetaFactory.getIdForValueMeta("String"));
        valueMeta.setOrigin(origin);
        r.addValueMeta(valueMeta);*/
	}

    /**
     * 处理每一行数据 <hr/>
     * 1）如果是输入插件，请重写该方法，将 if (r == null)判断部分去掉，<br/>
     * 然后在if (commonStep.first)里面做输入处理；<br/>
     * 2）如果是转换插件或者输出插件，只需要重写init()/disposeRow()/end()即可
     * @return
     * @throws Exception
     */
    public boolean run() throws Exception{
    	Object[] r = commonStep.getRow(); // get row, blocks when needed!
        if (r == null) { // no more input to be expected...
            end();
            commonStep.setOutputDone();
            return false;
        }
        if (commonStep.first) {
            data.outputRowMeta = (RowMetaInterface) commonStep.getInputRowMeta().clone();
            meta.getFields(data.outputRowMeta, commonStep.getStepname(), null, null, 
        			commonStep, commonStep.getRepository(), commonStep.getMetaStore());
            getFields(data.outputRowMeta, commonStep.getStepname(), null, null, commonStep);
            init();
            commonStep.first = false;
        }
        //创建输出记录
        Object[] outputRow = RowDataUtil.createResizedCopy( r, data.outputRowMeta.size() );
        outputRow = disposeRow(outputRow);
        commonStep.checkRowsInfo();
        //将该记录设置到下一步骤的读取序列中
        commonStep.putRow(data.outputRowMeta, outputRow); // copy row to possible alternate rowset(s)
        return true;
    }

    /**
    * 处理具体每一行数据 <br/>
     * 由于可能会有字段变动的情况，故重新返回新行
    * @param outputRow
    */
    protected Object[] disposeRow(Object[] outputRow) throws Exception{
        return outputRow;
    }

    protected void init() throws Exception{
    }

    protected void end() throws Exception{
    }

    /**
    * 添加字段 <br/>
    * @param r 行
    * @param name 字段名称
    * @param type 类型
    * @param trimType 去除空白规则
    * @param origin 宿主
    * @param comments 描述
    */
    protected void addField(RowMetaInterface r, String name, int type,
            int trimType, String origin, String comments) {
        addField(r, name, type, trimType, origin,comments, 0);
    }
    
    /**
    * 添加字段 <br/>
    * @param r 行
    * @param name 字段名称
    * @param type 类型
    * @param trimType 去除空白规则
    * @param origin 宿主
    * @param comments 描述
    * @param length 长度
    */
    @SuppressWarnings("deprecation")
    protected void addField(RowMetaInterface r, String name, int type,
            int trimType, String origin, String comments, int length) {
        ValueMetaInterface v = new ValueMeta();
        v.setName(name);
        v.setType(type);
        v.setTrimType(trimType);
        v.setOrigin(origin);
        v.setComments(comments);
        if(length>0){
            v.setLength(length);
        }
        r.addValueMeta(v);
    }
    /**
    * 获取输出字段在数组中的下标 <br/>
    * @see org.pentaho.di.core.row.ValueMetaInterface
    * @param field 字段名称
    * @return 下标
    */
    public int getFieldIndex(String field){
        return data.outputRowMeta.indexOfValue(field);
    }
    /**
    * 获取输出字段的类型 <br/>
    * @param field 字段名称
    * @return 下标
    */
    public int getFieldType(String field){
        return data.outputRowMeta.getValueMeta(getFieldIndex(field)).getType();
    }
    
    /**
    * 设置变量到根作业 <br/>
    * @param variableName 变量名
    * @param variableValue 变量值
    */
    public void setVariableRootJob(String variableName,String variableValue){
        commonStep.setVariable(variableName, variableValue);
        Trans trans = commonStep.getTrans();
        while(trans.getParentTrans()!=null){
            trans.setVariable(variableName, variableValue);
            trans = trans.getParentTrans();
        }
        trans.setVariable(variableName, variableValue);
        Job job = trans.getParentJob();
        while(job!=null){
            job.setVariable(variableName, variableValue);
            job = job.getParentJob();
        }
    }
    /**
    * 从根job获取变量 <br/>
    * @param variableName 变量名
    * @return 变量值
    */
    public String getVariavle(String variableName){
    	String value = commonStep.environmentSubstitute("${"+variableName+"}");
        if(value.startsWith("${")){
            return "";
        }else{
            return value;
        }
    }

    public CommonStep getCommonStep() {
        return commonStep;
    }

    public void setCommonStep(CommonStep cs) {
        this.commonStep = cs;
    }

    public CommonStepMeta getMeta() {
        return meta;
    }

    public void setMeta(CommonStepMeta meta, VariableSpace space) {
        this.meta = meta;
        //将配置信息解析成josn对象,支持变量
        try {
            if(!Strings.isNullOrEmpty(meta.getConfigInfo())){
                setConfigInfo(JSON.parseObject(space.environmentSubstitute(meta.getConfigInfo())));
            }
        } catch (Exception e) {
            log.info("配置信息转换为JSON对象失败："+meta.getConfigInfo(), e);
        }
    }

    public CommonStepData getData() {
        return data;
    }

    public void setData(CommonStepData data) {
        this.data = data;
    }

    public JSONObject getConfigInfo() {
        return configInfo;
    }

    public void setConfigInfo(JSONObject configInfo) {
        this.configInfo = configInfo;
    }
}
