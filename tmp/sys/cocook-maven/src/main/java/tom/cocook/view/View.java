package tom.cocook.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import tom.cocook.config.Constants;

public abstract class View {

	public abstract void render(String view);
	
	abstract void init();
	
	public static Properties config() {
		Properties properties = new Properties();
		InputStream in = null;
		try {
			File file = new File(Constants.getWebRoot(), Constants.getViewConfigLocation());
			in = new FileInputStream(file);
			properties.load(in);
		} catch (Exception e) {
			return null;
		} finally {
			if (in != null)
				try {
					in.close();
				} catch (IOException e) {
				}
		}
		return properties;
	}

}
