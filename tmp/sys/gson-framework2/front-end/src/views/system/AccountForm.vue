<template>
  <layout-content>
    <div class="h-panel">
      <div class="h-panel-body">
        <Form v-width="400" ref="form" :label-width="90" :rules="validationRules" :model="model">
          <FormItem label="帐号" prop="account">
            <input type="text" v-model="model.account" :readonly="model.id"/>
          </FormItem>
          <FormItem label="密码" prop="password">
            <input type="password" v-model="model.password"/>
          </FormItem>
          <FormItem label="电话" prop="telephone">
            <input type="text" v-model="model.telephone"/>
          </FormItem>
          <FormItem label="角色" prop="roles">
            <Select v-model="model.roles" :datas="roles" multiple></Select>
          </FormItem>
          <FormItem label="状态" prop="status">
            <Radio v-model="model.status" :datas="status"></Radio>
          </FormItem>
          <FormItem>
            <Button icon="fa fa-close" @click="reset">取消</Button>
            <Button icon="fa fa-save" color="primary" :loading="isLoading" @click="submit">保存</Button>
          </FormItem>
        </Form>
      </div>
    </div>
  </layout-content>
</template>

<script>
import Account from '../../model/account'

export default {
  name: 'AccountForm',
  props: {
    id: [Number, String]
  },
  data() {
    return {
      isLoading: false,
      model: Account.parse({
        status: '可用'
      }),
      status: ['可用', '禁用', '锁定'],
      roles: []
    }
  },
  computed: {
    saveApi() {
      return this.$api.system.account[this.model.id ? 'update' : 'save']
    },
    validationRules() {
      let required = ['account', 'telephone']
      if (!this.model.id) {
        required.push('password')
      }
      return {
        required: required,
        mobile: ['telephone']
      }
    }
  },
  methods: {
    submit() {
      let validResult = this.$refs.form.valid()
      if (validResult.result) {
        this.isLoading = true
        this.saveApi(Account.dispose(this.model)).then(({success, msg}) => {
          if (success) {
            this.$Message('创建成功！')
            this.reset()
          } else {
            this.$Message(msg)
          }
          this.isLoading = false
        }).catch(() => {
          this.isLoading = false
        })
      }
    },
    reset() {
      this.$refs.form.reset()
      this.$router.back()
    }
  },
  mounted() {
    if (this.id) {
      this.$api.system.account.load(this.id).then(res => {
        this.model = Account.dispose(res.data)
      })
    }
  }
}
</script>

<style scoped>

</style>
