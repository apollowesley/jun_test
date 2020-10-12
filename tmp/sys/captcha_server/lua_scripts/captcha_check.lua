-- Check Captcha
-- Written by Kevin.XU
-- 2016/8/20

function query_redis(skey)
    local resty_redis = require("resty.redis")
    local max_idle_timeout = 300*1000
    local pool_size = 20
    local timeout = 1000
    local redis_ip = "127.0.0.1"
    local redis_port = 6379
    local red = resty_redis:new()
    red:set_timeout(timeout)
    --connect
    local ok, err = red:connect(redis_ip, redis_port)
    if not ok then
        ngx.log(ngx.ERR, "connect failed")
        return nil
    end
    --get
    local res, err2 = red:get(skey)
    --delete key
    red:del(skey)
    --keep connection alive
    red:set_keepalive(max_idle_timeout, pool_size)
    if res == ngx.null then
        return nil
    else
        return res
    end
end

ngx.req.read_body()

--GET params
local args = ngx.req.get_uri_args()
local picgid = args["picgid"]
local picstr = args["picstr"]

if picgid == nil then
    ngx.log(ngx.ERR, "no param 'picgid'")
    return ngx.exit(ngx.HTTP_INTERNAL_SERVER_ERROR)
end
if picstr == nil then
    ngx.log(ngx.ERR, "no param 'picstr'")
    return ngx.exit(ngx.HTTP_INTERNAL_SERVER_ERROR)
end

picstr = string.lower(picstr)
ngx.log(ngx.INFO, "picstr="..picstr)

--Query string of pic
local saved_str = query_redis(picgid)
if saved_str == nil then
    return ngx.say("false")
end
saved_str = string.lower(saved_str)
ngx.log(ngx.INFO, "saved_str="..saved_str)

if saved_str == picstr then
    ngx.say("true")
else
    ngx.say("false")
end
