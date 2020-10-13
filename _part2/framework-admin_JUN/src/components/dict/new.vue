<template>
  <div>
    <ol class="breadcrumb">
      <li class="breadcrumb-item">系统管理</li>
      <li class="breadcrumb-item">
        <router-link to="/dict">字典管理</router-link>
      </li>
      <li class="breadcrumb-item active">新增字典</li>
    </ol>
    <form autocomplete="off" @submit.prevent="submit" class="mx-3">
      <div class="card mb-3 p-3 b-0">
        <div class="mb-3">
          <h4 class="card-title">必填项</h4>
          <span class="text-muted">These fileds are <code>required</code>.</span>
        </div>
        <div class="row">
          <label class="form-group col-lg-7">字典编码
            <input name="code" v-model="code" title="字典编码" class="form-control" placeholder="字典编码"
                   v-validate="{rules: { required: true, min:2, max:32, remote: ['/dict/code-validation', 'code']} }">
            <span class="invalid-feedback">{{ errors.first('code') }}</span>
          </label>
        </div>
        <label class="form-group">字典描述<input name="description" v-model="description" class="form-control" placeholder="描述" maxlength="256"></label>
        <div class="form-group">
          <button class="btn btn-outline-info btn-add" type="button" @click="add"><i class="fa fa-plus-square"></i> 添加键值对</button>
        </div>
        <div class="row">
          <label class="col-5 col-md-5 col-lg-3">键</label>
          <label class="col-7 col-md-6 col-lg-4">值</label>
          <label class="d-none d-lg-block col-lg-4 col-xl-3">注释</label>
          <label class="d-none d-xl-block col-xl-1">排序</label>
          <label class="d-none d-md-block col-md-1 text-truncate">操作</label>
        </div>
        <div class="row" v-for="(item, index) in dicts">
          <div class="form-group col-5 col-md-5 col-lg-3">
            <input :name="'key' + index" v-model="item.key" class="form-control" title="键" v-validate="'required|max:32'" placeholder="键">
            <span class="invalid-feedback">{{ errors.first('key' + index) }}</span>
          </div>
          <div class="form-group col-7 col-md-6 col-lg-4">
            <input :name="'value' + index" v-model="item.value" class="form-control" title="值" v-validate="'required|max:64'" placeholder="值">
            <span class="invalid-feedback">{{ errors.first('value' + index) }}</span>
          </div>
          <div class="form-group d-none d-lg-block col-lg-4 col-xl-3">
            <input :name="'comment' + index" v-model="item.comment" class="form-control" maxlength="255" placeholder="注释">
          </div>
          <div class="form-group d-none d-xl-block col-xl-1">
            <input :name="'priority' + index" v-model="item.priority" class="form-control" type="number" max="99999" placeholder="排序">
          </div>
          <div class="form-group d-none d-md-block col-md-1">
            <button type="button" class="btn btn-outline-info" @click="remove(index)" :disabled="dicts.length <= 1"><i class="fa fa-trash-o"></i></button>
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
  import {showResponse} from '../../misc/utils'
  import {Dict} from '../../resources'

  export default {
    data: () => ({
      dicts: [{comment: '', priority: 0, value: '', code: '', key: ''}],
      description: '',
      code: ''
    }),
    beforeRouteEnter (to, form, next) {
      next(vm => vm.$nextTick(() => vm.$emit('loaded')))
    },
    methods: {
      add () {
        this.dicts.unshift({
          priority: this.dicts[0].priority + 1,
          comment: '',
          value: '',
          key: ''
        })
      },
      remove (index) { this.dicts.splice(index, 1) },
      submit () {
        this.$validator.validateAll().then(success => {
          if (!success) return
          Dict.save(this.$data).then(response => {
            showResponse(response, () => this.$router.back())
          })
        })
      }
    }
  }
</script>
