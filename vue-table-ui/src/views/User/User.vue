<template>
  <div class="container-main">
    <!--查询表单-->
    <el-row :gutter="10">
      <el-form :inline="true" ref="queryForm" :model="queryForm" class="demo-form-inline">
        <el-form-item label="用户ID:" prop="userId">
          <el-input v-model="queryForm.userId" placeholder="请输入用户ID"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="queryUserInfo">查询</el-button>
          <el-button type="primary" @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>
    </el-row>
    <!--新增、其他按钮-->
    <el-row>
      <el-col :span="24">
        <div style="float: left;margin-top: 10px;margin-bottom: 10px">
          <el-button type="primary" icon="el-icon-plus" @click="addClick">新增</el-button>
          <el-button type="danger" icon="el-icon-delete" @click="deleteBatch">批量删除</el-button>
        </div>
      </el-col>
    </el-row>
    <!--数据表格-->
    <el-row>
      <el-col :span="24">
        <div style="margin-top: 10px;margin-bottom: 10px">
          <!--表格数据-->
          <el-table
              ref="userTable"
              :data="userTableData"
              v-loading="loading"
              element-loading-text="拼命加载中"
              element-loading-spinner="el-icon-loading"
              stripe
              border
              style="width: 100%">
            <el-table-column
                type="selection"
                width="55">
            </el-table-column>
            <el-table-column
                prop="userId"
                label="用户ID"
                align="center"
            >
            </el-table-column>
            <el-table-column
                prop="userName"
                label="用户名"
                align="center"
            >
            </el-table-column>
            <el-table-column
                prop="age"
                label="年龄"
                align="center"
            >
            </el-table-column>
            <el-table-column
                prop="address"
                label="地址"
                align="center"
            >
            </el-table-column>
            <el-table-column
                prop="createTime"
                label="创建时间"
                align="center"
            >
            </el-table-column>
            <el-table-column
                label="操作"
                align="center"
            >
              <template slot-scope="scope">
                <el-tooltip class="item" effect="dark" content="修改" placement="top">
                  <el-button type="primary" size="small" @click="updateClick(scope.row)" icon="el-icon-edit"
                             circle></el-button>
                </el-tooltip>
                <el-tooltip class="item" effect="dark" content="删除" placement="top">
                  <el-button type="danger" size="small" icon="el-icon-delete" @click="deleteClick(scope.row)"
                             circle></el-button>
                </el-tooltip>
              </template>
            </el-table-column>
          </el-table>
          <!--分页-->
          <el-pagination
              style="margin-top: 10px"
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
              :current-page="queryForm.currentPage"
              :page-sizes="[10, 20, 30, 40]"
              :page-size=queryForm.pageSize
              layout="total, sizes, prev, pager, next, jumper"
              :total=queryForm.total>
          </el-pagination>
        </div>
      </el-col>
    </el-row>

    <!--新增、修改弹窗-->
    <user-dialog ref="userDialog" @queryUserInfo="queryUserInfo"></user-dialog>
  </div>
</template>

<script>
import UserDialog from "@/views/User/components/UserDialog";
import {deleteUser, queryUser} from "@/api/user/user";

export default {
  name: "UserIndex",
  components: {
    UserDialog
  },
  data() {
    return {
      // 存放表格数据
      userTableData: [],
      saveVisible: false,
      showTitle: false,
      // 加载
      loading: false,
      queryForm: {
        /** 分页数据 */
        total: 0,
        pageSize: 10,
        currentPage: 1
      }
    }
  },
  created() {
    this.queryUserInfo();
  },
  methods: {
    /**
     * 分页查询
     */
    queryUserInfo() {
      this.loading = true
      queryUser(this.queryForm).then((res) => {
        this.loading = false
        this.userTableData = res.data.records
        this.queryForm.total = res.data.total
      })
    },
    /**
     * 页数编码改变
     * @param val
     */
    handleSizeChange(val) {
      this.queryForm.pageSize = val;
      this.queryForm.currentPage = 1;
      this.queryUserInfo();
    },
    /**
     * 当前改变
     * @param val
     */
    handleCurrentChange(val) {
      this.queryForm.currentPage = val;
      this.queryForm.queryUserInfo();
    },
    /**
     * 新增弹窗
     */
    addClick() {
      this.$refs.userDialog.saveVisible = true;
      this.$refs.userDialog.showTitle = true;
    },
    /**
     * 修改弹窗
     * @param row
     */
    updateClick(row) {
      this.$refs.userDialog.saveVisible = true;
      this.$refs.userDialog.showTitle = false;
      this.$refs.userDialog.userForm = JSON.parse(JSON.stringify(row));
    },
    /**
     * 批量删除
     */
    deleteBatch() {
      let selectData = this.$refs.userTable.selection;
      console.log(selectData)
      if (selectData.length > 0) {
        this.$confirm('是否删除', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let ids = [];
          selectData.forEach(item => {
            ids.push(item.id);
          })
          deleteUser(ids).then((res) => {
            if (res.data === true) {
              this.queryUserInfo();
              this.$message({
                type: 'success',
                message: res.msg
              });
            } else {
              this.$message({
                type: 'error',
                message: res.msg
              });
            }
          });
        });
      }else{
        this.$message({
          type: 'warning',
          message: '请至少选择一行数据'
        });
    }
  },
  /**
   * 删除
   * @param row
   */
  deleteClick(row) {
    this.$confirm('是否删除', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(() => {
      const ids = [];
      ids.push(row.id)
      deleteUser(ids).then((res) => {
        if (res.data === true) {
          this.queryUserInfo();
          this.$message({
            type: 'success',
            message: res.msg
          });
        } else {
          this.$message({
            type: 'error',
            message: res.msg
          });
        }
      })
    });
  },
  /**
   * 重置
   */
  resetForm() {
    // 清空表单内容
    this.$nextTick(() => {
      this.$refs['queryForm'].resetFields();
    })
  }
}
}
</script>

<style scoped>

</style>