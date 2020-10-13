package tom.cocook.view;

public abstract class ViewFactory {
	public static final DefaultViewFactory FACTORY = new DefaultViewFactory();
	
    public static View getView(){
    	return FACTORY.create();
    }
    
    public static void initView(){
    	getView().init();
    }
	
	abstract View create();
	

	
}
