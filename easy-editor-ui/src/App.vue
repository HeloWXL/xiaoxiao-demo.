<template>
  <div id="app">
    <quill-editor ref="quillEditor" class="quill-editor"></quill-editor>
  </div>
</template>

<script>
import QuillEditor from './components/QuillEditor.vue'
export default {
  name: 'App',
  components: {
    QuillEditor
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
  height: 100%;
}
.quill-editor{
  height: 400px;
}
</style>
