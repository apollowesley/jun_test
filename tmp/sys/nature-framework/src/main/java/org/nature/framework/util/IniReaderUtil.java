package org.nature.framework.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IniReaderUtil {
	private static Logger LOGGER = LoggerFactory.getLogger(IniReaderUtil.class);
	
	protected HashMap<String, Object> sections = new HashMap<String, Object>();
	private String currentSecion;
	private Properties current;

	public void IniReader(String filename) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = reader.readLine()) != null) {
				line = line.trim();
				if (line.matches("\\[.*\\]")) {
					currentSecion = line.replaceFirst("\\[(.*)\\]", "$1");
					current = new Properties();
					sections.put(currentSecion, current);
				} else if (line.matches(".*=.*")) {
					if (current != null) {
						int i = line.indexOf('=');
						String name = line.substring(0, i);
						String value = line.substring(i + 1);
						current.setProperty(name, value);
					}
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			LOGGER.error("file named "+filename+" is not found");
			throw new RuntimeException(e);
		} catch (IOException e) {
			LOGGER.error("file named "+filename+" reader error");
			throw new RuntimeException(e);
		}
	}

	public String getValue(String section, String name) {
		Properties p = (Properties) sections.get(section);
		if (p == null) {
			return null;
		}
		String value = p.getProperty(name);
		return value;
	}
}
