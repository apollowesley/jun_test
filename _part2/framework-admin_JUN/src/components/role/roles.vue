<template>
  <div class="d-flex flex-column">
    <ol class="breadcrumb">
      <li class="breadcrumb-item">系统管理</li>
      <li class="breadcrumb-item">角色管理</li>
    </ol>
    <div class="row no-gutters flex-grow">
      <div class="col-md-4 col-lg-3">
        <div class="bg-white h-100 mx-3 p-3 shadowed">
          <div class="input-holder b-ddd rad-3"><i class="ti-search"></i>
            <input name="keyword" v-model.trim="keyword" class="form-control" placeholder="过滤角色">
          </div>
          <div class="mt-3 px-3 bb-eee">角色列表</div>
          <div class="list-group stateful no-border mt-2">
            <router-link :to="'/roles/' + item.id" class="list-group-item" active-class="status-primary" :title="item.description" v-for="item in filtered" :key="item.id">
              <i class="ti-user mr-2"></i> {{item.name}}
            </router-link>
          </div>
          <role-menu dropup="true" initial="add" @add="add"></role-menu>
        </div>
      </div>

      <div class="col-md-8 col-lg-9 mt-3 mt-md-0">
        <div class="bg-white h-100 mx-3 ml-md-0 shadowed">
          <div class="d-flex align-items-center p-4 text-themedark">
            <div class="font-xl mr-auto" v-if="role">{{role.name}}
              <span class="text-muted hidden-md-down" v-show="role.description">({{role.description}})</span>
            </div>
            <div class="font-xl text-muted mr-auto" v-else>没有角色被选中</div>
            <label class="md-switch font-sm d-none d-md-block">
              <input type="checkbox" v-model="style" true-value="treeview" false-value="treelist">
              <i class="bg-warning"></i>
              <span class="text-grey">{{style == 'treeview' ? '样式二' : '样式一'}}</span>
            </label>
            <div class="font-sm" v-if="role">
              <a class="ml-3 text-link" @click="save"><i class="ti-upload"></i> 保存权限更改</a>
              <role-menu class="ml-3" direction="right" name="更多" :role="role" @edit="edit" @del="del"></role-menu>
            </div>
          </div>
          <div class="bt-eee p-3">
            <component :is="style" :treenodes="perms" :checked="owns"></component>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
  import {Role, Permission} from '../../resources'
  import treelist from '../../widgets/treelist.vue'
  import treeview from '../../widgets/treeview.vue'
  import RoleMenu from './role-menu.vue'
  import toastr from '../../misc/toastr.esm'

  export default {
    data: () => ({
      style: 'treelist',  // 树形样式
      loading: false, // 加载指示
      keyword: '',    // 搜索关键字
      roles: [],      // 所有角色
      perms: [],      // 权限树
      owns: [],       // 当前激活角色已拥有的权限ID集合
      role: ''        // 当前激活角色
    }),
    beforeRouteEnter (to, from, next) {
      var id = to.params.id
      var requests = [Role.all(), Permission.tree(), id ? Role.owns(id) : []]
      Promise.all(requests).then(([roles, perms, owns]) => {
        next(vm => {
          vm.roles = roles.data
          vm.perms = perms.data
          vm.owns = owns.data
          if (id) {
            vm.role = vm.roles.find(r => r.id === to.params.id)
          }
          vm.$nextTick(() => {
            vm.$emit('loaded')
            vm.loading = false
          })
        })
      })
    },
    beforeRouteUpdate (to, from, next) {
      if (to.params.id) {
        this.role = this.roles.find(r => r.id == to.params.id)
        return Role.owns(to.params.id).then(response => {
          this.owns = response.data
          this.$emit('loaded')
          next()
        })
      }
      this.role = null
      this.$emit('loaded')
      next()
    },
    methods: {
      // 保存权限改动. TODO 权限树通是过组件递归实现, 不能直接绑定到owns数组, 这里直接通过DOM查询拿到所有选中的权限, 是否有办通过vue实现?
      save () {
        var items = []
        document.querySelectorAll('[name=items]').forEach(e => {
          e.checked && items.push(e.value)
        })
        Role.put_perms({id: this.role.id, perms: items}).then(response => {
          if (response.data.success) {
            return toastr.success('角色权限更新成功!')
          }
          toastr.error(response.data.msg)
        })
      },
      add (role) {  // 角色添加成功回调
        this.roles.unshift(role)
      },
      edit (role) { // 角色修改成功回调
        var index = this.roles.findIndex(r => r.id === role.id)  // 找到元素坐标
        this.roles.splice(index, 1, role) // 替换元素
        this.role = role  // 替换当前角色
      },
      del (role) {  // 角色删除回调
        this.roles = this.roles.filter(r => r.id !== role.id)   // 从角色列表移除角色
        this.role = null  // 置空当前角色
      }
    },
    computed: {
      filtered () {
        return this.roles.filter(r => {
          return r.name.indexOf(this.keyword) !== -1
        })
      }
    },
    components: {RoleMenu, treelist, treeview}
  }
</script>
