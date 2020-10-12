-- Require Captcha
-- Written by Kevin.XU
-- 2016/8/20

ngx.req.read_body()

local resty_string = require("resty.string")

-- Set random seed
local now = os.time()
math.randomseed(tonumber(now))

-- Select mode by random
local mode = math.random(1,ngx.var.modecount)

-- Invoke modeX.lua and generate captcha
local suffix = ngx.var.query_string or ""
local res = ngx.location.capture("/mode"..mode.."?"..suffix)
if res.status==200 then
    ngx.header.content_type = "image/png"
    ngx.header.picgid = res.header.picgid
    ngx.say(res.body)
    ngx.exit(200)
end
