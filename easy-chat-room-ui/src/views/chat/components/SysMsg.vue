<template>
  <div id="sysMsg">
    <div class="main-top">
      <span>
          <el-avatar :src="require('@/assets/wx.png')"></el-avatar>
          <span>系统消息</span>
      </span>
    </div>
    <div class="sys-msg">
      <div class="msg">
        哈哈哈哈 进入房间了哦
      </div>
      <div class="msg">
        哈哈哈哈 进入房间了哦
      </div>
      <div class="msg">
        哈哈哈哈 进入房间了哦
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "SysMsg"
}
</script>

<style scoped>
  #sysMsg{
    overflow: auto;
    height: calc(100vh - 95px);
    background-color: white;
    border-radius: 5px;
  }
  .main-top {
    position: fixed;
    width: 680px;
    padding: 5px;
    height: 50px;
    border-bottom: 1px solid #eff3f6;
  }
  .el-avatar {
    display: block;
    background: #ffffff;
  }
  .main-top span {
    float: left;
    line-height: 40px;
    margin-left: 5px;
  }

  .sys-msg{
    margin-top: 60px;
  }

  .msg{
    margin: 5px;
    height: 40px;
    background-color: #a8c3ee;
    color: black;
    line-height: 40px;
    padding: 5px;
  }
</style>