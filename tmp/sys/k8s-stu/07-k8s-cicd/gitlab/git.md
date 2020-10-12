# git
https://git-scm.com/


#gvfs
https://github.com/Microsoft/VFSForGit
https://gitee.com/mirrors/VFS-for-Git


https://blog.csdn.net/shenhonglei1234/article/details/53884906

https://blog.csdn.net/shenhonglei1234/article/details/80510991
https://www.cnblogs.com/sunsky303/p/12851267.html

[git/svn比较](https://my.oschina.net/GIIoOS/blog/3163800?from=20200209)


# git 基本使用
```
//git 帮助信息
git config [-h]    //terminal
git config --help    //git-doc

//查看本地配置
git config --list   

//设置全局账户信息，存储在~/.gitconfig
git config --global user.name "mvpbang"
git config --global user.email "mvpbang@163.com"

//设置局部账户信息，仅仅对当前repo有效且优于全局
git config  user.name "mvpbang"
git config  user.email "mvpbang@163.com"

//修改设置的信息
git config --replace-all user.name  "mvpbang"
git config --replace-all user.email "mvpbang@qq.com"
````


#git config 
```
$ git config
usage: git config [<options>]
 
Config file location
    --global              use global config file
    --system              use system config file
    --local               use repository config file
    --worktree            use per-worktree config file
    -f, --file <file>     use given config file
    --blob <blob-id>      read config from given blob object
 
Action
    --get                 get value: name [value-regex]
    --get-all             get all values: key [value-regex]
    --get-regexp          get values for regexp: name-regex [value-regex]
    --get-urlmatch        get value specific for the URL: section[.var] URL
    --replace-all         replace all matching variables: name value [value_regex]
    --add                 add a new variable: name value
    --unset               remove a variable: name [value-regex]
    --unset-all           remove all matches: name [value-regex]
    --rename-section      rename section: old-name new-name
    --remove-section      remove a section: name
    -l, --list            list all
    -e, --edit            open an editor
    --get-color           find the color configured: slot [default]
    --get-colorbool       find the color setting: slot [stdout-is-tty]
 
Type
    -t, --type <>         value is given this type
    --bool                value is "true" or "false"
    --int                 value is decimal number
    --bool-or-int         value is --bool or --int
    --path                value is a path (file or directory name)
    --expiry-date         value is an expiry date
 
Other
    -z, --null            terminate values with NUL byte
    --name-only           show variable names only
    --includes            respect include directives on lookup
    --show-origin         show origin of config (file, standard input, blob, command line)
    --default <value>     with --get, use default value when missing entry
```

# git存储认证账户
问题描述：
```
git pull/push 每次都要输入账户密码认证，太烦人了
```

问题解决：
```
$ git remote -v  //git remote rm origin 移除
origin  https://gitee.com/m0p/k8s-stu.git (fetch)
origin  https://gitee.com/m0p/k8s-stu.git (push)

$ git config --global credential.helper store //本地存储git认证的账户信息，明文的太蛋疼了
C:\Users\{user_name}\.git-credentials

$ git push    //根据提示输入账户密码，即可存储在对应文件中下次提交不在提示输入信息
```


# git切换远程仓库
```
git remote rm origin
git remote add origin xxxx
git fetch

git branch --set-upstream-to=origin/master  master   //本地master指向服务器端master
git pull
```