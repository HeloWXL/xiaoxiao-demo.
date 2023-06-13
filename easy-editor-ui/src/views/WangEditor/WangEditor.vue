<template>
  <div style="border: 1px solid #ccc;">
    <Toolbar
        style="border-bottom: 1px solid #ccc"
        :editor="editor"
        :defaultConfig="toolbarConfig"
        :mode="mode"
    />
    <Editor
        style="height: 500px; overflow-y: hidden;"
        v-model="html"
        :defaultConfig="editorConfig"
        :mode="mode"
        @onCreated="onCreated"
        @onChange="onChange"
        @onDestroyed="onDestroyed"
        @onMaxLength="onMaxLength"
        @onFocus="onFocus"
        @onBlur="onBlur"
        @customAlert="customAlert"
        @customPaste="customPaste"
    />
    <el-row>
      <el-button type="primary" @click="setValue">设置值</el-button>
      <el-button type="success" @click="getValue">获取值</el-button>
    </el-row>
  </div>
</template>

<style src="@wangeditor/editor/dist/css/style.css"></style>
<script>

import {Editor, Toolbar} from '@wangeditor/editor'

export default {
  name: "WangEditor",
  components: {Editor, Toolbar},
  data() {
    return {
      editor: null,
      html: '<p>hello</p>',
      toolbarConfig: {},
      editorConfig: {placeholder: '请输入内容...'},
      mode: 'default', // or 'simple'
    }
  },
  methods: {
    onCreated(editor) {
      this.editor = Object.seal(editor) // 一定要用 Object.seal() ，否则会报错
    },
    onChange(editor) {
      console.log('onChange', editor.children)
    },
    onDestroyed(editor) {
      console.log('onDestroyed', editor)
    },
    onMaxLength(editor) {
      console.log('onMaxLength', editor)
    },
    onFocus(editor) {
      console.log('onFocus', editor)
    },
    onBlur(editor) {
      console.log('onBlur', editor)
    },
    customAlert(info, type) {
      window.alert(`customAlert in Vue demo\n${type}:\n${info}`)
    },
    customPaste(editor, event, callback) {
      console.log('ClipboardEvent 粘贴事件对象', event)
      // const html = event.clipboardData.getData('text/html') // 获取粘贴的 html
      // const text = event.clipboardData.getData('text/plain') // 获取粘贴的纯文本
      // const rtf = event.clipboardData.getData('text/rtf') // 获取 rtf 数据（如从 word wsp 复制粘贴）

      // 自定义插入内容
      editor.insertText('xxx')

      // 返回 false ，阻止默认粘贴行为
      event.preventDefault()
      callback(false) // 返回值（注意，vue 事件的返回值，不能用 return）

      // 返回 true ，继续默认的粘贴行为
      // callback(true)
    },
    setValue() {
      const editor = this.editor // 获取 editor 实例
      if (editor == null) return

      // 调用 editor 属性和 API
      editor.insertText('一段文字')
      console.log(editor.children)
    },
    getValue(){

    }
  },
  mounted() {
    // 模拟 ajax 请求，异步渲染编辑器
    setTimeout(() => {
      this.html = '<p>模拟 Ajax 异步设置内容 HTML</p>'
    }, 1500)
  },
  beforeDestroy() {
    const editor = this.editor
    if (editor == null) return
    editor.destroy() // 组件销毁时，及时销毁编辑器
  }
}
</script>

<style scoped>

</style>