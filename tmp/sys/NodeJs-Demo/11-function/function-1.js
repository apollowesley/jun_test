

/*使用函数作为参数*/

function say(word) {
    console.log(word);
}

function execute(someFunction, value) {
    someFunction(value);
}

execute(say, "Hello");