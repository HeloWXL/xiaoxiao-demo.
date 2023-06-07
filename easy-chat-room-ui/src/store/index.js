import Vue from 'vue';
import Vuex from 'vuex';


Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        // 用户ID
        userId: null,
        // 房间号
        roomId: null

    },
    // 异步方法
    mutations: {
        /**
         * 向vuex存入token信息
         */
        setUserId(state, data) {
            state.userId = data;
        },
        setRoomId(state, data) {
            state.roomId = data;
        }
    },
    // 同步方法
    actions: {},
    modules: {}
})
