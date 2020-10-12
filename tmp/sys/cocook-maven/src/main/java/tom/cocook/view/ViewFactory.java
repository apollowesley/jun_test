package tom.cocook.view;

public abstract class ViewFactory {
	public static final DefaultViewFactory FACTORY = new DefaultViewFactory();
	
    public static View getView(){
    	return FACTORY.create();
    }
    
    public static void initView(){
    	View view = getView();
    	if(view == null) return;
    	view.init();
    }
	
	abstract View create();
	

	
}
