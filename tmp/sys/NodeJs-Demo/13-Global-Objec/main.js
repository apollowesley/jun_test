/*全局变量：__filename，__dirname*/
console.log( __filename );
// D:\20180316-Nodejs\13-Global-Objec\inherits.js
console.log( __dirname );
// D:\20180316-Nodejs\13-Global-Objec

/*全局函数：setTimeout*/
function printHello(){
    console.log( "Hello, World!");
}
// 两秒后执行以上函数
setTimeout(printHello, 2000);



/*全局函数：clearTimeout*/
function printHello(){
    console.log( "Hello, World!");
}
// 两秒后执行以上函数
var t = setTimeout(printHello, 2000);

// 清除定时器
clearTimeout(t);