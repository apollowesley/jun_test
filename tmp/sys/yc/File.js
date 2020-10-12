	// blob图片转base64 
	/*
	* url='blob:http://localhost/fd.jpg'
	* callback=function 回调函数
	* outputFormat='image/png'
	*/
	function blobUrlToBase64(url, callback, outputFormat){
	  var canvas = document.createElement('CANVAS'),
		ctx = canvas.getContext('2d'),
		img = new Image();
	  img.crossOrigin = 'Anonymous';
	  img.onload = function(){
		 canvas.height = img.height;
		 canvas.width = img.width;
		 ctx.drawImage(img,0,0);
		 var dataURL = canvas.toDataURL(outputFormat || 'image/jpeg');
		 callback(dataURL,this);
		 canvas = null; 
	  };
	  img.src = url;
	}

export{
	blobUrlToBase64
}