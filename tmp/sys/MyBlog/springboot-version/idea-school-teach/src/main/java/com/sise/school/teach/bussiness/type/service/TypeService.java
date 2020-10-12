package com.sise.school.teach.bussiness.type.service;

import com.sise.school.teach.bussiness.type.dao.TypeDao;
import com.sise.school.teach.bussiness.type.po.TypePO;
import com.sise.school.teach.bussiness.type.vo.resp.TypeRespVO;
import com.sise.school.teach.utils.ResponseBuilder;
import com.sise.school.teach.vo.resp.ResponseMsgVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author idea
 * @data 2018/10/1
 */
@Service
@Slf4j
public class TypeService {

    @Autowired
    private TypeDao typeDao;

    private static String LOG_HEAD = "[系别服务]";

    public ResponseMsgVO<List<TypeRespVO>> selectAll() {
        try {
            List<TypePO> typePOS = typeDao.selectAll();
            return ResponseBuilder.buildSuccessResponVO(buildTypeReqVOList(typePOS));
        } catch (Exception e) {
            log.error(LOG_HEAD + "插入数据库异常，异常为{}", e);
            return ResponseBuilder.buildUnkownErrorResponseVO();
        }
    }

    /**
     * 构建系别类型集合
     *
     * @param typePOS
     * @return
     */
    private List<TypeRespVO> buildTypeReqVOList(List<TypePO> typePOS) {
        List<TypeRespVO> typeReqVOS = new ArrayList<>();
        typePOS.forEach((typePO -> {
            TypeRespVO typeRespVO = new TypeRespVO();
            BeanUtils.copyProperties(typePO, typeRespVO);
            typeReqVOS.add(typeRespVO);
        }));
        return typeReqVOS;
    }

}
