import Vue from 'vue'
import App from './App.vue'

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css'



// 第一步: 引入router 插件
import VueRouter from 'vue-router'
// 使用插件
Vue.use(VueRouter)
// 第二步: 创建router文件夹 引入实例
import router from './router'


Vue.use(ElementUI);

Vue.config.productionTip = false


// websocket 信令地址
Vue.prototype.$sipServerUrl =  process.env.NODE_ENV === 'development' ? 'ws://localhost:8086/sip' : 'wss://192.168.81.94:443/sip'

new Vue({
  render: h => h(App),
  router
}).$mount('#app')
