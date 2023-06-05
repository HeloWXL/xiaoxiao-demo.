import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)
export default new Vuex.Store({
    // 设置全局访问的state对象
    state: {
        userId: null,
        targetId: null
    },
    mutations: {
        setUserId: (state, data) => {
            state.userId = data
        },
        setTargetId: (state, data) => {
            state.targetId = data
        }
    }
})
