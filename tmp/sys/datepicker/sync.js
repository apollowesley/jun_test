// 将最新文件同步到项目主页中
var Client = require('ftp');
// ftp的配置信息，包括host，user，password
var syncConfig = require('./sync-config');

var c = new Client();
var task=0;
c.on('ready', function () {
    console.log('start sync');
    c.put('dist/datepicker.min.css', '/demo/datepicker/dist/datepicker.min.css', function (err) {
        if (err) throw err;
        console.log('mdeditor.min.css sync success');
        task++;
        end();
    });


    c.put('dist/datepicker.min.js', '/demo/datepicker/dist/datepicker.min.js', function (err) {
        if (err) throw err;
        console.log('mdeditor.min.js sync success');
        task++;
        end();
    });

    c.put('index.html', '/demo/datepicker/index.html', function (err) {
        if (err) throw err;
        console.log('index.html sync success');
        task++;
        end();
    });


});
function end() {
    if(task==3){
        c.end();
    }
}
c.connect(syncConfig);

