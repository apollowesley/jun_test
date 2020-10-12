module.exports = {
  root: true,
  env: {
    node: true,
  },	    // 此项是用来提供插件的，插件名称省略了eslint-plugin-，下面这个配置是用来规范html的
  extends: [
    'plugin:vue/essential',
    // '@vue/airbnb',
  ],
  rules: {
		"indent": ["off", 2],
        'no-console': process.env.NODE_ENV === 'production' ? 'error' : 'off',
        'no-debugger': process.env.NODE_ENV === 'production' ? 'error' : 'off',
        'linebreak-style': [0, 'error', 'windows'],
        "no-tabs":"off",
        "max-len" : ["error", {code : 300}],
        'vue/no-parsing-error': [2, { "x-invalid-end-tag": false }],
  },
  parserOptions: {
    parser: 'babel-eslint',
  },
};
