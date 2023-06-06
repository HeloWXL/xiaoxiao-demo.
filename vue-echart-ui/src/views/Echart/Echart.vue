<template>
  <div class="container">
    <el-row :gutter="20" style="margin: 0;">
      <el-col :span="8">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>柱状图</span>
          </div>
          <div id="bar" style="width: 100%;height: 400px;"></div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>折线图</span>
          </div>
          <div id="line" style="width: 100%;height: 400px;"></div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card class="box-card">
          <div slot="header" class="clearfix">
            <span>饼图</span>
          </div>
          <div id="pie" style="width: 100%;height: 400px;"></div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "EchartIndex",
  data(){
    return {

    }
  },
  mounted() {
    this.lineEcharts();
    this.pieEcharts();
    this.barEcharts();
  },
  methods:{
    /**
     * 加载折线图
     */
    lineEcharts () {
      var myChart = this.$echarts.init(document.getElementById('line'))
      // 配置图表
      var option = {
        title: {
          text: 'Stacked Line'
        },
        tooltip: {
          trigger: 'axis'
        },
        legend: {
          data: ['Email', 'Union Ads']
        },
        //笛卡尔坐标系的底板
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        //工具框
        toolbox: {
          feature: {
            saveAsImage: {}
          }
        },
        xAxis: {
          type: 'category',
          boundaryGap: false,
          data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
        },
        yAxis: {
          type: 'value'
        },
        series: [
          //线一
          {
            name: 'Email',
            type: 'line',
            stack: 'Total',
            data: [120, 132, 101, 134, 90, 230, 210]
          },
          //线二
          {
            name: 'Union Ads',
            type: 'line',
            stack: 'Total',
            data: [220, 182, 191, 234, 290, 330, 310]
          }
        ]
      }
      myChart.setOption(option)
    },
    /**
     * 加载饼图
     */
    pieEcharts(){
      var myChart = this.$echarts.init(document.getElementById('pie'))
      // 配置图表
      var option = {
        title: {
          text: 'Referer of a Website',
          subtext: 'Fake Data',
          left: 'center'
        },
        tooltip: {
          trigger: 'item'
        },
        legend: {
          orient: 'vertical',
          left: 'left'
        },
        series: [
          {
            name: 'Access From',
            type: 'pie',
            radius: '50%',
            data: [
              { value: 1048, name: 'Search Engine' },
              { value: 735, name: 'Direct' },
              { value: 580, name: 'Email' },
              { value: 484, name: 'Union Ads' },
              { value: 300, name: 'Video Ads' }
            ],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }
        ]
      }
      myChart.setOption(option);
    },

    /**
     * 柱状图
     */
    barEcharts(){
      var myChart = this.$echarts.init(document.getElementById('bar'))
      // 配置图表
      var option = {
        title: {
          text: '标题'
        },
        //提示框
        tooltip: {},
        legend: {
          data: ['']
        },
        //x轴显示种类
        xAxis: {
          data: ['种类一', '种类二', '种类三', '种类四', '种类五', '种类六']
        },
        //y轴可填数值等
        yAxis: {
        },
        series: [{
          name: '销量',
          type: 'bar',
          //y轴数值
          data: [5,
            {
              value: 20,
              itemStyle: {
                color: '#FFB5C5'
              }
            }, 36, 10, 10, 20]
        }]
      }
      myChart.setOption(option)
    }
  }
}
</script>

<style scoped>
</style>