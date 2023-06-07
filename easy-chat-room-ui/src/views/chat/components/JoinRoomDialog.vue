<template>
  <el-dialog
      title="加入房间"
      :visible.sync="dialogVisible"
      :modal="false"
      width="30%">
    <el-form :model="joinForm" :rules="rules" ref="joinForm" label-width="100px">
      <el-form-item label="用户ID:" prop="userId">
        <el-input v-model="joinForm.userId"></el-input>
      </el-form-item>
      <el-form-item label="房间号:" prop="roomId">
        <el-input v-model="joinForm.roomId"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="joinRoom">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
export default {
  name: "JoinRoomDialog",
  data() {
    return {
      dialogVisible: false,
      joinForm: {
        userId: null,
        roomId: null
      },
      rules: {
        userId: [
          {required: true, message: '请输入用户ID', trigger: 'blur'}
        ],
        roomId: [
          {required: true, message: '请输入房间号', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    /**
     * 加入房间
     */
    joinRoom() {
      this.$refs['joinForm'].validate((valid) => {
        if (valid) {
          this.$store.commit('setUserId',this.joinForm.userId);
          this.$store.commit('setRoomId',this.joinForm.roomId)
          this.$emit('initServer', this.joinForm.roomId, this.joinForm.userId);
          this.dialogVisible = false;
        } else {
          this.$message.warning('请完善表单');
          return false;
        }
      });
    }
  }
}
</script>

<style scoped>

</style>