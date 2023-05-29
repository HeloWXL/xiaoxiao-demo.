<template>
  <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="30%"
      center
      :modal="false"
  >
    <el-upload
        class="uploadFile"
        ref="fileUpload"
        drag
        action="#"
        :auto-upload="false"
        :http-request="uploadSectionFile"
        :file-list="fileList"
        :on-change="handleChange"
        >
      <i class="el-icon-upload"></i>
      <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
    </el-upload>
    <span slot="footer" class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="submitUpload">上 传</el-button>
    </span>
  </el-dialog>
</template>

<script>
export default {
  name: "UploadDialog",
  data(){
    return {
      dialogVisible:false,
      file:null,
      fileList:[],
      gradeId:'',
      materialTypeId:''
    }
  },
  methods:{
    /**
     * 上传文件至服务器
     * @param params
     */
    uploadSectionFile(params){
      if(!params.file){
        this.$message({
          type: 'warning',
          message: '请先选择文件哦'
        });
        return false;
      }
      const formData = new FormData();
      formData.append("file", params.file);
      // 设置contentType 信息
      const header = {
        "Content-Type": "mutipart/form-data"
      };
      this.$ajax.post(
          "/upload/uploadFile",
          formData,
          {
            headers: header
          }
      ).then((res) => {
        // 判断上传返回结果
        if(res.flag === true){
          this.$message({
            type: 'success',
            message: res.msg
          });
          // 更新文件列表数据
          this.$emit('queryFileList')
          // 清除文件
          this.clearFile();
          // 关闭弹窗
          this.dialogVisible = false;
        }else{
          this.$message({
            type: 'error',
            message: res.msg
          });
        }
      })
    },
    /**
     * 主动触发-上传文件
     */
    submitUpload(){
      this.$refs.fileUpload.submit();
    },
    /**
     * 文件发生改变时
     * @param file
     * @param fileList
     */
    handleChange(file, fileList) {
      this.fileList = fileList;
    },
    /**
     * 清除文件
     */
    clearFile(){
      this.$refs.fileUpload.clearFiles()
    }
  }
}
</script>

<style scoped>
.uploadFile{
  text-align: center;
}
</style>