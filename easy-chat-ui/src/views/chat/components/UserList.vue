<template>
  <div id="userList">
    <div class="main-top">
      <span>
          <el-avatar :src="require('@/assets/wx.png')"></el-avatar>
          <span>系统消息</span>
      </span>
    </div>

    <div class="user-list">
      <div class="user">
        Halo
      </div>
      <div class="user">
        啦啦啦啦
      </div>
      <div class="user">
        GOOOGO
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "UserList"
}
</script>

<style scoped>
#userList{
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

.user-list{
  margin-top: 60px;
}

.user{
  color: white;
  margin: 5px;
  height: 40px;
  background-color: #6CA1FA;
  line-height: 40px;
  padding: 5px;
}
</style>