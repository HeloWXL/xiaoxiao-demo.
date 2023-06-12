/**
 * Webrtc 交互 相关
 */
var RTC = {
    /**
     * 初始化房间
     */
    initRoom: async () => {
        const localUid = userId
        //找到当前房间的视频流发布者 即主播
        let publisher = this.roomList.filter(e => e.userId !== localUid && e.pub === 1).map((e) => {
            return e.userId
        })
        if (publisher.length > 0) {
            publisher = publisher[0]
        } else {
            return;
        }
        //和发布者建立RTC连接 不发送自己视频流
        let pcKey = localUid + '-' + publisher
        let pc = RtcPcMaps.get(pcKey);
        if (!pc) {
            pc = new PeerConnection(rtcPcParams)
            RtcPcMaps.set(pcKey, pc)
        }
        // sendrecv 既发送也接受对方媒体 sendonly 仅发送不接受 recvonly 仅接受 不发送 如何不设置 则不发送也不接受
        pc.addTransceiver("audio", {direction: "recvonly"});
        pc.addTransceiver("video", {direction: "recvonly"});
        RTC.onPcEvent(pc, localUid, publisher)
        //创建offer
        let offer = await pc.createOffer();
        //设置offer未本地描述
        await pc.setLocalDescription(offer)
        //发送offer给被呼叫端
        let params = {'type': 'offer', "targetUid": publisher, "userId": localUid, "offer": offer}
        SOCKET.sendMsg(params)
    },
    /**
     * 信息交换
     * @param fromUid
     * @param offer
     */
    onRemoteOffer: async (fromUid, offer) => {
        let pcKey = userId + '-' + fromUid
        let pc = new PeerConnection(this.rtcPcParams)
        RtcPcMaps.set(pcKey, pc)
        RTC.onPcEvent(pc, userId, fromUid)
        for (const track of localStream.getTracks()) {
            pc.addTrack(track);
        }
        pc.setRemoteDescription(offer)
        let answer = await pc.createAnswer();
        await pc.setLocalDescription(answer);
        let params = {"type": "answer", "targetUid": fromUid, "userId": userId, "answer": answer}
        SOCKET.sendMsg(params)
    },
    /**
     * 信息交换
     * @param fromUid
     * @param answer
     */
    onRemoteAnswer: async (fromUid,answer) => {
        const localUid = userId
        let pcKey = localUid+'-'+fromUid
        let pc = RtcPcMaps.get(pcKey)
        await pc.setRemoteDescription(answer);
    },
    /**
     * 候选信息处理
     * @param candidate
     */
    onCandiDate: (fromUid,candidate) => {
        const localUid = userId
        let pcKey = localUid+'-'+fromUid
        let pc = RtcPcMaps.get(pcKey)
        pc.addIceCandidate(candidate)
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
