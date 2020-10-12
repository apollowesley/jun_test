package com.sise.school.teach.bussiness.review.dao;

import com.sise.school.teach.bussiness.review.po.DialogPO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author idea
 * @data 2018/11/11
 */
@Repository
public interface DialogDao extends MongoRepository<DialogPO, String> {

}
