<template>
  <div class="container-main">
    <el-container>
      <el-container>
        <el-aside width="400px">
          <chat-aside ref="chatAside"></chat-aside>
        </el-aside>
        <el-container>
          <el-main>
            <chat-main ref="chatMain"></chat-main>
          </el-main>
          <el-footer>
            <chat-footer ref="chatFooter"></chat-footer>
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

export default {
  name: "ChatIndex",
  components: {
    ChatAside,
    ChatMain,
    ChatFooter
  },
  data() {
    return {}
  },
  created() {

  },
  methods: {}
}
</script>

<style scoped>
.chat-container{
  background-color: #D3D5D3;
}

.header{
  padding: 5px;
}

.aside{
  padding: 5px;
}
.main{
  margin-top: 5px;
  padding: 5px;
}
.el-footer {
  padding: 10px 20px;
}
</style>