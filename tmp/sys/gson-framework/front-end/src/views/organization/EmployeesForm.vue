<template>
  <layout-content>
    <div class="h-panel">
      <div class="h-panel-body">
        <Form ref="form" :label-width="110" :rules="validationRules" :model="model" mode="twocolumn">
          <FormItem label="类型" prop="type" single>
            <Radio v-model="model.type" dict="employeesType"/>
          </FormItem>
          <FormItem label="工号" prop="jobNumber">
            <input type="text" v-model="model.jobNumber"/>
          </FormItem>
          <FormItem label="入职日期" prop="entryDate">
            <DatePicker v-model="model.entryDate"/>
          </FormItem>
          <FormItem label="姓名" prop="userName">
            <input type="text" v-model="model.userName"/>
          </FormItem>
          <FormItem label="性别" prop="gender">
            <Radio v-model="model.gender" dict="gender"/>
          </FormItem>
          <FormItem label="电话" prop="telephone">
            <input type="text" v-model="model.telephone"/>
          </FormItem>
          <FormItem label="身份证" prop="idCode">
            <input type="text" v-model="model.idCode"/>
          </FormItem>
          <FormItem label="邮箱" prop="email">
            <input type="text" v-model="model.email"/>
          </FormItem>
          <FormItem label="家庭住址" prop="address">
            <input type="text" v-model="model.address"/>
          </FormItem>
          <FormItem label="籍贯信息">
            <CategoryPicker ref="CategoryPicker" :option="districtOptions" type="object" showAllLevels v-model="birthplaceInformation"></CategoryPicker>
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
import Emp from '../../model/employees'
import {getTotalData} from '../../kernel/locations/district'

export default {
  name: 'EmployeesForm',
  props: {
    id: [Number, String],
    dept: [Number, String]
  },
  data() {
    return {
      isLoading: false,
      birthplaceInformation: {},
      districtOptions: {
        keyName: 'id',
        titleName: 'title',
        dataMode: 'list',
        parentName: 'parentId',
        datas: getTotalData()
      },
      model: Emp.parse({masterDepartmentId: this.dept}),
      validationRules: {
        required: ['userName', 'jobNumber', 'entryDate', 'telephone', 'idCode', 'type'],
        email: ['email'],
        mobile: ['telephone']
      }
    }
  },
  computed: {
    saveApi() {
      return this.$api.organization.employees[this.model.id ? 'update' : 'save']
    }
  },
  methods: {
    submit() {
      let validResult = this.$refs.form.valid()
      if (validResult.result) {
        this.isLoading = true
        this.saveApi(Emp.dispose(this.model)).then(({success, msg}) => {
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
      this.$api.organization.employees.load(this.id).then(res => {
        this.model = Emp.dispose(res.data)
      })
    }
  }
}
</script>

<style scoped>

</style>
