/**
 * 信令服务器 相关
 */
var SOCKET = {
    /**
     * websocket对象
     */
    ws: null,
    /**
     * websocket 连接地址
     */
    url: "" + window.location.protocol + "//" + window.location.host + "/sip/",
    /**
     * 开始建立连接
     * @returns {boolean}
     */
    start: () => {
        if (typeof (WebSocket) == "undefined") {
            layer.msg('您的浏览器不支持WebSocket', {icon: 5})
            return false;
        }
        var uId = $("#registerDialog input[name='userId']").val();
        if (uId === '' || uId == null) {
            layer.msg('请输入您的用户Id', {icon: 5, time: 1500})
            return false;
        }
        var rId = $("#registerDialog input[name='roomId']").val();
        if (rId === '' || rId == null) {
            layer.msg('请输入您的用户Id', {icon: 5, time: 1500})
            return false;
        }
        roomId = rId;
        userId = uId;
        SOCKET.url = SOCKET.url.replace("https", "wss").replace("http", "ws");
        //实现化WebSocket对象，指定要连接的服务器地址与端口  建立连接
        if (SOCKET.ws != null) {
            SOCKET.ws.close();
            SOCKET.ws = null;
        }
        SOCKET.ws = new WebSocket(SOCKET.url + roomId + '/' + userId + '/1');
        //打开事件
        SOCKET.ws.onopen = SOCKET.onOpen;
        //关闭事件
        SOCKET.ws.onclose = SOCKET.onClose;
        //发生了错误事件
        SOCKET.ws.onerror = SOCKET.onError;
        // 接收消息
        SOCKET.ws.onmessage = SOCKET.onMessage;
    },
    /**
     * 成功建立
     */
    onOpen: () => {
        layer.closeAll();
        layer.msg('信令服务器连接成功', {icon: 1, time: 1500});
        // 开始建立房间
        RTC.initRoom();
    },
    /**
     * 接收服务端推送消息
     */
    onMessage: (msg) => {
        msg = JSON.parse(msg.data)
        // 呼叫信息
        if (msg.type === 'call') {
            RTC.onCall(msg);
        }
        // 信令
        if (msg.type === 'offer') {
            RTC.onRemoteOffer(msg.data.userId, msg.data.offer);
        }
        // 回应
        if (msg.type === 'answer') {
            RTC.onRemoteAnswer(msg.data.answer);
        }
        // 候选信息
        if (msg.type === 'candidate') {
            RTC.onCandiDate(msg.data.candidate)
        }
    },
    /**
     * 连接发生错误
     */
    onError: () => {
        layer.msg('信令服务器连接失败', {icon: 5, time: 1500})
    },
    /**
     * 连接关闭
     */
    onClose: () => {
        layer.msg('信令服务器已关闭', {icon: 5, time: 1500})
    },
    /**
     * 发送消息
     */
    sendMsg: (obj) => {
        if (SOCKET.ws && SOCKET.ws.readyState === 1) {
            SOCKET.ws.send(JSON.stringify(obj));
        } else {
            layer.msg('请检查连接是否正常？', {icon: 5, time: 1500});
        }
    },
    /**
     * 断开连接
     */
    disConnect: () => {
        if (SOCKET.ws) {
            SOCKET.ws.close();
        }
    }
}