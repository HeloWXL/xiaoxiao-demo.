<template>
  <el-row>
    <el-col :span="24">
      <div class="big-code-container">
        <el-row>
          <el-col :span="24">
            <el-button type="primary" @click="start">建立连接</el-button>
            <el-button type="danger" @click="disConnect">断开连接</el-button>
            <el-button @click="clearCode">清空</el-button>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <div class="code-container">
             <pre v-highlightDynamic>
               <code class="log" v-html="logData"></code>
             </pre>
            </div>
          </el-col>
        </el-row>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import {socket} from "@/util/socket";
export default {
  name: "LogMonitor",
  data() {
    return {
      logData: ''
    }
  },
  methods:{
    /**
     * 建立连接
     */
    start(){
      socket.ws_url = "ws://localhost:9001/logUtil";
      socket.successCallBack = ()=>{
        this.$message.success('连接成功');
      }
      socket.receive = (message)=>{
        this.logData += '\n'+message.data;
      }
      socket.init();
    },
    /**
     * 清空日志
     */
    clearCode(){
      this.logData = [];
    },
    /**
     * 断开连接
     */
    disConnect(){
      socket.close();
    }
  }
}
</script>

<style scoped>
.big-code-container{
  margin: 0 auto;
  height: 100%;
}
.code-container{
  height: 800px;
  overflow: auto;
  font-size: 16px;
}
</style>