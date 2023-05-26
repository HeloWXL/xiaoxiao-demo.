<template>
  <div class="container">
    <el-row style="margin-top: 10px;margin-bottom: 10px">
      <el-col :span="24">
        <el-button type="primary" @click="openUploadDialog">上传文件</el-button>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24">
        <!--表格数据-->
        <el-table
            :data="fileTableData"
            v-loading="loading"
            element-loading-text="拼命加载中"
            element-loading-spinner="el-icon-loading"
            stripe
            border
            style="width: 100%">
          <el-table-column
              prop="id"
              label="文件ID"
              align="center"
              show-overflow-tooltip
          >
          </el-table-column>
          <el-table-column
              prop="fileName"
              label="文件名"
              align="center"
              show-overflow-tooltip
          >
          </el-table-column>
          <el-table-column
              prop="fileSize"
              label="文件大小"
              align="center"
          >
          </el-table-column>
          <el-table-column
              prop="filePath"
              label="路径"
              show-overflow-tooltip
          >
          </el-table-column>
          <el-table-column
              prop="createTime"
              label="上传时间"
              show-overflow-tooltip
              align="center"
          >
          </el-table-column>
          <el-table-column label="操作">
            <template slot-scope="scope">
              <el-button
                  size="mini"
                  type="primary"
                  @click="downloadFile( scope.row)">下载
              </el-button>
              <el-button
                  size="mini"
                  type="success"
                  @click="previewFile(scope.row)">预览
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="currentPage"
            :page-sizes="[10, 20, 30, 40]"
            :page-size=pageSize
            layout="total, sizes, prev, pager, next, jumper"
            :total=total>
        </el-pagination>
      </el-col>
    </el-row>
    <!--文件上传-->
    <upload-dialog ref="uploadDialog"></upload-dialog>
    <!--文件预览-->
    <preview-file-dialog ref="previewFileDialog"></preview-file-dialog>
  </div>
</template>

<script>
import {queryUserFile, downloadFile} from "@/api/file/file";

import PreviewFileDialog from "@/views/File/components/PreviewFileDialog";
import UploadDialog from "@/views/File/components/UploadDialog";

export default {
  name: "PreviewIndex",
  components: {
    PreviewFileDialog, UploadDialog
  },
  data() {
    return {
      fileTableData: [],
      /** 分页数据 */
      total: 0,
      pageSize: 10,
      currentPage: 1,
      // 加载
      loading: false,
    }
  },
  created() {
    this.queryFileList();
  },
  methods: {
    /**
     * 查询文件列表
     */
    queryFileList() {
      this.loading = true
      queryUserFile({current: this.currentPage, size: this.pageSize}).then((res) => {
        this.loading = false
        this.fileTableData = res.data.records
        this.total = res.data.total
      })
    },
    /**
     * 页数编码改变
     * @param val
     */
    handleSizeChange(val) {
      this.pageSize = val;
      this.currentPage = 1;
      this.queryFileList();
    },
    /**
     * 当前改变
     * @param val
     */
    handleCurrentChange(val) {
      this.currentPage = val;
      this.queryFileList();
    },
    /**
     * 打开上传文件弹窗
     */
    openUploadDialog(){
      this.$refs.uploadDialog.dialogVisible = true;
    },
    /**
     * 文件下载
     * @param row
     */
    downloadFile(row) {
      downloadFile({fileId: row.id}).then(res => {
        let url = window.URL.createObjectURL(new Blob([res]));
        let link = document.createElement("a");
        link.style.display = "none";
        link.href = url;
        //指定下载后的文件名，防跳转
        link.setAttribute("download", row.fileName);
        document.body.appendChild(link);
        link.click();
        //下载完成移除元素
        document.body.removeChild(link);
        // 释放内存
        window.URL.revokeObjectURL(link.href)
      })
    },
    previewFile(row) {
      this.$refs.previewFileDialog.fileInfo = row;
      this.$refs.previewFileDialog.dialogVisible = true;
    }
  }
}
</script>

<style scoped>

</style>