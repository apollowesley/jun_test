package com.iotechn.iot.gw.ws;

import com.alibaba.fastjson.JSONObject;
import com.iotechn.iot.commons.entity.exception.ServiceException;
import com.iotechn.iot.gw.enums.WSRequestType;
import com.iotechn.iot.gw.exception.GatewayExceptionDefinition;
import com.iotechn.iot.gw.exception.GatewayServiceException;
import com.iotechn.iot.gw.model.WSRequest;
import com.iotechn.iot.gw.notify.NotifyHolder;
import com.iotechn.iot.gw.notify.NotifyProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yeauty.annotation.*;
import org.yeauty.pojo.Session;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: rize
 * Date: 2018-12-24
 * Time: 上午11:56
 */
@ServerEndpoint(value = "/ws.api" , port = 8002)
public class ApiWebsocket implements NotifyProcessor {

    private static final Logger logger = LoggerFactory.getLogger(ApiWebsocket.class);

    @Autowired
    private NotifyHolder notifyHolder;

    /**
     * 终端ws唯一关联的key
     */
    private String secretKey;

    private Session session;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        session.sendText("ok\r\n");
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        String result = handleMessage(message);
        session.sendText(result);
    }

    /**
     * 业务处理核心方法
     *
     * @param message
     * @return
     */
    private String handleMessage(String message) {
        try {
            WSRequest wsRequest = JSONObject.parseObject(message, WSRequest.class);
            if (WSRequestType.PARAM.getType() == wsRequest.getType()) {
                //请求之后，将订阅此secretKey的参数变动 TODO checkSecretKey合法性
                if (!notifyHolder.holdParam(wsRequest.getSecretKey(), this)) {
                    return "failed";
                }
                this.secretKey = wsRequest.getSecretKey();
            } else if (WSRequestType.HEART.getType() == wsRequest.getType()) {
                return "pong";
            } else {
                throw new GatewayServiceException(GatewayExceptionDefinition.GATEWAY_WS_REQUEST_NOT_DEFINE);
            }
            return "ok";
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            return e.getMessage();
        } catch (Exception e) {
            logger.error("[处理ws消息] 异常", e);
            return GatewayExceptionDefinition.GATEWAY_SYSTEM_INNER_UNKNOWN_EXCEPTION.getMsg();
        }
    }

    @OnClose
    public void onClose() {
        //当关闭连接时，从redis通知列表移除
        notifyHolder.unholdParam(this.secretKey);
        //当加了其他通知时，别忘了在这里移除
    }

    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("[ws 发生异常]", error);
    }

    @Override
    public void process(String msg) {
        //将变动通过ws发送给前端
        this.session.sendText(msg);
    }
}
