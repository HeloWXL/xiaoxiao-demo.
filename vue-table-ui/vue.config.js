const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  // 关闭 Eslint
  lintOnSave: false,
  devServer: {
    host: "0.0.0.0",
    port: 9000
  }
})
