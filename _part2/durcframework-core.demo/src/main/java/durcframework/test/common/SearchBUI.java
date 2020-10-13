package durcframework.test.common;

import org.durcframework.core.SearchEntity;

public class SearchBUI extends SearchEntity {

	private String field;
	private String direction;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.setSortname(field);
		this.field = field;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.setSortorder(direction);
		this.direction = direction;
	}

}
