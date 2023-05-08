import Vue from 'vue'
import File from '@/views/File/File'

import VueRouter from "vue-router";

Vue.use(VueRouter);

const routes = [
    {
        path: '/',
        name: 'File',
        component: File
    }
];

const router = new VueRouter({
    routes
});

export default router

