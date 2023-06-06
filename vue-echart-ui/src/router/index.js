import Vue from 'vue'
import Echart from '@/views/Echart/Echart'

import VueRouter from "vue-router";

Vue.use(VueRouter);

const routes = [
    {
        path: '/',
        name: 'Echart',
        component: Echart
    }
];

const router = new VueRouter({
    routes
});

export default router

