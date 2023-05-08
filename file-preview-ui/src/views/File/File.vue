<template>
  <div class="container">
    <el-row>
      <el-col :span="24">
        <el-button type="primary">上传文件</el-button>
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
              prop="fileName"
              label="文件名"
              align="center"
              show-overflow-tooltip
          >
          </el-table-column>
          <el-table-column
              prop="status"
              label="文件状态"
              align="center"
              :formatter="statusFormater"
          >
          </el-table-column>
          <el-table-column
              prop="remark"
              label="备注"
              show-overflow-tooltip
          >
          </el-table-column>
          <el-table-column
              prop="createTime"
              label="创建时间"
              show-overflow-tooltip
              align="center"
          >
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
import {queryFile} from "@/api/file/file";

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
  methods: {
    queryUserFile() {
      this.loading = true
      queryFile({current: 1, size: 10}).then((res) => {
        this.loading = false
        this.fileTableData = res.data.records
        this.total = res.data.total
      })
    },
    /** 页数编码改变 */
    handleSizeChange(val) {
      this.pageSize = val;
      this.currentPage = 1;
      this.queryUserFile();
    },
    /** 当前改变 */
    handleCurrentChange(val) {
      this.currentPage = val;
      this.queryUserFile();
    },
  }
}
</script>

<style scoped>

</style>