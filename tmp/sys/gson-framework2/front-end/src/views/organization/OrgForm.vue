<template>
  <layout-content>
    <div class="h-panel">
      <div class="h-panel-body">
        <Form v-width="800" ref="form" mode="twocolumn" :label-width="110" :rules="validationRules" :model="model">
          <FormItem label="组织名称" prop="name">
            <input type="text" v-model="model.name"/>
          </FormItem>
          <FormItem label="简称" prop="simpleName">
            <input type="text" v-model="model.simpleName"/>
          </FormItem>
          <FormItem label="法人" prop="legal">
            <input type="text" v-model="model.legal"/>
          </FormItem>
          <FormItem label="营业执照号" prop="code">
            <input type="text" v-model="model.code"/>
          </FormItem>
          <FormItem label="联系人" prop="contact">
            <input type="text" v-model="model.contact"/>
          </FormItem>
          <FormItem label="联系人电话" prop="telephone">
            <input type="text" v-model="model.telephone"/>
          </FormItem>
          <FormItem label="企业邮箱" prop="email">
            <input type="text" v-model="model.email"/>
          </FormItem>
          <FormItem label="地址" prop="address">
            <input type="text" v-model="model.address"/>
          </FormItem>
          <FormItem single>
            <Button icon="fa fa-close" @click="reset">取消</Button>
            <Button icon="fa fa-save" color="primary" :loading="isLoading" @click="submit">保存</Button>
          </FormItem>
        </Form>
      </div>
    </div>
  </layout-content>
</template>

<script>
import Company from '../../model/company'

export default {
  name: 'OrgForm',
  props: {
    id: [Number, String]
  },
  data() {
    return {
      isLoading: false,
      model: Company.parse({}),
      validationRules: {
        required: ['name', 'telephone', 'simpleName', 'legal', 'code', 'contact', 'address', 'email'],
        mobile: ['telephone'],
        email: ['email'],
      }
    }
  },
  computed: {
    saveApi() {
      return this.$api.organization.company[this.model.id ? 'update' : 'save']
    }
  },
  methods: {
    submit() {
      let validResult = this.$refs.form.valid()
      if (validResult.result) {
        this.isLoading = true
        this.saveApi(Company.dispose(this.model)).then(({success, msg}) => {
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
      this.$api.organization.company.load(this.id).then(res => {
        this.model = Company.dispose(res.data)
      })
    }
  }
}
</script>

<style scoped>

</style>
