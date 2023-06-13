import Vue from 'vue'

import QuillEditor from '@/views/QuillEditor/QuillEditor'

import WangEditor from '@/views/WangEditor/WangEditor'

import VueRouter from "vue-router";

Vue.use(VueRouter);

const routes = [
    {
        path: '/',
        name: 'QuillEditor',
        component: QuillEditor
    },
    {
        path: '/wangEditor',
        name: 'WangEditor',
        component: WangEditor
    }
];

const router = new VueRouter({
    routes
});

export default router

