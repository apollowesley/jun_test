<template>
  <el-row style="height:100%">
    <el-col :span="12" :offset="6" style="text-align:center">
      <el-card class="box-card" style="margin-top:200px">
        <div slot="header" class="clearfix">
          <h2><i class="el-icon-picture-outline"></i>404</h2>
        </div>
        <el-row style="margin-top:30px">
          <el-col>
            对不起，你要找的这个页面突然不见了。不过，放心，一切都在我的掌控之中，不会跑多远！
          </el-col>
          <el-col :span="1" :offset="11" style="margin-top:30px">
            <el-button @click="toHome">返回首页</el-button>
          </el-col>
        </el-row>
      </el-card>
    </el-col>
  </el-row>
</template>

<style scoped>
.not-fond {
  width: 100%;
  height: 100%;
}
</style>

<script>
export default {
  name: "notFound",
  methods: {
    toHome() {
      this.$router.push({ path: this.redirect || "/" });
    }
  }
};
</script>
