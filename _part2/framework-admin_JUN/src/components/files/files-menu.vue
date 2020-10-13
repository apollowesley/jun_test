<template>
  <div class="selectize dropdown" ref="dropdown">
    <a data-toggle="dropdown" class="text-link"><i class="fa fa-ellipsis-h"></i> 更多</a>
    <div class="dropdown-menu dropdown-menu-right mt-1">
      <div class="dropdown-title"><i class="ti-angle-left left" v-show="action != 'main'" @click="action = 'main'"></i>{{actions[action]}}</div>
      <div class="selectize-items" v-if="action == 'main'">
        <a class="selectize-item"><i class="mr-2 ti-files"></i>复制文件</a>
        <a class="selectize-item"><i class="mr-2 ti-link"></i>复制文件链接</a>
        <a class="selectize-item"><i class="mr-2 ti-new-window"></i>共享文件</a>
        <a class="selectize-item"><i class="mr-2 ti-trash"></i>删除文件</a>
      </div>
      <div class="selectize-items p-3" v-if="action == 'del'">
        <p>删除部门会同时删除其子部门，部门中的成员不会被删除。</p>
        <a class="btn btn-danger btn-block text-white" @click.stop="del">确定</a>
      </div>
    </div>
  </div>
</template>

<script>
  import {Department} from '../../resources'
  import toastr from '../../misc/toastr.esm'

  export default {
    name: 'usermenu',
    props: ['dept'],
    data () {
      return {
        // current: User.current(),
        actions: {
          main: '文件菜单',
          del: '删除部门'
        },
        action: 'main'
      }
    },
    methods: {
      del (exec) {
        Department.delete(this.dept).then(response => {
          if (response.data.success) {
            toastr.success('部门删除成功!')
            this.$emit('del', this.dept)
            this.close()
          }
        })
      },
      close () {
        this.$refs.dropdown.classList.remove('show')
        this.action = 'main'
      }
    }
  }
</script>
