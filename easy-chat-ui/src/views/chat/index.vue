<template>
  <div class="chat-container">
    <el-container>
      <el-container>
        <el-main class="main">
          <chat-main ref="chatMain" @initServer="initServer"></chat-main>
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

export default {
  name: "chatIndex",
  components: {
    ChatMain,
    ChatFooter
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
    initServer() {
      let that = this;
      socket.ws_url = that.$wsServerUrl + this.$store.state.userId ;
      // 设置连接成功时回调
      socket.successCallBack(() => {
        this.$message.success('连接成功')
      });
      // 重写Socket中的消息接收方法
      socket.receive = (msg) => {
        msg = JSON.parse(msg.data);
        let data = {
          type: 'other',
          content: msg.msg
        }
        this.$refs.chatMain.pushMsg(data);
      };
      // 开始建立连接
      socket.init();
    },
    /**
     * 发送消息
     */
    sendMsg(obj) {
      socket.send(obj,()=>{
        let msgObj = {content: obj.msg, type: 'my'}
        this.$refs.chatMain.pushMsg(msgObj);
        this.$refs.chatFooter.clearContent()
      },error=>{
        console.log(error);
        this.$message.error('socket 出现未知异常,请查看控制台')
      });
    }
  }
}
</script>

<style scoped>
.el-container {
  width: 600px;
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
