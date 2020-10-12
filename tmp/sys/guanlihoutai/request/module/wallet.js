import {API} from '@/config/index.js'
export default {
   UserWalletBill_Detail:{
       url: API + "/wallet/my.bill/detail",
       method: 'GET',
       header: {
           Authorization: true
       }
   },
   UserWallet_Detail:{
       url: API + "/wallet/my.wallet/info",
       method: 'GET',
       header: {
           Authorization: true
       }
   },
   UserWalletBill_List:{
       url: API + "/wallet/my.bill/list",
       method: 'GET',
       header: {
           Authorization: true
       }
   },
   Wallet_AliPay: {
       url: API + "/wallet/pay.ali/pay",
       method: 'POST',
       header: {
           Authorization: true
       }
   }
   
}
