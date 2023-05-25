const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  // 关闭 Eslint
  lintOnSave: false,
  proxyTable: { // 配置跨域
    '/': {
      target: `http://localhost:8082`, //请求后台接口
      changeOrigin: true, // 允许跨域
      pathRewrite: {
        '^/': '' // 重写请求
      }
    }
  },
  devServer: {
    host: "0.0.0.0",
    port: 9001
  }
})
