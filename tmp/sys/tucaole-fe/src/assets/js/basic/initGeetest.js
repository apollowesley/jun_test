import geetest from '../geetest/gt.js'
import axios from 'axios'
import api from '../common/api.js'

let util = function () {
    let gt =  function(res) {
        window.initGeetest({
            gt: res.data.result.gt,
            challenge: res.data.result.challenge,
            offline: ! res.data.result.success, // 表示用户后台检测极验服务器是否宕机，一般不需要关注
            product: "float", // 产品形式，包括：float，popup
            width: "100%"
        }, (captchaObj) => {
            captchaObj.appendTo("#captcha");
            captchaObj.onReady(function () {
                document.getElementById("wait").style.display = 'none';
            });
            captchaObj.onSuccess(function() {
                //false：验证失败 true：验证成功
                window.sessionStorage.setItem('gtFlag', true);
                window.sessionStorage.setItem('token', res.data.result.token);
                window.sessionStorage.setItem('geetest_challenge', captchaObj.getValidate().geetest_challenge);
                window.sessionStorage.setItem('geetest_seccode', captchaObj.getValidate().geetest_seccode);
                window.sessionStorage.setItem('geetest_validate', captchaObj.getValidate().geetest_validate);

            })
        });
    }

    /** 获取验证码 */
    let getGt = function() {
        axios({
            "url" : api.TC_URL.getCaptcha,
            "method" : "GET"
        }).then((data) => {
            gt(data);
        }).catch((err) => {
            // 处理err的逻辑
            this.$layer.msg("验证码初始化失败，请刷新后重试！");
        })
    }

    return {
        getGt
    }
}()

export default {
    util
}