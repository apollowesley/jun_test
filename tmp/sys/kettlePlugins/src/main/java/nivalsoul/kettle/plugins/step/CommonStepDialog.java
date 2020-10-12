package nivalsoul.kettle.plugins.step;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.Props;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStepMeta;
import org.pentaho.di.trans.step.StepDialogInterface;
import org.pentaho.di.ui.core.widget.StyledTextComp;
import org.pentaho.di.ui.core.widget.TextVar;
import org.pentaho.di.ui.trans.step.BaseStepDialog;

import nivalsoul.kettle.plugins.common.PluginType;
import nivalsoul.kettle.plugins.util.PluginUtil;

public class CommonStepDialog extends BaseStepDialog implements StepDialogInterface {

	private static Class<?> PKG = CommonStepMeta.class; // for i18n purposes
	
	private CommonStepMeta input;
	
	//插件类型
	private Label wlPluginType;
    private CCombo wPluginType;
    private FormData fdlPluginType, fdPluginType;

    //执行类名称
    private Label wlClassName;
    private TextVar wClassName;
	private FormData fdlClassName, fdClassName;

    //具体配置信息
	private Label wlConfigInfo;
    private StyledTextComp wConfigInfo;
    private FormData fdlConfigInfo, fdConfigInfo;
	
	public CommonStepDialog(Shell parent, Object in, TransMeta transMeta, String sname) {
		super(parent, (BaseStepMeta) in, transMeta, sname);
		input = (CommonStepMeta) in;
	}

	public String open() {
		Shell parent = getParent();
		Display display = parent.getDisplay();

		shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.RESIZE | SWT.MIN | SWT.MAX);
		shell.setSize(200, 300);
		props.setLook(shell);
		setShellImage(shell, input);

		ModifyListener lsMod = new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				input.setChanged();
			}
		};
		changed = input.hasChanged();

		FormLayout formLayout = new FormLayout();
		formLayout.marginWidth = Const.FORM_MARGIN;
		formLayout.marginHeight = Const.FORM_MARGIN;

		shell.setLayout(formLayout);
		shell.setText("Kettle公共转换插件"); 

		int middle = props.getMiddlePct() - 10;
		int margin = 6;

		// Stepname line
		wlStepname = new Label(shell, SWT.RIGHT);
		wlStepname.setText(BaseMessages.getString(PKG, "System.Label.StepName")); 
		props.setLook(wlStepname);
		fdlStepname = new FormData();
		fdlStepname.left = new FormAttachment(0, 0);
		fdlStepname.right = new FormAttachment(middle, -margin);
		fdlStepname.top = new FormAttachment(0, margin);
		wlStepname.setLayoutData(fdlStepname);
		
		wStepname = new Text(shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		wStepname.setText(stepname);
		props.setLook(wStepname);
		wStepname.addModifyListener(lsMod);
		fdStepname = new FormData();
		fdStepname.left = new FormAttachment(middle, 0);
		fdStepname.right = new FormAttachment(100, 0);
		fdStepname.top = new FormAttachment(0, margin);
		wStepname.setLayoutData(fdStepname);
		
		//For PluginTypeLabel
        this.wlPluginType = new Label(this.shell, 131072);
        this.wlPluginType.setText("插件类型");
        this.wlPluginType.setToolTipText("选择已有插件或者自定义插件，自定义插件需要指定实现类全路径");
        this.props.setLook(this.wlPluginType);
        this.fdlPluginType = new FormData();
        this.fdlPluginType.left = new FormAttachment(0, 0);
        this.fdlPluginType.right = new FormAttachment(middle, -margin);
        this.fdlPluginType.top = new FormAttachment(this.wStepname, margin);
        this.wlPluginType.setLayoutData(this.fdlPluginType);

        this.wPluginType = new CCombo(this.shell, SWT.READ_ONLY); //定义一个只读的下拉框
        int i = 0;
        for (PluginType one : PluginType.values()) {
        	this.wPluginType.add(one.getValue());
        	if(one != PluginType.Custom){
        		i++;
        	}
        }
        this.wPluginType.setToolTipText("选择已有插件或者自定义插件，自定义插件需要指定实现类全路径");
        this.wPluginType.select(i); 
        this.props.setLook(this.wPluginType);
        this.wPluginType.addModifyListener(lsMod);
        this.fdPluginType = new FormData();
        this.fdPluginType.left = new FormAttachment(middle, 0);
        this.fdPluginType.right = new FormAttachment(100, 0);
        this.fdPluginType.top = new FormAttachment(this.wStepname, margin);
        this.wPluginType.setLayoutData(this.fdPluginType);
        this.wPluginType.addSelectionListener( new SelectionAdapter() {
            public void widgetSelected( SelectionEvent e ) {
              input.setChanged();
              wConfigInfo.setText(PluginUtil.getDefaultConfig(wPluginType.getText()));
              wClassName.setText(PluginUtil.getClassName(wPluginType.getText()));
            }
        });

        //For ClassName
		wlClassName = new Label(shell, SWT.RIGHT);
		wlClassName.setText("类名称"); 
		props.setLook(wlClassName);
		fdlClassName = new FormData();
		fdlClassName.left = new FormAttachment(0, 0);
		fdlClassName.right = new FormAttachment(middle, -margin);
		fdlClassName.top = new FormAttachment(wPluginType, margin);
		wlClassName.setLayoutData(fdlClassName);

		wClassName = new TextVar(transMeta, shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
		props.setLook(wClassName);
		wClassName.addModifyListener(lsMod);
		fdClassName = new FormData();
		fdClassName.left = new FormAttachment(middle, 0);
		fdClassName.top = new FormAttachment(wPluginType, margin);
        fdClassName.right = new FormAttachment(100, margin );
		wClassName.setLayoutData(fdClassName);

		//For ConfigInfo
	    wlConfigInfo = new Label( shell, SWT.NONE );
	    wlConfigInfo.setText("配置信息");
	    props.setLook( wlConfigInfo );
	    fdlConfigInfo = new FormData();
	    fdlConfigInfo.left = new FormAttachment(0, 0 );
	    fdlConfigInfo.top = new FormAttachment( wClassName, margin );
	    fdlConfigInfo.right = new FormAttachment( middle, -margin );
	    wlConfigInfo.setLayoutData( fdlConfigInfo );
	    
        wConfigInfo =
          new StyledTextComp( transMeta, shell, SWT.MULTI | SWT.LEFT | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL, "" );
        wConfigInfo.setText("{}");
        props.setLook( wConfigInfo);
        wConfigInfo.addModifyListener( lsMod );
        fdConfigInfo = new FormData();
        fdConfigInfo.left = new FormAttachment(0, 0 );
        fdConfigInfo.top = new FormAttachment( wlConfigInfo, margin );
        fdConfigInfo.right = new FormAttachment(100, -margin);
        fdConfigInfo.height = 150;
        wConfigInfo.setLayoutData( fdConfigInfo );

        // OK and cancel buttons
        wOK = new Button(shell, SWT.PUSH);
        wOK.setText(BaseMessages.getString(PKG, "System.Button.OK")); 
    
		wCancel = new Button(shell, SWT.PUSH);
		wCancel.setText(BaseMessages.getString(PKG, "System.Button.Cancel")); 

		BaseStepDialog.positionBottomButtons(shell, new Button[] {wOK, wCancel}, margin, wConfigInfo);

		
		// Add listeners
		lsCancel = new Listener() {
			public void handleEvent(Event e) {
				cancel();
			}
		};
		lsOK = new Listener() {
			public void handleEvent(Event e) {
				ok();
			}
		};

		wCancel.addListener(SWT.Selection, lsCancel);
		wOK.addListener(SWT.Selection, lsOK);

		lsDef = new SelectionAdapter() {
			public void widgetDefaultSelected(SelectionEvent e) {
				ok();
			}
		};

		wStepname.addSelectionListener(lsDef);
		wClassName.addSelectionListener(lsDef);

		// Detect X or ALT-F4 or something that kills this window...
		shell.addShellListener(new ShellAdapter() {
			public void shellClosed(ShellEvent e) {
				cancel();
			}
		});

		
		// Set the shell size, based upon previous time...
		setSize();

		getData();
		input.setChanged(changed);

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		return stepname;
	}

	// Read data and place it in the dialog
	public void getData() {
		wStepname.selectAll();
		wPluginType.setText(input.getPluginType());
		wClassName.setText(input.getClassName());	
        wConfigInfo.setText(input.getConfigInfo()); 
	}

	private void cancel() {
		stepname = null;
		input.setChanged(changed);
		dispose();
	}
	
	// let the plugin know about the entered data
	private void ok() {
		stepname = wStepname.getText(); // return value
		input.setClassName(wClassName.getText());
        input.setConfigInfo(wConfigInfo.getText());
        input.setPluginType(wPluginType.getText());
		dispose();
	}
}
