<template>
  <div>
    <ol class="breadcrumb">
      <li class="breadcrumb-item">系统管理</li>
      <li class="breadcrumb-item active">权限管理</li>
    </ol>

    <div class="card mx-3">
      <div class="p-3">
        <div class="text-muted mb-3">系统权限列表</div>
        <router-link to="/permission/new"><span class="img-btn">┼</span> 新增权限</router-link>
      </div>

      <div class="d-flex py-2 px-4 text-muted">
        <span class="mr-auto">权限名</span>
        <span class="column">编码</span>
        <span class="column text-center">操作</span>
      </div>
      <tree class="px-3" :treenodes="treenodes" @sort="sort"></tree>
    </div>
  </div>
</template>
<script>
  import {Permission} from '../../resources'
  import tree from './tree.vue'

  export default {
    data: () => ({
      loading: false,
      treenodes: []
    }),
    beforeRouteEnter (to, from, next) {
      Permission.tree().then(response => {
        next(vm => {
          vm.treenodes = response.data
          vm.$nextTick(() => vm.$emit('loaded'))
        })
      })
    },
    methods: {
      remove (id, index) {
      },
      sort (e) {
        var parameter = {
          id: e.item.dataset.id,
          from: e.oldIndex,
          to: e.newIndex
        }
        Permission.priority(parameter)
      }
    },
    components: {tree}
  }
</script>

<style>
  .column { width: calc(100vw / 5) }
</style>
