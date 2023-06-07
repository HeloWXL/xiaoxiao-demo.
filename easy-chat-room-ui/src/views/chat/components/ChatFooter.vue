<template>
  <div class="chat-footer">
    <el-input v-model="content" placeholder="请输入发送内容" @keyup.enter.native="sendMsg"></el-input>
    <el-button type="primary" round class="footer-send-btn" icon="el-icon-position" @click="sendMsg"></el-button>
  </div>
</template>

<script>
export default {
  name: "ChatFooter",
  data() {
    return {
      content: ''
    }
  },
  methods: {
    sendMsg() {
      if (this.content) {
        let obj = {
          sender: this.$store.state.userId,
          roomId: this.$store.state.roomId,
          sysMsgType: 'text',
          data: this.content
        }
        this.$emit('sendMsg', obj);
      }
    },
    /**
     * 清除会话框
     */
    clearContent() {
      this.content = ''
    }
  }
}
</script>

<style scoped>
.footer-send-btn {
  position: relative;
  bottom: 35px;
  right: 5px;
  float: right;
  height: 30px;
}

.el-input input {
  border-radius: 20px !important;
}

.el-button.is-round {
  border-radius: 20px;
  padding: 8px 23px;
}
</style>
