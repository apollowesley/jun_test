<template>
  <div class="companyStaffMap">
    <el-amap
      ref="map"
      vid="amap"
      :zoom="zoom"
      :amap-manager="amapManager"
      :center="center"
      class="amap-demo"
    >
      <el-amap-marker
        v-for="(marker, index) in markers"
        :key="index"
        vid="component-marker"
        :position="marker.position"
        :label="marker.content"
      />
    </el-amap>
  </div>
</template>

<script>
import { getHomeUserList } from '@/api/homeUser'
import VueAMap from 'vue-amap';
// 初始化vue-amap
VueAMap.initAMapApiLoader({
// 申请的高德key
  key: 'd7819ac954a2b848343699ca5cdb0f6c',
  // 插件集合
  plugin: ['AMap.Autocomplete', 'AMap.PlaceSearch', 'AMap.Scale', 'AMap.OverView', 'AMap.ToolBar', 'AMap.MapType', 'AMap.PolyEditor', 'AMap.CircleEditor'],
});
const amapManager = new VueAMap.AMapManager();
export default {
  name: 'Map',
  data() {
    return {
      // 高德地图的相关数据
      zoom: 18,
      center: [120.393674, 36.266341],
      amapManager,
      markers: [

      ],
    };
  },
  mounted() {
    this.getMessage();
  },
  methods: {
    getMessage() {
      getHomeUserList().then((res) => {
        console.log(res);
        const result = [];
        res.data.map((item) => {
          if (item.homeRecord !== null) {
            result.push({
              position: [item.homeRecord.longitude, item.homeRecord.latitude],
              content: {
                content: item.userName,
                offset: [-8, -30],
              },
            });
          }
        });
        console.log(result);
        this.markers = result;
      });
    },
  },
};
</script>

<style scoped>

  .companyStaffMap {
    height: 800px;
  }
</style>
