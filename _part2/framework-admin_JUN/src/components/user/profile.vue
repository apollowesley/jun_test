<template>
  <div>
    <div class="p-3">
      <div class="h3 m-0">个人资料</div>
    </div>
    <form class="row p-3 bg-white" autocomplete="off" @submit.prevent="done">
      <div class="col-md-3">

        <div class="card mb-3 img-hover">
          <label class="img-mask" id="img-mask">
            <i class="fa fa-camera"></i> 点击上传头像
            <input type="file" hidden @change="change">
          </label>
          <img class="card-img-top img-fluid user-thumb" alt="用户头像" :src="user.avatar">
          <div class="progress">
            <div class="progress-bar progress-xs" style="width: 25%"></div>
          </div>
          <div class="card-body">
            <h3 class="d-inline-block align-middle">{{user.name}}</h3>
            <span class="badge badge-default">{{user.roleName}}</span>
            <div class="font-sm text-muted">最近登录为 {{user.visited}}</div>
          </div>
        </div>
      </div>

      <div class="col-md-9">
        <h4 class="text-themedark">个人信息</h4>
        <div class="sep sep-dashed"></div>
        <label class="form-group d-block">呢称
          <input name="nick" v-model="user.nick" class="form-control" placeholder="真实姓名"/>
        </label>
        <label class="form-group d-block">密码 (无需修改请留空)
          <input type="password" name="pass" v-model="user.pass" class="form-control" minlength="5" maxlength="32" placeholder="密码">
        </label>
        <label class="form-group d-block">邮箱
          <input name="email" type="email" v-model="user.email" v-validate="'email'" class="form-control" title="邮箱地址" placeholder="请输入邮箱地址">
          <span class="invalid-feedback">{{ errors.first('email') }}</span>
        </label>
        <label class="form-group d-block">手机号码
          <input name="phone" v-model="user.phone" v-validate="{rules: { required: true, regex: /^\d{11}$/} }" class="form-control" title="手机号码" placeholder="手机号码">
          <span class="invalid-feedback">{{ errors.first('phone') }}</span>
        </label>
        <div class="form-group d-block">性别
          <div class="radio mb-0 mt-2">
            <label class="ui-radio"><input type="radio" name="gender" value="1" v-model="user.gender"><i class="text-primary"></i> 男</label>
            <label class="ui-radio"><input type="radio" name="gender" value="0" v-model="user.gender"><i class="text-primary"></i> 女</label>
          </div>
        </div>
        <label class="form-group d-block">
          <label>生日</label>
          <date-select v-model="user.birthday"></date-select>
        </label>
        <a class="btn btn-outline-secondary rad-30 mr-2" href="javascript:history.back()"><i class="fa fa-arrow-circle-left"></i> 返回</a>
        <button class="btn btn-outline-info rounded" :disabled="errors.any()"><i class="fa fa-cloud-upload"></i> 提交</button>
      </div>
    </form>
  </div>
</template>

<script>
  import DateSelect from '../../widgets/dateselect.vue'
  import {User} from '../../resources'
  import toastr from '../../misc/toastr.esm'

  export default {
    data: () => ({
      user: {},
      role: {}
    }),
    beforeRouteEnter (to, from, next) {
      User.profile().then(response => {
        next(vm => {
          vm.user = response.data.user
          vm.$nextTick(() => vm.$emit('loaded'))
        })
      })
    },
    methods: {
      change (e) {
        console.log(e)
      },
      done () {
        User.profile(this.user).then(response => {
          if (response.data.success) {
            toastr.success('用户信息更新成功!')
          }
        })
      }
    },
    components: {DateSelect}
  }
</script>

<style scoped>
  .img-mask:hover {
    background: rgba(0, 0, 0, .6);
  }
  .img-mask:hover, .img-hover:hover .img-mask {
    opacity: 1;
    visibility: visible;
  }
  .img-mask {
    color: #fff !important;
    border-radius: .25rem .25rem 0 0;
    background: rgba(0, 0, 0, .3);
    width: 100%;
    padding: 10px;
    margin: 0;
    opacity: 0;
    visibility: hidden;
    text-align: center;
    position: absolute;
    transition: all .3s ease-in-out;
  }
</style>
