let util = function () {
    /** 检查是否为null */
    let isNull = function(str) {
        if('' == str || null == str || undefined === str) {
            return false;
        }
        return true;
    }

    return {
        isNull
	}
}()

export default {
    util
}