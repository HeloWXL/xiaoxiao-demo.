import Vue from 'vue'
import App from './App.vue'

Vue.config.productionTip = false

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

Vue.use(ElementUI);

import router from './router'

Vue.prototype.$wsServerUrl =  'ws://localhost:8076/chat/'

new Vue({
  render: h => h(App),
  router
}).$mount('#app')
