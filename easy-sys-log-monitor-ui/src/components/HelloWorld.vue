<template>
  <div class="hello">
    <h1>{{ msg }}</h1>
    <div class="code">
          <pre v-highlightStatic>
      <code class="log">{{ watch_data }}</code>
  </pre>
    </div>

  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
  props: {
    msg: String
  },
  data() {
    return {
      watch_data: '2023-06-15 11:46:55,718 @xiancheng@[http-nio-9001-exec-2]@xiancheng@ @dengji@ERROR@dengji@ @bao@xx.log.monitor.controller.LogController@bao@ - hello\n' +
          '\n' +
          '2023-06-15 11:46:55,718 @xiancheng@[http-nio-9001-exec-2]@xiancheng@ @dengji@INFO @dengji@ @bao@xx.log.monitor.controller.LogController@bao@ - hello\n' +
          '\n' +
          '2023-06-15 11:46:55,719 @xiancheng@[http-nio-9001-exec-2]@xiancheng@ @dengji@WARN @dengji@ @bao@xx.log.monitor.controller.LogController@bao@ - hello'
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.code{
  width: 100%;
  height: 800px;
  overflow: auto;
}
h3 {
  margin: 40px 0 0;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: inline-block;
  margin: 0 10px;
}

a {
  color: #42b983;
}
</style>
