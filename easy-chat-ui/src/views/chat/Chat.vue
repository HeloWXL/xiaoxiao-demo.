<template>
  <div class="container-main">
    <el-container>
      <el-container>
        <el-aside width="400px">
          <chat-aside ref="chatAside" @initServer="initServer"></chat-aside>
        </el-aside>
        <el-container>
          <el-main>
            <chat-main ref="chatMain"></chat-main>
          </el-main>
          <el-footer>
            <chat-footer ref="chatFooter" @sendMsg="sendMsg"></chat-footer>
          </el-footer>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import ChatAside from "@/components/ChatAside";
import ChatMain from "@/components/ChatMain";
import ChatFooter from "@/components/ChatFooter";
import {socket} from "@/socket/socket";

export default {
  name: "ChatIndex",
  components: {
    ChatAside,
    ChatMain,
    ChatFooter
  },
  data() {
    return {

    }
  },
  created() {

  },
  methods: {
    /**
     * 连接服务器
     */
    initServer(userId) {
      let that = this;
      socket.ws_url = that.$wsServerUrl + userId;
      // 开始建立连接
      socket.init();
      // 设置连接成功时回调
      socket.successCallBack(() => {
        this.$notify.success('连接成功')
      });
      // 重写Socket中的消息接收方法
      socket.receive = (msg) => {
        msg = JSON.parse(msg.data);
        console.log(msg)
      };
    },
    sendMsg(msg,succCallback){
      socket.send(msg,succCallback);
    }
  }
}
</script>

<style scoped>
.chat-container {
  background-color: #D3D5D3;
}

.header {
  padding: 5px;
}

.aside {
  padding: 5px;
}

.main {
  margin-top: 5px;
  padding: 5px;
}

.el-footer {
  padding: 10px 20px;
}
</style>