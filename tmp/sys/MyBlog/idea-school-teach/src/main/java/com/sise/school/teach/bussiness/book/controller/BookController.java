package com.sise.school.teach.bussiness.book.controller;

import com.sise.school.teach.bussiness.book.service.BookService;
import com.sise.school.teach.bussiness.book.vo.resp.BookRespVO;
import com.sise.school.teach.vo.resp.ResponseMsgVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author idea
 * @data 2018/12/7
 */
@RestController
@RequestMapping(value = "/book")
public class BookController {

    @Autowired
    private BookService bookService;



    @PostMapping(value = "/selectAllBookIsOnline")
    @ApiOperation(value = "图书搜索功能")
    public ResponseMsgVO<List<BookRespVO>> selectAllBookIsOnline(){
        return bookService.selectAllBookIsOnline();
    }
}
