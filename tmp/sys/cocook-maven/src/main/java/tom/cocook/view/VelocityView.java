package tom.cocook.view;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Properties;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.io.VelocityWriter;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.RuntimeSingleton;
import org.apache.velocity.util.SimplePool;

import tom.cocook.config.Constants;
import tom.cocook.core.RequestContext;

public class VelocityView extends View {

	private static SimplePool writerPool = new SimplePool(40);
	private static final String encoding = Constants.getEncoding(); 

	@Override
	public void init() {
		Properties properties = config();
		String velocityLoadPath = Constants.getWebRoot();
		if(properties != null){
			properties.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, velocityLoadPath);
			properties.setProperty(RuntimeConstants.INPUT_ENCODING, encoding);
			properties.setProperty(RuntimeConstants.OUTPUT_ENCODING, encoding);
			RuntimeSingleton.init(properties);
			return;
		}
		properties = new Properties();
		 /*重新设置模板路径 和 编码*/
		properties.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, velocityLoadPath);
		properties.setProperty(RuntimeConstants.INPUT_ENCODING, encoding);
		properties.setProperty(RuntimeConstants.OUTPUT_ENCODING, encoding);

		RuntimeSingleton.init(properties);
	}

	@Override
	public void render(String view) {
		RequestContext requestContext = RequestContext.get();
		HttpServletResponse res = requestContext.getResponse();
		res.setContentType("text/html;charset="+encoding);
        res.setCharacterEncoding(encoding);
        VelocityWriter vw = null;
		try {
	        Template template =  RuntimeSingleton.getTemplate(view);
	        // init context:
	        Context context = new VelocityContext(requestContext.getAttributsMap());
        
        	ServletOutputStream output = res.getOutputStream();
        	vw = (VelocityWriter) writerPool.get();
        	if(vw == null){
        		//TODO 有问题
        		vw = new VelocityWriter(new OutputStreamWriter(output, encoding), 4 * 1024, true);
        	}else{
        		vw.recycle(new OutputStreamWriter(output, encoding));
        	}
            template.merge(context, vw);
            vw.flush();
        } catch(ResourceNotFoundException ee){
        	try {
				requestContext.error(404, ee.toString());
			} catch (IOException e) {}
        }catch (Exception e) {
        	try{
        		error(res, e);
        	}catch(IOException ee){}
        }finally {
            if (vw != null){
            	vw.recycle(null);
            }
            writerPool.put(vw);
        }
    
	}

	private void error(HttpServletResponse response, Exception cause) throws IOException{
		StringBuffer html = new StringBuffer();
		html.append("<html>");
		html.append("<title>Error</title>");
		html.append("<body bgcolor=\"#ffffff\">");
		html.append("<h2>You know: Error processing the template</h2>");
		html.append("<xmp>");
		/* 打印所有信息 */
		StringWriter sw = new StringWriter();
		cause.printStackTrace(new PrintWriter(sw));
		html.append(sw.toString());
		html.append("</xmp>");
		html.append("</body>");
		html.append("</html>");
		response.getOutputStream().print(html.toString());
	}
}
