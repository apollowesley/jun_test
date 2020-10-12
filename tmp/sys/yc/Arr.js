// console.log(urlEncode(arr||obj).slice(1))//调用
var Arr = {
	// 获取元素或对象在数组中的索引位置
	getIndex(arr, obj) {
    let index = null;
    let key = Object.keys(obj)[0];
    arr.every(function(value, i) {
        if (value[key] === obj[key]) {
            index = i;
            return false;
        }
        return true;
    });
    return index;
	},
	// 从数组中删除元素或对象
	del(oldArr,obj){
		Array.prototype.baoremove = function(dx) { 
			  if(isNaN(dx)||dx>this.length){return false;} 
			  this.splice(dx,1); 
		}
		var idx =Arr.getIndex(oldArr,obj);
		if(idx>=0){
			oldArr.baoremove(idx,1);	
		}else{
			
		}
		return oldArr;
	}
}
var Obj={
	update(oldobj,newobj){
		for(var o in oldobj){
			if(newobj[o]){
				oldobj[o]= newobj[o];
			}
		}
		return oldobj;
	}
}
function isArrayFn(value){
    if (typeof Array.isArray === "function") {
        return Array.isArray(value);
    }else{
        return Object.prototype.toString.call(value) === "[object Array]";
    }
}
	/**
	 * 将数据格式化成树形结构返回数组 注意id===数组索引
	 * 要点 如果存在父pid 就把自己id作为父组的主键
	 * @author yichen
	 * @param object 
	 * @return Arr
	 */
	function getTree(Objdata) {
       var obj= Objdata || {};
       var data=[];
        for(let i in obj){
            if(obj[i].pid){
                obj[obj[i].pid]=obj[obj[i].pid]|| {};
                obj[obj[i].pid]['children']=obj[obj[i].pid]['children']||[];
                obj[obj[i].pid]['children'].push(obj[i]);
            }else{
                data.push(obj[i]);
            }
        }
        return data;
	}

  var objUpdate=Obj.update;
  var arrDel=Arr.del;
  var arrGetIndex=Arr.getIndex;
//   var isArrayFn=isArrayFn;
//   var getTree=getTree;
  export {
      objUpdate,
      arrDel,
      arrGetIndex,
      isArrayFn,
      getTree,
  }