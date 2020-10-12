/*方法模块*/

/*第一种写法*/
// exports.sayHello = function(name) {
//     console.log('Hello '+name);
// }


/*第二种写法*/
function sayHello(name) {
    console.log('Hello '+name);
}
exports.sayHello = sayHello


/*第三种写法 error*/
// function sayHello(name) {
//     console.log('Hello '+name);
// }
// module.exports = sayHello;