package com.sise.school.teach.bussiness.review.dao;

import com.sise.school.teach.bussiness.review.po.ReviewPO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author idea
 * @data 2018/11/11
 */
@Repository
public interface ReviewDao extends MongoRepository<ReviewPO,String> {

    ReviewPO findAllByReviewCode(String reviewCode);

    List<ReviewPO> findAllByVideoCode(String videoCode);
}
