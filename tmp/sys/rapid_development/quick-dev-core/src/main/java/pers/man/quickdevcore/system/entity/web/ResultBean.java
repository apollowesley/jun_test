package pers.man.quickdevcore.system.entity.web;


import lombok.Getter;
import lombok.Setter;
import pers.man.quickdevcommon.constant.web.CodeEnum;

/**
 * 响应实体
 *
 * @author MAN
 * @version 1.0
 * @date 2020-03-31 20:22
 * @project quick-dev
 */
@Getter
@Setter
public class ResultBean {
    /**
     * 返回信息
     */
    private String msg;
    /**
     * 返回状态
     */
    private String code;
    /**
     * 返回数据
     */
    private Object data;

    /**
     * 成功响应
     * 默认返回消息"操作成功"
     * 无默认返回数据
     *
     * @return
     */
    public static ResultBean success() {
        return success("操作成功");
    }

    /**
     * 成功响应
     * 无返回数据
     * 默认返回数据为空
     *
     * @param msg 返回消息
     * @return
     */
    public static ResultBean success(String msg) {
        return success(msg, null);
    }

    /**
     * 成功响应
     * 无返回消息
     * 默认返回消息为操作成功
     * @param data
     * @return
     */
    public static ResultBean success(Object data) {
        return success("操作成功", data);
    }

    /**
     * 成功响应
     *
     * @param msg  返回消息
     * @param data 返回数据
     * @return
     */
    public static ResultBean success(String msg, Object data) {
        ResultBean resultBean = new ResultBean();
        resultBean.msg = msg;
        resultBean.code = CodeEnum.SUCCESS.getValue();
        resultBean.data = data;
        return resultBean;
    }

    /**
     * 失败响应
     *
     * @return
     */
    public static ResultBean fail() {
        return fail("操作失败");
    }

    /**
     * 失败响应
     *
     * @param msg 返回消息
     * @return
     */
    public static ResultBean fail(String msg) {
        return fail(msg, null);
    }

    /**
     * 失败响应
     * 无返回消息
     * 默认返回消息为操作失败
     * @param data
     * @return
     */
    public static ResultBean fail(Object data) {
        return fail("操作失败", data);
    }

    /**
     * 失败响应
     *
     * @param msg  返回消息
     * @param data 返回数据
     * @return
     */
    public static ResultBean fail(String msg, Object data) {
        ResultBean resultBean = new ResultBean();
        resultBean.msg = msg;
        resultBean.code = CodeEnum.FAIL.getValue();
        resultBean.data = data;
        return resultBean;
    }
}
