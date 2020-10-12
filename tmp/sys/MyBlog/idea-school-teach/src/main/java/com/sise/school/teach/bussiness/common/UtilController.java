package com.sise.school.teach.bussiness.common;

import com.sise.school.teach.utils.*;
import com.sise.school.teach.utils.redis.JedisFactory;
import com.sise.school.teach.utils.redis.SerializeUtil;
import com.sise.school.teach.utils.redis.dto.RedisTokenDTO;
import com.sise.school.teach.vo.resp.ResponseMsgVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.sise.idea.service.ApplicationSettingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import static com.sise.school.teach.constants.ApplicationConstants.*;

/**
 * @author idea
 * @data 2018/11/29
 */
@Api(description = "工具控制器")
@RestController
@RequestMapping(value = "/util")
@Slf4j
public class UtilController {


    @PostMapping(value = "/sendSms")
    public ResponseMsgVO<Boolean> sendSms(String tel) {
        try {
            String content = ApplicationSettingService.getValue(SMS_TEMPLATE_KEY) + CodeGenerateUtil.buildRandNumber(ApplicationSettingService.getInteger(SMS_CODE_LENGTH_KEY));
            SmsUtil.sendSms(ApplicationSettingService.getValue(SMS_API_KEY), content, tel);
            return ResponseBuilder.buildSuccessResponVO(true);
        } catch (Exception e) {
            log.error("[工具控制器]短信发送失败，出现未知异常{}", e);
            return ResponseBuilder.buildUnkownErrorResponseVO();
        }
    }

    @PostMapping(value = "/sendEmail")
    public ResponseMsgVO<Boolean> sendEmail(String title, String content, String reciverEmailAddr) {
        try {
            EmailUtil.asyncSendEmail(title, content, reciverEmailAddr);
            return ResponseBuilder.buildSuccessResponVO(true);
        } catch (Exception e) {
            log.error("[工具控制器]邮件发送失败，出现未知异常{}", e);
            return ResponseBuilder.buildUnkownErrorResponseVO();
        }
    }

    @PostMapping(value = "/token/getMessage")
    public ResponseMsgVO<Object> activation(String token) {
        if (StringUtils.isBlank(token)) {
            return ResponseBuilder.buildErrorParamResponVO();
        } else {
            byte[] bytes = TokenUtil.getToken(token);
            RedisTokenDTO redisTokenDTO = TokenUtil.getObjMessageFromSerializeObj(SerializeUtil.unserialize(bytes));
            return ResponseBuilder.buildSuccessResponVO(redisTokenDTO);
        }
    }
}
