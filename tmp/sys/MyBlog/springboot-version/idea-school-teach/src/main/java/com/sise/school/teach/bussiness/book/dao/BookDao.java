package com.sise.school.teach.bussiness.book.dao;

import com.sise.school.teach.bussiness.book.po.BookPO;
import com.sise.school.teach.common.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author idea
 * @data 2018/12/7
 */
@Mapper
public interface BookDao extends BaseDao<BookPO> {

    /**
     * 通过状态搜索书本
     *
     * @param status
     * @return
     */
    List<BookPO> selectAllByStatus(@Param("status") String status);

    /**
     * 根据图书名称进行查找
     *
     * @param bookName
     * @return
     */
    BookPO selectOneByBookName(@Param("bookName") String bookName);

    /**
     * 根据图书名称进行更新
     *
     */
    void updateByBookName(BookPO bookPO);
}
