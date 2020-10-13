package common.framework.dsb.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.StringTokenizer;

public class HA implements Serializable {

	String dsb_host;
	int dsb_port;

	static Collection<HA> readHA(String haName) throws Exception {
		String haConfig = System.getProperty(haName);
		if (haConfig == null) {
			throw new NullPointerException("HA [" + haName + "] not configured!");
		}
		StringTokenizer stier = new StringTokenizer(haConfig, ",");
		Collection<HA> has = new ArrayList<HA>();
		while (stier.hasMoreTokens()) {
			String part = stier.nextToken();
			int index = part.indexOf(":");
			String host = part.substring(0, index);
			String sport = part.substring(index + 1);
			int port = Integer.parseInt(sport);
			HA ha = new HA();
			ha.dsb_host = host;
			ha.dsb_port = port;
			has.add(ha);
		}
		return has;
	}

	public static void main(String[] args) throws Exception {
		System.setProperty("dsb_ha_1", "localhost:10000,localhost:20000");
		Collection<HA> has = readHA("dsb_ha_1");
		System.out.println(has.size());
		for (HA ha : has) {
			System.out.println(ha.dsb_host + " " + ha.dsb_port);
		}
	}
}
