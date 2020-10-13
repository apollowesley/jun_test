package com.cjlgb.design.common.oauth.feign;

import com.cjlgb.design.common.core.bean.ResultBean;
import com.cjlgb.design.common.core.constant.HttpStatus;
import com.cjlgb.design.common.core.exception.BizException;
import com.cjlgb.design.common.oauth.entity.OauthUser;
import com.cjlgb.design.common.security.bean.AccessToken;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author WFT
 * @date 2020/7/2
 * description:资源Api接口
 */
@FeignClient(value = "cjlgb-design-oauth",contextId = "oauthUserFeign")
public interface OauthUserFeign {

    /**
     * 获取用户信息
     * @param accessToken 访问令牌
     * @param flag 标识
     * @return com.cjlgb.design.common.oauth.entity.OauthUser
     */
    @GetMapping(value = "/user/getMyInfo")
    ResultBean<OauthUser> getMyInfo(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION) String accessToken,
            @RequestHeader(value = "flag") String flag);

    /**
     * 获取用户信息
     * @param accessToken 访问令牌
     * @return com.cjlgb.design.common.oauth.entity.OauthUser
     */
    default OauthUser getMyInfo(AccessToken accessToken){
        ResultBean<OauthUser> result = this.getMyInfo(accessToken.getToken(), accessToken.getFlag());
        if (result.getCode() != HttpStatus.OK){
            //  请求失败直接结束
            throw new BizException(result.getCode(),result.getMsg());
        }
        return result.getData();
    }
}
