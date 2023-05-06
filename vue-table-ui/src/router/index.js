import Vue from 'vue'
import User from '@/views/User/User'

import VueRouter from "vue-router";

Vue.use(VueRouter);

const routes = [
    {
        path: '/',
        name: 'User',
        component: User
    }
];

const router = new VueRouter({
    routes
});

export default router

