<template>
  <el-row style="height:100%">
    <el-col :span="12" :offset="6" style="text-align:center">
      <el-card class="box-card" style="margin-top:200px">
        <div slot="header" class="clearfix">
          <h2><i class="el-icon-picture-outline"></i>401</h2>
        </div>
        <el-row style="margin-top:30px">
          <el-col>
            对不起，你好像没有权限访问这个页面,快找管理员申请权限吧
          </el-col>
          <el-col :span="1" :offset="11" style="margin-top:30px">
            <el-button @click="toHome">返回首页</el-button>
          </el-col>
        </el-row>
      </el-card>
    </el-col>
  </el-row>
</template>

<script>
export default {
  name: "noAuthority",
  methods: {
    toHome() {
      this.$router.push({ path: this.redirect || "/" });
    }
  }
};
</script>
