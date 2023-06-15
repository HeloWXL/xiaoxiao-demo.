
# Vue 代码高亮

> highlightjs

https://highlightjs.org/static/demo/

## 安装

```shell
 npm install highlight.js --save
```

## 自定义指令

新建util 文件夹，创建 highlight.js，将以下的代码复制粘贴进去即可。

```js
// highlight.js  代码高亮指令
import Vue from 'vue';
import Hljs from 'highlight.js';
import 'highlight.js/styles/gradient-light.css';

let Highlight = {};

Highlight.install = function () {
    // 先有数据再绑定，调用highlightA
    Vue.directive('highlightA', {
        inserted: function(el) {
            let blocks = el.querySelectorAll('pre code');
            for (let i = 0; i < blocks.length; i++) {
                const item = blocks[i];
                Hljs.highlightBlock(item);
            }
        }
    });

    // 先绑定，后面会有数据更新，调用highlightB
    Vue.directive('highlightB', {
        componentUpdated: function(el) {
            let blocks = el.querySelectorAll('pre code');
            for (let i = 0; i < blocks.length; i++) {
                const item = blocks[i];
                Hljs.highlightBlock(item);
            }
        }
    });

};

export default Highlight;

```

## 引入highlight.js

```js
// highlight.js代码高亮指令
import Highlight from '@/util/highlight';

Vue.use(Highlight)
```

## 使用

```vue
   <pre v-highlightA>
      <code class="json">{{ watch_data }}</code>
  </pre>
```

watch_data 为 待展示的数据;
注意：pre标签中的v-highlightA和v-highlightB根据需求自己选择，对于code标签中的class，定义成自己数据需要格式，我的需求是显示json格式，所以就有class=“json”，然后进行数据的绑定。