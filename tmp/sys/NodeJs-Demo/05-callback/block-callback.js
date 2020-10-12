var fs = require("fs");

var data = fs.readFileSync('input.txt');

/*同步阻塞式的输出结果*/
console.log(data.toString());
console.log("程序执行结束!");