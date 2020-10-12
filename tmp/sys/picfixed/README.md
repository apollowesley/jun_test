
picfixed 是一个**等高且等宽**瀑布流,可以让图片或者div轻松实现**等高等宽**瀑布流(这里的等宽是指行等宽)

![等高等宽瀑布流](http://git.oschina.net/uploads/images/2015/1023/140145_9cd6bf9e_393536.jpeg "等高等宽瀑布流,非常适合图库类网站")

#插件特点:
等高 - 同行的所有元素等高<br>
等宽 - 所有行的总体宽度相等<br>
等比缩放 - 所有图片均通过CSS等比例缩放,不会变形<br>
使用简单 - 使用非常简单<br>


#使用方法(详细请看程序里面的例子):
###1.先引入jquery和本程序的fixedpic.js

< script src="jquery.min.js"></script><br>
< script src="fixedpic.js"></script>

###基本使用方法

HTML:
```
<div id="box-img">
		<img src="img/1.jpg" alt="">
		<img src="img/img.webp" alt="">
		<img src="img/img (2).webp" alt="">
		<img src="img/img (3).webp" alt="">
		<img src="img/img (4).webp" alt="">
		<img src="img/img (5).webp" alt="">
		<img src="img/img (6).webp" alt="">
		<img src="img/img (7).webp" alt="">
		<img src="img/img (8).webp" alt="">
		<img src="img/img (9).webp" alt="">
		<img src="img/img (10).webp" alt="">
		<img src="img/img (11).webp" alt="">
		<img src="img/img (12).webp" alt="">
		<img src="img/img (13).webp" alt="">
		<img src="img/img (14).webp" alt="">
		<img src="img/img (15).webp" alt="">
		<img src="img/img (16).webp" alt="">
		<img src="img/img (16).webp" alt="">
	</div>
```
javascript:

```
//指定靠拢高度
$('#box-img').fixedpic(300);
```

```
//指靠拢高度及标准宽度
$('#box-img').fixedpic(350,990);//指定靠拢高度,宽度
```


### 本插件也可以针对DIV

```
    <div id="box-img">
		<div style="width:124px;height:300px;background-color: #ccc;padding:3px;">1</div>
		<div style="width:235px;height:367px;background-color: #233">2</div>
		<div style="width:1110px;height:987px;background-color: #099">3</div>
		<div style="width:821px;height:334px;background-color:#309">4</div>
		<div style="width:314px;height:200px;background-color:#639">5<div>更多说明</div></div>
		<div style="width:327px;height:399px;background-color:#949">6</div>
		<div style="width:500px;height:800px;background-color:#179">7</div>
		<div style="width:600px;height:800px;background-color:#739">8</div>
		<div style="width:800px;height:600px;background-color:#236">9</div>
		<div style="width:480px;height:720px;background-color:#547">0</div>
		<div style="width:300px;height:300px;background-color:#362">11</div>
	</div>
```



###留边框
```
$('#box-img').fixedpicSet({
        paddingTop:11,paddingLeft:11,paddingRight:0,paddingBottom:0,
        borderTop:1,borderLeft:1,borderRight:1,borderBottom:1,
        borderStyle:"dotted"
    }).fixedpic(340,990);
```


