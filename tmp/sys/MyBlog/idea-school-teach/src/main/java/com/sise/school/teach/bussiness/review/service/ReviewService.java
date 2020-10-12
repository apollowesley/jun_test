package com.sise.school.teach.bussiness.review.service;

import com.sise.school.teach.bussiness.review.vo.ReviewVO;

import java.util.List;

/**
 * @author idea
 * @data 2018/11/11
 */
public interface ReviewService {

    void save(ReviewVO reviewVO,String token);

    void update(ReviewVO reviewVO);

    boolean addGood(String reviewCode, String token);

    boolean addBad(String reviewCode, String token);

    ReviewVO findOneByReviewCode(String reviewCode);

    List<ReviewVO> findAllByVideoCode(String videoCode,String token);

}
