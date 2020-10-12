package com.zhengjieblog.pocket.service.impl;

import com.zhengjieblog.pocket.entity.Pocket;
import com.zhengjieblog.pocket.entity.PocketDetail;
import com.zhengjieblog.pocket.entity.User;
import com.zhengjieblog.pocket.repo.PocketDetailRepo;
import com.zhengjieblog.pocket.service.PocketDetailService;
import com.zhengjieblog.pocket.util.ResponseData;
import com.zhengjieblog.pocket.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 郑杰
 * @date 2018-7-20
 */
@Service
public class PocketDetailServiceImpl implements PocketDetailService {
    @Autowired
    private PocketDetailRepo pocketDetailRepo;

    @Override
    public Pocket create(User user, Pocket pocket, PocketDetail pocketDetail) {
        Double money = (double)Math.round(pocketDetail.getMoney()*100)/100;
        pocket.setUser(user);
        if(pocketDetail.getPocketType().equals("收入")){
            pocket.setIncome(pocket.getIncome()+money);
            pocketDetail.setPayMethod(null);
        } else {
            pocket.setOutlay(pocket.getOutlay()+money);
        }
        pocket.setBalance(pocket.getIncome()-pocket.getOutlay());
        pocketDetail.setPocket(pocket);
        pocketDetail.setMoney(money);
        pocketDetail.setHouse(TimeUtil.gethouse(pocketDetail.getTime()));
        pocketDetailRepo.save(pocketDetail);
        return pocket;
    }

    @Override
    public ResponseData delete(Long id) {
        ResponseData responseData = ResponseData.ok();
        PocketDetail pocketDetail = pocketDetailRepo.findOne(id);
        if(pocketDetail == null){
            responseData = ResponseData.serverInternalError();
        } else {
            pocketDetailRepo.delete(id);
        }
        return responseData;
    }

    @Override
    public ResponseData info(Long id) {
        ResponseData responseData = ResponseData.ok();
        PocketDetail pocketDetail = pocketDetailRepo.findOne(id);
        if(pocketDetail == null){
            responseData = ResponseData.serverInternalError();
        }
        responseData.putDataValue("pocketDetail",pocketDetail);
        return responseData;
    }

    @Override
    public PocketDetail getPocketDetail(Long id) {
        return pocketDetailRepo.findOne(id);
    }

    @Override
    public Pocket edit(Pocket pocket, PocketDetail now, PocketDetail old) {
        Double money = (double)Math.round(now.getMoney()*100)/100;
        if(now.getPocketType().equals("收入")){
            pocket.setIncome(pocket.getIncome()+money);
            old.setPayMethod(null);
        } else {
            pocket.setOutlay(pocket.getOutlay()+money);
            old.setPayMethod(now.getPayMethod());
        }
        pocket.setBalance(pocket.getIncome()-pocket.getOutlay());
        old.setPocketType(now.getPocketType());
        old.setType(now.getType());
        old.setPocket(pocket);
        old.setMoney(money);
        old.setRemark(now.getRemark());
        old.setHouse(TimeUtil.gethouse(now.getTime()));
        old.setTime(now.getTime());
        pocketDetailRepo.save(old);
        return pocket;
    }
}
