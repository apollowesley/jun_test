package org.rapidoid.docs.jpascaffold;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Book {

	@Id
	@GeneratedValue
	public Long id;

	@NotNull
	public String title;

	public int year;

}