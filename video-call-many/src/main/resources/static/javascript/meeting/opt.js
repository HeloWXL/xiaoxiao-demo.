/**
 * 操作相关
 */
var OPT = {
    /**
     * 注册
     */
    register: () => {
        layer.open({
            id: 'registerLayer',
            type: 1,
            title: ['注册'],
            skin: 'layui-layer-molv',
            area: '400px',
            offset: 'auto',
            content: $('#registerDialog'),
            btn: ['提交', '取消'],
            // 弹窗加载完成后回调
            success: (layero) => {
                layero.find('.layui-layer-btn').css('text-align', 'center');
            },
            yes: (layero, index) => {
                SOCKET.start();
            },
            cancel: (index, layero) => {
                layer.close(index)
            }
        })
    },
    /**
     * 摄像头
     */
    videoSwitch: () => {
        let others = this.roomList.filter(e => e.userId !== userId).map((e) => {
            return e.userId;
        })
        let flag = OPT.getVideoStatus('localVideo')
        others.forEach(async (uid) => {
            let pcKey = userId + '-' + uid
            let pc = RtcPcMaps.get(pcKey);
            const senders = pc.getSenders();
            const send = senders.find((s) => s.track.kind === 'video');
            if (flag) {
                send.track.enabled = false;
            } else {
                send.track.enabled = true;
            }
        })
        if (flag) {
            layer.msg('您已关闭视频！', {icon: 1, time: 1500});
        } else {
            layer.msg('您已打开视频！', {icon: 1, time: 1500});
        }
    },
    /**
     * 麦克风
     */
    audioSwitch: () => {
        let others = this.roomList.filter(e => e.userId !== userId).map((e) => {
            return e.userId;
        })
        let flag = OPT.getAudioStatus('localVideo')
        others.forEach(async (uid) => {
            let pcKey = userId + '-' + uid
            let pc = RtcPcMaps.get(pcKey);
            const senders = pc.getSenders();
            const send = senders.find((s) => s.track.kind === 'audio');
            if (flag) {
                send.track.enabled = false;
            } else {
                send.track.enabled = true;
            }
        })
        if (flag) {
            layer.msg('您已关闭麦克风！', {icon: 1, time: 1500});
        } else {
            layer.msg('您已打开麦克风！', {icon: 1, time: 1500});
        }
    },
    /**
     * 获取音频状态
     * @param domId
     * @returns {boolean}
     */
    getAudioStatus(domId) {
        let video = document.getElementById(domId)
        let stream = video.srcObject
        return stream.getAudioTracks()[0].enabled
    },
    /**
     * 获取视频状态
     * @param domId
     * @returns {boolean}
     */
    getVideoStatus(domId) {
        let video = document.getElementById(domId)
        let stream = video.srcObject
        return stream.getVideoTracks()[0].enabled
    },
    /**
     * 屏幕分享
     */
    shareScreen: () => {
        const displayMediaStreamConstraints = {
            video: {
                cursor: "always"
            },
            audio: true
        };
        //获取分享窗口流
        if (navigator.mediaDevices.getDisplayMedia) {
            navigator.mediaDevices.getDisplayMedia(displayMediaStreamConstraints).then(mediaStream => {
                localStream = mediaStream;
                let others = this.roomList.filter(e => e.userId !== userId).map((e) => {
                    return e.userId;
                })
                others.forEach(async (uid) => {
                    let pcKey = userId + '-' + uid
                    let pc = RtcPcMaps.get(pcKey);
                    const senders = pc.getSenders();
                    const send = senders.find((s) => s.track.kind === 'video')
                    send.replaceTrack(mediaStream.getVideoTracks()[0])
                })

                //监听手动点击“停止分享”
                mediaStream.getVideoTracks()[0].onended = () => {
                    layer.msg('屏幕共享已关闭,正在切换为摄像头数据,请稍后...！', {icon: 1, time: 1500});
                    OPT.switchVideo(this.roomList)
                }
            }).catch(error => {
                console.log('获取屏幕分享流异常')
                layer.msg('您已取消屏幕分享', {icon: 2, time: 1500});
            });
        } else {
            console.log("navigator.mediaDevices.getDisplayMedia false");
            layer.msg('浏览器不不支持', {icon: 2, time: 1500});
        }
    },
    /**
     * 切换为摄像头数据
     */
    async switchVideo(roomList) {
        let others = roomList.filter(e => e.userId !== userId).map((e) => {
            return e.userId;
        })
        localStream = await RTC.getLocalUserMedia();
        others.forEach(async (uid) => {
            let pcKey = userId + '-' + uid
            let pc = RtcPcMaps.get(pcKey);
            const senders = pc.getSenders();
            const send = senders.find((s) => s.track.kind === 'video')
            send.replaceTrack(localStream.getVideoTracks()[0])
        })
    }
}