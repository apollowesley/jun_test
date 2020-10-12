-- Require Captcha
-- Written by Kevin.XU
-- 2016/8/20

function store_redis(skey, sval, validity)
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
        return ngx.exit(ngx.HTTP_INTERNAL_SERVER_ERROR)
    end
    --save
    ok, err = red:setex(skey, validity, sval)
    if not ok then
        red:set_keepalive(max_idle_timeout, pool_size)
        ngx.log(ngx.ERR, "setex failed")
        return ngx.exit(ngx.HTTP_INTERNAL_SERVER_ERROR)
    end
    --keep connection alive
    red:set_keepalive(max_idle_timeout, pool_size)
end

--Read body
ngx.req.read_body()

local resty_string = require("resty.string")
local resty_uuid = require("resty.uuid")

-- Set random seed
local now = os.time()
ngx.log(ngx.INFO, "now="..now)
math.randomseed(tonumber(now))

--GET params
local mode = "1"
local args = ngx.req.get_uri_args()
local picgid = args["picgid"]
local picwidth = args["picwidth"]
local picheight = args["picheight"]
local validity = 60 * 5

if picgid == nil then
    picgid = mode..resty_uuid.gen20()..".png"
end
if picwidth == nil then
    picwidth = 78
else
    picwidth = tonumber(picwidth)
end
if picheight == nil then
    picheight = 26
else
    picheight = tonumber(picheight)
end

--select captcha characters
local dict = {'A','B','C','D','E','F','G','H','J','K','L','M','N','P','Q','R','S','T','U','V','W','X','Y','Z','2','3','4','5','6','7','8','9'}
local dict_size = #dict
local stringmark = ""
for i=1,4 do
    local ind = math.random(1,dict_size)
    stringmark = stringmark..dict[ind]
end

--font size
local wsize = 17.5

--interfering line
local line = "yes"

local gd = require('gd')

--panal
local im = gd.createTrueColor(picwidth, picheight)
--color
local black = im:colorAllocate(0, 0, 0)
local grey = im:colorAllocate(202,202,202)
local color={}
for c=1,100 do
    color[c] = im:colorAllocate(math.random(100),math.random(100),math.random(100))
end

--background
x, y = im:sizeXY()
im:filledRectangle(0, 0, x, y, grey)

--draw characters
gd.useFontConfig(true)
for i=1,4 do
    k=(i-1)*16+3
    im:stringFT(color[math.random(100)],"Arial:bold",wsize,math.rad(math.random(-10,10)),k,22,string.sub(stringmark,i,i))
end
--draw interfering lines
if line=="yes" then
    for j=1,math.random(3) do
        im:line(math.random(picwidth),math.random(picheight),math.random(picwidth),math.random(picheight),color[math.random(100)])
    end
    for p=1,20 do
        im:setPixel(math.random(picwidth),math.random(picheight),color[math.random(100)])
    end
end
--require png str
local fp = im:pngStr(75)

--save mapping
store_redis(picgid, stringmark, validity)

--response header
ngx.header.content_type = "image/png"
ngx.header.picgid = picgid

--response 
ngx.say(fp)
