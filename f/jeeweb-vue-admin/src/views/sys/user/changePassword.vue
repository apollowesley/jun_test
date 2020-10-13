<template>
  <div class="app-container calendar-list-container">
    <el-form ref="userForm" :model="userForm" :rules="rules" label-width="100px" class="demo-userForm">
      <el-form-item label="原密码" prop="oldPassword">
        <el-input :type="passwordType" v-model.trim="userForm.oldPassword" placeholder="原密码"/>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input :type="passwordType" v-model.trim="userForm.password" placeholder="密码"/>
      </el-form-item>
      <el-form-item label="确认密码" prop="passwordConfirm">
        <el-input :type="passwordType" v-model.trim="userForm.passwordConfirm" placeholder="确认密码"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('userForm')">保存</el-button>
        <el-button @click="resetForm('userForm')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import { myUpdateChangePassword } from '@/api/sys/user'

export default {
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.userForm.password) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      userForm: {
        password: '',
        oldPassword: '',
        passwordConfirm: ''
      },
      passwordType: 'password',
      rules: {
        passwordConfirm: [{ required: true, validator: validatePass, trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
        oldPassword: [{ required: true, message: '请输入原密码', trigger: 'blur' }]
      }
    }
  },
  created() {
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          myUpdateChangePassword(this.userForm).then(response => {
            if (response.data.code === 0) {
              this.$notify({
                title: '成功',
                message: response.data.msg,
                type: 'success',
                duration: 2000
              })
              this.$store.dispatch('LogOut').then(() => {
                location.reload()// In order to re-instantiate the vue-router object to avoid bugs
              })
            } else {
              this.$notify.error({
                title: '失败',
                message: response.data.msg,
                duration: 2000
              })
            }
          })
        } else {
          return false
        }
      })
    },
    resetForm(formName) {
      this.$refs[formName].resetFields()
    }
  }
}
</script>
