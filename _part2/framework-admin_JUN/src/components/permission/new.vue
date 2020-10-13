<template>
  <div>
    <ol class="breadcrumb">
      <li class="breadcrumb-item">系统管理</li>
      <li class="breadcrumb-item">
        <router-link to="/permission">权限管理</router-link>
      </li>
      <li class="breadcrumb-item active">新增权限</li>
    </ol>
    <form autocomplete="off" @submit.prevent="submit" class="mx-3">
      <div class="card mb-3 p-3 b-0">
        <div class="mb-3">
          <h4 class="card-title">必填项</h4>
          <span class="text-muted">These fileds are <code>required</code>.</span>
        </div>

        <div class="form-group row">
          <label class="col-form-label col-sm-2">名称</label>
          <div class="col-sm-10">
            <input name="name" class="form-control" v-model="permission.name" v-validate="'required'" placeholder="名称" title="权限名称">
            <span class="invalid-feedback">{{ errors.first('name') }}</span>
          </div>
        </div>

        <div class="form-group row">
          <label class="col-form-label col-sm-2">编码(权限唯一标识)</label>
          <div class="col-sm-10">
            <input name="code" v-model="permission.code" v-validate="'required|min:2|max:64'" class="form-control" title="编码" placeholder="权限编码">
            <span class="invalid-feedback">{{ errors.first('code') }}</span>
          </div>
        </div>

        <div class="form-group row">
          <label class="col-form-label col-sm-2">图标</label>
          <div class="col-sm-10 d-flex align-items-center">
            <span class="fa-2x text-themepink" :class="permission.icon ? permission.icon : 'fa fa-fonticons'"></span>
            <span class="mx-2 text-grey">{{permission.icon || '您可以为' + (permission.type == 1 ? '菜单' : '权限') + '指定一个图标:'}}</span>
            <span class="btn btn-sm btn-outline-info pointer" @click="modal.show()">选择图标</span>
            <span class="font-xs text-muted ml-2">(双击确认)</span>
          </div>
        </div>

        <div class="form-group row">
          <label class="col-form-label col-sm-2">指定父级权限</label>
          <div class="col-sm-10">
            <input class="form-control" id="parent" placeholder="父级权限" readonly>
            <jstree class="mt-0" attach="#parent" :treenodes="treenodes" v-model="permission.parent" dropdown="down"></jstree>
          </div>
        </div>

        <div class="form-group row align-items-center">
          <label class="col-form-label col-sm-2">添加为权限组</label>
          <div class="col-sm-10">
            <label class="ui-switch ui-switch-lg">
              <input type="checkbox" value="true" v-model="group" @change="change"><i></i>
            </label>
            <span class="align-middle" :class="[group ? 'text-cyan' : 'text-danger']">{{group ? '已开启' : '已关闭'}}</span>
            <span class="align-middle text-muted font-sm">(可快速添加一组默认的子权限, 可在数据字典<code>defaut_permission_group</code>中配置)</span>
          </div>
        </div>

        <div class="form-group row" v-show="group">
          <label class="col-form-label col-sm-2">默认子权限</label>
          <div class="col-sm-10">
            <label class="d-flex align-items-center hover px-1 rad-3" v-for="item in perms">
              <label class="md-checkbox"><input type="checkbox" name="perms" v-model="item.checked" value="true"><i class="bg-cyan"></i></label>
              <i class="font-xxxl text-center text-themepink" :class="item.comment" style="min-width: 2.2rem"></i>
              <span class="d-inline-block ">
                <span>{{item.value}}{{permission.name}}</span>
                <code>{{permission.code}}.{{item.key}}</code>
              </span>
            </label>
          </div>
        </div>

        <div class="form-group row">
          <label class="col-form-label col-sm-2">排序</label>
          <div class="col-sm-10">
            <input name="priority" v-model.number="permission.priority" class="form-control" type="number" max="9999" min="-9999" placeholder="排列顺序">
          </div>
        </div>

        <div class="form-group row">
          <label class="col-form-label col-sm-2">描述</label>
          <div class="col-sm-10">
            <textarea name="description" v-model="permission.description" class="form-control" placeholder="权限描述" rows="2"></textarea>
          </div>
        </div>
      </div>

      <div class="modal fade black-overlay" v-modal>
        <div class="modal-dialog modal-lg">
          <div class="modal-content">
            <div class="modal-header">
              <h4 class="modal-title">选择图标 <small class="text-muted ml-2">(双击图标确认)</small></h4>
              <a class="close ti-close" data-dismiss="modal"></a>
            </div>
            <div class="modal-body p-0" ref="mbody"></div>
            <div class="modal-footer">
              <span class="btn btn-secondary" data-dismiss="modal">Close</span>
            </div>
          </div>
        </div>
      </div>

      <div class="px-3">
        <a class="btn btn-outline-secondary rad-30 mr-2" href="javascript:history.back()"><i class="fa fa-arrow-circle-left"></i> 返回</a>
        <button class="btn btn-outline-info rounded" :disabled="errors.any()"><i class="fa fa-cloud-upload"></i> 提交</button>
      </div>

    </form>
  </div>
</template>


<script>
  import {Permission, Dict} from '../../resources'
  import {showResponse} from '../../misc/utils'
  import jstree from '../../widgets/jstree.vue'
  import axios from 'axios'

  export default {
    data: () => ({
      permission: {
        name: '',
        code: '',
        icon: '',
        parent: ''
      },
      treenodes: [],
      group: false,   // 是否以权限组添加(可指定一组子权限)
      perms: null,    // 子权限组
      modal: 0
    }),
    beforeRouteEnter (to, from, next) {
      Permission.jstree().then(tree => {
        next(vm => {
          vm.treenodes = tree.data
          vm.permission.parent = to.query.parent || ''
          vm.$nextTick(() => vm.$emit('loaded'))
        })
      })
    },
    methods: {
      change (e) {
        var checked = e.target.checked
        if (checked && !this.perms) {   // 第一次选中时从字典数据中加载默认子权限组
          Dict.bycode('default_permission_group').then(response => {
            this.perms = response.data
          })
        }
      },
      submit () {
        this.$validator.validateAll().then(success => {
          if (!success) return
          if (this.group && this.perms) {
            this.permission.children = this.perms.filter(p => p.checked).map(p => p.key)
          }
          Permission.save(this.permission).then(response => {
            showResponse(response, () => this.$router.back())
          })
        })
      }
    },
    watch: {
      'modal.visible' (v) {
        if (v && !this.modal.loaded) {
          axios.get('/static/html/ui.font-awesome-icons.html').then(response => {
            this.$refs.mbody.innerHTML = response.data
            this.$refs.mbody.addEventListener('dblclick', (e) => {
              var iconClass = e.target.className
              if (!iconClass.startsWith('fa')) return
              if (iconClass.startsWith('fa-hover')) {
                iconClass = e.target.querySelector('i.fa').className
              }
              this.permission.icon = iconClass
              this.modal.hide()
            })
            this.modal.loaded = true
          })
        }
      }
    },
    components: {jstree}
  }
</script>
