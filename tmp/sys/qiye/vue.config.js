const path = require('path');
const CompressionWebpackPlugin = require('compression-webpack-plugin');
const productionGzipExtensions = ['js', 'css'];
const isProduction = process.env.NODE_ENV === 'production';//是否开发环境

function resolve(dir) {
    return path.join(__dirname, dir);
}
// // 预发布环境
// const isLocalBuild = process.env.IS_LOCAL_BUILD === 'isLocalBuild';
// console.log('前端文件预发布打包- isLocalBuild:', isLocalBuild);
// 非externals CND前缀设置
// const CDN_URL = isLocalBuild ? '/' : '//s.zypj.nasetech.com/';

// 区分生产环境打包和预发布打包，使用不同的CDN
const JS_CDN = isProduction ? [
    //发布环境
        "https://unpkg.com/vue@2.6.10/dist/vue.js",
        "https://unpkg.com/vue-router@3.0.3/dist/vue-router.js",
        "https://unpkg.com/vuex@3.1.0/dist/vuex.js",
        "https://echarts.baidu.com/dist/echarts.simple.min.js",
        "https://unpkg.com/element-ui/lib/index.js",
        "https://unpkg.com/axios/dist/axios.min.js"
] : [
    //开发环境
];
const CSS_CDN=isProduction?[
        //发布环境
        'https://unpkg.com/element-ui/lib/theme-chalk/index.css',
    ]:[
        //开发环境
        'https://unpkg.com/element-ui/lib/theme-chalk/index.css',
        ];
const cdn = {
    css: CSS_CDN,
    js: JS_CDN
}

var runBuildPath = '/qiye'; //生产环境path地址
var runServePath = "/"; //本地环境path地址
module.exports = {
    lintOnSave: undefined,
    // 输出文件存放目录
    outputDir: isProduction?'dist/'+runServePath:'dist/'+runBuildPath,

    publicPath: isProduction?runBuildPath:runServePath,

    configureWebpack: config => {
        if (isProduction) {
            // 为生产环境修改配置... 不打包的组件 采用CDN，需要手动引入到public index.html
            config.externals = {
                'vue': 'Vue',
                'vue-router': 'VueRouter',
                'vuex': 'Vuex',
                "axios": "axios",
                'element-ui': 'ELEMENT',
                'echarts':'echarts',
            }
            // 打包生产.gz 压缩包
            config.plugins.push(new CompressionWebpackPlugin({
                algorithm: 'gzip',
                test: new RegExp('\\.(' + productionGzipExtensions.join('|') + ')$'),
                threshold: 1024, //超过多大字节开始压缩
                minRatio: 0.8
            }))

        } else {
            // 为开发环境修改配置...
        }
    },

    chainWebpack: config => {
        // if (isProduction) {
            config.plugin('html').tap(args => {
                args[0].cdn = cdn;
                return args;
            })
        // }
    },

    // 生产环境是否生成 sourceMap 文件
  productionSourceMap:false,
 // vue-loader 配置项
 // https://vue-loader.vuejs.org/en/options.html
 // vueLoader: {},
 // css相关配置
 css: {
  // 是否使用css分离插件 ExtractTextPlugin
  extract: true,
  // 开启 CSS source maps?
  sourceMap: false,
  // css预设器配置项
  // loaderOptions: {},
  // 启用 CSS modules for all css / pre-processor files.
  modules: false
 },
 parallel: require('os').cpus().length > 1,
 // 是否启用dll
 // See https://github.com/vuejs/vue-cli/blob/dev/docs/cli-service.md#dll-mode
 // dll: false,
 // PWA 插件相关配置
 // see https://github.com/vuejs/vue-cli/tree/dev/packages/%40vue/cli-plugin-pwa
 pwa: {},
 // webpack-dev-server 相关配置
 devServer: {
  open: process.platform === 'darwin',
  host: '0.0.0.0',
  port: 8080,
  https: false,
  hotOnly: false,
  proxy: null, // 设置代理
  before: app => {}
 },
 // 第三方插件配置
 pluginOptions: {
  // ...
 }
}
// module.exports = config