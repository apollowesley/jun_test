<template>
  <div>
    <ol class="breadcrumb">
      <li class="breadcrumb-item">系统管理</li>
      <li class="breadcrumb-item active">部门管理</li>
    </ol>
    <div class="card mx-3">
      <div class="layer-loading" v-show="loading"><i></i><i></i><i></i></div>
      <div class="text-muted p-3 pb-0">系统部门树.</div>
      <form action="/permission" class="form-inline px-3 mb-3" autocomplete="off" @submit.prevent="paging">
        <div class="btn-group">
          <button type="button" class="btn btn-outline-info" title="expandAll" @click="toggle">展开全部</button>
          <button type="button" class="btn btn-outline-info" title="collapseAll" @click="toggle">折叠全部</button>
        </div>
        <router-link class="btn btn-outline-info rad-30 ml-auto" to="/department/new"><i class="fa fa-plus"></i> 新增</router-link>
      </form>

      <table class="table table-advanced table-hover treetable" ref="treetable">
        <thead>
        <tr>
          <th>部门名称</th>
          <th class="d-none d-md-table-cell">负责人</th>
          <th class="d-none d-md-table-cell">更新时间</th>
          <th class="text-xs-center">操作</th>
        </tr>
        </thead>
        <tbody>
        <tr :data-id="item.id" :data-parent="item.parent" v-for="(item, index) in items" :key="item.id">
          <td><a class="text-link" @click.stop="edit(item.id)">{{item.name}}</a></td>
          <td class="d-none d-md-table-cell">{{item.leaderName}}</td>
          <td class="d-none d-md-table-cell">{{item.updated}}</td>
          <td class="text-xs-center">
            <router-link :to="'/department/' + item.id"><i class="fa fa fa-pencil"></i>编辑</router-link>
            <router-link :to="'/department/new?parent=' + item.id"><i class="fa fa-angle-double-down"></i>添加下级</router-link>
            <a class="text-danger" @click="remove(item.id, index)"><i class="fa fa-trash-o"></i>删除</a>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>
<script>
  import {del, treeify} from '../../misc/utils'
  import {Department} from '../../resources'
  import storage from 'localStorage'
  import $ from 'jquery'

  var stored = storage.getItem('departmentExpanded')  // get stored expanded nodes
  var expanded = stored ? JSON.parse(stored) : {}

  export default {
    data: () => ({
      loading: false,
      items: []
    }),
    beforeRouteEnter (to, from, next) {
      Department.treetable().then(response => {
        next(vm => {
          vm.items = response.data
          vm.$nextTick(() => {
            vm.$treetable = treeify(vm.$refs.treetable, expanded)
            vm.$emit('loaded')
          })
        })
      })
    },
    methods: {
      toggle (e) {
        var $this = $(e.target)
        $this.addClass('active').siblings().removeClass('active')
        this.$treetable[e.target.title]()
      },
      edit (id) {
        this.$router.push('/department/' + id)
      },
      remove (id, index) {
        del(this, Department, id, this.items, index)
      }
    },
    destroyed () {
      storage.setItem('departmentExpanded', JSON.stringify(expanded))
    }
  }
</script>
