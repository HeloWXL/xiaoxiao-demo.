<template>
  <div class="meeting-container">
    <el-row :gutter="20">
      <el-col span="24">
        <!--标题-->
        <el-row>
          <el-col :span="24">
            <div class="title">
              <h2>基于"WebRtc"视频会议</h2>
            </div>
          </el-col>
        </el-row>
        <div class="lm-container">
          <el-container>
            <!--远程视频存放-->
            <el-main>
              <el-row :gutter="20">
                <el-col :span="16">
                  <el-row :gutter="20" class="other-video-container">
                    <!--本人参会视频画面-->
                    <el-col :span="8">
                      <el-badge value="我" class="item" type="primary">
                        <video autoplay id="localVideo" class="other-video"
                               :poster="require('@/../../../video-chat-room-ui/src/assets/img/b.webp')"></video>
                      </el-badge>
                    </el-col>
                    <!--参会人员参会视频画面-->
                    <el-col :span="8" v-if="item.userId !== userInfo.userId" v-for="(item) in roomList"
                            :key="item.userId" :ref="item.userId">
                      <el-badge :value="item.userId" class="item" type="primary" :id="item.userId">
                      </el-badge>
                    </el-col>
                  </el-row>
                </el-col>
                <!--房间人员列表-->
                <el-col :span="8">
                  <div class="title">
                    <h2>房间人员列表</h2>
                  </div>
                  <el-table
                      :data="roomList"
                      stripe
                      border
                      style="width: 100%;text-align: center">
                    <el-table-column
                        prop="roomId"
                        label="房间号"
                        align="center"
                    >
                    </el-table-column>
                    <el-table-column
                        prop="userId"
                        label="用户ID"
                        align="center"
                    >
                    </el-table-column>
                    <el-table-column
                        prop="pub"
                        align="center"
                        label="角色">
                      <template slot-scope="scope">
                        <div v-if="scope.row.pub === '1'" slot="reference" class="name-wrapper">
                          <el-tag size="medium">主持人</el-tag>
                        </div>
                        <div v-else slot="reference" class="name-wrapper">
                          <el-tag size="medium">参会人员</el-tag>
                        </div>
                      </template>
                    </el-table-column>
                  </el-table>
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
                  <el-tooltip effect="dark" content="开始会议" placement="top">
                       <el-button icon="el-icon-phone" circle class="shardBtn"
                                  @click="dialogVisible = true"></el-button>
                  </el-tooltip>
                </span>
              </div>
            </el-footer>
          </el-container>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {socket} from "@/util/socket";

var PeerConnection = window.RTCPeerConnection ||
    window.mozRTCPeerConnection ||
    window.webkitRTCPeerConnection;

// 存放其他用户的PeerConnection
var RtcPcMaps = new Map();

export default {
  name: "WebrtcMeeting",
  data() {
    return {
      content: md,
      loading: false,
      // 房间人数 列表
      roomList: [],
      // websocket对象
      ws: null,
      // 主持人
      publisher: null,
      rtcPcParams: {
        iceServers: [
          {urls: 'stun:stun.l.google.com:19302'}
        ]
      },
      // 本地媒体流
      localStream: null
    };
  },
  /**
   * 方法集合
   */
  methods: {
    /**
     * 连接信令服务器
     */
    init(userId, roomId, pub) {
      let that = this;
      socket.ws_url = that.$sipServerRoomUrl + '/' + roomId + '/' + userId + '/' + pub
      /**
       * 重新定义信令连接成功
       */
      socket.successCallBack = ()=>{
        that.$notify({
          title: '提示',
          message: '信令服务器连接成功',
          type: 'success'
        });
        //视频会议初始化
        setTimeout(async () => {
          if (that.roomList.length) {
            await that.initMeetingRoomPc()
          }
        }, 2000)
      }
      /**
       * 重新定义消息接收
       * @param msg
       */
      socket.receive = (msg)=>{
        msg = JSON.parse(msg.data);
        // 加入和离开房间
        if (msg.type === 'join' || msg.type === 'leave') {
          that.$notify({
            title: '提示',
            message: msg.msg,
            type: 'success'
          });
          // 获取房间列表 用于更新
          setTimeout(() => {
            let params = {"type": "roomUserList", "roomId": that.userInfo.roomId}
            that.ws.send(JSON.stringify(params));
          }, 1000)
        }
        // 房间信息
        if (msg.type === 'roomUserList') {
          that.roomList = msg.data
        }
        // 呼叫信息
        if (msg.type === 'call') {
          that.onCall(msg);
        }
        // 信令
        if (msg.type === 'offer') {
          let data = JSON.parse(msg.data)
          that.onRemoteOffer(data.userId, data.offer);
        }
        // 回应
        if (msg.type === 'answer') {
          let data = JSON.parse(msg.data)
          that.onRemoteAnswer(data.userId, data.answer);
        }
        // 候选信息
        if (msg.type === 'candidate') {
          let data = JSON.parse(msg.data)
          that.onCandiDate(data.userId, data.candidate)
        }
      }
      socket.init();
    },
    /**
     * 初始化房间信息
     */
    async initMeetingRoomPc() {
      const that = this
      // 获取当前的媒体流信息
      that.localStream = await this.getLocalUserMedia();
      that.setDomVideoStream("localVideo", that.localStream);
      // 获取当前用户的ID
      const localUid = this.userInfo.userId
      // 获取房间内除了当前用户的所有用户
      let others = this.roomList.filter(e => e.userId !== localUid).map((e) => {
        return e.userId
      })
      // 设置绑定关系 与房间内的所有人绑定
      others.forEach(async (uid) => {
        let pcKey = localUid + '-' + uid
        // 从本地通过key 获取 peerconnection
        let pc = RtcPcMaps.get(pcKey)
        // 判断是否存在 不存在则创建
        if (!pc) {
          pc = new PeerConnection(that.rtcPcParams)
          RtcPcMaps.set(pcKey, pc)
        }
        for (const track of that.localStream.getTracks()) {
          pc.addTrack(track);
        }
        //创建offer
        let offer = await pc.createOffer({iceRestart: true});
        //设置offer未本地描述
        await pc.setLocalDescription(offer)
        //发送offer给被呼叫端
        let params = {'type': 'offer', "targetUid": uid, "userId": localUid, "offer": offer}
        that.ws.send(JSON.stringify(params))
        // 开始监听peercoonnetion事件
        that.onPcEvent(pc, localUid, uid)
      })
    },
    /**
     * 监听peercoonnetion事件
     * @param pc
     * @param localUid
     * @param remoteUid
     */
    onPcEvent(pc, localUid, remoteUid) {
      const that = this
      pc.ontrack = function (event) {
        that.createRemoteDomVideoStream(remoteUid, event.track)
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
     * 处理远端SDP
     * @param fromUid
     * @param offer
     */
    async onRemoteOffer(fromUid, offer) {
      let that = this
      const localUid = that.userInfo.userId
      let pcKey = localUid + '-' + fromUid
      let pc = RtcPcMaps.get(pcKey)
      if (!pc) {
        pc = new PeerConnection(that.rtcPcParams)
        RtcPcMaps.set(pcKey, pc)
      }
      that.onPcEvent(pc, localUid, fromUid)
      for (const track of this.localStream.getTracks()) {
        pc.addTrack(track);
      }
      pc.setRemoteDescription(offer)
      let answer = await pc.createAnswer();
      await pc.setLocalDescription(answer);
      // 响应
      let params = {"targetUid": fromUid, "userId": localUid, "answer": answer, 'type': 'answer'}
      that.ws.send(JSON.stringify(params))
    },
    /**
     * 应答远端SDP
     * @param fromUid
     * @param answer
     * @returns {Promise<void>}
     */
    async onRemoteAnswer(fromUid, answer) {
      const localUid = this.userInfo.userId
      let pcKey = localUid + '-' + fromUid
      let pc = RtcPcMaps.get(pcKey)
      await pc.setRemoteDescription(answer);
    },
    /**
     * 网络信息协商
     * @param fromUid
     * @param candidate
     */
    onCandiDate(fromUid, candidate) {
      const localUid = this.userInfo.userId
      let pcKey = localUid + '-' + fromUid
      let pc = RtcPcMaps.get(pcKey)
      pc.addIceCandidate(candidate)
    },
    /**
     * 获取设备 stream
     * @returns {Promise<MediaStream>}
     */
    async getLocalUserMedia() {
      let constraints = {
        audio: true,
        video: true
      };
      if (window.stream) {
        window.stream.getTracks().forEach(track => {
          track.stop();
        });
      }
      return await navigator.mediaDevices.getUserMedia(constraints).catch(this.handleError)
    },
    /**
     * 动态创建 Video
     * @param domId
     * @param trick
     */
    createRemoteDomVideoStream(domId, trick) {
      // 创建video以用户名作为ID的dom
      let parentDom = document.getElementById(domId)
      let id = domId + '-media'
      let video = document.getElementById(id)
      if (!video) {
        video = document.createElement('video')
        video.id = id
        video.controls = false;
        video.autoplay = true;
        video.muted = false
        video.style.width = '100%'
        video.style.height = '100%'
        video.class = 'other-video'
      }
      let stream = video.srcObject
      if (stream) {
        stream.addTrack(trick)
      } else {
        let newStream = new MediaStream()
        newStream.addTrack(trick)
        video.srcObject = newStream
        video.muted = false
        parentDom.appendChild(video)
      }
    },
    /**
     * 将流显示在页面上
     * @param domId
     * @param newStream
     */
    setDomVideoStream(domId, newStream) {
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
     * 摄像头控制
     */
    videoSet() {

    },
    /**
     * 麦克风控制
     */
    audioSet() {

    },
    /**
     * 屏幕共享
     */
    shardScreenSet() {

    },
    endChat() {

    }
  }
}
</script>

<style scoped>
.el-header {
  padding: 0;
}

.el-footer {
  padding: 0;
}

.meeting-container {
  background-color: #ffffff;
}

.other-video-container {
  height: 500px;
}

.other-video {
  width: 100%;
  height: 200px;
  object-fit: fill;
  border-radius: 5px;
  background-color: #999999;
}

.local-video-container {
  position: absolute;
  bottom: -55px;
  left: 0;
  background-color: #999999;
}

.local-video {
  width: 200px;
  height: 200px;
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
