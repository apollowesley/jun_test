<template>
  <div class="selectize" :class="['drop' + (dropup ? 'up' : 'down')]" ref="dropdown">
    <a data-toggle="dropdown" class="list-group-item text-primary font-sm b-0" v-if="action == 'add'">
      <span class="img-btn mr-2">┼</span> 创建角色
    </a>
    <a data-toggle="dropdown" class="text-link" v-else><i class="fa fa-ellipsis-h"></i> 更多</a>

    <form class="dropdown-menu mt-1" :class="['dropdown-menu-' + direction]" autocomplete="off" @submit.prevent="submit">
      <div class="dropdown-title"><i class="ti-angle-left left" v-show="action.match(/edit|del/)" @click="action = 'main'"></i>{{actions[action]}}</div>

      <div class="selectize-items p-3" v-if="action.match(/add|edit/)">
        <div class="form-group">
          <input class="form-control" name="name" v-model="me.name" v-validate="'required'" placeholder="名称" maxlength="32" tabindex="-1">
        </div>
        <div class="form-group">
          <textarea class="form-control" v-model="me.description" placeholder="备注" style="resize: none"></textarea>
        </div>
        <p class="text-grey">角色名称全局唯一不能重复。</p>
        <button class="btn btn-danger btn-block text-white" :disabled="errors.any() || loading">确定</button>
      </div>

      <div class="selectize-items" v-if="action == 'main'">
        <a class="selectize-item" @click.stop="action = 'edit'">编辑角色</a>
        <a class="selectize-item text-danger" @click="action = 'del'">删除角色</a>
      </div>

      <div class="selectize-items p-3" v-if="action == 'del'">
        <p>确定要永远删除这个角色吗？</p>
        <a class="btn btn-danger btn-block text-white" @click.stop="del">确定</a>
      </div>

    </form>
  </div>
</template>

<script>
  import {Role} from '../../resources'
  import toastr from '../../misc/toastr.esm'

  export default {
    name: 'rolemenu',
    props: [
      'direction',  // 菜单左右方向
      'dropup',     // 菜单向上
      'initial',    // 初始操作(覆盖action)
      'icon',       // 按钮图标
      'role'        // 当前角色
    ],
    data () {
      return {
        loading: false,
        actions: {        // 所有菜单操作
          main: '角色菜单',
          edit: '编辑角色',
          add: '创建角色',
          del: '删除角色'
        },
        action: 'main',    // 当前操作
        me: {
          description: '',
          name: ''
        }
      }
    },
    created () {
      this.action = this.initial || 'main'  // 覆盖action初始值
      if (this.role) {                      // copy角色数据
        this.me = Object.assign({}, this.role)
      }
    },
    methods: {
      submit (e) {  // 创建角色
        this.loading = true
        Role[this.action === 'edit' ? 'update' : 'save'](this.me).then(response => {
          this.loading = false
          if (!response.data.success) {
            return toastr.error(response.data.msg)
          }
          toastr.success('角色' + (this.action === 'edit' ? '编辑' : '创建') + '成功!')
          this.me = response.data.role
          this.$emit(this.action, this.me)
          this.close()
        }).catch(error => {
          this.loading = false
          console.log(error)
        })
      },
      del (exec) {
        Role.delete(this.role.id).then(response => {
          if (response.data.success) {
            toastr.success('角色删除成功!')
            this.$emit('del', this.role)
            this.close()
          }
        })
      },
      close () {
        this.$refs.dropdown.classList.remove('show')
      }
    },
    watch: {
      role (v) {
        this.me = Object.assign({}, v)
      }
    }
  }
</script>
