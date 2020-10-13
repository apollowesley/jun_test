package org.apache.center.dto;

import org.apache.center.model.User;
import org.apache.center.model.enums.user.Gender;

public class UserDto extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6180335041371924603L;

	private String genderText;
	
	public void setGenderText(String genderText) {
		this.genderText = genderText;
	}

	public String getGenderText() {
		return Gender.getInstance().getText(getGender());
	}
	
}
