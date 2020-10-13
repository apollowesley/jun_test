<template>
  <div>
    <ol class="breadcrumb">
      <li class="breadcrumb-item">系统管理</li>
      <li class="breadcrumb-item active">字典信息</li>
    </ol>
    <div class="card mb-3 mx-3">
      <div class="layer-loading" v-show="loading"><i></i><i></i><i></i></div>
      <div class="text-muted p-3 pb-0">搜索字典信息.</div>
      <form class="form-inline p-3" autocomplete="off" @submit.prevent="paging">

        <div class="form-group mr-md-2">
          <input name="code" v-model="params.code" placeholder="字典编码" class="form-control">
        </div>

        <div class="form-group mr-md-2">
          <input name="description" v-model="params.description" placeholder="字典描述" class="form-control">
        </div>

        <button class="btn btn-outline-info"><i class="fa ti-search"></i> 搜索</button>
        <router-link class="btn btn-outline-info rad-30 ml-sm-auto" to="/dict/new"><i class="fa fa-plus-square"></i> 新增</router-link>
      </form>
      <table class="table table-hover table-advanced m-0">
        <thead>
        <tr>
          <th class="text-xs-center">#</th>
          <th>编码</th>
          <th class="hidden-md-down">描述</th>
          <th class="text-xs-center">操作</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(item, index) in page.items" :key="item.id">
          <td class="text-xs-center">{{page.firstElementIndex + index + 1}}</td>
          <td><router-link class="text-link" :to="'/dict/' + item.code">{{item.code}}</router-link></td>
          <td class="hidden-md-down">{{item.description}}</td>
          <td class="text-xs-center">
            <router-link class="d-none d-md-inline-block" :to="'/dict/' + item.code"><i class="fa fa-pencil"></i>编辑</router-link>
            <a @click="del(item.code, index)" class="text-danger"><i class="fa fa-trash-o"></i>删除</a>
          </td>
        </tr>
        </tbody>
      </table>
      <pagination class="py-3 justify-content-center" :page="page" @paging="paging"></pagination>
    </div>
  </div>
</template>
<script>
  import Pagination from '../../widgets/pagination.vue'
  import {Dict} from '../../resources'
  import {del} from '../../misc/utils'
  import swal from 'sweetalert'

  export default {
    data: () => ({
      params: {
        description: '',
        code: ''
      },
      loading: false,
      page: {}
    }),
    beforeRouteEnter (to, from, next) {
      Dict.query().then(response => {
        next(vm => {
          vm.page = response.data
          vm.$nextTick(() => vm.$emit('loaded'))
        })
      })
    },
    methods: {
      paging (pn) { Dict.paging(this, pn) },
      del (code, index) {
        del(() => {
          this.loading = true
          Dict.delete(code).then(response => {
            this.loading = false
            if (response.data.success) {
              swal('已删除!', '您指定的记录已被删除.', 'success')
              return this.page.items.splice(index, 1)
            }
            swal('删除失败!', response.data.msg, 'error')
          }, response => {
            swal('操作失败', response.data.msg, 'error')
            this.loading = false
          }).catch(error => {
            swal('操作失败', error, 'error')
            this.loading = false
          })
        })
      }
    },
    components: {Pagination}
  }
</script>
