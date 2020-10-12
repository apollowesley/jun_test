var express = require('express');
var app = express();
var fs = require("fs");

app.get('/listUsers', function (req, res) {
    fs.readFile( __dirname + "/" + "users.json", 'utf8', function (err, data) {
        console.log( data );
        res.end( data );
    });
});


var user = {
    "user4" : {
        "name" : "kiwipeach",
        "password" : "xxxxx",
        "profession" : "teacher",
        "id": 8825
    }
};
app.get('/addUser', function (req, res) {
    // 读取已存在的数据
    fs.readFile( __dirname + "/" + "users.json", 'utf8', function (err, data) {
        data = JSON.parse( data );
        data["user4"] = user["user4"];
        console.log( data );
        res.end( JSON.stringify(data));
    });
});

app.get('/query/:id', function (req, res) {
    // 首先我们读取已存在的用户
    fs.readFile( __dirname + "/" + "users.json", 'utf8', function (err, data) {
        data = JSON.parse( data );
        var user = data["user" + req.params.id]
        console.log( user );
        res.end( JSON.stringify(user));
    });
});

app.get('/deleteUser', function (req, res) {

    // First read existing users.
    fs.readFile( __dirname + "/" + "users.json", 'utf8', function (err, data) {
        data = JSON.parse( data );
        delete data["user" + 2];

        console.log( data );
        res.end( JSON.stringify(data));
    });
});



var server = app.listen(8081, function () {
    var host = server.address().address;
    var port = server.address().port;

    console.log("应用实例，访问地址为 http://localhost:8081/listUsers", host, port);
    console.log("应用实例，访问地址为 http://localhost:8081/query/3", host, port);
    console.log("应用实例，访问地址为 http://localhost:8081/addUser", host, port);
    console.log("应用实例，访问地址为 http://localhost:8081/deleteUser", host, port);

});