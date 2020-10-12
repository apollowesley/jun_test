package com.zhengjieblog.pocket.controller;

import com.zhengjieblog.pocket.entity.Pocket;
import com.zhengjieblog.pocket.entity.PocketDetail;
import com.zhengjieblog.pocket.entity.User;
import com.zhengjieblog.pocket.service.PocketDetailService;
import com.zhengjieblog.pocket.service.PocketService;
import com.zhengjieblog.pocket.service.UserService;
import com.zhengjieblog.pocket.util.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author 郑杰
 * @date 2018-7-20
 */
@RestController
@RequestMapping("pocketDetail")
public class PocketDetailController {

    @Autowired
    private PocketDetailService pocketDetailService;

    @Autowired
    private PocketService pocketService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseData create(@RequestParam Long userID, @RequestBody PocketDetail pocketDetail){
        ResponseData responseData = ResponseData.ok();
        User user = null;
        Pocket pocket = pocketService.getPocketByYearAndMonthAndDay(pocketDetail.getTime());
        try {
            user = userService.getUserByID(userID);
        } catch (Exception e) {
            responseData = ResponseData.serverInternalError();
            return responseData;
        }
        pocket = pocketDetailService.create(user,pocket,pocketDetail);
        pocketService.save(pocket);
        return responseData;
    }

    @DeleteMapping
    public ResponseData delete(@RequestParam Long id,@RequestParam Long userID){
        ResponseData responseData =pocketDetailService.delete(id);
        pocketService.check(userID);
        return responseData;
    }

    @GetMapping(value = "/info")
    public ResponseData info(@RequestParam Long id){
        return pocketDetailService.info(id);
    }

    @PutMapping
    public ResponseData edit(@RequestParam Long id,@RequestBody PocketDetail pocketDetail){
        PocketDetail oldPocketDetail = pocketDetailService.getPocketDetail(id);
        if(oldPocketDetail == null){
            return ResponseData.serverInternalError();
        }
        Pocket pocket = pocketService.getPocketByYearAndMonthAndDay(pocketDetail.getTime());
        Pocket oldPocket = oldPocketDetail.getPocket();
        pocketService.updatePocket(oldPocket,oldPocketDetail);
        pocket = pocketDetailService.edit(pocket,pocketDetail,oldPocketDetail);
        pocketService.save(pocket);
        return ResponseData.ok();
    }
}
