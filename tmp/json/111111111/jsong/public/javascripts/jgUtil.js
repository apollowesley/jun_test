//json转字符串
function obj2str(o){   
    var r = [];   
    if(typeof o =="string") return "\""+o.replace(/([\'\"\\])/g,"\\$1").replace(/(\n)/g,"\\n").replace(/(\r)/g,"\\r").replace(/(\t)/g,"\\t")+"\"";   
    if(typeof o =="undefined") return "";   
    if(typeof o == "object"){   
        if(o===null){
            return "null";
        }else if(!o.sort){   
            for(var i in o){
                r.push('"'+i+'":'+obj2str(o[i]));
            }   
            r="{"+r.join()+"}";  
        }else{   
            for(var i =0;i<o.length;i++){   
                r.push(obj2str(o[i]));
            }   
            r="["+r.join()+"]";  
        }   
        return r;   
    }   
    return o.toString();   
}
//生成uuid
function uuid(len, radix) {
    var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
    var uuid = [], i;
    radix = radix || chars.length;
    if (len) {
      for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random()*radix];
    } else {
      var r;
      uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
      uuid[14] = '4';
      for (i = 0; i < 36; i++) {
        if (!uuid[i]) {
          r = 0 | Math.random()*16;
          uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
        }
      }
    }
    return uuid.join('');
}
//指定范围生成随机数
function rd(n,m){
    var c = m-n+1;  
    return Math.floor(Math.random() * c + n);
}
//随机日期
function randomDate(){
    var current = new Date();
    var year = rd(1900,current.getFullYear());
    var month = rd(0,11);
    var day = rd(1,28);
    switch(month){
        case 1:case 3:case 5:case 7: case 8: case 10: case 12:
            day = rd(1,31);
        break;
        case 4:case 6: case 9: case 11:
            day = rd(1,30);
        break;
        case 2:
            if (((year % 4)===0) && ((year % 100)!==0) || ((year % 400)===0)) {
                day = rd(1,29);
            } else { 
                day = rd(1,28); 
            }
        break;
    }
    return year+'-'+(month+1)+'-'+day;
}
//计数器
var counter = (function(){
    var count = 0;
    return function(){
        return ++count;
    }
})();
//格式化json
var formatJson = function(json, options) {
    var reg = null,
        formatted = '',
        pad = 0,
        PADDING = '    '; 
    options = options || {};
    options.newlineAfterColonIfBeforeBraceOrBracket = (options.newlineAfterColonIfBeforeBraceOrBracket === true) ? true : false;
    options.spaceAfterColon = (options.spaceAfterColon === false) ? false : true;

    if (typeof json !== 'string') {
        json = JSON.stringify(json);
    } else {
        json = JSON.parse(json);
        json = JSON.stringify(json);
    }

    reg = /([\{\}])/g;
    json = json.replace(reg, '\r\n$1\r\n');

    reg = /([\[\]])/g;
    json = json.replace(reg, '\r\n$1\r\n');

    reg = /(\,)/g;
    json = json.replace(reg, '$1\r\n');

    reg = /(\r\n\r\n)/g;
    json = json.replace(reg, '\r\n');

    reg = /\r\n\,/g;
    json = json.replace(reg, ',');

    if (!options.newlineAfterColonIfBeforeBraceOrBracket) {         
        reg = /\:\r\n\{/g;
        json = json.replace(reg, ':{');
        reg = /\:\r\n\[/g;
        json = json.replace(reg, ':[');
    }
    if (options.spaceAfterColon) {          
        reg = /\:/g;
        json = json.replace(reg, ': ');
    }
    var jsonArray = json.split('\r\n');
    for(var j=0,len=jsonArray.length;j<len;j++){
        var i = 0,
            indent = 0,
            padding = '';
        var node = jsonArray[j];

        if (node.match(/\{$/) || node.match(/\[$/)) {
            indent = 1;
        } else if (node.match(/\}/) || node.match(/\]/)) {
            if (pad !== 0) {
                pad -= 1;
            }
        } else {
            indent = 0;
        }

        for (i = 0; i < pad; i++) {
            padding += PADDING;
        }

        formatted += padding + node + '\r\n';
        pad += indent;
    }
    return formatted;
};   
//随机数据
function randomData(dataType,dataSize){
  var data ;
  switch(dataType){
    case 'String':
      var dataSize = dataSize||20;
      data = uuid(dataSize);
      break;
    case 'Integer':
      var tmp = 1;
      var dataSize = dataSize||2;
      for(var i=0;i<dataSize;i++){
        tmp = tmp * 10 ;
      }
      data = Math.ceil(Math.random()*tmp);
      break;
    case 'Double':
      var tmp = 1;
      var dataSize = dataSize||2;
      for(var i=0;i<dataSize;i++){
        tmp = tmp * 10 ;
      }
      data = Math.ceil((Math.random()*tmp)*tmp)/tmp;
      break;
    case 'Date':
      data = randomDate();
      break;
    case 'Boolean':
      data = Math.ceil(Math.random()*10)%2;
      break;            
    default:
      data = 'unknow dataType';
      break;
  }
  return data ;
}