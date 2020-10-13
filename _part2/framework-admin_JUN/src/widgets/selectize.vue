<template>
  <div>
    <span class="img-btn cover mr-1" :title="item.name" :key="item.id"
          :style="{backgroundImage: 'url(' + item.avatar + ')'}" v-for="item in selected"></span>
    <div class="dropdown selectize" ref="dropdown">
      <a class="img-btn text-white unselectable" title="选择用户" @click.stop="show">┼</a>
      <div class="dropdown-menu" :class="{'loading' : dirty || loading}">
        <div class="input">
          <input ref="input" v-model.trim="keyword" class="form-control" placeholder="搜索用户" maxlength="32" tabindex="-1">
          <i v-if="keyword" class="ti-close" @click="keyword = ''"></i>
          <i v-else class="ti-search"></i>
        </div>
        <div class="selectize-items">
          <label v-if="multi && items.length" class="selectize-item">
            <span class="img img-xxs"><i class="fa fa-users font-lg text-grey"></i></span>
            <input type="checkbox" hidden @change="checkall">
            <span class="info font-xs ml-2">选择所有</span>
          </label>
          <label v-show="!items.length && keyword" class="selectize-item">
            <span class="info font-xs text-grey ml-2">没有找到该用户</span>
          </label>
          <label v-show="!items.length" class="selectize-item">
            <span class="img-btn text-white" title="创建用户">┼</span>
            <router-link to="/user/new" class="info font-xs text-blue ml-2">创建用户</router-link>
          </label>
          <label class="selectize-item" v-for="(item, index) in items" :key="item.id">
            <span class="img img-xxs rounded-circle" :style="{backgroundImage: 'url(' + item.avatar + ')'}"></span>
            <input name="item" :type="type" :checked="idarr.includes(item.id)" @change="change(item, $event)">
            <span class="info ml-2">
              <span class="font-xs">{{item.name}}</span>
              <span class="font-xs text-grey">{{item.phone}}</span>
            </span>
          </label>
        </div>
      </div>
    </div>
  </div>
</template>
<!--
  用户选择框
-->
<script>
  import {User} from '../resources'
  import {debounce} from '../misc/utils'
  import $ from 'jquery'
  var backup = [] // 初始用户列表备份, 当用户清空查询时, 直接使用备份数据而不再执行查询请求

  export default {
    name: 'selectize',
    props: ['value', 'multi'],
    data: () => ({
      selected: [], // 选中用户列表
      idarr: [],    // 选中用户ID列表
      items: [],    // 用户列表
      keyword: '',  // 搜索关键字
      loading: 1,   // 加载标识
      dirty: 0,     // 用户输入标识
      type: ''      // chckbox 或 radio
    }),
    created () {
      this.type = this.multi ? 'checkbox' : 'radio'
      this.check(this.value, true)
      console.log('created', this.value)
    },
    methods: {
      // 点击时触发的change事件
      change (item, event) {
        var checked = event.target.checked  // 是否选中
        var index = this.selected.findIndex(e => e.id === item.id)  // 选中目标在数组中的位置
        var include = index !== -1 // 是否在数组中

        if (this.multi) { // 多选时
          if (checked) {  // 选中时
            if (!include) this.selected.push(item)  // 不存在时添加
          } else {  // 反选时
            if (include) this.selected.splice(index, 1) // 存在时删除
          }
          this.idarr = this.selected.map(e => e.id)
          this.$emit('input', this.idarr)
        } else {  // 单选时
          this.selected = [item] // 直接覆盖
          this.$emit('input', item.id)  // 触发v-model事件设值
        }
      },
      // 加载默认已选中用户, initial为true时表示组件初始化时调用
      check (v, initial) {
        if (!v) return
        this.idarr = Array.isArray(v) ? v.map(Number) : [Number(v)]
        // 如果是初始化进入, 请求数据
        if (initial) {
          User.search({include: this.idarr}).then(response => {
            this.selected = response.data
            this.loading = 0
          })
        }
      },
      // 全选/反选
      checkall (e) {
        this.selected = e.target.checked ? this.items.slice() : []
        this.idarr = this.selected.map(e => e.id)
        this.$emit('input', this.idarr)
      },
      // DOM点击事件, 当点击元素处于Dropdown外部时隐藏Dropdown并移除事件监听
      handler (e) {
        if (!$.contains(this.$refs.dropdown, e.target)) {
          this.$refs.dropdown.classList.remove('show')
          document.removeEventListener('click', this.handler)
        }
      },
      // 显示菜单
      show () {
        if (this.$refs.dropdown.className.indexOf('show') === -1) {
          this.$refs.dropdown.classList.add('show')
          this.$refs.input.focus()
          document.addEventListener('click', this.handler)
          if (!this.keyword && !this.items.length) {
            this.paging()
          }
        }
      },
      // 查询(支持分页)
      paging: debounce(function () {
        this.loading = 1
        User.search({keyword: this.keyword}).then(response => {
          this.items = response.data
          this.loading = 0
          this.dirty = 0
          if (!backup.length) {
            backup = response.data
          }
        })
      }, 500)
    },
    watch: {
      keyword (v) {
        if (!v) { // 清空搜索框时, 已经备份过原始数据, 直接使用跳过查询
          this.items = this.selected.concat(backup.filter(e => {
            return !this.idarr.includes(e.id)
          }))
          return
        }
        this.dirty = 1
        this.paging()
      },
      value (v, o) {
        console.log('watch', v, o)
        this.check(v, !o)
      }
    }
  }
</script>
