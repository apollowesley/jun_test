package tom.test.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import tom.cocook.annotation.Handler.Controller;
import tom.cocook.annotation.Handler.Resource;
import tom.cocook.core.CocookException;

@Controller
public class Demo2Controller extends BaseController{
	
	@Resource(DemoService.class)
	DemoService demoService;
	
	public String deemo() {
		System.out.println("deemo/deemo");
		return "view:deemo";
	}

	
	public String dddd() {
		System.out.println("deemo/dddd");
		return "view:dddd";
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
