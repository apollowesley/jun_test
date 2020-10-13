package tom.cocook.view;

import java.util.HashMap;

import tom.cocook.config.Constants;

public class DefaultViewFactory extends ViewFactory{

	private final static HashMap<String, Class<? extends View>> views = new HashMap<String, Class<? extends View>>() ; 
	private String viewType  = getViewType();
	static{
		views.put("velocity", VelocityView.class);
		views.put("freemarker", FreeMarkerView.class);
	}
	
	@Override
	public View create() {
		Class<? extends View> clazz =  views.get(viewType);
		if(clazz == null) return null;
		View view;
		try {
			view = clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} 
		return view;
	}
	
	
	private String getViewType(){
		return Constants.getViewType();
	}
	
}
