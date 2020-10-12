var fs = require("fs");
var zlib = require('zlib');

// 压缩 pip-stream-source.jpg 文件为 pip-stream-source.jpg.gz
fs.createReadStream('pip-stream-source.jpg')
    .pipe(zlib.createGzip())
    .pipe(fs.createWriteStream('pip-stream-source.jpg.gz'));

console.log("文件压缩完成。");