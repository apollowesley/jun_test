package com.sise.school.teach.bussiness.review.service;

import com.sise.school.teach.bussiness.review.vo.DialogVO;

import java.util.List;

/**
 * @author idea
 * @data 2018/11/11
 */
public interface DialogService {

    void save(DialogVO dialog, String reviewCode, String token);

    boolean addGood(String reviewCode, String dialogCode, String token);

    boolean addBad(String reviewCode, String dialogCode, String token);

}
