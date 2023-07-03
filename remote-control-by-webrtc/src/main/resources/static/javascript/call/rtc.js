/**
 * Webrtc 交互 相关
 */
var RTC = {
    /**
     * 呼叫
     */
    call: () => {
        var tUserId = $("#callDialog input[name='targetUserId']").val();
        if (tUserId === '' || tUserId == null) {
            layer.msg('请输入对方的用户ID', {icon: 5, time: 1500})
            return false;
        }
        targetUserId = tUserId;
        RTC.initCallerInfo(userId, targetUserId)
        let params = {
            "type": "call", "userId": userId, "targetUid": targetUserId
        }
        SOCKET.sendMsg(params);
        // 关闭弹窗
        layer.closeAll();
    },
    /**
     * 初始化呼叫者信息
     */
    initCallerInfo: async (callerId, calleeId) => {
        //初始化pc
        localRtcPc = new PeerConnection();
        //获取本地媒体并添加到pc中
        let localStream = await RTC.getLocalUserMedia();
        // 获取流的轨道信息
        for (const track of localStream.getTracks()) {
            localRtcPc.addTrack(track);
        }
        // 本地dom渲染
        await RTC.setDomVideoStream("localVideo", localStream);
        //回调监听
        RTC.onPcEvent(localRtcPc, callerId, calleeId);
        //流程-> 呼叫方A创建offer 保存到本地，并通过信令服务器转发给B
        //创建offer
        let offer = await localRtcPc.createOffer({iceRestart: true});
        //设置offer本地描述
        await localRtcPc.setLocalDescription(offer)
        //发送offer给被呼叫端
        let params = {
            "type": "offer", "userId": callerId, "targetUid": calleeId, "offer": offer
        }
        SOCKET.sendMsg(params)
    },
    /**
     * 初始化被呼叫者信息
     */
    initCalleeInfo: async (localUid, fromUid) => {
        //初始化pc
        localRtcPc = new PeerConnection()
        //初始化本地媒体信息
        localStream = await RTC.getLocalUserMedia()
        // 将流的所有轨道信息添加到localRtcPc中去
        for (const track of localStream.getTracks()) {
            localRtcPc.addTrack(track);
        }
        // dom渲染
        await RTC.setDomVideoStream("localVideo", localStream)
        //监听
        RTC.onPcEvent(localRtcPc, localUid, fromUid)
    },
    /**
     * 接收到呼叫请求
     */
    onCall: async (e) => {
        await RTC.initCalleeInfo(e.data['targetUid'], e.data['userId'])
    },
    /**
     * 信息交换
     * @param fromUid
     * @param offer
     */
    onRemoteOffer: async (fromUid, offer) => {
        localRtcPc.setRemoteDescription(offer)
        let answer = await localRtcPc.createAnswer();
        await localRtcPc.setLocalDescription(answer);
        let params = {"type": "answer", "targetUid": fromUid, "userId": this.userId, "answer": answer}
        SOCKET.sendMsg(params)
    },
    /**
     * 信息交换
     * @param fromUid
     * @param answer
     */
    onRemoteAnswer: async (answer) => {
        await localRtcPc.setRemoteDescription(answer);
    },
    /**
     * 候选信息处理
     * @param candidate
     */
    onCandiDate: (candidate) => {
        localRtcPc.addIceCandidate(candidate)
    },
    /**
     * 事件监听
     * @param pc
     * @param localUid
     * @param remoteUid
     */
    onPcEvent: (pc, localUid, remoteUid) => {
        const that = this
        // 创建数据传输通道
        channel = pc.createDataChannel("remote-control");

        /**
         * 数据通道监听
         * @param ev
         */
        pc.ondatachannel = function (ev) {
            console.log('Data channel is created!');

            ev.channel.onopen = function () {
                console.log('Data channel ------------open----------------');
                RTC.monitor();
            };

            ev.channel.onmessage = function (data) {
                console.log('Data channel ------------msg----------------', data);
                // that.formInline.rtcmessageRes = data.data
            };

            ev.channel.onclose = function () {
                console.log('Data channel ------------close----------------');
            };
        };

        pc.ontrack = function (event) {
            RTC.setRemoteDomVideoStream("remoteVideo", event.track)
        };

        /**
         * 监听候选信息
         * @param event
         */
        pc.onicecandidate = (event) => {
            if (event.candidate) {
                let params = {
                    'type': 'candidate', 'targetUid': remoteUid, "userId": localUid, "candidate": event.candidate
                }
                SOCKET.sendMsg(params)
            } else {
                /* 在此次协商中，没有更多的候选了 */
                console.log("在此次协商中，没有更多的候选了")
            }
        }
    },
    /**
     * 获取设备 stream
     * @param constraints
     * @returns {Promise<MediaStream>}
     */
    async getLocalUserMedia() {
        let constraints = {video: true, audio: true}
        return await navigator.mediaDevices.getUserMedia(constraints)
    },
    /**
     * 显示本地视频流
     * @param domId
     * @param newStream
     * @returns {Promise<void>}
     */
    async setDomVideoStream(domId, newStream) {
        let video = document.getElementById(domId)
        let stream = video.srcObject
        if (stream) {
            stream.getAudioTracks().forEach(e => {
                stream.removeTrack(e)
            })
            stream.getVideoTracks().forEach(e => {
                stream.removeTrack(e)
            })
        }
        video.srcObject = newStream
        video.muted = true
    },
    /**
     * 显示远程视频流
     * @param domId
     * @param track
     */
    async setRemoteDomVideoStream(domId, track) {
        let video = document.getElementById(domId)
        let stream = video.srcObject
        if (stream) {
            stream.addTrack(track)
        } else {
            let newStream = new MediaStream()
            newStream.addTrack(track)
            video.srcObject = newStream
            video.muted = true
        }
    },

    /**
     * 远程监控操作
     */
    monitor(){
        const div = document.getElementById('remote-control')
        let totalW = div.clientWidth
        const totalH = div.clientHeight // 884
        div.addEventListener('canplay', function() {
            console.log('videoWidth', this.videoWidth)
            console.log('videoHeight', this.videoHeight)
            totalW = totalH * (this.videoWidth / this.videoHeight)
            console.log('totalW, totalH', totalW, totalH)
        })

        document.oncontextmenu = function(ev) {
            return false // 屏蔽右键菜单
        }

        // 键盘按下
        document.addEventListener('keydown', (e) => {
            const msg = {}
            msg.event = 0
            msg.type = 5
            msg.code = e.keyCode
            this.channelSend(msg)
        })

        // 键盘松开
        document.addEventListener('keyup', (e) => {
            const msg = {}
            msg.event = 1
            msg.type = 5
            msg.code = e.keyCode
            this.channelSend(msg)
        })

        // 滚轮
        document.addEventListener('mousewheel', (e) => {
            const msg = {}
            msg.type = 4
            if (e.wheelDelta < 0) {
                msg.event = 3
            } else {
                msg.event = 2
            }
            this.channelSend(msg)
        })

        // 鼠标按下
        div.onmousedown = (e) => {
            const msg = {}
            msg.event = 0
            if (Number(e.button) === 0) { // 左键
                msg.type = 2
            } else if (Number(e.button) === 1) { // 中键
                msg.type = 4
            } else if (Number(e.button) === 2) { // 右键
                msg.type = 3
            }
            msg.x = e.offsetX / totalW
            msg.y = e.offsetY / totalH
            this.channelSend(msg)
            // 鼠标左键按下时移动
            if (Number(e.button) === 0) {
                div.onmousemove = (e) => {
                    const msg = {}
                    msg.type = 1
                    msg.x = e.offsetX / totalW
                    msg.y = e.offsetY / totalH
                    this.channelSend(msg)
                }
            }
        }
        // 鼠标抬起
        div.onmouseup = (e) => {
            div.onmousemove = null // 清除移动事件
            const msg = {}
            msg.event = 1
            if (Number(e.button) === 0) { // 左键
                msg.type = 2
            } else if (Number(e.button) === 1) { // 中键
                msg.type = 4
            } else if (Number(e.button) === 2) { // 右键
                msg.type = 3
            }
            msg.x = e.offsetX / totalW
            msg.y = e.offsetY / totalH
            this.channelSend(msg)
        }
    },
    channelSend(msg) {
        if (!channel || channel.readyState !== 'open') return // 通道未建立
        channel.send(JSON.stringify(msg))
        console.log('send msg ===>', JSON.stringify(msg))
    }

}
