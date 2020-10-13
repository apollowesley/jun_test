#Ghost-login
***
> - 此处所说的模拟登录不是指利用网站本身提供的认证API接口进行登录，主要是通过分析前端代码，摸清登录认证原理、核心加密代码，然后程序模拟，来实现自动登录网站的过程；
> - 专门用来解决爬虫采集相关网站数据时模拟登录，验证码自动识别的问题；欢迎加入一起开发完善。
> - Specifically designed to solve the crawler when collecting Internet web data who need to login the web-site by useing some  Simulated ways. The Ghost-login will verificate the captcha code automatically; Welcome to join in together to develop and improve it.

***
---

#主要基于以下第三方lib及技术 
* 1. [HttpComponents 4.5.x](http://hc.apache.org/downloads.cgi) HTTP请求以及响应
* 2. [Selenium 2.53.x](http://docs.seleniumhq.org/download/) 模拟自动登录
* 3. [Bouncycastle](http://www.bouncycastle.org/) 加密解密
* 4. [Tesseract-ocr](https://github.com/tesseract-ocr) 验证码Tesseract识别
* 5. [Captcha-Recognize](http://blog.csdn.net/problc/article/details/5794460)验证码LibSVM识别

##模拟登录一些常见的网站Done

* [x] [百度](https://www.baidu.com)(已实现)
* [x] [知乎](https://www.zhihu.com/#signin)(已实现)
* [x] [豆瓣](https://accounts.douban.com/login?redir=https://m.douban.com/)(已实现)
* [x] [QQ微博](http://w.t.qq.com/touch) (已实现)
* [x] [Sina微博](https://passport.weibo.cn/signin/login?entry=mweibo&res=wel&wm=3349&r=http%3A%2F%2Fm.weibo.cn%2F%3Fjumpfrom%3Dwapv4%26tip%3D1) (已经实现)
* [x] [京东](http://www.jd.com/)(已实现)
* [x] [淘宝](https://www.taobao.com/)(已实现)
* [x] [新浪邮箱](http://mail.sina.com.cn/)(已实现)
* [x] [QQ邮箱](https://mail.qq.com/cgi-bin/loginpage)(已实现)
* [x] [163邮箱](http://smart.mail.163.com/?dv=smart)(已实现)
* [ ] [126邮箱](http://smart.mail.126.com/?dv=smart)(还有点问题)
* [ ] [支付宝](https://www.alipay.com/)(还有点问题)


##Todolist
0. **重构代码，增加可扩展性**
1. 增加简单验证码识别模块;
2. 重新组织文件结构和代码风格;
3. 增加可扩展性，方便添加新的功能;

## tips of pull request 

欢迎大家一起来 pull request 

0. 兼容JDK1.8版本；
1. 自动模拟登录新的网；
2. 改进bug, 完善代码；
3. 增加新的模拟自动登录的方法；

## something to add

0. 网站的前端更新、验证、变化较快，若不能用了请及时告知，我会定期修改完善，同时热烈欢迎有兴趣的加入我们。
1. 接下来最重要的是重构代码，让大家可以更容易的做出一些满足个性需求的功能。
2. 如果你觉得某个网站的登录很有代表性，欢迎在 issue 中提出，如果你感觉网站的模拟自动登录很有意思，加入我们吧。
3. 验证码的识别模块还有待完善...

## 除责申明

1. 本开源项目仅为技术交流此一目的，严禁用于其他任何商业、违法犯罪、恶意攻击等行为；
3. 若第三者用此项目侵犯相关网站权益，一切责任自负；
2. 若本项目侵犯相关网站、个人，组织机构权益，请及时联系；

## 交流讨论

1. 本项目会不断更新，欢迎提交有难度的登录网站；
2. 开源网络爬虫QQ交流群:322937592
3. Email address: liinux at qq.com

## 联系作者

- [oschina](http://git.oschina.net/liinux)
- [cnblogs](http://www.cnblogs.com/liinux)
- [github](https://github.com/liinnux)