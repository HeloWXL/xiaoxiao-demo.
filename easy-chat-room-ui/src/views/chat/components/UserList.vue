<template>
  <div id="userList">
    <div class="main-top">
      <span>
          <el-avatar :src="require('@/assets/wx.png')"></el-avatar>
          <span>在线用户列表</span>
      </span>
    </div>
    <div class="student-list">
      <div class="student" v-for="(item,index) in userList" :key="index">
        {{item}}
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "UserList",
  data(){
    return {
      userList:[]
    }
  },
  methods:{
    /**
     * 加载所有的用户
     * @param list
     */
    loadUserList(list){
      list.forEach(item=>{
        this.userList.push(item.userId);
      })
    },
    /**
     * 移除某一个用户
     * @param userId
     */
    removeUserList(userId){
      this.userList.forEach((item, index, arr)=>{
        if(item === userId){
          arr.splice(index, 1);
        }
      })
    },
    pushUserList(userId){
     this.userList.push(userId)
    }
  }
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

.student-list{
  margin-top: 60px;
}

.student{
  color: white;
  margin: 5px;
  height: 40px;
  background-color: #6CA1FA;
  line-height: 40px;
  padding: 0px 15px;
}
</style>