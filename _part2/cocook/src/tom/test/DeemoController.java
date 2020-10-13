package tom.test;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import tom.cocook.annotation.Handler.Controller;
import tom.cocook.annotation.Handler.Resource;
import tom.cocook.core.CocookException;

@Controller
public class DeemoController extends BaseAction{
	
	@Resource(DemoService.class)
	DemoService demoService;
	
	public String deemo(HttpServletRequest req, Map<String, String> map) {
		System.out.println("deemo/deemo");
		return super.exec(req, map);
	}

	public static void read(){
		try {
			Class.forName("DeemoController").newInstance();
		} catch(Exception e){
			throw new CocookException(e);
		}

	}
	
	
	public static void main(String[] args) {
		try{
			read();
		}catch(Exception e){
			System.out.println("e==="+e);
		}
		
	}
}
