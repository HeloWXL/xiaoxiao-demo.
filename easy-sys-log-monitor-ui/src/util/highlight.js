// highlight.js  代码高亮指令
import Vue from 'vue';
import Hljs from 'highlight.js';
import 'highlight.js/styles/atom-one-dark.css';

let Highlight = {};

Highlight.install = function () {
    // 先有数据再绑定，调用highlightA
    Vue.directive('highlightStatic', {
        inserted: function(el) {
            let blocks = el.querySelectorAll('pre code');
            for (let i = 0; i < blocks.length; i++) {
                const item = blocks[i];
                Hljs.highlightBlock(item);
            }
        }
    });

    // 先绑定，后面会有数据更新，调用highlightB
    Vue.directive('highlightDynamic', {
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
