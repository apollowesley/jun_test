package tom.kit.json.smd;

import java.util.Set;
import java.util.TreeSet;

public class SMDMethod implements Comparable<Object> {
	private String name;
	private Set<SMDMethodParameter> parameters = new TreeSet();

	public SMDMethod(String name) {
		this.name = name;
	}

	public void addSMDMethodParameter(SMDMethodParameter parameter) {
		this.parameters.add(parameter);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<SMDMethodParameter> getParameters() {
		return this.parameters;
	}

	public int compareTo(Object o) {
		if (o == null)
			return 1;
		if (!(o instanceof SMDMethod))
			return 1;
		SMDMethod other = (SMDMethod) o;
		if ((this.name == null) && (other.name == null))
			return 0;
		if (this.name == null)
			return -1;
		if (this.name.equals(other.name)) {
			return this.parameters.size() - other.parameters.size();
		}
		return this.name.compareTo(other.name);
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof SMDMethod))
			return false;
		SMDMethod toCompare = (SMDMethod) obj;
		if ((this.name == null) && (toCompare.name == null))
			return true;
		return (this.name != null) && (this.name.equals(toCompare.name)) && (this.parameters.size() == toCompare.parameters.size());
	}
}
