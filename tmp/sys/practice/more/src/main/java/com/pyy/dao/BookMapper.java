package com.pyy.dao;

import java.util.List;

import com.pyy.model.Book;

public interface BookMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Book record);

    Book selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Book record);
    
    List<Book> selectAllBooks();

}