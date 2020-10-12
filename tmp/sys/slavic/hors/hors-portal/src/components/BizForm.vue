<template>
  <div>
    <el-form
      :size="form.formSize ? form.formSize: 'small'"
      inline
    >
      <el-form-item
        v-for='item in form.formItems'
        :key='item.prop'
        :label-width="item.width ? item.width : '180px'"
      >
        <el-input
          v-if="item.type==='input'"
          :placeholder="item.placeholder"
          v-model="form.search[item.prop]"
          :clearable="item.clearable"
        ></el-input>
        <el-select
          v-if="item.type==='select'"
          v-model="form.search[item.prop]"
          @change="item.change(form.search[item.prop])"
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
          v-model="form.search[item.prop]"
        >
          <el-radio
            v-for="ra in item.radios"
            :label="ra.value"
            :key="ra.value"
          >{{ra.label}}</el-radio>
        </el-radio-group>
        <el-radio-group
          v-if="item.type==='radioButton'"
          v-model="form.search[item.prop]"
          @change="item.change && item.change(form.search[item.prop])"
        >
          <el-radio-button
            v-for="ra in item.radios"
            :label="ra.value"
            :key="ra.value"
          >{{ra.label}}</el-radio-button>
        </el-radio-group>
        <!-- <el-checkbox-group
          v-if="item.type==='checkbox'"
          v-model="form.search[item.prop]"
        >
          <el-checkbox
            v-for="box in item.boxs"
            :label="box.label"
            :key="box.value"
          ></el-checkbox>
        </el-checkbox-group> -->
        <el-date-picker
          v-if="item.type==='date'"
          v-model="form.search[item.prop]"
          :placeholder="item.placeholder"
          :clearable="item.clearable"
        ></el-date-picker>
        <el-date-picker
          v-if="item.type === 'daterange'"
          v-model="form.search[item.prop]"
          type="daterange"
          unlink-panels
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        >
        </el-date-picker>
        <el-time-select
          v-if="item.type==='time'"
          v-model="form.search[item.prop]"
          :placeholder="item.placeholder"
          type='time'
        ></el-time-select>
        <el-date-picker
          v-if="item.type==='dateTime'"
          type='datetime'
          :placeholder="item.placeholder"
          v-model="form.search[item.prop]"
          :clearable="item.clearable"
          :disabled="item.disable && item.disable(form.search[item.prop])"
        ></el-date-picker>
        <el-date-picker
          v-if="item.type === 'datetimerange'"
          v-model="form.search[item.prop]"
          type="datetimerange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :clearable="item.clearable"
          :default-time="['12:00:00', '08:00:00']"
        >
        </el-date-picker>
        <el-slider
          v-if="item.type==='slider'"
          v-model="form.search[item.prop]"
        ></el-slider>
        <el-switch
          v-if="item.type==='switch'"
          v-model="form.search[item.prop]"
        ></el-switch>
      </el-form-item>
      <el-form-item
        v-if="!form.unline"
        :label-width="form.labelWidth"
      >
        <el-button
          v-for='item in form.actions'
          :icon='item.icon'
          :key="item.label"
          :type="item.type"
          :size="item.size ? item.size : 'small'"
          @click='item.handle()'
        >{{item.label}}</el-button>
      </el-form-item>
    </el-form>
    <el-form
      :size="form.formSize ? form.formSize : 'small'"
      inline
      v-if="form.unline"
      :label-width="form.labelWidth"
      align="right"
    >
      <el-form-item>
        <el-button
          v-for='item in form.actions'
          :icon='item.icon'
          :key="item.label"
          :type="item.type"
          :size="item.size ? item.size : 'small'"
          @click='item.handle()'
        >{{item.label}}</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>


<script>
export default {
  name: 'BizForm',
  props: {
    form: {
      type: Object,
      default: () => {
        return {}
      }
    }
  }

}
</script>