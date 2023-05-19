<template>
  <el-dialog
      :title="showTitle?'新增':'修改'"
      :visible.sync="saveVisible"
      width="30%"
      @closed = "closeCallBack"
      center>
    <el-form :model="userForm" ref="userForm" :rules="userFormRules" label-width="100px">
      <el-form-item label="用户ID:" prop="userId">
        <el-input v-model="userForm.userId"></el-input>
      </el-form-item>
      <el-form-item label="用户名:" prop="userName">
        <el-input v-model="userForm.userName"></el-input>
      </el-form-item>
      <el-form-item label="年龄:" prop="age">
        <el-input v-model="userForm.age"></el-input>
      </el-form-item>
      <el-form-item label="地址:" prop="address">
        <el-input v-model="userForm.address"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
         <el-button @click="saveVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdate">提 交</el-button>
      </span>
  </el-dialog>
</template>

<script>
import {saveOrUpdateUser} from "@/api/user/user";

export default {
  name: "UserDialog",
  data(){
    return {
      showTitle:false,
      saveVisible:false,
      userForm:{},
      userFormRules:{
        userId: [
          { required: true, message: '请填写用户ID', trigger: 'blur' }
        ],
        userName: [
          { required: true, message: '请填写用户名', trigger: 'blur' }
        ],
        age: [
          { required: true, message: '请填写年龄', trigger: 'blur' }
        ],
        address: [
          { required: true, message: '请填写地址', trigger: 'blur' }
        ]
      }
    }
  },
  methods:{
    /**
     * 保存、修改提交执后端
     */
    saveOrUpdate(){
      this.$refs['userForm'].validate((valid) => {
        if (valid) {
          saveOrUpdateUser(this.userForm).then((res) => {
            if (res.code === 0) {
              this.$message({
                type: 'success',
                message: res.msg
              });
              // 关闭弹窗
              this.saveVisible = false
              this.$emit("queryUserInfo")
            } else {
              this.$message({
                type: 'error',
                message: res.msg
              });
            }
          });
        } else {
          this.$message({
            type: 'warning',
            message: '请完善信息！'
          });
          return false;
        }
      })
    },
    closeCallBack(){
      // 清空表单内容
      this.$nextTick(() => {
        this.$refs['userForm'].resetFields();
        this.userForm = {}
      })
    }
  }
}
</script>

<style scoped>

</style>