<template>
  <el-dialog
      :title="fileInfo.fileName"
      :visible.sync="dialogVisible"
      :modal="false"
      width="70%"
      @opened="openCallBack"
      @close="closeForBack"
  >
    <div class="docx-preview-wrap">
      <!--预览 显示-->
      <div id="preview-container">
        <!-- word 文档-->
        <div v-if="fileType === 'docx'" ref="docxContainer"></div>
        <!--XLXS-->
        <div v-else-if="fileType === 'xlsx'" id="luckysheet" style="margin:0px;padding:0px;width:100%;height:100vh;"></div>
        <!--图片-->
        <img v-else-if="fileType === 'png' || fileType === 'jpg' || fileType === 'gif' " ref="imgContainer"/>
        <!--其他-->
        <el-empty v-else description="该格式文件暂不支持预览"></el-empty>
      </div>
    </div>
  </el-dialog>
</template>

<script>
import {renderAsync} from "docx-preview";
import LuckyExcel from "luckyexcel";
export default {
  name: "PreviewFileDialog",
  data() {
    return {
      dialogVisible: false,
      /**
       * docx 预览参数配置
       */
      docxOptions: {
        className: "kaimo-docx-666", // string：默认和文档样式类的类名/前缀
        inWrapper: true, // boolean：启用围绕文档内容的包装器渲染
        ignoreWidth: false, // boolean：禁用页面的渲染宽度
        ignoreHeight: false, // boolean：禁止渲染页面高度
        ignoreFonts: false, // boolean：禁用字体渲染
        breakPages: true, // boolean：在分页符上启用分页
        ignoreLastRenderedPageBreak: true, // boolean：在 lastRenderedPageBreak 元素上禁用分页
        experimental: false, // boolean：启用实验功能（制表符停止计算）
        trimXmlDeclaration: true, // boolean：如果为true，解析前会从xml文档中移除 xml 声明
        useBase64URL: false, // boolean：如果为true，图片、字体等会转为base 64 URL，否则使用URL.createObjectURL
        useMathMLPolyfill: false, // boolean：包括用于 chrome、edge 等的 MathML polyfill。
        showChanges: false, // boolean：启用文档更改的实验性渲染（插入/删除）
        debug: false, // boolean：启用额外的日志记录
      },
      /**
       * 文件信息
       */
      fileInfo: {},
      // 文件类型
      fileType: null,
    };
  },
  methods:{
    openCallBack() {
      var that = this;
      const addTypeArray = this.fileInfo.filePath.split(".");
      // 文件类型格式后缀
      const fileType = addTypeArray[addTypeArray.length - 1];
      // 文件类型赋值
      this.fileType = fileType;
      // 参数
      const params = {
        fileId: this.fileInfo.id
      }
      const url = '/upload/previewFile';
      // word
      if (this.fileType === 'docx') {
        this.$ajax.get(url, {
          params: params,
          responseType: 'blob'
        }).then((res) => {
          let blob = new Blob([res.data], {
            type: 'application/pdf'
          })
          that.docxRender(blob)
        }).catch(error => {
          console.error(error)
        })
      }
      // Excel
      else if (this.fileType === 'xlsx') {
        this.$ajax.get(url, {
          params: params,
          responseType: 'blob'
        }).then((res) => {
          const files = new window.File([res.data], this.fileInfo.fileName, {type: "application/vnd.ms-excel;charset=utf-8"});
          LuckyExcel.transformExcelToLucky(files, exportJson => {
            // eslint-disable-next-line
            luckysheet.destroy()
            // eslint-disable-next-line
            luckysheet.create({
              container: 'luckysheet', // luckysheet为容器id
              lang: 'zh',   //默认语言
              title: this.fileInfo.fileName,
              data: exportJson.sheets  //导入excel数据
            })
          })
        }).catch(error => {
          console.error(error)
        })
      }
      else if(this.fileType === 'png' || this.fileType === 'jpg' || this.fileType === 'gif'){
        this.$ajax.get(url, {
          params: params,
          responseType: 'blob'
        }).then((res) => {
            this.$refs.imgContainer.src = res;

        }).catch(error => {
          console.error(error)
        })
      }
    },
    // docx 渲染
    docxRender(buffer) {
      let docxContainer = this.$refs.docxContainer;
      renderAsync(
          buffer, // Blob | ArrayBuffer | Uint8Array, 可以是 JSZip.loadAsync 支持的任何类型
          docxContainer, // HTMLElement 渲染文档内容的元素,
          null, // HTMLElement, 用于呈现文档样式、数字、字体的元素。如果为 null，则将使用 bodyContainer。
          this.docxOptions // 配置
      ).then(res => {
        console.log("res---->", res)
      })
    },
    /**
     * 关闭回调
     */
    closeForBack() {
      this.fileType = null;
      this.fileInfo = {}
    }
  }
}
</script>

<style scoped>

</style>