var express = require('express');
var router = express.Router();

/* GET users listing. */
router.get('/', function(req, res, next) {
  var body =' <!DOCTYPE html> '+
        ' <html> '+
        ' <head></head> '+
        ' <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/> '+
        ' <body> '+
        ' <input type="button" id="btn1" value="生成数据" onclick="jgb.test();"> '+
        ' <input type="button" id="btn2" value="编辑元数据"> '+
        ' <div id="tblDiv"></div> '+
        ' <div id="dataDiv"></div> '+

        ' </body> '+
        ' </html> ';
//   res.writeHead(200,{"Content-Type":"text/html"});
//   res.write(body);
//   res.end();
  res.send(body);

});

module.exports = router;
