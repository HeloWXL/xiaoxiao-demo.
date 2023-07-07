<template>
  <div class="meeting-container">
    <el-row :gutter="20">
      <el-col :span="expanState?0:18">
        <!--标题-->
        <el-row>
          <el-col :span="24">
            <div class="title">
              <h2>基于"WebRtc"直播</h2>
            </div>
          </el-col>
        </el-row>
        <!--用户基本信息-->
        <el-row>
          <el-col :span="24">
            <span>昵称：{{ userInfo.userId ? userInfo.userId : '用户未注册' }}</span>
          </el-col>
        </el-row>
        <div class="lm-container">
          <el-container>
            <!--远程视频存放-->
            <el-main>
              <el-row :gutter="20">
                <el-col :span="12">
                  <el-badge :value="userInfo.pub === '1'?'我':'主播'" class="item" type="primary">
                    <video autoplay id="localVideo" class="local-video"
                           :poster="require('@/assets/img/b.webp')"></video>
                  </el-badge>
                </el-col>
                <!--房间人员列表-->
                <el-col :span="12">
                  <div class="title">
                    <h2>房间人员列表</h2>
                  </div>
                  <el-table
                      :data="roomList"
                      stripe
                      border
                      :row-style="rowStyle"
                      >
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
                            <el-tag size="medium">主播</el-tag>
                          </div>
                          <div v-else slot="reference" class="name-wrapper">
                            <el-tag size="medium">观众</el-tag>
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
                <span v-show="userInfo.pub === '1'">
                   <el-tooltip effect="dark" content="摄像头" placement="top">
                    <el-button icon="el-icon-video-camera" circle class="videoBtn" @click="videoSet"></el-button>
                  </el-tooltip>
                </span>
                <span v-show="userInfo.pub === '1'">
                   <el-tooltip effect="dark" content="音频" placement="top">
                      <el-button icon="el-icon-microphone" circle class="audioBtn" @click="audioSet"></el-button>
                  </el-tooltip>
                </span>
                <span v-show="userInfo.pub === '1'">
                  <el-tooltip effect="dark" content="屏幕共享" placement="top">
                       <el-button icon="el-icon-data-analysis" circle class="shardBtn"
                                  @click="shardScreenSet"></el-button>
                  </el-tooltip>
                </span>
                <span>
                  <el-tooltip effect="dark" content="退出房间" placement="top">
                       <el-button icon="el-icon-switch-button" circle class="endBtn" @click="endChat"></el-button>
                  </el-tooltip>
                </span>
                <span>
                  <el-tooltip effect="dark" content="加入房间" placement="top">
                       <el-button icon="el-icon-phone" circle class="shardBtn"
                                  @click="dialogVisible = true"></el-button>
                  </el-tooltip>
                </span>
              </div>
            </el-footer>
          </el-container>
        </div>
      </el-col>
      <el-col :span="expanState?24:6">
        <h1>使用手册及文档
          <span>
               <el-tooltip v-if="expanState === true" content="还原" placement="top">
                   <i class="el-icon-d-arrow-right" @click="expanState = !expanState"></i>
               </el-tooltip>
               <el-tooltip v-if="expanState === false" content="全屏" placement="top">
                   <i class="el-icon-full-screen" @click="expanState = !expanState"></i>
               </el-tooltip>
            </span>
        </h1>
        <div class="document">
          <mavon-editor
              v-model="content"
              :ishljs="true"
              default-open="preview"
              :editable="false"
              :subfield="false"
              :toolbarsFlag="false"
          />
        </div>
      </el-col>
    </el-row>
    <!--进入直播间-->
    <el-dialog
        title="进入直播间"
        :visible.sync="dialogVisible"
        width="30%"
        center>
      <el-form ref="userInfoForm" label-width="80px" :model="userInfo" :rules="userInfoRules" >
        <el-form-item label="用户名：" prop="userId">
          <el-input v-model="userInfo.userId"></el-input>
        </el-form-item>
        <el-form-item label="房间号：" prop="roomId">
          <el-input v-model="userInfo.roomId"></el-input>
        </el-form-item>
        <el-form-item label="角色：" prop="pub">
          <el-radio v-model="userInfo.pub" label="1">主播</el-radio>
          <el-radio v-model="userInfo.pub" label="0">观众</el-radio>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="gotoLiveRoom('userInfoForm')">确 定</el-button>
   </span>
    </el-dialog>
  </div>
</template>

<script>

var PeerConnection = window.RTCPeerConnection ||
    window.mozRTCPeerConnection ||
    window.webkitRTCPeerConnection;

// 存放其他用户的PeerConnection
var RtcPcMaps = new Map();
// 数据通道map
var dataChannelMap = new Map();

const md = require('@/md/Live.md')

export default {
  name: "WebrtcLive",
  data() {
    return {
      content: md,
      loading: false,
      expanState: false,
      // 用户信息
      userInfo: {
        // 用户ID
        userId: '',
        // 房间ID
        roomId: '',
        // 1:主播 0:观众
        pub: ''
      },
      // 控制用户注册信息弹窗
      dialogVisible: false,
      // 房间人数 列表
      roomList: [],
      // websocket对象
      ws: null,
      // 主播
      publisher: null,
      rtcPcParams:{
        iceServers: [
          {urls: 'stun:stun.l.google.com:19302'}
        ]
      },
      // 规则校验
      userInfoRules:{
        userId: [
          { required: true, message: '请填写用户名', trigger: 'blur' }
        ],
        roomId: [
          { required: true, message: '请填写房间号', trigger: 'blur' }
        ],
        pub: [
          { required: true, message: '请选择角色', trigger: 'blur' }
        ]
      }
    };
  },
  /**
   * 生命周期 - 创建完成（可以访问当前this实例）
   */
  created() {
    console.log(PeerConnection)
  },
  /**
   * 生命周期 - 挂载完成（可以访问DOM元素）
   */
  mounted() {

  },
  /**
   * 方法集合
   */
  methods: {
    /**
     * 进入直播间
     */
    gotoLiveRoom(formName) {
      this.$refs[formName].validate((valid) => {
        if(valid){
          this.init()
          this.dialogVisible = false
        }else{
          this.$notify.warning('请完善表单信息');
        }
      })
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
      that.ws = new WebSocket(that.$sipServerRoomUrl + '/' + that.userInfo.roomId + '/' + that.userInfo.userId + '/' + that.userInfo.pub);
      /**
       * 成功建立websocket连接
       */
      that.ws.onopen = function () {
        that.$notify({
          title: '提示',
          message: '信令服务器连接成功',
          type: 'success'
        });

        //视频会议初始化
        setTimeout(async () => {
          if (that.roomList.length) {
            await that.initRoom()
          }
        }, 2000)
      };
      //
      /**
       * websocket连接关闭
       */
      that.ws.onclose = function () {
        console.log("websocket已关闭");
      };
      /**
       * 发生了错误事件
       */
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
          that.onCandiDate(data.userId,data.candidate)
        }
      };
    },
    /**
     * 初始化房间
     */
    async initRoom() {
      const that = this
      if (that.userInfo.pub === '1') {
        const localStream = await this.getLocalUserMedia();
        this.setDomVideoStream("localVideo", localStream);
      }
      const localUid = this.userInfo.userId
      //找到当前房间的视频流发布者 即主播
      let publisher = this.roomList.filter(e => e.userId !== localUid && e.pub === '1').map((e) => {
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
      //创建数据通道
      await this.createDataChannel(pc, localUid, publisher)
      //创建offer
      let offer = await pc.createOffer();
      //设置offer未本地描述
      await pc.setLocalDescription(offer)
      //发送offer给被呼叫端
      let params = { 'type': 'offer', "targetUid": publisher, "userId": localUid, "offer": offer}
      that.ws.send(JSON.stringify(params))
    },
    /**
     * 创建数据通道
     * @param {Object} pc
     * @param {Object} localUid
     * @param {Object} remoteUid
     */
    async createDataChannel(pc, localUid, remoteUid) {
      let datachannel = await pc.createDataChannel(localUid + '-' + remoteUid);
      console.log("datachannel " + localUid + '-' + remoteUid, datachannel)
      dataChannelMap.delete(localUid + '-' + remoteUid)
      dataChannelMap.set(localUid + '-' + remoteUid, datachannel)
    },
    /**
     * 处理远端SDP
     */
    async onRemoteOffer(fromUid,offer) {
      let that = this
      const localUid = this.userInfo.userId
      let pcKey = localUid + '-' + fromUid
      let pc = new PeerConnection(that.rtcPcParams)
      RtcPcMaps.set(pcKey, pc)
      console.log("主播监听到远端offer");
      this.onPcEvent(pc, localUid, fromUid)
      //NOTE: 主播端创建数据通道
      await this.createDataChannel(pc, localUid, fromUid)
      for (const track of this.localStream.getTracks()) {
        pc.addTrack(track);
      }
      pc.setRemoteDescription(offer)
      let answer = await pc.createAnswer();
      await pc.setLocalDescription(answer);
      let params = {"targetUid": fromUid, "userId": localUid, "answer": answer,'type':'answer'}
      that.ws.send(JSON.stringify(params))
    },
    /**
     * 应答远端SDP
     */
    async onRemoteAnswer(fromUid, answer) {
      const localUid = this.userInfo.userId
      let pcKey = localUid + '-' + fromUid
      let pc = RtcPcMaps.get(pcKey)
      await pc.setRemoteDescription(answer);
    },
    /**
     * 网络信息协商
     */
    onCandiDate(fromUid, candidate){
      const localUid = this.userInfo.userId
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
    onPcEvent(pc, localUid, remoteUid) {
      const that = this
      this.channel = pc.createDataChannel("chat");
      pc.ontrack = function (event) {
        console.log(event)
        that.setRemoteDomVideoStream("localVideo", event.track)
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
     * 异常处理
     * @param error
     */
    handleError(error) {
      alert("缺少必要的音频或视频输入驱动设备")
      console.error('navigator.MediaDevices error: ', error.message, error.name);
    },
    /**
     * 显示远程传输过来的视频流
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
    videoSet() {

    },
    audioSet() {

    },
    shardScreenSet() {

    },
    endChat() {

    },
    rowStyle(){
      return 'text-align:center'
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


.local-video{
  width: 100%;
  height: 450px;
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
