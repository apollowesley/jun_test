/**
 *  HTML编辑器，支持上传图片，音频，视频
 * @author qingfeng
 * @date 2019年09月09日
 * */
var config = {
    image: "image/*",//上传图片格式
    video: "video/*",//视频格式
    audio: "audio/*"//音频格式
}
//http请求
var http = {
    uploadFile: function (url, fileid, type, success, error) {
        var fileObj = document.getElementById(fileid).files[0]; // js 获取文件对象
        var form = new FormData(); // FormData 对象
        form.append("file", fileObj); // 文件对象
        form.append("type", type);//文件类型
        var table = document.getElementById("table").value;
        if (table) {
            form.append("table", table);//关联索引-表
        }
        var xhr = new XMLHttpRequest();  // XMLHttpRequest 对象
        xhr.open("post", url, true); //post方式，url为服务器请求地址，true 该参数规定请求是否异步处理。
        xhr.onload = function (evt) {
            var data = JSON.parse(evt.target.responseText);
            success(data);
            setTimeout(function () {
                document.getElementById("panel").style.display = "none";
            }, 1000)
        }; //请求完成
        xhr.onerror = function (evt) {
            var data = JSON.parse(evt.target.responseText);
            error(data);
            setTimeout(function () {
                var panel = document.getElementById("panel");
                panel.style.display = "none"
            }, 1000)
        }; //请求失败

        xhr.upload.onprogress = progressFunction;//【上传进度调用方法实现】
        xhr.upload.onloadstart = function () {//上传开始执行方法
            ot = new Date().getTime();   //设置上传开始时间
            oloaded = 0;//设置上传开始时，以上传的文件大小为0
        };
        xhr.send(form); //开始上传，发送form数据
    },
    post: function (url, data, callback) {
        var httpRequest = new XMLHttpRequest();//第一步：建立所需的对象

        httpRequest.open('POST', url, true); //第二步：打开连接
        httpRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");//设置请求头 注：post方式必须设置请求头（在建立连接后设置请求头）
        httpRequest.send(data);//发送请求 将情头体写在send中
        /**
         * 获取数据后的处理程序
         */
        httpRequest.onreadystatechange = function () {
            if (httpRequest.readyState == 4 && httpRequest.status == 200) {
                var json = httpRequest.responseText;//获取到json字符串，还需解析
                callback(eval('(' + json + ')'));
            }
        };
    },
    get: function (url, data) {
        var httpRequest = new XMLHttpRequest();//第一步：建立所需的对象
        httpRequest.open('GET', url + "?" + data, true);//第二步：打开连接  将请求参数写在url中  ps:"./Ptest.php?name=test&nameone=testone"
        httpRequest.send();//第三步：发送请求  将请求参数写在URL中
        /**
         * 获取数据后的处理程序
         */
        httpRequest.onreadystatechange = function () {
            if (httpRequest.readyState == 4 && httpRequest.status == 200) {
                var json = httpRequest.responseText;//获取到json字符串，还需解析
                callback(eval('(' + json + ')'));
            }
        };
    }

}

//上传进度
function progressFunction(evt) {
    var panel = document.getElementById("panel");
    panel.style.display = "block";
    var progressBar = document.getElementById("progressBar");
    var percentageDiv = document.getElementById("percentage");
    // event.total是需要传输的总字节，event.loaded是已经传输的字节。如果event.lengthComputable不为真，则event.total等于0
    if (evt.lengthComputable) {//
        progressBar.max = evt.total;
        progressBar.value = evt.loaded;
        percentageDiv.innerHTML = Math.round(evt.loaded / evt.total * 100) + "%";
    }
    var time = document.getElementById("time");
    var nt = new Date().getTime();//获取当前时间
    var pertime = (nt - ot) / 1000; //计算出上次调用该方法时到现在的时间差，单位为s
    ot = new Date().getTime(); //重新赋值时间，用于下次计算
    var perload = evt.loaded - oloaded; //计算该分段上传的文件大小，单位b
    oloaded = evt.loaded;//重新赋值已上传文件大小，用以下次计算
    //上传速度计算
    var speed = perload / pertime;//单位b/s
    var bspeed = speed;
    var units = 'b/s';//单位名称
    if (speed / 1024 > 1) {
        speed = speed / 1024;
        units = 'k/s';
    }
    if (speed / 1024 > 1) {
        speed = speed / 1024;
        units = 'M/s';
    }
    speed = speed.toFixed(1);
    //剩余时间
    var resttime = ((evt.total - evt.loaded) / bspeed).toFixed(1);
    time.innerHTML = '，速度：' + speed + units + '，剩余时间：' + resttime + 's';
    if (bspeed == 0) time.innerHTML = '上传已取消';
}

var contextDiv = document.createElement("div");
var context = {
    init: function (tagid) {
        document.getElementById(tagid).style.border = "1px solid #e1e1e1";
        document.getElementById(tagid).innerHTML = "";
        var iconDiv = document.createElement("div");
        iconDiv.className = "icons";
        iconDiv.style = "width: 100%;height: 30px;";
        var icons = "<ul style='list-style:none;height:25px;padding-inline-start:0px;'>" +
            "<li style='float:left;margin-left:10px;width:auto;'><label><img  width='25px' height='25px' src='/static/public/editor/image/image.png' title='上传图片'/><input id='image' type='file' style='display:none;' accept='" + config.image + "'/></label></li>" +
            "<li style='float:left;margin-left:10px;width:auto;'><label><img width='25px' height='25px' src='/static/public/editor/image/music.png' title='上传音频'/><input id='music' type='file' style='display:none;' accept='" + config.audio + "'/></label></li>" +
            "<li style='float:left;margin-left:10px;width:auto;'><label><img width='25px' height='25px' src='/static/public/editor/image/video.png' title='上传视频'/><input id='video' type='file' style='display:none;' accept='" + config.video + "'/></label></li>" +
            "</ul>";
        iconDiv.innerHTML = icons;
        if (tagid == "context") {
            contextDiv.id = "content";
        } else {
            contextDiv.id = "context";
        }
        contextDiv.className = "context";
        contextDiv.contentEditable = true;
        contextDiv.style = "border: 1px solid #e1e1e1;min-height:300px;width:100%;background:#fff;";
        //图标
        document.getElementById(tagid).appendChild(iconDiv);
        //内容
        document.getElementById(tagid).appendChild(contextDiv);
        //背景（未使用）
        var bgk = document.createElement("div");
        bgk.style = "position:fixed;width:100%;height:100%;background:#e1e1e1;z-index:998;top:0;left:0;opacity:0.5;display:none;";
        //document.body.appendChild(bgk);
        //上传面板
        var panel = document.createElement("div");
        panel.id = "panel";
        panel.style = "position:fixed;width:300px;background:#fff;z-index:999;display:none;right:0;bottom:0;";
        panel.innerHTML = "上传进度:<progress id='progressBar' value='0' max='100' style='width: 300px;'></progress>" +
            "<span id='percentage'></span><span id='time'></span>";
        document.body.appendChild(panel);
        //图片上传
        document.getElementById("image").onchange = function () {
            http.uploadFile("/tool/upload", "image", "images", function (result) {
                if (result.state == 1) {
                    var image = "<img src='" + result.data + "' />";
                    console.log(image);
                    contextDiv.innerHTML = contextDiv.innerHTML + image;
                }
            }, function (error) {
                console.log(error);
            });
        }
        //音频上传
        document.getElementById("music").onchange = function () {
            http.uploadFile("/tool/upload", "music", "audios", function (result) {
                if (result.state == 1) {
                    var audio = "<audio  controls src='" + result.data + "'></audio><span> </span>";
                    contextDiv.innerHTML = contextDiv.innerHTML + audio;
                }
            }, function (error) {
                console.log(error);
            });
        }
        //视频上传
        document.getElementById("video").onchange = function () {
            http.uploadFile("/tool/upload", "video", "videos", function (result) {
                if (result.state == 1) {
                    var video = "<video controls  src='" + result.data + "'></video><span> </span>";
                    contextDiv.innerHTML = contextDiv.innerHTML + video;
                }
            }, function (error) {
                console.log(error);
            });
        }
    },
    set: function (content) {
        console.log();
        var id = contextDiv.getAttribute("id");
        var htmlContent = document.getElementById(id).innerHTML + content;
        document.getElementById(id).innerHTML = htmlContent;

    },
    get: function () {
        return contextDiv.innerHTML;
    }
}



