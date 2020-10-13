package tom.kit.json.smd;
public class SMDMethodParameter implements Comparable<Object> {
	private String name;

	public SMDMethodParameter(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int compareTo(Object o) {
		if (o == null)
			return 1;
		if (!(o instanceof SMDMethodParameter))
			return 1;
		if ((this.name == null) && (((SMDMethodParameter) o).name == null))
			return 0;
		if (this.name == null)
			return -1;
		return this.name.compareTo(((SMDMethodParameter) o).name);
	}

	public boolean equals(Object o) {
		if (!(o instanceof SMDMethodParameter))
			return false;
		if ((this.name == null) && (((SMDMethodParameter) o).name == null))
			return true;
		return (this.name != null) && (this.name.equals(((SMDMethodParameter) o).name));
	}
}
