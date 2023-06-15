import Vue from 'vue'
import LogMonitor from '@/views/LogMonitor'
import HelloWorld from '@/components/HelloWorld'
import VueRouter from "vue-router";

Vue.use(VueRouter);

const routes = [
    {
        path: '/',
        name: 'LogMonitor',
        component: LogMonitor
    },
    {
        path: '/hello',
        name: 'HelloWorld',
        component: HelloWorld
    }
];

const router = new VueRouter({
    routes
});

export default router

