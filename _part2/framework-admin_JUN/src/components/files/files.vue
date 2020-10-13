<!--
  文件库 (上传流程: 文件 => 本地qiniu4js => 七牛)
-->
<template>
  <div class="d-flex flex-column">
    <ol class="breadcrumb">
      <li class="breadcrumb-item">系统管理</li>
      <li class="breadcrumb-item">文件库</li>
    </ol>
    <div class="bg-white flex-grow shadowed relative mx-3" ref="view">
      <div class="layer-loading" v-show="loading"><i></i><i></i><i></i></div>
      <div class="flex-center justify-content-between p-3">
        <ol class="breadcrumb path p-1">
          <router-link v-if="parents.length" class="breadcrumb-item" active-class="" to="/files">文件库</router-link>
          <li v-else class="breadcrumb-item">文件库</li>
          <template v-for="(item, index) in parents">
            <li class="breadcrumb-item" :key="item.id" v-if="index == parents.length - 1">{{item.name}}</li>
            <router-link class="breadcrumb-item" active-class="" :to="'/files/' + item.id" :key="item.id" v-else>{{item.name}}</router-link>
          </template>
        </ol>
        <div class="font-sm pr-3 d-none d-md-block">
          <a class="text-link mr-3" @click="createFolder"><span class="img-btn">┼</span>&nbsp;创建文件夹</a>
          <label class="text-link mr-3 mb-0" ref="select" @click="choose"><span class="img-btn">┼</span> 上传</label>
          <file-menu></file-menu>
        </div>
      </div>

      <div class="sep mx-3 my-0"></div>
      <div class="flex-center col-header text-grey mx-3 py-3">
        <div class="col check">
          <label class="md-checkbox"><input type="checkbox" @change="check"><i class="bg-themedark"></i></label>
        </div>
        <div class="flex-center font-sm" v-if="checked.length">
          <div class="mx-3">已选择 {{checked.length}} 项</div>
          <a class="text-link mx-3"><i class="ti-arrow-circle-down"></i> <span>下载</span></a>
          <a class="text-link mx-3"><i class="ti-split-h"></i> <span>移动</span></a>
          <a class="text-link mx-3"><i class="ti-files"></i> <span>复制</span></a>
          <a class="text-link mx-3" v-show="checked.length == 1"><i class="ti-arrow-circle-up "></i> <span>更新</span></a>
          <a class="text-link mx-3" v-show="checked.length == 1"><i class="ti-pencil"></i> <span>重命名</span></a>
          <a class="text-link mx-3" @click="del(null)"><i class="ti-trash"></i> <span>删除</span></a>
        </div>
        <div class="flex-center filecontent no-gutters" v-else>
          <a class="col text-link name" :class="{sortable: params.sort == 'name', asc: params.asc}" @click="sort('name')">文件名 <i></i></a>
          <a class="col text-link size d-none d-md-block" :class="{sortable: params.sort == 'size', asc: params.asc}" @click="sort('size')">大小 <i></i></a>
          <div class="col creator hidden-lg-down">创建者</div>
          <a class="col text-link updated hidden-lg-down" :class="{sortable: params.sort == 'updated', asc: params.asc}" @click="sort('updated')">更新时间 <i></i></a>
          <div class="col text-right">
            <a class="hint-top" aria-label="列表视图"><i class="text-link mx-1 active ti-view-list-alt"></i></a>
            <a class="hint-top" aria-label="缩略图模式"><i class="text-link mx-1 ti-view-grid"></i></a>
          </div>
        </div>
      </div>
      <div class="sep mx-3 my-0"></div>
      <ul class="list-unstyled text-grey filelist" ref="list">

        <!-- 创建文件夹项 -->
        <li class="filerow flex-center px-3" v-show="create">
          <div class="col check"><label class="md-checkbox"><input type="checkbox" disabled><i class="bg-themedark"></i></label></div>
          <div class="flex-center filecontent no-gutters">
            <div class="col name text-default">
              <span class="fa fa-folder img-sm"></span>
              <input placeholder="按 回车(Enter) 创建文件夹" ref="input" @keyup="submit(1, $event)" @focusout="submit(0, $event)">
            </div>
            <div class="col size d-none d-md-block">-</div>
            <div class="col creator hidden-md-down">-</div>
            <div class="col hidden-md-down">-</div>
          </div>
        </li>

        <li class="filerow flex-center visible px-3" v-for="(item, index) in page.items" :key="item.id">
          <div class="col check">
            <label class="md-checkbox"><input type="checkbox" :value="item.id" name="files" @change="check"><i class="bg-themedark"></i></label>
          </div>
          <div class="flex-center filecontent no-gutters" @click="open(item)">
            <div class="col name text-default">
              <span class="fa fa-folder img-sm" v-if="item.folder == 1"></span> <!-- 文件夹 -->
              <span class="img img-sm" :style="{backgroundImage: 'url(' + item.url + ')'}" v-else-if="item.mime.startsWith('image/')"></span> <!-- 图片 -->
              <img class="img-sm" src="/static/img/file_ps.svg" v-else> <!-- 其它类型文件 -->
              <input v-if="item.edit" :value="item.name" @keyup="submit(1, $event, item)" @focusout="submit(0, $event, item)" placeholder="文件名">
              <span v-else class="ml-3">{{item.name}}</span>
            </div>
            <div class="col size d-none d-md-block" v-if="item.folder == 1">-</div>
            <div class="col size d-none d-md-block" v-else>{{item.size | filesize}}</div>
            <div class="col creator hidden-lg-down">backflow</div>
            <div class="col updated hidden-lg-down">{{item.updatedTime | timepast}}</div>
            <div class="col invisible d-none d-md-block">
              <a class="text-link mx-2 hint-top" aria-label="下载" :href="item.url" :download="item.name"><i class="ti-arrow-circle-down"></i></a>
              <a class="text-link mx-2 hint-top" aria-label="更新" @click="choose(item)"><i class="ti-arrow-circle-up "></i></a>
              <a class="text-link mx-2 hint-top" aria-label="移动"><i class="ti-split-h"></i></a>
              <a class="text-link mx-2 hint-top" aria-label="重命名" @click.stop="edit(item, $event)"><i class="ti-pencil"></i></a>
              <a class="text-link mx-2 hint-top" aria-label="更多操作"><i class="ti-angle-down"></i></a>
            </div>
          </div>
        </li>
      </ul>
      <pagination class="py-3 justify-content-center" :page="page" @paging="paging"></pagination>
    </div>

    <div class="upload-queue-wrapper" :class="{minimize}" v-show="showQueue">
      <div class="card">
        <div class="card-header text-right pointer" @click="minimize = !minimize">
          <span class="float-left" v-if="queueing && queue.length">正在上传 1 / {{queue.length}}</span>
          <span class="float-left" v-else>上传完成</span>
          <a class="text-link ml-2" :class="[minimize ? 'ti-arrow-up' : 'ti-arrow-down']"></a>
          <a class="text-link ml-2 ti-close" @click="showQueue = false"></a>
        </div>
        <ul class="upload-queue">
          <li class="upload-item flex-center" v-for="(task, index) in queue">
            <span class="img img-sm" :style="{backgroundImage: 'url(' + task.url + ')'}" v-if="task.file.type.startsWith('image/')"></span>
            <img class="img-sm" src="/static/img/file_ps.svg" v-else>
            <div class="flex-fill ml-2">
              <div class="flex-center text-truncate">
                <span class="upload-name">{{task.file.name}}</span>
                <span class="upload-info">({{task.file.size * task.progress / 100 | filesize}}/{{task.file.size | filesize}})</span>
              </div>
              <div class="progress progress-xxs">
                <span class="progress-bar bg-skyblue" :style="{width: task.progress + '%'}"></span>
              </div>
            </div>
            <i class="upload-operation text-link ti-check" v-if="task.isFinish"></i>
            <a class="upload-operation text-link ti-close" v-else @click="abort(task, index)"></a>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
  import {filesize, shorten, closest, del} from '../../misc/utils'
  import Pagination from '../../widgets/pagination.vue'
  import {FileMapping, baseURL} from '../../resources'
  import {UploaderBuilder} from 'qiniu4js'
  import FileMenu from './files-menu.vue'
  import toastr from '../../misc/toastr.esm'
  import swal from 'sweetalert'

  var maxsize = 1024 * 1024 * 10   // 最大10MB
  var uploader      // uploader实例
  var timestamp = 0 // 事件时间戳

  export default {
    components: {FileMenu, Pagination},
    data: () => ({
      showQueue: false,   // 显示上传队列
      minimize: true,     // 最小化上传队列
      queueing: false,    // 是否正在上传
      loading: false,     // 加载指示
      parents: [],        // 当前文件夹路径数组
      checked: [],
      queue: [],
      create: false,      // 创建文件夹
      params: {
        parent: 0,        // 父文件 (当前目录)
        sort: 'updated',  // 排序字段
        asc: false        // 是否正序
      },
      page: {}
    }),
    beforeRouteEnter (to, from, next) {
      var parent = to.params.parent || 0
      var promise = parent ? FileMapping.parents(parent) : Promise.resolve(parent)
      Promise.all([FileMapping.query({parent, sort: 'updated'}), promise]).then(([paging, parents]) => {
        next(vm => {
          vm.params.parent = parent
          vm.parents = parents ? parents.data : []
          vm.page = paging.data
          vm.$nextTick(() => {
            vm.loading = false
            vm.$emit('loaded')
          })
        })
      })
    },
    beforeRouteUpdate (to, from, next) {
      var parent = to.params.parent || 0
      var promise = parent ? FileMapping.parents(parent) : Promise.resolve(parent)
      Promise.all([FileMapping.query({parent, sort: 'updated'}), promise]).then(([paging, parents]) => {
        this.params.parent = parent
        this.parents = parents ? parents.data : []
        this.page = paging.data
        this.$nextTick(() => {
          this.loading = false
          this.$emit('loaded')
          next()
        })
      })
    },
    created () {
      var that = this
      uploader = new UploaderBuilder()
        .tokenUrl(baseURL + '/filemapping/uptoken') // 设置token获取URL：客户端向该地址发送HTTP GET请求, 若成功，服务器端返回{"uptoken": 'i-am-token'}。
        .interceptor({      // 任务拦截器
          onIntercept (task) {  // 拦截任务,返回true，任务将会从任务队列中剔除，不会被上传
            if (task.file.size > maxsize) {
              return toastr.warning('请上传小于' + filesize(maxsize) + '的文件')
            }
          }
        })
        .listener({
          onReady (tasks) { // 该回调函数在图片处理前执行, 也就是说task.file中的图片都是没有处理过的, 选择上传文件确定后,该生命周期函数会被回调。
            that.showQueue = true
            tasks.forEach(task => {
              var file = task.file
              task.key = shorten() + file.name.substr(file.name.lastIndexOf('.'))
              task.url = ''
              if (file.type.startsWith('image/')) { // 如果为图片文件, 读取DataURL显示图片
                var reader = new FileReader()
                reader.readAsDataURL(file)
                return reader.onload = (e) => {
                  task.url = e.target.result
                  that.queue.unshift(task)
                }
              }
              that.queue.unshift(task)
            })
          },
          onStart (tasks) {  // 所有内部图片任务处理后执行, 开始上传
            that.minimize = false
            that.queueing = true
          },
          // onTaskProgress (task) { console.info(task) }, // 每一个任务的上传进度,通过`task.progress`获取
          onTaskRetry (task) { toastr.warning(task.file.name + ' 第' + task.retry + '次重试失败') }, // 开始重传
          onTaskFail (task) { toastr.warning(task.file.name + ' 上传失败') }, // 一个任务在经历重传后依然失败后回调此函数
          onFinish (tasks) { that.queueing = false }, // 所有任务结束后回调，注意，结束不等于都成功，该函数会在所有HTTP上传请求响应后回调(包括重传请求)。
          onTaskSuccess (task) {   // 一个任务上传成功后回调
            FileMapping.save({
              parent: that.params.parent,
              name: task.file.name,
              mime: task.file.type,
              size: task.file.size,
              hash: task.result.hash,
              key: task.result.key
            }).then(response => {
              Object.assign(task, response.data.file)
              that.page.items.unshift(task)
            })
          }
        }).build()
    },
    methods: {
      choose (update) {
        console.log(update)
        uploader.chooseFile()
      },  // 开始选择文件(上传)
      abort (task, index) { // 取消上传
        this.queue.splice(index, 1)
        // TODO 上传SDK暂时不支持取消, 因此上传中删除任务后台会继续上传至完成
        // task.xhr.abort()
      },
      check (e) {
        var files  = this.$refs.list.querySelectorAll('input[name=files]'),
            target = e.target
        var checked = []
        for (var i = 0; i < files.length; i++) {
          var f = files[i]
          if (target.name) {
            if (f.checked) {
              checked.push(f.value)
            }
          } else if (f.checked = target.checked) {
            checked.push(f.value)
          }
        }
        this.checked = checked
      },
      paging (pn) {
        FileMapping.paging(this, pn).then(() => {
          this.$refs.view.querySelectorAll('input[type=checkbox]').forEach(f => f.checked = false)
          this.checked = []
        })
      },
      createFolder () {
        var input = this.$refs.input
        this.create = true
        input.value = ''
        this.$nextTick(() => {
          input.focus()
        })
      },
      edit (file, e) {  /* 点击修改图标, 显示输入框并获取焦点 */
        if (!file.folder) {
          var dot = file.name.lastIndexOf('.')
          if (dot >= 0) { // 隐藏扩展名
            file.name = file.name.substring(0, dot)
          }
        }
        file.edit = true
        this.$nextTick(() => {
          closest(e.target, '.filecontent').querySelector('input').focus()
        })
      },
      submit (keyup, e, file) { // 提交文件修改, file为空时表示创建文件夹, 否则为重命名
        var temp = {
          parent: this.params.parent,
          name: e.target.value.trim(),
          folder: 1 // 标识为文件夹
        }
        var create = false          // 是否执行创建请求
        if (keyup) {                // keyup事件
          if (e.keyCode === 13) {   // 按下Enter键, 执行创建
            create = true
          } else if (e.keyCode === 27) {  // 按下Esc键, 撤消创建
            this.create = false
          }
        } else {                    // 失焦事件
          if (temp.name) {      // 失焦但有输入值, 执行创建
            create = true
          } else {
            this.create = false     // 无输入值，撤消创建
            if (file) {             // 撤消重命名时恢复扩展名
              if (file.ext) {
                file.name += '.' + file.ext
              }
              file.edit = false
            }
          }
        }
        if (create) {
          if (e.timeStamp - timestamp < 1000) {   // 按Enter键后会立即触发失焦事件, 这里进行过滤
            return console.log('略过重复创建')
          }
          timestamp = e.timeStamp
          if (file) { // 重命名
            if (file.name == temp.name) {  // 同名, 跳过修改
              file.edit = false
              return
            }
            file.name = temp.name
            if (file.ext) { // 恢复扩展名
              file.name += '.' + file.ext
            }
          }
          FileMapping.save(file || temp).then(response => {
            if (response.data.success) {
              this.create = false
              if (file) {
                file.edit = false
                toastr.success('重命名成功')
              } else {
                this.page.items.unshift(response.data.file)
                toastr.success('文件夹创建成功')
              }
            }
          })
        }
      },
      sort (sort) {
        if (this.params.sort === sort) {
          this.params.asc = !this.params.asc
        } else {
          this.params.asc = true
        }
        this.params.sort = sort
        this.paging()
      },
      open (file) { // 点击(打开)文件
        if (file.edit) return // 编辑时忽略
        if (file.folder) {    // 打开文件夹
          this.$router.push('/files/' + file.id)
        } else {

        }
      },
      move () { /* 移动文件 */ },
      del (file, e) { /* 删除文件 */
        var params = {items: this.checked}
        if (file) {

        } else {
          del(() => {
            FileMapping.http.delete('/filemapping', {params}).then(response => {
              if (response.data.success) {
                swal('已删除!', '删除成功.', 'success')
                this.paging()
              }
            }).catch(error => {
              swal.close(error)
            })
          })
        }
      }
    }
  }
</script>

<style>
  .breadcrumb.path a.breadcrumb-item { color: #383838 }
  .breadcrumb.path a.breadcrumb-item:hover { color: #0c92f3 }
  .col.name, .upload-name {
    text-overflow: ellipsis;
    white-space: nowrap;
    overflow: hidden;
  }
  .col-header [class^="ti-"] { vertical-align: top }
  .col-header span { vertical-align: text-bottom }
  .col.check { max-width: 50px; text-align: center }
  .col.name {
    display: -webkit-flex;
    display: -ms-flexbox;
    display: flex;
    -webkit-align-items: center;
    -ms-flex-align: center;
    align-items: center;
    padding-right: 1rem;
    min-width: 45%;
  }
  .col.name .fa-folder { font-size: 2.3rem; color: #3da8f5 }
  .col.name input {
    background-color: #FFFFFF;
    border: 1px solid #D9D9D9;
    border-radius: 3px;
    margin-left: .625rem;
    line-height: 28px;
    padding: 0 .3125rem;
    outline: 0;
    flex: 1;
  }
  .col.size,
  .col.updated { max-width: 6rem }
  .col.creator { max-width: 8rem }
  .col.invisible { text-align: center }
  .filelist li { transition: background-color 218ms }
  .filelist li:hover { background: #f5f5f5 }
  .filelist li .col, .col-header .col { white-space: nowrap }
  .filelist .filecontent {
    border-bottom: 1px solid #f5f5f5;
    padding-bottom: .5rem;
    padding-top: .5rem;
    cursor: pointer;
  }
  .filecontent {
    padding-right: 1rem;
    padding-left: 1rem;
    width: 100%;
  }
  /* 上传队列 */
  .upload-queue-wrapper {
    box-shadow: 0 7px 21px rgba(0, 0, 0, 0.1);
    transition: bottom .3s;
    position: fixed;
    width: 480px;
    bottom: 1rem;
    z-index: 23;
    right: 2rem;
  }
  .upload-queue-wrapper.minimize {
    bottom: -15rem;
  }
  .upload-queue-wrapper .card-header {
    border-bottom: 1px solid #e1e1e1;
  }
  .upload-queue {
    padding: .6rem .6rem 0;
    background: #fbfbfb;
    list-style: none;
    overflow-y: auto;
    height: 15rem;
    margin: 0;
  }
  .upload-item {
    padding: .5rem 0 .5rem .5rem;
    margin-bottom: .6rem;
    background: #efefef;
    font-size: .875rem;
  }
  .upload-operation {
    text-align: center;
    width: 3rem;
  }
  .upload-item .progress {
    background: #d0eaff;
    margin-top: 3px;
  }
  .upload-item .ti-check {
    color: #0275d8 !important;
  }
  .upload-name {
    max-width: 14rem;
  }
  .upload-info {
    margin: 3px 0 0 1rem;
    font-size: 12px;
    color: #A6A6A6;
  }
  @media (max-width: 767px) {
    .col.name {
      max-width: 16rem
    }
  }
</style>
