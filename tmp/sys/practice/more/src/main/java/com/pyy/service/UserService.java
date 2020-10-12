package com.pyy.service;

import java.util.List;

import com.pyy.model.Book;
import com.pyy.model.User;

/**
 * 
 * @author PYY
 *
 */
public interface UserService {
	
	/**
	 * 
	 * @return
	 */
	List<User> getUserList();
	
	List<Book> getBookList();	
}
