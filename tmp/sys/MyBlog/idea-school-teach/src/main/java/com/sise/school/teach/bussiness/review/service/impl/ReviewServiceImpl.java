package com.sise.school.teach.bussiness.review.service.impl;

import com.sise.school.teach.bussiness.review.dao.ReviewDao;
import com.sise.school.teach.bussiness.review.po.DialogPO;
import com.sise.school.teach.bussiness.review.po.ReviewPO;
import com.sise.school.teach.bussiness.review.service.ReviewService;
import com.sise.school.teach.bussiness.review.vo.DialogVO;
import com.sise.school.teach.bussiness.review.vo.ReviewVO;
import com.sise.school.teach.common.GenerateUnionCode;
import com.sise.school.teach.utils.DecodeUtil;
import com.sise.school.teach.utils.LoginUtil;
import com.sise.school.teach.utils.TokenUtil;
import com.sise.school.teach.utils.redis.JedisUtil;
import com.sise.school.teach.utils.redis.SerializeUtil;
import com.sise.school.teach.utils.redis.dto.CommentDTO;
import com.sise.school.teach.utils.redis.dto.RedisTokenDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.sise.school.teach.constants.UnionCodePrefix.REVIEW_PREFIX;

/**
 * @author idea
 * @data 2018/11/11
 */
@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewDao reviewDao;

    private JedisUtil jedisUtil = new JedisUtil();

    @Override
    public void save(ReviewVO reviewVO, String token) {
        byte[] bytes = TokenUtil.getToken(token);
        RedisTokenDTO redisTokenDTO = TokenUtil.getObjMessageFromSerializeObj(SerializeUtil.unserialize(bytes));
        ReviewPO reviewPO = new ReviewPO();
        BeanUtils.copyProperties(reviewVO, reviewPO);
        reviewPO.setCreateDate(new Date());
        reviewPO.setUsername(redisTokenDTO.getAccount());
        reviewPO.setReviewCode(GenerateUnionCode.generateUnionCode(REVIEW_PREFIX));
        reviewDao.save(reviewPO);
    }

    @Override
    public void update(ReviewVO reviewVO) {
        ReviewPO reviewPO = new ReviewPO();
        reviewPO.setUsername(LoginUtil.getUser());
        BeanUtils.copyProperties(reviewVO, reviewPO);
        reviewDao.save(reviewPO);
    }

    @Override
    public boolean addGood(String reviewCode, String token) {
        byte[] bytes = TokenUtil.getToken(token);
        RedisTokenDTO redisTokenDTO = TokenUtil.getObjMessageFromSerializeObj(SerializeUtil.unserialize(bytes));
        if (jedisUtil.isReviewGoodCodeExist(redisTokenDTO.getUnionCode(), reviewCode)) {
            return false;
        }
        ReviewPO reviewPO = reviewDao.findAllByReviewCode(reviewCode);
        reviewPO.setGoods(reviewPO.getGoods() + 1);
        reviewDao.save(reviewPO);
        jedisUtil.addReviewGoodLog(redisTokenDTO.getUnionCode(), reviewCode);
        return true;
    }

    @Override
    public boolean addBad(String reviewCode, String token) {
        byte[] bytes = TokenUtil.getToken(token);
        RedisTokenDTO redisTokenDTO = TokenUtil.getObjMessageFromSerializeObj(SerializeUtil.unserialize(bytes));
        if (jedisUtil.isReviewBadCodeExist(redisTokenDTO.getUnionCode(), reviewCode)) {
            return false;
        }
        ReviewPO reviewPO = reviewDao.findAllByReviewCode(reviewCode);
        reviewPO.setBad(reviewPO.getBad() + 1);
        reviewDao.save(reviewPO);
        jedisUtil.addReviewBadLog(redisTokenDTO.getUnionCode(), reviewCode);
        return true;
    }

    public static void main(String[] args) {
        String result = DecodeUtil.decodeStr("dG9rZW5fa2V5XzM2NzEwMzc0OTk=");
        System.out.println(result);
    }

    @Override
    public ReviewVO findOneByReviewCode(String reviewCode) {
        ReviewPO reviewPO = reviewDao.findAllByReviewCode(reviewCode);
        ReviewVO reviewVO = new ReviewVO();
        BeanUtils.copyProperties(reviewPO, reviewVO);
        List<DialogVO> dialogVOS = new ArrayList<>();
        List<DialogPO> dialogPOS = reviewPO.getDialogList();
        for (DialogPO dialogPO : dialogPOS) {
            DialogVO dialogVO = new DialogVO();
            BeanUtils.copyProperties(dialogPO, dialogVO);
            dialogVO.setReviewCode(reviewCode);
            dialogVOS.add(dialogVO);
        }
        reviewVO.setDialogList(dialogVOS);
        return reviewVO;
    }

    @Override
    public List<ReviewVO> findAllByVideoCode(String videoCode, String token) {
        List<ReviewPO> reviewPOS = reviewDao.findAllByVideoCode(videoCode);
        List<ReviewVO> reviewVOS = new ArrayList<>();
        for (ReviewPO reviewPO : reviewPOS) {
            ReviewVO reviewVO = new ReviewVO();
            BeanUtils.copyProperties(reviewPO, reviewVO);
            List<DialogVO> dialogVOS = new ArrayList<>();
            List<DialogPO> dialogPOS = reviewPO.getDialogList();
            for (DialogPO dialogPO : dialogPOS) {
                DialogVO dialogVO = new DialogVO();
                BeanUtils.copyProperties(dialogPO, dialogVO);
                dialogVO.setReviewCode(reviewPO.getReviewCode());
                dialogVO.setBadIsComment(false);
                dialogVO.setGoodIsComment(false);
                dialogVOS.add(dialogVO);
            }
            reviewVO.setGoodIsComment(false);
            reviewVO.setBadIsComment(false);
            reviewVO.setDialogList(dialogVOS);
            reviewVOS.add(reviewVO);
        }
        //如果用户没有登录，则无法显示是否已经点赞过相应的记录
        //如果登录过，则需要标识出曾经点赞过的记录信息
        if (StringUtils.isNotEmpty(token)) {
            reviewVOS = myCommentHandle(token, reviewVOS);
        }
        return reviewVOS;
    }


    /**
     * 我的评论集合点赞处理
     *
     * @param token
     * @param reviewVOS
     * @return
     */
    public List<ReviewVO> myCommentHandle(String token, List<ReviewVO> reviewVOS) {
        JedisUtil jedisUtil = new JedisUtil();
        CommentDTO commentDTO = jedisUtil.createCommentDTO(token);
        //如果没有登录或者登录过期
        if (commentDTO != null) {
            for (ReviewVO reviewVO : reviewVOS) {
                String reviewCode = reviewVO.getReviewCode();
                if (commentDTO.getReviewGoodCodeList().contains(reviewCode)) {
                    reviewVO.setGoodIsComment(true);
                }
                if (commentDTO.getReviewBadCodeList().contains(reviewCode)) {
                    reviewVO.setBadIsComment(true);
                }
                List<DialogVO> dialogVOS = reviewVO.getDialogList();
                for (DialogVO dialogVO : dialogVOS) {
                    if (commentDTO.getDialogBadCodeList().contains(dialogVO.getDialogCode())) {
                        dialogVO.setBadIsComment(true);
                    }
                    if (commentDTO.getDialogGoodCodeList().contains(dialogVO.getDialogCode())) {
                        dialogVO.setGoodIsComment(true);
                    }
                }
            }
        }
        return reviewVOS;
    }
}
