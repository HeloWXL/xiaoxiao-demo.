<template>
  <div class="chat-main" id="scrollbar">
    <div class="msg-list" v-for="(item,value) in msgList" :key="value">
      <div v-if="item.type === 'other'" class="other chat-box">
        <el-avatar size="medium" :src="require('@/assets/img/head.png')" class="left-head-img"></el-avatar>
        <!--文本消息-->
        <div class="other-msg">
          <!--文本消息-->
          <div v-if="item.msgType === 1">{{ item.msgCont }}</div>
          <!--图片消息-->
          <img v-if="item.msgType === 2" :src="item.msgCont" class="msg-img">
          <!--视频消息-->
          <video v-if="item.msgType === 6" :src="item.msgCont" class="msg-video" controls autoplay
                 playsinline></video>
        </div>
      </div>
      <div v-else class="my chat-box">
        <div class="my-msg">
          <!--文本消息-->
          <div v-if="item.msgType === 1">{{ item.msgCont }}</div>
          <!--图片消息-->
          <img v-if="item.msgType === 2" :src="item.msgCont" class="msg-img">
          <!--视频消息-->
          <video v-if="item.msgType === 6" :src="item.msgCont" class="msg-video" controls autoplay
                 playsinline></video>
        </div>
        <el-avatar size="medium" :src="require('@/assets/img/head.png')" class="right-head-img"></el-avatar>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "MainComponent",
  data() {
    return {
      msgList: []
    }
  },
  methods:{
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
      this.$nextTick(()=>{
        var div = document.getElementById('scrollbar');
        div.scrollTop = div.scrollHeight-div.clientHeight;
      })
    }
  }
}
</script>

<style scoped>
.chat-main {
  background: #6CA1FA;
  overflow: auto;
  height: calc(100vh - 160px);
  /*background-color: white;*/
  border-radius: 5px;
}

.right-head-img {
  position: relative;
  top: 12px;
}

.left-head-img {
  position: relative;
  top: 15px;
}

.other {
  padding: 10px 5px;
  margin: 0 10px;
  text-align: left;
}

.other-msg, .my-msg {
  font-size: 12px;
  margin-left: 5px;
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
</style>