export default function (context) {
    console.log("这是一个中间件");
    context.userAgent = process.server ? context.req.headers['user-agent'] : navigator.userAgent
    console.log("userAgent", context.userAgent)
}