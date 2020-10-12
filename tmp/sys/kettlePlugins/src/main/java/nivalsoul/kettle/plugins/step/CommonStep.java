package nivalsoul.kettle.plugins.step;

import java.util.Arrays;

import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.exception.KettleStepException;
import org.pentaho.di.core.row.RowDataUtil;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStep;
import org.pentaho.di.trans.step.StepDataInterface;
import org.pentaho.di.trans.step.StepInterface;
import org.pentaho.di.trans.step.StepMeta;
import org.pentaho.di.trans.step.StepMetaInterface;

import com.google.common.base.Strings;

import nivalsoul.kettle.plugins.common.CommonStepRunBase;


public class CommonStep extends BaseStep implements StepInterface {

	private CommonStepData data;
	private CommonStepMeta meta;
	private CommonStepRunBase csrb;
	
	public CommonStep(StepMeta s, StepDataInterface stepDataInterface, int c, TransMeta t, Trans dis) {
		super(s, stepDataInterface, c, t, dis);
	}

	public boolean processRow(StepMetaInterface smi, StepDataInterface sdi) throws KettleException {
		meta = (CommonStepMeta) smi;
		data = (CommonStepData) sdi;
		if(!Strings.isNullOrEmpty(meta.getClassName())){
            try {
                //实例化配置的类
                if(first){
                    csrb = (CommonStepRunBase) Class.forName(
                            environmentSubstitute(meta.getClassName())).newInstance();
                    csrb.setCommonStep(this);
                    csrb.setMeta(meta, this);
                }
                csrb.setData(data);
                return csrb.run();
            } catch (Exception e) {
                setErrors(getErrors()+1);
				logError("运行失败," + meta.getClassName() + "," + Arrays.toString(getRow()) + ","
						+ environmentSubstitute(meta.getConfigInfo()), e);
                return defaultRun();
            }
		}else{
	        return defaultRun();
		}
	}

    public boolean defaultRun() throws KettleException, KettleStepException {
        Object[] r = getRow(); // get row, blocks when needed!
		if (r == null) {// no more input to be expected...
			setOutputDone();
			return false;
		}

		if (first) {
			first = false;
			this.data.outputRowMeta = getInputRowMeta().clone();
            this.meta.getFields(this.data.outputRowMeta, getStepname(), 
            		null, null, this, this.repository, this.metaStore);
		}
		
		Object[] outputRow = RowDataUtil.createResizedCopy( r, data.outputRowMeta.size() );

		putRow(data.outputRowMeta, outputRow); // copy row to possible alternate rowset(s)

		if (checkFeedback(getLinesRead())) {
			logBasic("Linenr " + getLinesRead()); // Some basic logging
		}

		return true;
    }
    
    public void checkRowsInfo(){
		if ( checkFeedback( getLinesRead() ) ) {
			logBasic( "has proccessed：" + getLinesRead() );
		}
	}

	public boolean init(StepMetaInterface smi, StepDataInterface sdi) {
		meta = (CommonStepMeta) smi;
		data = (CommonStepData) sdi;

		return super.init(smi, sdi);
	}

	public void dispose(StepMetaInterface smi, StepDataInterface sdi) {
		meta = (CommonStepMeta) smi;
		data = (CommonStepData) sdi;

		super.dispose(smi, sdi);
	}
}
