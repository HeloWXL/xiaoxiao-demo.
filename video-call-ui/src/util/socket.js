// 导出socket对象
export {
    socket
}
import {Message} from 'element-ui'

// socket主要对象
var socket = {
    /**
     * webSocket对象
     */
    websocket: null,
    /**
     * 这个是我们的ws的地址
     * */
    ws_url: "",
    /**
     * 初始化连接
     */
    init: () => {
        if (!("WebSocket" in window)) {
            Message({
                message: '当前浏览器与网站不兼容丫',
                type: 'error',
            });
            console.log('浏览器不支持WebSocket')
            return
        }
        // 已经创建过连接不再重复创建
        if (socket.websocket) {
            return socket.websocket
        }
        // 判断Websocket地址是否为空
        if (!socket.ws_url) {
            Message({
                message: '请查看控制台',
                type: 'error',
            });
            console.error('无Websocket 连接地址呢')
            return
        }
        // 创建Websocket 对象
        socket.websocket = new WebSocket(socket.ws_url)
        //  监听消息
        socket.websocket.onmessage = function (e) {
            // 消息方法处理
            socket.receive(e)
        }
        // 关闭连接
        socket.websocket.onclose = function (e) {
            console.log('连接已断开')
            console.log('connection closed (' + e.code + ')')
        }
        // 连接成功
        socket.websocket.onopen = function () {
            socket.successCallBack()
        }
        // 连接发生错误
        socket.websocket.onerror = function (err) {
            Message({
                message: '服务连接发送错误！',
                type: 'error',
            });
            console.log('WebSocket连接发生错误', err)
        }
    },
    /**
     * 获取websocket对象
     * @returns {null}
     */
    getSocket: () => {
        //创建了直接返回，反之重来
        if (socket.websocket) {
            return socket.websocket
        } else {
            socket.init();
        }
    },
    /**
     * 获取websocket 连接状态
     * @returns {string}
     */
    getStatus: () => {
        if (socket.websocket.readyState === 0) {
            return "未连接";
        } else if (socket.websocket.readyState === 1) {
            return "已连接";
        } else if (socket.websocket.readyState === 2) {
            return "连接正在关闭";
        } else if (socket.websocket.readyState === 3) {
            return "连接已关闭";
        }
    },
    /**
     * 发送消息
     * @param {*} data 发送数据
     * @param {*} succCallback 发送成功后的自定义回调函数
     * @param {*} errorCallBack 发送失败后的自定义回调函数
     */
    send: (data, succCallback = null, errorCallBack = null) => {
        if(!socket.websocket){
            Message({
                message: '请先建立连接哦',
                type: 'warning',
            });
            return false;
        }
        // 开启状态直接发送
        if (socket.websocket.readyState === socket.websocket.OPEN) {
            socket.websocket.send(JSON.stringify(data))
            if (succCallback) {
                succCallback()
            }
        } else {
            if (errorCallBack) {
                errorCallBack()
            }
        }
    },
    /**
     * 接收消息
     * @param {*} message 接收到的消息
     */
    receive: (message) => {
        var recData = JSON.parse(message.data)
        console.log(recData)
        /**
         *这部分是我们具体的对消息的处理
         * */
        // 自行扩展其他业务处理...
    },
    /**
     * 主动关闭连接
     */
    close: () => {
        console.log('主动断开连接')
        if(socket.websocket){
            socket.websocket.close()
        }
    },
    /**
     * 成功连接回调
     */
    successCallBack: () => {
        console.log('连接成功')
    }
}
