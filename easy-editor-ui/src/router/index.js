import Vue from 'vue'

import QuillEditor from '@/views/QuillEditor/QuillEditor'

import VueRouter from "vue-router";

Vue.use(VueRouter);

const routes = [
    {
        path: '/',
        name: 'QuillEditor',
        component: QuillEditor
    }
];

const router = new VueRouter({
    routes
});

export default router

