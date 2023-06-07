<template>
  <div class="chat-main scroller" id="scrollbar">
    <div class="main-top">
      <span>
          <el-avatar :src="require('@/assets/wx.png')"></el-avatar>
          <span>房间号</span>
      </span>
      <span style="float: right;line-height: 50px">
        <i class="el-icon-setting" style="font-size: 25px;cursor: pointer;margin-right: 5px"></i>
        <i class="el-icon-thumb" style="font-size: 25px;cursor: pointer;margin-right: 5px" @click="openJoinRoomDialog"></i>
      </span>
    </div>
    <div style="margin-top: 60px">
      <div class="msg-list" v-for="(item,value) in msgList" :key="value">
        <div v-if="item.type === 'other'" class="other chat-box">
          <el-avatar size="medium" :src="require('@/assets/wx.png')" class="left-head-img"></el-avatar>
          <!--文本消息-->
          <div class="other-msg">
            <!--文本消息-->
            <div>{{ item.content }}</div>
          </div>
        </div>
        <div v-else class="my chat-box">
          <div class="my-msg">
            <!--文本消息-->
            <div>{{ item.content }}</div>
          </div>
          <el-avatar size="medium" :src="require('@/assets/head.png')" class="right-head-img"></el-avatar>
        </div>
      </div>
    </div>
    <!-- 加入房间弹窗-->
    <join-room-dialog ref="joinRoomDialog" @initServer="initServer"></join-room-dialog>
  </div>
</template>

<script>
import JoinRoomDialog from "@/views/chat/components/JoinRoomDialog";

export default {
  name: "ChatMain",
  components: {JoinRoomDialog},
  data() {
    return {
      msgList: []
    }
  },
  methods: {
    /**
     * 新增消息
     * @param item
     */
    pushMsg(item) {
      this.msgList.push(item);
      this.keepInBottom();
    },
    /**
     * 内容永远保持在底部
     */
    keepInBottom() {
      this.$nextTick(() => {
        var div = document.getElementById('scrollbar');
        div.scrollTop = div.scrollHeight - div.clientHeight;
      })
    },
    openJoinRoomDialog(){
      this.$refs.joinRoomDialog.dialogVisible = true;
    },
    /**
     * 建立连接
     * @param roomId
     * @param userId
     */
    initServer(roomId, userId) {
      this.$emit('initServer', roomId, userId);
    }
  }
}
</script>

<style scoped>

.chat-main {
  overflow: auto;
  height: calc(100vh - 95px);
  background-color: white;
  border-radius: 5px;
}

.right-head-img {
  position: relative;
  top: 5px;
  float: right;
}

.left-head-img {
  position: relative;
  top: 42px;
}

.other {
  padding: 0px 5px;
  margin: 0 10px;
  text-align: left;
}

.other-msg, .my-msg {
  font-size: 12px;
  margin-left: 40px;
  vertical-align: middle;
  background-color: #eff3f6;
  max-width: 80%;
  /*宽度内容自适应*/
  width: -webkit-fit-content;
  padding: 15px 15px;
  border-radius: 5px;
  /*文本自动换行*/
  word-break: break-all;
  display: inline-block;
  box-shadow: 0 2px 4px 0 rgb(207 209 211);
}

.msg-img {
  height: 100px;
  width: 100px;
}

.msg-video {
  width: 300px;
  height: 200px;
}

.my-msg {
  background-color: #6CA1FA;
  color: white;
  text-align: left;
  margin-right: 5px;
}

.my {
  text-align: right;
  padding: 10px 5px;
  margin: 0 10px;
}

.chat-box {
  position: relative;
}

.el-avatar {
  display: block;
  background: #ffffff;
}

.main-top {
  position: fixed;
  width: 610px;
  padding: 5px;
  height: 50px;
  border-bottom: 1px solid #eff3f6;
}

.main-top span {
  float: left;
  line-height: 40px;
  margin-left: 5px;
}


.scroller::-webkit-scrollbar {
  width: 8px;
  height: 8px;
}

.scroller::-webkit-scrollbar-track {
  background-color: transparent;
  -webkit-border-radius: 2em;
  -moz-border-radius: 2em;
  border-radius: 2em;
}

.scroller::-webkit-scrollbar-thumb {
  background-color: rgb(147, 147, 153, 0.5);
  -webkit-border-radius: 2em;
  -moz-border-radius: 2em;
  border-radius: 2em;
}

</style>
