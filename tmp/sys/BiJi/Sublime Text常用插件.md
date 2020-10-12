### 配置Sublime Text 2 的Python运行环境
```
1. 在工具栏点击Preferences，打开Browse Packages。在打开的文件夹中找到Python，并打开这个文件夹。找到文件Python.sublime-build，并打开。
2. 修改以下内容：

{
"cmd": ["python", "-u", "$file"],
"path":"F:/Program Files/Python27",
"file_regex": "^[ ]*File \"(...*?)\", line ([0-9]*)",
"selector": "source.python"
}

把path里面的内容修改为编译器的安装目录即可。保存代码，ctrl+b便可以运行了。
```

### Sublime Text 2 安装python自动补全插件jedi


- 输入Package Control:Install Package 打开Install Package面板
- 输入jedi 找到jedi选择回车安装
- 参数配置：
- 点击打开：Preferences->Setting-User
- 添加配置项
```
"auto_complete_triggers": [{"selector": "source.python", "characters": "."}],            
"auto_complete_selector": "-",
"sublime_completions_visibility": "jedi" 
```

### Emmet
前端开发必备，Write less , show more，使用Tab键触发。安装Emmet后，可以输入少量代码后摁Tab键，系统自动补全代码。
### JsFormat
JsForma可以自动帮助你格式化JavaScript代码，形成一种通用的格式，比如对压缩、空格、换行的js代码进行整理，使得js代码结构清晰，易于观看。在已压缩的JS文件中，右键选择jsFormat或者使用默认快捷键（Ctrl+Alt+F），如果该热键被占用了，可以在Preferences→Key Bindings-User中配置： { "keys": ["ctrl+shift+alt+j"], "command": "js_format","context": [{"key": "selector", "operator": "equal", "operand": "source.js,source.json"}]},
### DocBlockr
安装该插件后，可以快速生成各种注释格式，当需要生成注释符号时，输入/*、/然后回车系统即帮你自动生成，如果/后面刚好是一个函数的定义，注释格式会根据函数的参数生成。

### Sidebar Enhancements
sub侧栏右键文件提供的功能很少，但在实际开发中，文件通常会有各种处理请求，而该插件增强侧栏文件右键功能，比如可以直接右键将文件移入回收站，在浏览器中浏览，将文件复制到剪切板等。详情查看sidebar文档
安装该插件前，文件右键选项很少：安装插件后，文件右键选项大大增强：

### CSS Format
CSS Format可以将任意的 CSS、SASS、SCSS、LESS 代码格式化为展开、紧凑、压缩的形式，选中需要格式化的样式代码，右键选中CSS Format，选择需要形成的格式即可。

### ConvertToUTF8
通过本插件，您可以编辑并保存目前编码不被 Sublime Text 支持的文件，特别是中日韩用户使用的 GB2312，GBK，BIG5，EUC-KR，EUC-JP 等。可以通过 File→
Set File Encoding to 菜单对文件编码进行手工转换。例如，您可以打开一个 UTF-8 编码的文件，指定保存为 GBK，反之亦然。

### Terminal
打开文件的终端，终端默认是CMD。ctrl+shift+t 打开文件所在文件夹，ctrl+shift+alt+t 打开文件所在项目的根目录文件夹，可以自己重新配置快捷键。也可以右键open terminal here打开。
### 
因为快捷键过多，下面仅罗列一下比较常用的快捷键：
### 1.操作
- Ctrl + `： 打开Sublime Text控制台（Esc退出）
- Ctrl+Shift+P：打开命令面板（Esc退出）
- Ctrl + K, Ctrl + B： 组合键，显示或隐藏侧栏
- Alt ：光标调到菜单栏，↑↓←→ 移动光标

### 2.编辑
- Ctr+Shift+D：复制粘贴光标所在行
- Alt+.：关闭标签
- Ctrl+/：用//注释当前行。
- Ctrl+Shift+/：用/**/注释。
- Ctrl + Enter： 在当前行下面新增一行然后跳至该行
- Ctrl + Shift + Enter： 在当前行上面增加一行并跳至该行
- Ctrl + ←/→： 进行逐词移动，
- Ctrl + Shift + ←/→： 进行逐词选择
- Ctrl + Shift + ↑/↓： 移动当前行（文件会被修改）
- Ctrl+KK ：从光标处删除至行尾
- Ctrl+K Backspace ：从光标处删除至行首
- Ctrl+Z：撤销
- Ctrl+Y：恢复撤销
- Ctrl+J：合并行（已选择需要合并的多行时）
- Ctrl + [： 选中内容向左缩进
- Ctrl + ]： 选中内容向右缩进
### 3.选择
- Alt+F3：选中关键词后，选中所有相同的词。可以配合Ctrl+D使用。
- Ctrl + D Ctrl + K Ctrl + U：Ctrl + D选择当前光标所在的词并高亮该词所有出现的位置，再次Ctrl + D，会选择该词出现的下一个位置。在多重选词的过程中，Ctrl + K会将当前选中的词进行跳过在多重选词的过程中，Ctrl + U进行回退
- Ctrl+L ：选择光标所在整行
- Ctrl+X：删除光标所在行
- Ctrl + J： 把当前选中区域合并为一行
- Ctrl+Shift+M：选中当前括号内容，重复可选着括号本身
### 4.查找
- （如果有窗口弹出都是Esc退出弹出窗口）
- Ctr+p：输入@显示容器（css或者js里面）
- Ctrl + F： 调出搜索框
- Ctrl + H： 调出替换框进行替换
- Ctrl + Shift + H： 输入替换内容后，替换当前关键字
- Ctrl + Alt + Enter： 输入替换内容后，替换所有匹配关键字。(NOTE: 注意此时如果鼠标焦点在编辑窗口中，则替换失败，将鼠标焦点调到替换框中，Ctrl + Alt + Enter才会起作用)
- Ctrl + Shift + F： 开启多文件搜索&替换
- Alt + C： 切换大小写敏感（Case-sensitive）模式
- Alt + W： 切换整字匹配（Whole matching）模式
- Alt + R： 切换正则匹配模式的开启/关闭
### 5.跳转
- Ctrl + P：列出当前打开的文件（或者是当前文件夹的文件），输入文件名然后 Enter 跳转至该文件，输入@symbol跳转到symbol符号所在的位置，输入#keyword跳转到keyword所在的位置，输入:n跳转到文件的第n行
- Ctrl + R：列出当前文件中的符号（例如类名和函数名，但无法深入到变量名），输入符号名称 Enter 即可以跳转到该处。
- 会列出Markdown文件的大纲
- F12： 快速跳转到当前光标所在符号的定义处（Jump to Definition）。比如当前光标所在为一个函数调用，F12会跳转至该函数的定义处。
- Ctrl + G： 输入行号以跳转到指定行
- Ctrl+M：跳转到括号另一半。
### 6.窗口和Tab页
- Ctrl + N： 在当前窗口创建一个新标签
- Ctrl + Shift + N： 创建一个新窗口（该快捷键 和搜狗输入法快捷键冲突）
- Ctrl + W： 关闭标签页，如果没有标签页了，则关闭该窗口
- Ctrl+Shift+W：关闭所有打开文件
- Ctrl + Shift + T： 恢复刚刚关闭的标签。
- Ctrl +Tag：移动标签。
### 7.屏幕
- F11： 切换普通全屏
- Shift + F11： 切换无干扰全屏
- Alt + Shift + 2： 进行左右分屏
- Alt + Shift + 8进行上下分屏
- Alt + Shift + 5进行上下左右分屏（即分为四屏）
- Ctrl + 数字键： 跳转到指定屏
- Ctrl + Shift + 数字键： 将当前屏移动到指定屏

