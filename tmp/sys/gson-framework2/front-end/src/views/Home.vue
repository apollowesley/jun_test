<template>
  <layout-content class="app-home-vue">
    <Row :space-x="20">
      <Cell :xs='24' :sm='12' :md="6" :lg='6'>
        <div class="info-box">
          <span class="info-box-icon bg-aqua">
          <i class="fa fa-gear"></i>
          </span>
          <div class="info-box-content">
            <span class="info-box-text">CPU Traffic</span>
            <span class="info-box-number">90<small>%</small></span>
          </div>
        </div>
      </Cell>
      <Cell :xs='24' :sm='12' :md="6" :lg='6'>
        <div class="info-box">
          <span class="info-box-icon bg-red">
          <i class="fa fa-google-plus"></i>
          </span>
          <div class="info-box-content">
            <span class="info-box-text">LIKES</span>
            <span class="info-box-number">41,410</span>
          </div>
        </div>
      </Cell>
      <Cell :xs='24' :sm='12' :md="6" :lg='6'>
        <div class="info-box">
          <span class="info-box-icon bg-green">
          <i class="fa fa-shopping-cart"></i>
          </span>
          <div class="info-box-content">
            <span class="info-box-text">SALES</span>
            <span class="info-box-number">760</span>
          </div>
        </div>
      </Cell>
      <Cell :xs='24' :sm='12' :md="6" :lg='6'>
        <div class="info-box">
          <span class="info-box-icon bg-yellow">
          <i class="fa fa-users"></i>
          </span>
          <div class="info-box-content">
            <span class="info-box-text">NEW MEMBERS</span>
            <span class="info-box-number">2,000</span>
          </div>
        </div>
      </Cell>
    </Row>
    <Row :space="30">
      <Cell :xs='24' :sm='24' :md='24' :lg='16' :xl='16'>
        <div class="h-panel">
          <div class="relative">
            <Tabs class="common-panel-tabs" v-model="type" :datas="{type1: '数据走势', type2: '数据分布'}"></Tabs>
          </div>
          <div class="home-part-body">
            <Chart :options="data1" v-if="type=='type1'" key="type1"></Chart>
            <Chart :options="data3" v-if="type=='type2'" key="type2"></Chart>
          </div>
        </div>
      </Cell>
      <Cell :xs='24' :sm='24' :md='24' :lg='8' :xl='8'>
        <div class="h-panel">
          <div class="h-panel-bar">
            <div class="h-panel-title">订单统计</div>
            <div class="h-panel-right"><span class="gray-color">总共达成</span><i class="h-split"></i><span class="font20 primary-color">200</span><i class="h-split"></i><span class="gray-color">W</span></div>
          </div>
          <div class="h-panel-body progress-div home-part-body">
            <p>
              <Progress :percent="99" color="green"><span slot="title">订单状态</span><span slot="text">4个</span></Progress>
            </p>
            <p>
              <Progress :percent="88" color="blue"><span slot="title">订单状态</span><span slot="text">0个</span></Progress>
            </p>
            <p>
              <Progress :percent="55" color="red"><span slot="title">订单状态</span><span slot="text">0个</span></Progress>
            </p>
            <p>
              <Progress :percent="77" color="blue"><span slot="title">订单状态</span><span slot="text">0个</span></Progress>
            </p>
            <p>
              <Progress :percent="66" color="yellow"><span slot="title">订单状态</span><span slot="text">0个</span></Progress>
            </p>
          </div>
        </div>
      </Cell>

      <Cell :xs='24' :sm='24' :md='24' :lg='16' :xl='16'>
        <div class="h-panel">
          <div class="h-panel-bar">
            <div class="h-panel-title">数据比例</div>
          </div>
          <div class="home-part-body2">
            <Chart :options="data2"></Chart>
          </div>
        </div>
      </Cell>

      <Cell :xs='24' :sm='24' :md='24' :lg='8' :xl='8'>
        <div class="h-panel">
          <div class="h-panel-bar">
            <div class="h-panel-title">数据比例</div>
          </div>
          <div class="h-panel-body home-part-body2">
            <Row :space="20">
              <Cell :width="10" class="text-right">
                <h-circle :percent="76" :stroke-width="10" :size="90">
                  <p><span class="font28">{{parseInt(123*76/100)}}</span><span class="gray-color"> / 123</span></p>
                </h-circle>
              </Cell>
              <Cell :width="14">
                <p class="gray-color">目前达成比例</p>
                <p class="dark-color font22">122,332,98</p>
              </Cell>
              <p class="clearfix"></p>
              <Cell :width="10" class="text-right">
                <h-circle :percent="99" :stroke-width="10" :size="90" color="green">
                  <p><span class="font28">{{parseInt(123*76/100)}}</span><span class="gray-color"> / 123</span></p>
                </h-circle>
              </Cell>
              <Cell :width="14">
                <p class="gray-color">目前达成比例</p>
                <p class="dark-color font22">122,332,98</p>
              </Cell>
              <p class="clearfix"></p>
              <Cell :width="10" class="text-right">
                <h-circle :percent="22" :stroke-width="10" :size="90" color="red">
                  <p><span class="font28">{{parseInt(123*76/100)}}</span><span class="gray-color"> / 123</span></p>
                </h-circle>
              </Cell>
              <Cell :width="14">
                <p class="gray-color">目前达成比例</p>
                <p class="dark-color font22">122,332,98</p>
              </Cell>
            </Row>
          </div>
        </div>
      </Cell>
    </Row>
  </layout-content>
</template>

<script>

import Chart from '../components/chart'
import data1 from '../components/datas/data1'
import data2 from '../components/datas/data2'
import data3 from '../components/datas/data4'

export default {
  name: 'Home',
  components: {Chart},
  data() {
    return {
      data1,
      data2,
      data3,
      type: 'type1'
    }
  },
}
</script>
<style lang="less">

  .info-box {
    display: block;
    min-height: 90px;
    background: #fff;
    width: 100%;
    box-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);
    border-radius: 2px;
    margin-bottom: 15px;

    small {
      font-size: 14px
    }

    .progress {
      background: rgba(0, 0, 0, 0.2);
      margin: 5px -10px 5px -10px;
      height: 2px;
      border-radius: 0;

      .progress-bar {
        border-radius: 0;
        background: #fff
      }
    }
  }

  .info-box-icon {
    border-radius: 2px 0 0 2px;
    display: block;
    color: #FFFFFF;
    float: left;
    height: 90px;
    width: 90px;
    text-align: center;
    font-size: 45px;
    line-height: 90px;
    background: rgba(0, 0, 0, 0.2);
  }

  .info-box-icon > img {
    max-width: 100%;
  }

  .info-box-content {
    padding: 5px 10px;
    margin-left: 90px;
  }

  .info-box-number {
    display: block;
    font-weight: bold;
    font-size: 18px;
  }

  .progress-description, .info-box-text {
    display: block;
    font-size: 14px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }

  .info-box-text {
    text-transform: uppercase;
  }

  .app-home-vue {
    background: #f0f2f5;
    margin: -20px;
    padding: 20px;
    margin-top: 0 !important;

    .home-part-body {
      height: 350px;

      .echarts-vue {
        height: 350px;
      }
    }

    .home-part-body2 {
      height: 420px;

      .echarts-vue {
        height: 420px;
      }
    }

    .progress-div {
      > p {
        padding: 8px 0;
      }

      .h-progress {
        &-title {
          color: @dark2-color;
          font-size: 15px;
        }

        &-text {
          width: 40px;
        }
      }
    }
  }
</style>