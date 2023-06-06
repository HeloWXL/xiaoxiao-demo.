/**
 * Webrtc 交互 相关
 */
var RTC = {
    /**
     * 初始化房间
     */
    initRoom:async () => {
        const localStream = await RTC.getLocalUserMedia();
        RTC.setDomVideoStream("localVideo", localStream);
        const localUid = userId
        //找到当前房间的视频流发布者 即主播
        let publisher = roomList.filter(e => e.userId !== localUid && e.pub === '1').map((e) => {
            return e.userId
        })
        console.log('publisher:', publisher)
        if (publisher.length > 0) {
            publisher = publisher[0]
            this.publisher = publisher
        } else {
            return;
        }
        //和发布者建立RTC连接 不发送自己视频流
        let pcKey = localUid + '-' + publisher
        console.log("pcKey", pcKey);
        let pc = RtcPcMaps.get(pcKey)
        if (!pc) {
            pc = new PeerConnection(that.rtcPcParams)
            RtcPcMaps.set(pcKey, pc)
        }
        // sendrecv 既发送也接受对方媒体 sendonly 仅发送不接受 recvonly 仅接受 不发送 如何不设置 则不发送也不接受
        pc.addTransceiver("audio", {direction: "recvonly"});
        pc.addTransceiver("video", {direction: "recvonly"});
        that.onPcEvent(pc, localUid, publisher)
    },
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
        pc.ontrack = function (event) {
            RTC.setRemoteDomVideoStream("remoteVideo", event.track)
        };
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
}
