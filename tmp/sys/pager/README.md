#pager
#项目说明
    一个简单的HTML页面管理插件，可以统一管理页面，控制页面的切换、加载、返回等操作；
    很多时候我们一个HTML 项目并不大，没必要用一个臃肿的UI框架来提供一个页面管理切换的功能，这时就可以使用Pager,Pager功能简单、轻量，提供了完整的页面加载、切换和返回功能，使用简单，基本没有学习成本
#使用说明： 
    将页面内容放入一个元素中，这个元素需要设置 id,class="page",data-init,data-show
    id：（必需）页面的ID；注意：id为全局唯一标识，如果页面出现相同id的page时仅会保留后出现的那个；
    class="page"：（必需）表示页面这是一个页面，如果没有该class，页面将不能被pager管理；
    data-init：（非必需）[函数名称]页面显示时调用的方法，默认与id同名,当需要页面显示时执行该方法，请使用open(id,data)函数来打开一个页面；
    data-show：（非必需）[true|false]默认是否显示，一次只能显示一个，如果指定多个，将会显示最后指定的那个；
    data-ref：（非必须）[元素选择器，一遍使用ID]页面中某个元素设置data-ref属性后，pager将通过设置的选择器找到对应的内容，然后将内容插入到该元素的内部
#函数和属性说明：
    load(url,data)：加载一个远程页面，返回需要显示的页面的ID；
        参数：url，ajax请求的数据
    open(id,data)：打开一个页面；
        参数：
            id:页面ID;
            data:传给页面的数据；
        注意：参数二非必需，如果选择使用参数二，将会将在调用 data-init方法的时候将这个参数传入；
	    如果未指定data-init且也不存在和page的id 同名的函数，传入参数 data将不会有任何作用;
    back()：返回到上一页面
    pager会自动使用 data-src 属性内容来加载图片


#例子：
    <script>
        var pager = new Pager({pageClass : "page"});
    </script>
    <div id="main" class="page">
        <div>hello pager;</div>
        <input type="button" onclick="pager.open('content')">
    </div>
    <div id="content" class="page">
        <div>page content;</div>
    </div>