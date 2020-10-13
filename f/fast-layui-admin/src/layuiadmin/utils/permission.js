var permission = {
    getPermission: function (name) {
        var tree = JSON.parse($.session.get('navTree')); //由JSON字符串转换为JSON对象
        var find = getMyChildrenList(tree, name);
      //  console.info(find);
        if (find.permission == name) {
            return true;
        } else {
            return false;
        }
    },
};

var myPermission = {
    getPermission: function (name) {
        var tree = JSON.parse($.session.get('navTree')); //由JSON字符串转换为JSON对象
        var find = getMyChildrenList(tree, name);
        //console.info(find);
        if (find.permission == name) {
            return true;
        } else {
            return false;
        }
    },
};

function getMyChildrenList(tree, name) {
    return find(tree, name)
}

var returnedItem = {};

function find(arr, name) {
    arr.forEach((item) => {
        if (item.permission == name) {
            returnedItem = item;
            return item;
        } else if (item.children.length > 0) {
            find(item.children, name);
        }
    })
    return returnedItem;
}