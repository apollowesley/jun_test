<template>
  <div class="row">
    <div class="col">
      <select v-model="birth[0]" name="0" ref="0" class="form-control" @change="birthChange" title="年">
        <option value="">年</option>
        <option :value="currentYear - val - 9" v-for="val in 60">{{currentYear - val - 9}}</option>
      </select>
    </div>
    <div class="col">
      <select v-model="birth[1]" name="1" ref="1" class="form-control" @change="birthChange" title="月">
        <option value="">月</option>
        <option :value="val" v-for="val in 12">{{val}}</option>
      </select>
    </div>
    <div class="col">
      <select v-model="birth[2]" name="2" ref="2" class="form-control" @change="birthChange" title="日">
        <option value="">日</option>
        <option :value="val" v-for="val in days">{{val}}</option>
      </select>
    </div>
  </div>
</template>
<!--
  日期选择控件, 三个下拉框实现的年-月-日选择
-->
<script>
  export default {
    name: 'dateSelect',
    data: () => ({
      currentYear: new Date().getFullYear(),
      birth: ['', '', '']
    }),
    props: ['value'],
    methods: {
      birthChange (e) {
        var index = e.target.name
        var value = e.target.value
        if (value) {
          e.target.dataset.prev = value
          this.birth.forEach((e, i) => { // 检查所有日期组成部分, 非当前操作且没有值的目标
            if (i === index || this.birth[i]) {
              return
            }
            this.birth[i] = this.$refs[i].options[1].value  // 赋默认值
          })
        } else {
          this.birth[index] = e.target.dataset.prev
        }
        this.$emit('input', this.birth.join('-'))
      }
    },
    computed: {
      days () {
        if (this.birth[0] && this.birth[1]) {
          return new Date(this.birth[0], this.birth[1], 0).getDate()
        }
        return 31
      }
    }
  }
</script>
