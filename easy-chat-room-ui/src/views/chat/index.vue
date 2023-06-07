<template>
  <div class="chat-container">
    <el-container>
      <el-container>
        <el-main class="main">
          <el-row :gutter="10">
            <el-col :span="8">
              <!--系统消息-->
              <div class="left-msg">
                <sys-msg ref="sysMsg"></sys-msg>
              </div>
            </el-col>
            <el-col :span="8">
              <chat-main ref="chatMain" @initServer="initServer"></chat-main>
            </el-col>
            <el-col :span="8">
              <!--右侧用户列表-->
              <div class="right-msg">
                <user-list ref="userList"></user-list>
              </div>
            </el-col>
          </el-row>
        </el-main>
        <el-footer class="footer">
          <chat-footer ref="chatFooter" @sendMsg="sendMsg"></chat-footer>
        </el-footer>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import {socket} from "@/util/socket";
import ChatFooter from "./components/ChatFooter";
import ChatMain from "./components/ChatMain";
import UserList from "@/views/chat/components/UserList";
import SysMsg from "@/views/chat/components/SysMsg";

export default {
  name: "chatIndex",
  components: {
    ChatMain,
    ChatFooter,
    UserList,
    SysMsg
  },
  data() {
    return {
      ws: null
    }
  },
  created() {

  },
  methods: {
    /**
     * 连接服务器
     */
    initServer(roomId, userId) {
      let that = this;
      socket.ws_url = that.$wsServerUrl + '/' + roomId + '/' + userId;
      // 设置连接成功时回调
      socket.successCallBack(() => {
        this.$message.success('连接成功')
        // 获取 房间所有人
        setTimeout(() => {
          this.getRoomList()
        }, 1000);
      });
      // 重写Socket中的消息接收方法
      socket.receive = (msg) => {
        msg = JSON.parse(msg.data);
        if (msg.sysMsgType === 'join') {
            this.$refs.sysMsg.pushSysMsg(msg.data+'加入了房间');
          this.$refs.userList.pushUserList(msg.data)
        }
        if (msg.sysMsgType === 'leave') {
          this.$refs.sysMsg.pushSysMsg(msg.data+'离开了房间');
          this.$refs.userList.removeUserList(msg.data)
        }
        if (msg.sysMsgType === 'roomList') {
          this.$refs.userList.loadUserList(msg.data)
        }
        if (msg.sysMsgType === 'text') {
          let data = {
            type: 'other',
            content: msg.data
          }
          this.$refs.chatMain.pushMsg(data);
        }

      };
      // 开始建立连接
      socket.init();
    },
    /**
     * 发送消息 会将消息展示在页面上
     */
    sendMsg(obj) {
      socket.send(obj, () => {
        let msgObj = {content: obj.data, type: 'my'}
        this.$refs.chatMain.pushMsg(msgObj);
        this.$refs.chatFooter.clearContent()
      }, error => {
        console.log(error);
        this.$message.error('socket 出现未知异常,请查看控制台')
      });
    },
    /**
     * 获取房间人列表
     */
    getRoomList(){
      var msgObj = {
        sysMsgType: 'roomList',
        sender: this.$store.state.userId,
        roomId: this.$store.state.roomId
      }
      socket.send(msgObj);
    }
  }
}
</script>

<style scoped>
.el-container {
  width: 100%;
  margin: 0 auto;
}

.chat-container {
  width: 100%;
  background-color: #a8c3ee;
  border-radius: 10px;
  opacity: 0.8;
}

.main {
  margin-top: 5px;
  padding: 5px;
}

.footer {
  padding: 5px;
}
</style>
