import Vue from 'vue';
import axios from 'axios';
import {Message} from 'element-ui'

/**
 * 创建ajax实例
 * 并设置超时时间
 */
const ajax = axios.create({
    timeout: 50000,
    baseURL:"/api"
})


ajax.interceptors.request.use((config) => {
    return config
}, error => {
    console.error('请求异常', error)
})

/**
 * 响应拦截器
 * 后端响应的信息
 */
ajax.interceptors.response.use((res) => {
    if( res.data instanceof Blob){
        return res.data;
    }
    if (res.data.code !== 0) {
        Message.error(res.data.msg);
        return false;
    }
    return res.data;

}, error => {
    if (error === 'Error: Network Error') {
        Message.error("Network Error");
    } else if (error.response.status === 400) {
        Message.error(error.response.data.msg);
    }  else if (error.response.status === 404) {
        Message.error("后端接口未找到");
    } else if (error.response.status === 500) {
        Message.error("后端接口异常");
    } else {
        Message.error("未知错误");
    }
})

export function download(url, params = {}) {
    return new Promise((resolve, reject) => {
        ajax.get(url, {params: params,responseType: "blob"},).then(response => {
            resolve(response)
        }).catch(err => {
            reject(err)
        })
    })
}

export function get(url, params = {}) {
    return new Promise((resolve, reject) => {
        ajax.get(url, {params: params}).then(response => {
            resolve(response)
        }).catch(err => {
            reject(err)
        })
    })
}

export function post(url, data = {}) {
    data = JSON.stringify(data)
    let config = {
        headers: {'Content-Type': 'application/json'}
    }
    return new Promise((resolve, reject) => {
        ajax.post(url, data, config).then(response => {
            resolve(response)
        }).catch(err => {
            reject(err)
        })
    })
}

// 挂载
Vue.prototype.$ajax = ajax
export default ajax
