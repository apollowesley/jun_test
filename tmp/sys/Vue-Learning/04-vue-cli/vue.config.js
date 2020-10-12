// vue.config.js
module.exports = {
  devServer: {
    port: 8083,
    proxy: {
      '/': {
        target: 'http://www.kiwipeach.cn/', // target host
        ws: true, // proxy websockets
        changeOrigin: true, // needed for virtual hosted sites
        pathRewrite: {
        }
      }
    }
  }
}
