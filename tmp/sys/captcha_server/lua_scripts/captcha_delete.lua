-- Delete Captcha
-- Written by Kevin.XU
-- 2016/8/20

function delete_redis(skey)
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
        return
    end
    --delete key
    red:del(skey)
    --keep connection alive
    red:set_keepalive(max_idle_timeout, pool_size)
end

ngx.req.read_body()

--GET params
local args = ngx.req.get_uri_args()
local picgid = args["picgid"]

if picgid == nil then
    ngx.log(ngx.ERR, "no param 'picgid'")
    return ngx.exit(ngx.HTTP_INTERNAL_SERVER_ERROR)
end

--Delete string of pic
delete_redis(picgid)
