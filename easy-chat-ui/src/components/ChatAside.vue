<template>
  <div class="chat-aside">
    <el-form ref="form" :model="form" label-width="100px" label-position="left">
      <el-form-item label="用户ID:">
        <el-input v-model="form.userId"></el-input>
      </el-form-item>
      <el-form-item label="对方用户ID:">
        <el-input v-model="form.targetId"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="connect">建立连接</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "AsideComponent",
  data() {
    return {
      form: {
        userId: null,
        targetId: null
      }
    }
  },
  methods:{
    connect(){
      if(this.form.userId){
        this.$emit('initServer',this.form.userId)
      }else{
        this.$message.warning('请输入用户ID')
      }
    }
  }
}
</script>

<style scoped>
.chat-aside {
  background-color: #eff3f6;
  height: calc(100vh - 125px);
  border-radius: 5px;
  padding: 25px;
}
</style>