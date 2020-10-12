package com.zhengjieblog.pocket.service.impl;

import com.zhengjieblog.pocket.entity.Pocket;
import com.zhengjieblog.pocket.entity.PocketDetail;
import com.zhengjieblog.pocket.repo.PocketRepo;
import com.zhengjieblog.pocket.repo.spec.PocketSpec;
import com.zhengjieblog.pocket.service.PocketService;
import com.zhengjieblog.pocket.util.ResponseData;
import com.zhengjieblog.pocket.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author 郑杰
 * @date 2018-7-16
 */
@Service
public class PocketServiceImpl implements PocketService {

    @Autowired
    private PocketRepo pocketRepo;

    @Override
    public Pocket getPocketByYearAndMonthAndDay(Date time) {
        Pocket pocket = pocketRepo.findByYearAndMonthAndDay(TimeUtil.getYear(time),TimeUtil.getMonth(time),TimeUtil.getDay(time));
        if(pocket == null){
            pocket = new Pocket();
            pocket.setYear(TimeUtil.getYear(time));
            pocket.setMonth(TimeUtil.getMonth(time));
            pocket.setDay(TimeUtil.getDay(time));
            pocket.setWeekDay(TimeUtil.getweekDay(time));
            pocketRepo.save(pocket);

        }
        return pocket;
    }

    @Override
    public void save(Pocket pocket) {
        pocketRepo.save(pocket);
    }

    @Override
    public List<Pocket> find(PocketSpec pocketSpec, Pageable pageable) {
        Page<Pocket> page = pocketRepo.findAll(pocketSpec,pageable);
        List<Pocket> pockets = page.getContent();
        List<Pocket> pockets1 = new ArrayList<>();
        for(Pocket pocket:pockets){
            List<PocketDetail> pocketDetails = pocket.getPocketDetails();
            Collections.sort(pocketDetails, new Comparator<PocketDetail>() {
                @Override
                public int compare(PocketDetail o1, PocketDetail o2) {
                    Long i = o2.getTime().getTime() - o1.getTime().getTime();
                    if(i == 0){
                        Long j = o2.getId() - o1.getId();
                        return j.intValue();
                    }
                    return i.intValue();
                }
            });
            pocket.setPocketDetails(pocketDetails);
            pockets1.add(pocket);
        }
        return pockets1;
    }

    @Override
    public ResponseData findDetail(PocketSpec pocketSpec) {
        List<Pocket> pockets = pocketRepo.findAll(pocketSpec);
        Double outlay=0D;
        Double income=0D;
        Double balance=0D;
        if(null == pockets||pockets.size() == 0){
            ResponseData responseData = ResponseData.ok();
            responseData.putDataValue("outlay",outlay);
            responseData.putDataValue("income",income);
            responseData.putDataValue("balance",balance);
            return responseData;
        }
        for(Pocket pocket:pockets){
            outlay+=pocket.getOutlay();
            income+=pocket.getIncome();
        }
        balance = income - outlay;
        ResponseData responseData = ResponseData.ok();
        responseData.putDataValue("outlay",outlay);
        responseData.putDataValue("income",income);
        responseData.putDataValue("balance",balance);
        return responseData;
    }

    @Override
    public void check(Long userID) {
        List<Pocket> pockets = pocketRepo.findAll(new PocketSpec(userID,true));
        pocketRepo.delete(pockets);
    }

    @Override
    public void updatePocket(Pocket oldPocket, PocketDetail oldPocketDetail) {
        if(oldPocketDetail.getPocketType().equals("收入")){
            oldPocket.setIncome(oldPocket.getIncome()-oldPocketDetail.getMoney());
        } else {
            oldPocket.setOutlay(oldPocket.getOutlay()-oldPocketDetail.getMoney());
        }
        oldPocket.setBalance(oldPocket.getIncome()-oldPocket.getOutlay());
        pocketRepo.save(oldPocket);
    }
}
