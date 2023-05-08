import Vue from 'vue'
import Chat from '@/views/chat/Chat'

import VueRouter from "vue-router";

Vue.use(VueRouter);

const routes = [
    {
        path: '/',
        name: 'Chat',
        component: Chat
    }
];

const router = new VueRouter({
    routes
});

export default router

