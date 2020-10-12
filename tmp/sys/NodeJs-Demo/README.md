# NodeJs-Demo
关于Nodejs的练手案例，编码内容参考网站:http://www.runoob.com/nodejs/nodejs-tutorial.html

### 一、npm publish发布相关命令

- 1.1设置npm账号:npm login

- 1.2查看当前用户:npm whoami

- 1.3编写模块代码(kiwipeach.js):
```javascript
var kiwipeach = {
    name: null,
    set_name: function (name) {
        this.name = name;
    },
    get_name: function () {
        return this.name;
    },
    sayHello: function sayHello() {
        console.log('Hello ' + kiwipeach.name);
    }
}
module.exports = kiwipeach;

```

- 1.4初始化项目:npm init，将会生成package.json文件的描述信息
```json
{
  "name": "npm-kiwipeach",
  "version": "1.0.2",
  "description": "测试npm发布使用",
  "main": "kiwipeach.js",
  "scripts": {
    "test": "npm kiwipeach.js",
    "version": "npm -v"
  },
  "keywords": [
    "demo"
  ],
  "author": "kiwipeach",
  "license": "ISC"
}

```
- 1.5发布模块到npm官网: npm publish

- 1.6下载已经发布的npm: npm i npm-kiwipeach@1.0.1

- 1.7客户端调用
```javascript
var kiwipeach = require('npm-kiwipeach');
kiwipeach.set_name("kakaluote");
kiwipeach.sayHello();
```
- 1.8其他拓展

> 脚本命令使用:npm run test/version



