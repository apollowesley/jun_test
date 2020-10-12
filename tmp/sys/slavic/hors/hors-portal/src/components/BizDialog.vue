<template>
  <div>
    <el-dialog
      v-if="dialog"
      :title="dialog.title ? dialog.title : '弹框'"
      :visible.sync="dialog.isShow"
      @closed='change()'
      :width="dialog.width ? dialog.width : '30%'"
      :modal="dialog.modal == 'need' ? true : false"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :show-close="false"
    >
      <div>
        <el-form
          v-if="dialog && dialog.form"
          :size="dialog.form.formSize ? dialog.form.formSize: 'small'"
          label-position="left"
        >
          <el-form-item
            v-for='item in dialog.form.formItems'
            :label="item.label"
            :key='item.prop'
            :label-width="item.width ? item.width : '100px'"
          >
            <el-input
              v-if="item.type==='input'"
              :placeholder="item.placeholder"
              v-model="dialog.form.data[item.prop]"
              :clearable="item.clearable"
            ></el-input>
            <el-select
              v-if="item.type==='select'"
              v-model="dialog.form.data[item.prop]"
              @change="item.change(dialog.form.data[item.prop])"
              :placeholder="item.placeholder"
              :clearable="item.clearable"
            >
              <el-option
                v-for="op in item.options"
                :label="op.label"
                :value="op.value"
                :key="op.value"
              ></el-option>
            </el-select>
            <el-radio-group
              v-if="item.type==='radio'"
              v-model="dialog.form.data[item.prop]"
            >
              <el-radio
                v-for="ra in item.radios"
                :label="ra.value"
                :key="ra.value"
              >{{ra.label}}</el-radio>
            </el-radio-group>
            <el-radio-group
              v-if="item.type==='radioButton'"
              v-model="dialog.form.data[item.prop]"
              @change="item.change && item.change(dialog.form.data[item.prop])"
            >
              <el-radio-button
                v-for="ra in item.radios"
                :label="ra.value"
                :key="ra.value"
              >{{ra.label}}</el-radio-button>
            </el-radio-group>
            <!-- <el-checkbox-group
          v-if="item.type==='checkbox'"
          v-model="dialog.form.data[item.prop]"
        >
          <el-checkbox
            v-for="box in item.boxs"
            :label="box.label"
            :key="box.value"
          ></el-checkbox>
        </el-checkbox-group> -->
            <el-date-picker
              v-if="item.type==='date'"
              v-model="dialog.form.data[item.prop]"
              :placeholder="item.placeholder"
              :clearable="item.clearable"
            ></el-date-picker>
            <el-date-picker
              v-if="item.type === 'daterange'"
              v-model="dialog.form.data[item.prop]"
              type="daterange"
              unlink-panels
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            >
            </el-date-picker>
            <el-time-select
              v-if="item.type==='time'"
              v-model="dialog.form.data[item.prop]"
              :placeholder="item.placeholder"
              type='time'
            ></el-time-select>
            <el-date-picker
              v-if="item.type==='dateTime'"
              type='datetime'
              :placeholder="item.placeholder"
              v-model="dialog.form.data[item.prop]"
              :clearable="item.clearable"
              :disabled="item.disable && item.disable(dialog.form.data[item.prop])"
            ></el-date-picker>
            <el-date-picker
              v-if="item.type === 'datetimerange'"
              v-model="dialog.form.data[item.prop]"
              type="datetimerange"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              :clearable="item.clearable"
              :default-time="['12:00:00', '08:00:00']"
            >
            </el-date-picker>
            <el-slider
              v-if="item.type==='slider'"
              v-model="dialog.form.data[item.prop]"
            ></el-slider>
            <el-switch
              v-if="item.type==='switch'"
              v-model="dialog.form.data[item.prop]"
            ></el-switch>
          </el-form-item>
        </el-form>
      </div>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          v-if="dialog && dialog.cancel"
          @click="dialog.cancel"
        >取 消</el-button>
        <el-button
          v-if="dialog && dialog.confirm"
          type="primary"
          @click="dialog.confirm"
        >确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'BizDialog',
  props: {
    dialog: {
      type: Object,
      default: null
    }
  },
  methods: {
    change() {
      this.$emit('change')
    }
  }
}
</script>