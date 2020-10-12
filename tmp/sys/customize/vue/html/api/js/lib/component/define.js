/**
 * Created by guozhongqiang on 16/6/16.
 */

tools.define = function (id, ids, callBack) {
    'use strict';
    var self = this;
    if (self.isFunction(id)) {
        callBack = id;
        define(function (require, exports, module) {
            module.exports = callBack.apply(this);
        });
    } else {
        id = id.toString();
        if (self.isFunction(ids)) {
            callBack = ids;
            define(id.toString(), function (require, exports, module) {
                module.exports = callBack.apply(this);
            });
        } else {
            if (self.isFunction(callBack)) {
                define(id, ids, function (require, exports, module) {
                    var context = this,
                        args = [];
                    if (self.isString(ids)) {
                        args[0] = require(ids);
                    } else if (objectUtil.isArray(ids)) {
                        var id = null,
                            len = ids.length,
                            i = 0;
                        for (; i < len; i++) {
                            id = ids[i];
                            if (self.isString(id)) {
                                args.push(require(id));
                            }
                        }
                    }
                    module.exports = callBack.apply(context, args);
                });
            }
        }
    }
};
