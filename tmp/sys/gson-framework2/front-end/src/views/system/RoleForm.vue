<template>
  <layout-content :loading="isLoading">
    <div class="h-panel">
      <div class="h-panel-body">
        <Form v-width="600" ref="form" :label-width="110" :rules="validationRules" :model="model">
          <FormItem label="名称" prop="name">
            <input type="text" v-model="model.name"/>
          </FormItem>
          <FormItem label="角色说明" prop="caption">
            <textarea rows="4" v-autosize v-wordcount="500" v-model="model.caption"></textarea>
          </FormItem>
          <FormItem label="排序号" prop="rank">
            <NumberInput type="text" v-model="model.rank"/>
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
import Role from '../../model/role'

export default {
  name: 'SystemRoleForm',
  props: {
    id: [Number, String],
    org: [Number, String]
  },
  data() {
    return {
      isLoading: false,
      model: Role.parse({companyId: this.org, rank: 0}),
      validationRules: {
        required: ['name']
      }
    }
  },
  computed: {
    saveApi() {
      return this.$api.system.role[this.model.id ? 'update' : 'save']
    }
  },
  methods: {
    submit() {
      let validResult = this.$refs.form.valid()
      if (validResult.result) {
        this.isLoading = true
        this.saveApi(Role.dispose(this.model)).then(({success, msg}) => {
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
      this.isLoading = true
      this.$api.system.role.load(this.id).then(res => {
        this.model = Role.dispose(res.data)
        this.isLoading = false
      })
    }
  }
}
</script>

<style scoped>

</style>
