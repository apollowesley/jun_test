package common.framework.dsb.main;

import java.util.StringTokenizer;

/**
 * License object
 * 
 * @author david
 * 
 */
public class License {

	private String name;
	private String type;
	private String mac;
	private String issueDate;
	private String expiryDate;
	private String createdOn;

	public License(String lcsString) throws Exception {

		StringTokenizer stier = new StringTokenizer(lcsString, "\n");
		while (stier.hasMoreTokens()) {
			String token = stier.nextToken();

			if (token.startsWith("n")) {
				this.name = token.substring(2).trim();
			} else if (token.startsWith("t")) {
				this.type = token.substring(2).trim();
			} else if (token.startsWith("m")) {
				this.mac = token.substring(2).trim();
			} else if (token.startsWith("i")) {
				this.issueDate = token.substring(2).trim();
			} else if (token.startsWith("e")) {
				this.expiryDate = token.substring(2).trim();
			} else if (token.startsWith("c")) {
				this.createdOn = token.substring(2).trim();
			}
		}
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getMac() {
		return mac;
	}

	public String getIssueDate() {
		return issueDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	@Override
	public String toString() {
		return "License [name=" + name + ", type=" + type + ", mac=" + mac + ", issueDate=" + issueDate + ", expiryDate=" + expiryDate + ", createdOn=" + createdOn + "]";
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
