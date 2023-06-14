var socket = {
    /**
     * websocket连接地址
     */
    url: null,
    /**
     * websocket连接对象
     */
    ws: null,
    /**
     * 开始建立连接
     * @param params
     * @returns {boolean}
     */
    start: () => {
        if(socket.url === null){
            if(layer){
                layer.msg('websocket地址不能为空', {icon: 5, time: 1500})
            }else{
                console.warn('websocket地址不能为空');
            }
            return false;
        }
        if (typeof (WebSocket) == "undefined") {
            if (layer){
                layer.msg('您的浏览器不支持WebSocket', {icon: 5, time: 1500})
            }
            return false;
        }
        socket.url = socket.url.replace("https", "ws").replace("http", "ws");
        //实现化WebSocket对象，指定要连接的服务器地址与端口  建立连接
        if (socket.ws != null) {
            socket.ws.close();
            socket.ws = null;
        }
        socket.ws = new WebSocket(socket.url);
        //打开事件
        socket.ws.onopen = socket.onOpen;
        //关闭事件
        socket.ws.onclose = socket.onClose;
        //发生了错误事件
        socket.ws.onerror = socket.onError;
        // 接收消息
        socket.ws.onmessage = socket.onMessage;
    },

    /**
     * 成功建立
     */
    onOpen: () => {
        if (layer) {
            layer.msg('已建立WebSocket连接', {icon: 1, time: 1500})
        } else {
            console.log('已建立WebSocket连接')
        }
    },
    onMessage: (msg) => {
        console.log(msg)
    },
    /**
     * 连接发生错误
     */
    onError: () => {
        if (layer) {
            layer.msg('websocket发生了错误', {icon: 5, time: 1500})
        }else{
            console.error('websocket发生了错误')
        }
    },
    /**
     * 连接关闭
     */
    onClose: () => {
        if (layer) {
            layer.msg('websocket已关闭', {icon: 5, time: 1500})
        }else{
            console.warn('websocket已关闭')
        }
    },
    /**
     * 发送消息
     */
    sendMsg: () => {

    },
    /**
     * 断开连接
     */
    disConnect: () => {
        if (socket.ws) {
            socket.ws.close();
        }
    }
}
