package cn.backflow.admin.common.secure;

/**
 * 未验证异常
 * Created by hunan on 2017/5/21.
 */
public class UnauthorizedException extends Exception {

    public UnauthorizedException(String msg){
        super(msg);
    }
}
