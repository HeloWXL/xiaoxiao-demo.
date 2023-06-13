import VueRouter from 'vue-router'
import VideoCallUi from '@/views/Call/Call.vue'

import Meeting from '@/views/Meeting/Meeting.vue'

const router = [
    {
        path: "/",
        name: 'VideoCallUi',
        component: VideoCallUi
    },
    {
        path: "/Meeting",
        name: 'Meeting',
        component: Meeting
    }
]

const routes = new VueRouter({
    routes: router
})

export default routes



