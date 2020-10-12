<template>
  <layout-content>
    <div class="h-panel">
      <div class="h-panel-body">
        <Form v-width="400" ref="form" :label-width="110" :rules="validationRules" :model="model">
          <FormItem label="名称" prop="name">
            <input type="text" v-model="model.name"/>
          </FormItem>
          <FormItem label="办公位置" prop="address">
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
import Dept from '../../model/dept'

export default {
  name: 'DeptForm',
  props: {
    id: [Number, String],
    org: [Number, String]
  },
  data() {
    return {
      isLoading: false,
      model: Dept.parse({companyId: this.org}),
      validationRules: {
        required: ['name', 'address']
      }
    }
  },
  computed: {
    saveApi() {
      return this.$api.organization.dept[this.model.id ? 'update' : 'save']
    }
  },
  methods: {
    submit() {
      let validResult = this.$refs.form.valid()
      if (validResult.result) {
        this.isLoading = true
        this.saveApi(Dept.dispose(this.model)).then(({success, msg}) => {
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
    if (this.id && !this.org) {
      this.$api.organization.dept.load(this.id).then(res => {
        this.model = Dept.dispose(res.data)
      })
    } else if (this.id && this.org) {
      this.model.parentId = this.id
    }
  }
}
</script>

<style scoped>

</style>
