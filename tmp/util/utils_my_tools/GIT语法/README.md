# git的基本操作

**设置邮箱和用户名**

- git config --global user.name "你的名字"
- git config --global user.email "你的邮箱"

**[SSH秘钥设置](http://git.oschina.net/oschina/git-osc/wikis/%E5%B8%AE%E5%8A%A9)**

- 1、ssh-keygen -t rsa -C "注册账户的邮箱"
- 2、cat ~/.ssh/id_rsa.pub //会弹出你的秘钥
- 3、登录开源中国的账号并保存秘钥
- 4、ssh -T git@git.oschina.net //若返回Welcome to Git@OSC, yourname! 则表示成功

**推送文件步骤**

- touch 创建文件
- echo 要输入的内容，如果原来有文件的话会覆盖 > 文件名加后缀
- git status
- git add 要添加的文件加后缀
- git commit -m "添加描述说明"
- git push origin master

**拷贝远程库文件**

- git clone //拷贝文件的话要输入ssh链接

**查看远程仓库**

- [git remote -v](https://git-scm.com/book/zh/ch2-5.html#查看远程仓库)

**参考网站**

[菜鸟教程](http://www.runoob.com/git/git-tutorial.html)
[git官方文档](https://git-scm.com/book/zh/v2/%E8%B5%B7%E6%AD%A5-%E5%85%B3%E4%BA%8E%E7%89%88%E6%9C%AC%E6%8E%A7%E5%88%B6)
[易百](http://www.yiibai.com/git/home.html)
[git动画入门教程](http://git.oschina.net/wzw/git-quick-start)
[开源中国](http://gitref.org/zh/index.html)