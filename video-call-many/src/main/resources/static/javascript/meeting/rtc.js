/**
 * Webrtc 交互 相关
 */
var RTC = {
    /**
     * 初始化房间
     */
    initRoom: async () => {
        if(!localStream){
            localStream = await RTC.getLocalUserMedia();
            RTC.setDomVideoStream("localVideo", localStream);
        }
        const localUid = userId
        //找到当前房间的视频流发布者 即主播
        let others = this.roomList.filter(e => e.userId !== localUid).map((e) => {
            return e.userId
        })
        others.forEach(async (uid) => {
            //和发布者建立RTC连接 不发送自己视频流
            let pcKey = localUid + '-' + uid
            let pc = RtcPcMaps.get(pcKey);
            if (!pc) {
                pc = new PeerConnection(rtcPcParams)
                RtcPcMaps.set(pcKey, pc)
            }
            for (const track of localStream.getTracks()) {
                pc.addTrack(track);
            }
            RTC.onPcEvent(pc, localUid, uid)
            //创建offer
            let offer = await pc.createOffer();
            //设置offer未本地描述
            await pc.setLocalDescription(offer)
            //发送offer给被呼叫端
            let params = {'type': 'offer', "targetUid": uid, "userId": localUid, "offer": offer}
            SOCKET.sendMsg(params)
        })
    },
    /**
     * 信息交换
     * @param fromUid
     * @param offer
     */
    onRemoteOffer: async (fromUid, offer) => {
        let pcKey = userId + '-' + fromUid
        let pc = new PeerConnection(rtcPcParams)
        RtcPcMaps.set(pcKey, pc);
        RTC.onPcEvent(pc, userId, fromUid);
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
            RTC.createOtherDom(remoteUid,event.track)
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
    createOtherDom(remoteUid,track){
        let id = remoteUid+'-video'
        let video = document.getElementById(id);
        if(!video){
            video = document.createElement('video')
            video.id = id
            video.controls = false;
            video.autoplay = true;
            video.muted = false
            video.className = 'remote-video'
        }

        let stream = video.srcObject
        if(stream){
            stream.addTrack(track)
        }else{
            let newStream = new MediaStream()
            newStream.addTrack(track)
            video.srcObject =newStream
            video.muted = false
        }
        let div = document.createElement('div');
        div.className = 'layui-col-xs3';
        div.appendChild(video);
        $('#other-container').append(div)
    }
}
