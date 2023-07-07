<template>
  <div class="meeting-container">
    <el-row>
      <el-col :span="24">
        <!--标题-->
        <el-row>
          <el-col :span="24">
            <div class="title">
              <h2>基于"WebRtc"一对一视频通话</h2>
            </div>
          </el-col>
        </el-row>
        <div class="lm-container">
          <el-container>
            <!--远程视频存放-->
            <el-main>
              <el-row :gutter="20">
                <el-col :span="8" style="text-align: center;">
                  <el-card class="box-card">
                    <div slot="header" class="clearfix">
                      <span>我</span><span v-if="userId!=null">{{userId}}</span>
                    </div>
                    <video autoplay id="localVideo" class="local-video"
                           :poster="require('@/assets/img/a.jpeg')"></video>
                  </el-card>
                </el-col>
                <el-col :span="8">
                  <el-card class="box-card">
                    <div slot="header" class="clearfix">
                      <span>对方</span>
                    </div>
                    <video autoplay id="remoteVideo" class="remote-video"
                           :poster="require('@/assets/img/b.webp')"></video>
                  </el-card>
                </el-col>
              </el-row>
            </el-main>
            <!--用户操作-->
            <el-footer>
              <div class="student-opt-container">
                <span>
                   <el-tooltip effect="dark" content="摄像头" placement="top">
                    <el-button icon="el-icon-video-camera" circle class="videoBtn" @click="videoSet"></el-button>
                  </el-tooltip>
                </span>
                <span>
                   <el-tooltip effect="dark" content="音频" placement="top">
                      <el-button icon="el-icon-microphone" circle class="audioBtn" @click="audioSet"></el-button>
                  </el-tooltip>
                </span>
                <span>
                  <el-tooltip effect="dark" content="屏幕共享" placement="top">
                       <el-button icon="el-icon-data-analysis" circle class="shardBtn"
                                  @click="shardScreenSet"></el-button>
                  </el-tooltip>
                </span>
                <span>
                  <el-tooltip effect="dark" content="关闭通话" placement="top">
                       <el-button icon="el-icon-switch-button" circle class="endBtn" @click="endChat"></el-button>
                  </el-tooltip>
                </span>
                <span>
                  <el-tooltip effect="dark" content="呼叫" placement="top">
                       <el-button icon="el-icon-phone" circle class="shardBtn"
                                  @click="callDialogVisible = true"></el-button>
                  </el-tooltip>
                </span>
              </div>
            </el-footer>
          </el-container>
        </div>
      </el-col>
    </el-row>
    <!--用户注册-->
    <el-dialog
        title="注册"
        :visible.sync="dialogVisible"
        width="30%"
        center>
      <el-form ref="form" label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="userId"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="initUserInfo">确 定</el-button>
   </span>
    </el-dialog>
    <!--呼叫用户-->
    <el-dialog
        title="呼叫"
        :visible.sync="callDialogVisible"
        width="30%"
        center>
      <el-form ref="form" label-width="100px">
        <el-form-item label="被呼叫者ID">
          <el-input v-model="targetUserId"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
      <el-button @click="callDialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="call">确 定</el-button>
   </span>
    </el-dialog>
  </div>
</template>

<script>

var PeerConnection = window.RTCPeerConnection ||
    window.mozRTCPeerConnection ||
    window.webkitRTCPeerConnection;

export default {
  name: "VideoCallUi",
  data() {
    return {
      // 控制弹窗显示
      dialogVisible: false,
      // 本地peer
      localRtcPc: null,
      // 本地视频流
      localStream: null,
      //用户信息
      userId: null,
      // websocket对象
      ws: null,
      // 被呼叫着ID
      targetUserId: null,
      // 呼叫弹窗控制
      callDialogVisible: false
    };
  },
  /**
   * 生命周期 - 创建完成（可以访问当前this实例）
   */
  created() {

  },
  /**
   * 生命周期 - 挂载完成（可以访问DOM元素）
   */
  mounted() {
    if (!this.userId) {
      this.dialogVisible = true;
    }
  },
  /**
   * 方法集合
   */
  methods: {
    /**
     * 初始化用户信息
     */
    initUserInfo() {
      if (this.userId) {
        this.dialogVisible = false
        this.init()
      }
    },
    /**
     * 连接信令服务器
     */
    init() {
      const that = this
      if (that.ws != null) {
        that.ws.close();
        that.ws = null;
      }
      that.ws = new WebSocket(that.$sipServerUrl + '/' + that.userId);
      //打开事件
      that.ws.onopen = function () {
        that.$notify({
          title: '提示',
          message: '信令服务器连接成功',
          type: 'success'
        });
      };
      //关闭事件
      that.ws.onclose = function () {
        console.log("websocket已关闭");
      };
      //发生了错误事件
      that.ws.onerror = function () {
        that.$notify({
          title: '提示',
          message: '信令服务器连接失败',
          type: 'error'
        });
        console.log("websocket发生了错误");
      };
      /**
       * 接收消息
       * @param msg
       */
      that.ws.onmessage = function (msg) {
        msg = JSON.parse(msg.data);
        // 呼叫信息
        if (msg.type === 'call') {
          that.onCall(msg);
        }
        // 信令
        if (msg.type === 'offer') {
          that.onRemoteOffer(msg.data.userId, msg.data.offer);
        }
        // 回应
        if (msg.type === 'answer') {
          that.onRemoteAnswer(msg.data.userId, msg.data.answer);
        }
        // 候选信息
        if (msg.type === 'candidate') {
          that.onCandiDate(msg.data.candidate)
        }
      };
    },
    /**
     * 获取设备 stream
     * @param constraints
     * @returns {Promise<MediaStream>}
     */
    async getLocalUserMedia() {
      let constraints = {video: true, audio: true}
      return await navigator.mediaDevices.getUserMedia(constraints).catch(this.handleError)
    },
    /**
     * 异常处理
     * @param error
     */
    handleError(error) {
      alert("摄像头无法正常使用，请检查是否占用或缺失")
      console.error('navigator.MediaDevices.getUserMedia error: ', error.message, error.name);
    },
    /**
     * 发送呼叫请求
     */
    call() {
      if (!this.targetUserId) {
        this.$message.warning('请输入被呼叫着ID');
        return false;
      }
      this.initCallerInfo(this.userId, this.targetUserId)
      let params = {
        "type": "call", "userId": this.userId, "targetUid": this.targetUserId
      }
      this.ws.send(JSON.stringify(params))
    },
    /**
     * 接收到呼叫请求
     */
    async onCall(e) {
      console.log("接收到远程呼叫：", e)
      await this.initCalleeInfo(e.data['targetUid'], e.data['userId'])
    },
    /**
     * 初始化呼叫者信息
     */
    async initCallerInfo(callerId, calleeId) {
      //初始化pc
      this.localRtcPc = new PeerConnection()
      //获取本地媒体并添加到pc中
      let localStream = await this.getLocalUserMedia({audio: true, video: true})
      // 获取流的轨道信息
      for (const track of localStream.getTracks()) {
        this.localRtcPc.addTrack(track)
      }
      // 本地dom渲染
      await this.setDomVideoStream("localVideo", localStream)
      //回调监听
      this.onPcEvent(this.localRtcPc, callerId, calleeId)
      //流程-> 呼叫方A创建offer 保存到本地，并通过信令服务器转发给B
      //创建offer
      let offer = await this.localRtcPc.createOffer({iceRestart: true});
      //设置offer本地描述
      await this.localRtcPc.setLocalDescription(offer)
      //发送offer给被呼叫端
      let params = {
        "type": "offer", "userId": callerId, "targetUid": calleeId, "offer": offer
      }
      this.ws.send(JSON.stringify(params))
    },
    /**
     * 初始化被叫者信息
     */
    async initCalleeInfo(localUid, fromUid) {
      //初始化pc
      this.localRtcPc = new PeerConnection()
      //初始化本地媒体信息
      let localStream = await this.getLocalUserMedia({audio: true, video: true})
      // 将流的所有轨道信息添加到localRtcPc中去
      for (const track of localStream.getTracks()) {
        this.localRtcPc.addTrack(track);
      }
      // dom渲染
      await this.setDomVideoStream("localVideo", localStream)
      //监听
      this.onPcEvent(this.localRtcPc, localUid, fromUid)
    },
    /**
     * 响应远程SDP
     * @param fromUid
     * @param offer
     * @returns {Promise<void>}
     */
    async onRemoteOffer(fromUid, offer) {
      this.localRtcPc.setRemoteDescription(offer)
      let answer = await this.localRtcPc.createAnswer();
      await this.localRtcPc.setLocalDescription(answer);
      let params = {"type": "answer", "targetUid": fromUid, "userId": this.userId, "answer": answer}
      this.ws.send(JSON.stringify(params))
    },
    /**
     * 设置本地描述
     * @param fromUid
     * @param answer
     * @returns {Promise<void>}
     */
    async onRemoteAnswer(fromUid, answer) {
      await this.localRtcPc.setRemoteDescription(answer);
    },
    /**
     * 候选信息处理
     * @param userId
     * @param candidate
     */
    onCandiDate(candidate) {
      this.localRtcPc.addIceCandidate(candidate)
    },
    /**
     * 事件监听
     * @param pc
     * @param localUid
     * @param remoteUid
     */
    onPcEvent(pc, localUid, remoteUid) {
      const that = this
      // 创建数据传输通道
      this.channel = pc.createDataChannel("chat");
      pc.ontrack = function (event) {
        that.setRemoteDomVideoStream("remoteVideo", event.track)
      };
      pc.onnegotiationneeded = function (e) {
        console.log("重新协商", e)
      }
      pc.ondatachannel = function (ev) {
        console.log('Data channel is created!');
        ev.channel.onopen = function () {
          console.log('Data channel ------------open----------------');
        };
        ev.channel.onmessage = function (data) {
          console.log('Data channel ------------msg----------------', data);
          // that.formInline.rtcmessageRes = data.data
        };
        ev.channel.onclose = function () {
          console.log('Data channel ------------close----------------');
        };
      };
      pc.onicecandidate = (event) => {
        if (event.candidate) {
          let params = {
            'type': 'candidate', 'targetUid': remoteUid, "userId": localUid, "candidate": event.candidate
          }
          that.ws.send(JSON.stringify(params))
        } else {
          /* 在此次协商中，没有更多的候选了 */
          console.log("在此次协商中，没有更多的候选了")
        }
      }
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
    setRemoteDomVideoStream(domId, track) {
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
     * 摄像头开启或关闭
     */
    videoSet() {
      this.$notify({
        title: '成功',
        message: '摄像头',
        type: 'success'
      });
    },
    /**
     * 语音开启、关闭
     */
    audioSet() {
      this.$notify({
        title: '成功',
        message: '音频',
        type: 'success'
      });
    },
    /**
     * 屏幕共享
     */
    shardScreenSet() {
      let that = this;
      if (!that.localRtcPc) {
        that.$notify.warning("请先进行通话")
        return false
      }

      const displayMediaStreamConstraints = {
        video: {
          cursor: "always"
        },
        audio: true
      };

      //获取分享窗口流
      if (navigator.mediaDevices.getDisplayMedia) {
        navigator.mediaDevices.getDisplayMedia(displayMediaStreamConstraints).then(mediaStream => {
          that.localStream = mediaStream;
          const senders = that.localRtcPc.getSenders();
          const send = senders.find((s) => s.track.kind === 'video')
          send.replaceTrack(mediaStream.getVideoTracks()[0])
          //监听手动点击“停止分享”
          mediaStream.getVideoTracks()[0].onended = () => {
            that.$notify({
              title: '成功',
              message: '屏幕共享已关闭,正在切换为摄像头数据,请稍后...',
              type: 'success'
            });
            that.switchCamera()
          }
        }).catch(error => {
          console.log("error", error)
          that.$message.error('您已取消屏幕分享');
        });
      } else {
        console.log("navigator.mediaDevices.getDisplayMedia false");
        that.$message.error('浏览器不不支持');
      }
    },
    /**
     * 切换为摄像头数据
     */
    async switchCamera() {
      let that = this;
      that.localStream = await this.getLocalUserMedia();
      const senders = that.pushPc.getSenders();
      const send = senders.find((s) => s.track.kind === 'video')
      send.replaceTrack(that.localStream.getVideoTracks()[0])
    },
    /**
     * 结束通话
     */
    endChat() {
      let that = this;
      if (!that.localRtcPc) {
        that.$notify.warning("您还没进行通话呢,请先进行通话呢~")
        return false
      }
      this.$notify({
        title: '成功',
        message: '关闭通话',
        type: 'success'
      });
    },
    /**
     * 关闭流数据
     */
    closeStream() {
      let videoEle = document.getElementById('localVideo');
      let stream = videoEle.srcObject;
      if (stream) {
        stream.getTracks().forEach(trick => {
          trick.stop()
        })
      }
    }
  }
}
</script>

<style scoped>
.title {
  text-align: center;
}

.el-header {
  padding: 0;
}

.el-footer {
  padding: 0;
}

.meeting-container {
  background-color: #ffffff;
}


.local-video, .remote-video {
  width: 100%;
  height: 500px;
  object-fit: fill;
  border-radius: 5px;
  background-color: #999999;
}


/*用户操作*/
.student-opt-container {
  height: 60px;
  text-align: center;
}

.student-opt-container span {
  margin-left: 10px;
  text-align: center;
  line-height: 60px;
}

.endBtn {
  background-color: red;
  border: 1px solid red;
  color: white
}

.endBtn:focus, .endBtn:hover {
  background-color: white;
  border: 1px solid white;
  color: red
}

.shardBtn, .audioBtn, .videoBtn {
  background-color: grey;
  border: 1px solid grey;
  color: white
}

.el-button:focus, .el-button:hover {
  color: #409EFF;
}

</style>
