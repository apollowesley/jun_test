<template>
  <layout-content>
    <div class="h-panel">
      <div class="h-panel-body">
        <Form v-width="600" ref="form" :label-width="110" :rules="validationRules" :model="model">
          <FormItem label="名称" prop="name">
            <input type="text" v-model="model.name"/>
          </FormItem>
          <FormItem label="职位职责" prop="responsibility">
            <textarea rows="4" v-autosize v-wordcount="500" v-model="model.responsibility"></textarea>
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
import Position from '../../model/position'

export default {
  name: 'PositionForm',
  props: {
    id: [Number, String],
    org: [Number, String]
  },
  data() {
    return {
      isLoading: false,
      model: Position.parse({companyId: this.org, rank: 0}),
      validationRules: {
        required: ['name', 'responsibility']
      }
    }
  },
  computed: {
    saveApi() {
      return this.$api.organization.position[this.model.id ? 'update' : 'save']
    }
  },
  methods: {
    submit() {
      let validResult = this.$refs.form.valid()
      if (validResult.result) {
        this.isLoading = true
        this.saveApi(Position.dispose(this.model)).then(({success, msg}) => {
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
      this.$api.organization.position.load(this.id).then(res => {
        this.model = Position.dispose(res.data)
      })
    }
  }
}
</script>

<style scoped>

</style>
