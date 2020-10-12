import Mock from 'mockjs'
import config from '../config'
import {customizedReturn} from '../common'
/*
* 用户登陆
* */
Mock.mock(config.api + '/api/user/login', customizedReturn(true, {
    token: 'token'
}, '登陆成功', 10000));