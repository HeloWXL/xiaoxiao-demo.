import VueRouter from 'vue-router'
import VideoCallUi from '@/views/Chat.vue'

const router = {
    path: "/",
    name: 'VideoCallUi',
    component: VideoCallUi
}

const routes = new VueRouter({
    routes: [
        router
    ]
})

export default routes



