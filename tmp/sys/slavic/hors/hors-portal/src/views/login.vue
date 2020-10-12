<template>
  <div class="login-main">
    <div class="login-content">
      <el-row style="overflow: hidden;">
        <el-col
          :xs="2"
          :sm="3"
          :md="4"
          :lg="7"
          :xl="8"
        ><br /></el-col>
        <el-col
          :xs="20"
          :sm="18"
          :md="16"
          :lg="10"
          :xl="8"
        >
          <el-card class="login-card slideInRight">
            <div
              slot="header"
              class="clearfix"
            >
              <h1>Welcome To Hors Portal</h1>
            </div>
            <div>
              <el-form
                label-position="left"
                label-width="80px"
                :model="loginForm"
              >
                <el-form-item label="用户名">
                  <el-input v-model="loginForm.username"></el-input>
                </el-form-item>
                <el-form-item label="密码">
                  <el-input
                    v-model="loginForm.password"
                    show-password
                  ></el-input>
                </el-form-item>
                <el-form-item label-width="0px">
                  <el-button
                    v-if="!loginForm.isloading"
                    icon="el-icon-key"
                    type="info"
                    @click="login"
                  >登录系统</el-button>
                  <el-button
                    v-if="loginForm.isloading"
                    icon="el-icon-loading"
                    type="info"
                    disabled
                  >正在登陆</el-button>
                </el-form-item>
              </el-form>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>
<script>
import { setToken } from "@/utils/token";
import { login } from "@/api/portalUser";

export default {
  name: "login",
  data() {
    return {
      loginForm: {
        username: 'admin',
        password: '123456',
        isloading: false
      }
    };
  },
  methods: {
    login() {
      if (!this.loginForm.username) {
        this.$message.error("请填写用户名")
        return
      }
      if (!this.loginForm.password) {
        this.$message.error("请填写密码")
        return
      }
      this.loginForm.isloading = true
      login(this.loginForm).then(res => {
        setToken(res.token);
        this.loginForm.isloading = false
        this.$router.push({ path: this.redirect || "/" });
      });
    }
  }
};
</script>

<style scoped>
.login-main {
  width: 100%;
  height: 100%;
  background-image: url("../assets/login.jpeg");
  background-size: 100%;
  background-repeat: no-repeat;
  text-align: center;
}
.login-content {
  width: 100%;
  height: 100%;
  background-repeat: no-repeat;
  padding-top: 10%;
  background-color: rgba(0, 0, 0, 0.3);
}
.login-card {
  border-radius: 6px;
  box-shadow: 0 16px 24px 2px rgba(0, 0, 0, 0.14),
    0 6px 30px 5px rgba(0, 0, 0, 0.12), 0 8px 10px -5px rgba(0, 0, 0, 0.2);
  margin-bottom: 100px;
  padding-top: 40px;
}

.slideInRight {
  animation: slideInRight 1s 1;
}

@keyframes slideInRight {
  0% {
    transform: translateX(2000px);
    visibility: visible;
  }
  to {
    transform: translateX(0);
  }
}
</style>
