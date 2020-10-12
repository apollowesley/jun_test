package com.sise.school.teach.bussiness.review.service.impl;

import com.sise.school.teach.bussiness.review.dao.DialogDao;
import com.sise.school.teach.bussiness.review.service.DialogService;
import com.sise.school.teach.bussiness.review.service.ReviewService;
import com.sise.school.teach.bussiness.review.vo.DialogVO;
import com.sise.school.teach.bussiness.review.vo.ReviewVO;
import com.sise.school.teach.common.GenerateUnionCode;
import com.sise.school.teach.utils.LoginUtil;
import com.sise.school.teach.utils.TokenUtil;
import com.sise.school.teach.utils.redis.JedisUtil;
import com.sise.school.teach.utils.redis.SerializeUtil;
import com.sise.school.teach.utils.redis.dto.RedisTokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.sise.school.teach.constants.UnionCodePrefix.DIALOG_PREFIX;

/**
 * @author idea
 * @data 2018/11/11
 */
@Service
public class DialogServiceImpl implements DialogService {

    @Autowired
    private DialogDao dialogDao;
    @Autowired
    private ReviewService reviewService;

    private JedisUtil jedisUtil=new JedisUtil();

    @Override
    public void save(DialogVO dialogVO, String reviewCode,String token) {
        ReviewVO reviewVO = reviewService.findOneByReviewCode(reviewCode);
        dialogVO.setReviewCode(reviewCode);
        byte[] bytes = TokenUtil.getToken(token);
        RedisTokenDTO redisTokenDTO = TokenUtil.getObjMessageFromSerializeObj(SerializeUtil.unserialize(bytes));
        dialogVO.setAskerName(redisTokenDTO.getAccount());
        dialogVO.setCreateTime(new Date());
        dialogVO.setVideoCode(reviewVO.getVideoCode());
        dialogVO.setDialogCode(GenerateUnionCode.generateUnionCode(DIALOG_PREFIX));
        reviewVO.getDialogList().add(dialogVO);
        reviewService.update(reviewVO);
    }

    @Override
    public boolean addGood(String reviewCode, String dialogCode,String token) {
        byte[] bytes = TokenUtil.getToken(token);
        RedisTokenDTO redisTokenDTO = TokenUtil.getObjMessageFromSerializeObj(SerializeUtil.unserialize(bytes));
        if (jedisUtil.isDialogGoodCodeExist(redisTokenDTO.getUnionCode(), dialogCode)) {
            return false;
        }
        ReviewVO reviewVO = reviewService.findOneByReviewCode(reviewCode);
        List<DialogVO> dialogVOList = reviewVO.getDialogList();
        for (DialogVO dialogVO : dialogVOList) {
            if(dialogVO.getDialogCode().equals(dialogCode)){
                dialogVO.setGoods(dialogVO.getGoods()+1);
            }
        }
        reviewVO.setDialogList(dialogVOList);
        reviewService.update(reviewVO);
        jedisUtil.addDialogGoodLog(redisTokenDTO.getUnionCode(),dialogCode);
        return true;
    }

    @Override
    public boolean addBad(String reviewCode, String dialogCode,String token) {
        byte[] bytes = TokenUtil.getToken(token);
        RedisTokenDTO redisTokenDTO = TokenUtil.getObjMessageFromSerializeObj(SerializeUtil.unserialize(bytes));
        if (jedisUtil.isDialogBadCodeExist(redisTokenDTO.getUnionCode(), dialogCode)) {
            return false;
        }
        ReviewVO reviewVO = reviewService.findOneByReviewCode(reviewCode);
        List<DialogVO> dialogVOList = reviewVO.getDialogList();
        for (DialogVO dialogVO : dialogVOList) {
            if(dialogVO.getDialogCode().equals(dialogCode)){
                dialogVO.setBad(dialogVO.getBad()+1);
            }
        }
        reviewVO.setDialogList(dialogVOList);
        reviewService.update(reviewVO);
        jedisUtil.addDialogBadLog(redisTokenDTO.getUnionCode(),dialogCode);
        return true;
    }

}
