package com.zhengjieblog.pocket.controller;

import com.zhengjieblog.pocket.entity.Pocket;
import com.zhengjieblog.pocket.repo.spec.PocketSpec;
import com.zhengjieblog.pocket.service.PocketService;
import com.zhengjieblog.pocket.util.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 郑杰
 * @date 2018-7-18
 */
@RestController
@RequestMapping("pocket")
public class PocketController {

    @Autowired
    private PocketService pocketService;

    @GetMapping
    public ResponseData find(@RequestParam Long userID,
                             @RequestParam Integer year,
                             @RequestParam Integer month,
                             @PageableDefault(value = 5, sort = { "day" }, direction = Sort.Direction.DESC)Pageable pageable){

        ResponseData responseData = ResponseData.ok();
        List<Pocket> pockets = pocketService.find(new PocketSpec(userID,year,month),pageable);
        responseData.putDataValue("pockets",pockets);
        return responseData;
    }

    @GetMapping(value = "detail")
    public ResponseData findDetail(@RequestParam Long userID,
                             @RequestParam Integer year,
                             @RequestParam Integer month){
        return pocketService.findDetail(new PocketSpec(userID,year,month));
    }

}
