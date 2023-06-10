import Vue from 'vue'
import Chat from '@/views/chat/index'
import Emoji from '@/views/chat/TestEmoji'
import VueRouter from "vue-router";

Vue.use(VueRouter);

const routes = [
    {
        path: '/',
        name: 'Chat',
        component: Chat
    },
    {
        path: '/emoji',
        name: 'Emoji',
        component: Emoji
    }
];

const router = new VueRouter({
    routes
});

export default router

