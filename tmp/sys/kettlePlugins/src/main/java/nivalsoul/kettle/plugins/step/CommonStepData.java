package nivalsoul.kettle.plugins.step;


import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.trans.step.BaseStepData;
import org.pentaho.di.trans.step.StepDataInterface;

public class CommonStepData extends BaseStepData implements StepDataInterface {

	public RowMetaInterface outputRowMeta;
	
    public CommonStepData()
	{
		super();
	}
}
	
