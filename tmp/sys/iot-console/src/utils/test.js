export function checkMobile(str) {
  var reg = /^1\d{10}$/
      var promise1
      return  promise1 = new Promise(function(resolve,reject){
          var reg = /^1\d{10}$/
          if (reg.test(str)) {
              resolve("正确");
          } else {
              reject("您的手机输入错误");
          }
          })
  }
  