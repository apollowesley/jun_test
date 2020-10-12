var hello = require('./4.hello');
hello.world();

var Hello = require('./4.hello2'); 
hello = new Hello(); 
hello.setName('BYVoid'); 
hello.sayHello(); 