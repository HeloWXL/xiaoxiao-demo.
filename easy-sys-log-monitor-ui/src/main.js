import Vue from 'vue'
import App from './App.vue'

Vue.config.productionTip = false


// highlight.js代码高亮指令
import Highlight from '@/util/highlight';

Vue.use(Highlight)


import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

Vue.use(ElementUI);

import router from './router'

new Vue({
  render: h => h(App),
  router
}).$mount('#app')
