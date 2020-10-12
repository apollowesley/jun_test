package tom.cocook.view;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Locale;
import java.util.Properties;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import tom.cocook.config.Constants;
import tom.cocook.core.RequestContext;

import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class FreeMarkerView extends View {

	private static final Configuration config = new Configuration();
	
	private static final String encoding = Constants.getEncoding(); 
	
	@Override
	public void init() {
		Properties properties = config();
		if(properties!=null){
			try {
				config.setSettings(properties);
			} catch (TemplateException e) {
				e.printStackTrace();
			}
			return;
		}

        // Initialize the FreeMarker configuration;
        // - Create a configuration instance
        // config = new Configuration();
        // - Templates are stoted in the WEB-INF/templates directory of the Web app.
        //config.setServletContextForTemplateLoading(servletContext, "/");	// "WEB-INF/templates"
        try{
        	config.setDirectoryForTemplateLoading(new File(Constants.getWebRoot()));
        }catch(IOException e){
        }
        
        // - Set update dealy to 0 for now, to ease debugging and testing.
        //   Higher value should be used in production environment.
        
    	config.setTemplateUpdateDelay(0);
       
        
        // - Set an error handler that prints errors so they are readable with
        //   a HTML browser.
        // config.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        
        // - Use beans wrapper (recommmended for most applications)
        config.setObjectWrapper(ObjectWrapper.BEANS_WRAPPER);
        // - Set the default charset of the template files
        config.setDefaultEncoding(encoding);		// config.setDefaultEncoding("ISO-8859-1");
        // - Set the charset of the output. This is actually just a hint, that
        //   templates may require for URL encoding and for generating META element
        //   that uses http-equiv="Content-type".
        config.setOutputEncoding(encoding);			// config.setOutputEncoding("UTF-8");
        // - Set the default locale
        config.setLocale(Locale.CHINA /* Locale.CHINA */ );		// config.setLocale(Locale.US);
        config.setLocalizedLookup(false);
        
        // 去掉int型输出时的逗号, 例如: 123,456
        // config.setNumberFormat("#");		// config.setNumberFormat("0"); 也可以
        config.setNumberFormat("#0.#####");
    
	}

	@Override
	public void render(String view) {
		RequestContext context = RequestContext.get();
		HttpServletResponse res = context.getResponse();
		
		res.setContentType("text/html;charset="+encoding);
		res.setCharacterEncoding(encoding);
		PrintWriter writer = null;
        try {
			Template template = config.getTemplate(view);
			ServletOutputStream output = res.getOutputStream();
			template.process(context.getAttributsMap(), new OutputStreamWriter(output, encoding));		// Merge the data-model and the template
		} catch (Exception e) {
			try{
        		error(res, e);
        	}catch(IOException ee){}
		}
		finally {
			if (writer != null)
				writer.close();
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
