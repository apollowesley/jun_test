/*url管理*/
let TC_URL = function () {
    /** 前缀 */
    let prefix = "http://localhost:9090/";

    /** 通用的 */
    let getCaptcha = prefix + "common/captcha/getCaptcha"; //获取验证码

    /** 用户 */
    let regist = prefix + "tucaole/user/regist"; //注册
    let activeUser = prefix + "tucaole/user/activeUser"; //激活用户
    let checkEmail = prefix + "tucaole/user/checkEmail"; //检查邮箱唯一性

    return {
        prefix,
        getCaptcha,
        regist,
        activeUser,
        checkEmail
    }
}()

export default {
	TC_URL
}