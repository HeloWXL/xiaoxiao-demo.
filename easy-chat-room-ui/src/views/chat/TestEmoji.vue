<template>
  <div>
    <el-popover
        ref="popover5"
        placement="top-start"
        width="360"
        v-model="chatPopover"
    >
      <p>
        <chat-emoji :inpMessage="message" @selectEmoji="selectEmoji"></chat-emoji>
      </p>

    </el-popover>
    <el-input v-model="msg" placeholder="请输入内容"></el-input>
    <el-button type="primary" round class="footer-send-btn" icon="el-icon-position" @click="chatPopover = true"></el-button>

<!--    <div-->
<!--        v-popover:popover5-->
<!--        class="chatRoom_input_emoji"-->
<!--        @click="showEmojiHandle"-->
<!--    >-->
<!--      <img src="此处微表情图标" />-->
<!--    </div>-->

  </div>
</template>

<script>
import ChatEmoji from "@/compoents/emoji/ChatEmoji";

export default {
  name: "TestEmoji",
  components:{
    ChatEmoji
  },
  data(){
    return{
      chatPopover:false,
      message:null,
      msg: null
    }
  },
  methods:{
    showEmojiHandle(){

    },
    selectEmoji(emoji){
      this.msg = emoji;
      this.chatPopover = false;
    }
  }

}
</script>

<style scoped>

</style>