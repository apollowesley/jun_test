<template>
  <select class="form-control" title="dict" @change="change">
    <slot></slot>
    <option v-for="item in dicts" :value="item.key" :selected="value == item.key">{{item.value}}</option>
  </select>
</template>
<!--
  TODO
  (未实现)
  数据字典控件, 读取字典信息以不同的方式展现
-->
<script>
  import {Dict} from '../resources'

  export default {
    name: 'dict',
    data: () => ({
      dicts: []
    }),
    props: {
      element: {
        type: String
      },
      value: [String, Number, Array],
      code: String
    },
    created () {
      Dict.bycode(this.code).then(response => {
        this.dicts = response.data
      })
    },
    methods: {
      change (e) {
        this.$emit('input', e.target.value)
      }
    }
  }
</script>
