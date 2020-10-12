//hello.js
function student() {
    var name;
    this.setName = function(thyName) {
        this.name = thyName;
    };
    this.getName = function() {
       return this.name;
    };
};
module.exports = student;
