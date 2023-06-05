<template>
  <el-dialog
      title="登录"
      :visible.sync="dialogVisible"
      width="40%"
      :modal="false"
  >
    <el-form ref="form" :model="form" label-width="100px" :rules="formRules">
      <el-form-item label="用户ID" prop="userId">
        <el-input v-model="form.userId"></el-input>
      </el-form-item>
      <el-form-item label="对方用户ID" prop="targetId">
        <el-input v-model="form.targetId"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="connect()">连 接</el-button>
    </span>
  </el-dialog>
</template>

<script>
export default {
  name: "RegisterDialog",
  data() {
    return {
      dialogVisible: false,
      form: {
        userId: null,
        targetId: null
      },
      formRules: {
        userId: [
          {required: true, message: '请填写用户ID', trigger: 'blur'}
        ],
        targetId: [
          {required: true, message: '请填写对方用户ID', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    connect() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          this.$store.commit('setUserId', this.form.userId);
          this.$store.commit('setTargetId', this.form.targetId);
          this.$emit('initServer');
          this.dialogVisible = false;
        } else {
          this.$message.warning('请填完表单')
          return false;
        }
      });
    }
  }
}
</script>

<style scoped>

</style>