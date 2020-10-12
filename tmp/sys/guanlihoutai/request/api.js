import config from '../config/index.js'
// var API = config.API;
var API = config.API;
import NEWS from './module/news.js';
import LOGIN from './module/login.js';
import GOODS from './module/goods.js';
import USER from './module/user.js';
import WALLET from './module/wallet.js';
var index={
   
    Device_edition: {
        url: API + "/home/update/edition/",
    },
    
    Captcha: {
        url: API + "/verify/home/captcha",
        method: 'GET',
        header: {
            Authorization: true
        }
    }
}
export default {
    ...GOODS,
    ...LOGIN,
    ...NEWS,
    ...USER,
    ...WALLET,
    ...index
    
}
