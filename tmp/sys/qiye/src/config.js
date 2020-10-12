var domain='cs.01film.cn/qiye';//默认前端域名
var api=domain+'/api/public';//api服务端域名
var config={
    appid:101552115164859,
    runLocal:false,//本地模式，不发送ajax请求服务器
    domain:domain,//前端域名
    cdn:'http://dt.01film.cn',
    adminAPI:'http://'+api+'/admin.php',//用戶中心接口
    api:'http://'+api+'/home.php',//服务器api接口
    // userApi:'http://dt.01film.cn/api/public/user.php',//用戶中心接口
    // api:'http://dt.01film.cn/api/public/home.php',//服务器api接口
    fileUrl:{
        image:'http://'+api+'/upload/',//文件服务器通用地址
        media:'http://'+api+'/image/',//图片服務器地址
        file:'http://'+api+'/image/',//媒体服务器地址
    },
    //获取附件服务器的url地址 如果图片地址有http开头就直接返回如果没有将会拼接接口地址
    getFileUrl(url,type){
        var type=type || 'image';
        var fileUrl=this.fileUrl[type] || '';
        if(url && url.slice(0, 4) != "http"){
               url=fileUrl+url;
        }
        return url;
    }
}
export default config