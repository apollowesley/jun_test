
/*Http中的匿名函数*/

var http = require("https");

function onRequest(request, response) {
    response.writeHead(200, {"Content-Type": "text/plain"});
    response.write("Hello World");
    response.end();
}

http.createServer(onRequest).listen(8888);
console.log("服务发布成功,地址:"+"http://localhost:8888");
