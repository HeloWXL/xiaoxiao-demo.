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
          msg: this.content,
          receiver:this.$store.state.targetId
        }
        this.$emit('sendMsg', obj);
      }else{
        this.$message.warning('消息内容不能为空')
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
