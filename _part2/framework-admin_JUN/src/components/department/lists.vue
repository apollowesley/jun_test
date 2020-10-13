<template>
  <div>
    <ol class="breadcrumb">
      <li class="breadcrumb-item">系统管理</li>
      <li class="breadcrumb-item active">部门管理</li>
    </ol>

    <div class="row no-gutters">
      <div class="col mx-3">
        <div class="bg-white h-100 p-3 shadowed">
          <div class="d-flex align-items-center py-3">
            <div class="mr-auto">部门列表 <span class="text-muted font-xs">(拖动进行排序)</span></div>
            <router-link class="font-sm" to="/department/new"><span class="img-btn">┼</span> 添加下级部门</router-link>
            <router-link class="font-sm ml-2" to="/department/new"><span class="img-btn">┼</span> 新增部门</router-link>
          </div>

          <tree :treenodes="treenodes" @active="active"></tree>

        </div>
      </div>

      <div class="col-lg-6 mx-3 ml-lg-0 mt-3 mt-lg-0 ">
        <div class="layer-loading" v-show="loading"><i></i><i></i><i></i></div>
        <div class="bg-white h-100 p-3 shadowed">
          <div class="d-flex justify-content-between py-3">
            <span v-if="department.name">{{department.name}}</span>
            <span v-else class="text-muted text-danger">没有选择部门</span>
            <router-link class="font-sm ml-2" to="/department/new"><span class="img-btn">┼</span> 新增部门</router-link>
          </div>

          <form autocomplete="off" @submit.prevent="submit">
            <div class="form-group row">
              <label class="col-form-label col-sm-3">部门名称</label>
              <div class="col-sm-9">
                <input name="name" v-model="department.name" v-validate="'required'" title="部门名称" class="form-control" maxlength="32">
                <span class="invalid-feedback">{{ errors.first('name') }}</span>
              </div>
            </div>
            <div class="form-group row">
              <label class="col-form-label col-sm-3">负责人</label>
              <div class="col-sm-9 d-flex align-items-center">
                <selectize v-model="department.leader"></selectize>
              </div>
            </div>
            <div class="form-group row">
              <label class="col-form-label col-sm-3">上级部门</label>
              <div class="col-sm-9 form-control-static">上级部门</div>
            </div>
            <button class="btn btn-primary" :disabled="errors.any()"><i class="fa fa-cloud-upload"></i> 保存</button>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
  import selectize from '../../widgets/selectize.vue'
  import {Department} from '../../resources'
  import toast from '../../misc/toastr.esm'
  import storage from 'localStorage'
  import tree from './tree.vue'

  // import Nestable from '../../misc/nestable.esm'

  var stored = storage.getItem('departmentExpanded')  // get stored expanded nodes
  var expanded = stored ? JSON.parse(stored) : {}

  export default {
    name: 'departments',
    data: () => ({
      department: {},
      treenodes: [],
      loading: false
    }),
    beforeRouteEnter (to, from, next) {
      Department.tree().then(response => {
        next(vm => {
          vm.treenodes = response.data
          vm.$nextTick(() => vm.$emit('loaded'))
        })
      })
    },
    methods: {
      remove (id, index) {
      },
      active (item) {
        this.department = item
      },
      sort (e) {
        var parameter = {
          id: e.item.dataset.id,
          from: e.oldIndex,
          to: e.newIndex
        }
        Department.priority(parameter).then(response => {
          if (response.data.success) {
            toast.success('顺序修改成功')
          }
        })
      }
    },
    destroyed () {
      storage.setItem('departmentExpanded', JSON.stringify(expanded))
    },
    components: {selectize, tree}
  }
</script>
