package com.sise.school.teach.bussiness.book.service;

import com.sise.school.teach.bussiness.book.dao.BookDao;
import com.sise.school.teach.bussiness.book.po.BookPO;
import com.sise.school.teach.bussiness.book.vo.req.BookReqVO;
import com.sise.school.teach.bussiness.book.vo.resp.BookRespVO;
import com.sise.school.teach.common.GenerateUnionCode;
import com.sise.school.teach.common.em.StatusEnum;
import com.sise.school.teach.constants.CodePrefixConstants;
import com.sise.school.teach.utils.CodeGenerateUtil;
import com.sise.school.teach.utils.DateUtils;
import com.sise.school.teach.utils.ResponseBuilder;
import com.sise.school.teach.vo.resp.ResponseMsgVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author idea
 * @data 2018/12/7
 */
@Service
@Slf4j
public class BookService {

    private static String LOG_HEAD = "【图书服务】";

    private ReentrantLock reentrantLock = new ReentrantLock();

    @Autowired
    public BookDao bookDao;

    /**
     * 查找所有上线的书籍
     *
     * @return
     */
    public ResponseMsgVO<List<BookRespVO>> selectAllBookIsOnline() {
        try {
            List<BookPO> bookPOS = bookDao.selectAllByStatus(String.valueOf(StatusEnum.IS_UP.getCode()));
            if (CollectionUtils.isEmpty(bookPOS)) {
                return ResponseBuilder.buildSuccessResponVO(Collections.emptyList());
            }
            List<BookRespVO> bookVOS = new ArrayList<>();
            for (BookPO bookPO : bookPOS) {
                BookRespVO bookVO = new BookRespVO();
                BeanUtils.copyProperties(bookPO, bookVO);
                bookVO.setCreateTime(DateUtils.dateToString(bookPO.getCreateTime()));
                bookVO.setUpdateTime(DateUtils.dateToString(bookPO.getUpdateTime()));
                bookVOS.add(bookVO);
            }
            return ResponseBuilder.buildSuccessResponVO(bookVOS);
        } catch (Exception e) {
            log.error(LOG_HEAD + "，图书查询出现异常,异常为{}", e);
            return ResponseBuilder.buildUnkownErrorResponseVO();
        }
    }


    /**
     * 查找所有上线的书籍
     *
     * @return
     */
    public ResponseMsgVO<Boolean> insert(BookReqVO bookReqVO) {
        try {
            reentrantLock.lock();
            if (StringUtils.isBlank(bookReqVO.getBookName())
                    || StringUtils.isBlank(bookReqVO.getDes())
                    || StringUtils.isBlank(bookReqVO.getBookName())){
                log.error(LOG_HEAD+"，图书上传出现参数为空");
                return ResponseBuilder.buildErrorParamResponVO();
            }
            BookPO originBookPO=bookDao.selectOneByBookName(bookReqVO.getBookName());
            //原来就有的数据
            if(originBookPO!=null){
                if (originBookPO.getBookImg().equals(StringUtils.EMPTY)) {
                    originBookPO.setBookImg(bookReqVO.getBookImg());
                }
                if (originBookPO.getBookUrl().equals(StringUtils.EMPTY)) {
                    originBookPO.setBookUrl(bookReqVO.getBookUrl());
                }
                bookDao.updateByBookName(originBookPO);
                return ResponseBuilder.buildSuccessResponVO(true);
            }
            BookPO bookPO=new BookPO();
            BeanUtils.copyProperties(bookReqVO,bookPO);
            bookPO.setBookCode(CodeGenerateUtil.codeGenerate(CodePrefixConstants.BOOK_PREFIX));
            bookPO.setCreateTime(new Date());
            bookPO.setUpdateTime(new Date());
            bookDao.insert(bookPO);
            return ResponseBuilder.buildSuccessResponVO(true);
        } catch (Exception e) {
            log.error(LOG_HEAD + "，图书上传出现异常,异常为{}", e);
            return ResponseBuilder.buildUnkownErrorResponseVO();
        }finally {
            reentrantLock.unlock();
        }
    }

}
