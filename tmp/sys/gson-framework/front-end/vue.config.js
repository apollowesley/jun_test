const path = require('path')
const webpack = require('webpack')

module.exports = {
  devServer: {
    port: 9000,
    proxy: {
      '^/api': {
        target: 'http://localhost:8080',
        pathRewrite: {'^/api': ''}
      }
    }
  },
  configureWebpack: {
    plugins: [
      new webpack.ProvidePlugin({
        'Model': 'js-model',
        'HeyUI': 'heyui',
        'G': 'hey-global'
      })
    ]
  },
  chainWebpack: config => {
    const types = ['vue-modules', 'vue', 'normal-modules', 'normal']
    types.forEach(type => addStyleResource(config.module.rule('less').oneOf(type)))
  },
}

function addStyleResource(rule) {
  rule.use('style-resource')
    .loader('style-resources-loader')
    .options({
      patterns: path.resolve(__dirname, './src/style/variables/*.less'),
      injector: 'append'
    })
}
