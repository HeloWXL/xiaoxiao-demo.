<template>
  <!--进入直播间-->
  <el-dialog
      title="进入直播间"
      :visible.sync="dialogVisible"
      width="30%"
      center>
    <el-form ref="userInfoForm" label-width="80px" :model="userInfo" :rules="userInfoRules">
      <el-form-item label="用户名：" prop="userId">
        <el-input v-model="userInfo.userId"></el-input>
      </el-form-item>
      <el-form-item label="房间号：" prop="roomId">
        <el-input v-model="userInfo.roomId"></el-input>
      </el-form-item>
      <el-form-item label="角色：" prop="pub">
        <el-radio v-model="userInfo.pub" label="1">主持人</el-radio>
        <el-radio v-model="userInfo.pub" label="0">参会人员</el-radio>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="gotoMeetingRoom('userInfoForm')">确 定</el-button>
   </span>
  </el-dialog>
</template>

<script>
export default {
  name: "RegisterMeetingDialog",
  data(){
    return {
      // 控制用户注册信息弹窗
      dialogVisible: false,
      userInfoRules: {
        userId: [
          {required: true, message: '请填写用户名', trigger: 'blur'}
        ],
        roomId: [
          {required: true, message: '请填写房间号', trigger: 'blur'}
        ],
        pub: [
          {required: true, message: '请选择角色', trigger: 'blur'}
        ]
      },
      // 用户信息
      userInfo: {}
    }
  },
  methods:{
    /**
     * 进入会议
     */
    gotoMeetingRoom(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.init()
          this.dialogVisible = false
        } else {
          this.$notify.warning('请完善表单信息');
        }
      })
    },
  }
}
</script>

<style scoped>

</style>
