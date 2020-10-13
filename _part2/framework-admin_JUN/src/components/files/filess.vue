<!--
  文件库 (上传流程: 文件 => 上传至服务端 => 七牛)
-->
<template>
  <div class="d-flex flex-column">
    <ol class="breadcrumb">
      <li class="breadcrumb-item">系统管理</li>
      <li class="breadcrumb-item">文件库</li>
    </ol>
    <div class="bg-white flex-grow mx-3 shadowed">
      <div class="flex-center justify-content-between p-3">
        <ol class="breadcrumb p-1">
          <li class="breadcrumb-item">系统管理</li>
          <li class="breadcrumb-item">文件库</li>
        </ol>
        <div class="font-sm pr-3 d-none d-md-block">
          <a class="text-link mr-3"><span class="img-btn">┼</span>&nbsp;创建文件夹</a>
          <label class="text-link mr-3 mb-0">
            <input type="file" @change="change" hidden multiple>
            <span class="img-btn">┼</span>
            <span>上传</span>
          </label>
          <file-menu></file-menu>
        </div>
      </div>

      <div class="sep mx-3 my-0"></div>
      <div class="flex-center col-header text-grey mx-3 py-3">
        <div class="col check">
          <label class="md-checkbox"><input type="checkbox" @change="check"><i class="bg-primary"></i></label>
        </div>
        <div class="flex-center no-gutters w-100 pl-3">
          <a class="col text-link filename">文件名称 <i class="fa fa-sort-up"></i></a>
          <a class="col text-link size d-none d-md-block">大小</a>
          <a class="col text-link creator hidden-md-down">创建者</a>
          <a class="col text-link hidden-md-down">更新时间</a>
          <div class="col text-right">
            <a class="hint-top" aria-label="列表视图"><i class="text-link mx-1 active ti-view-list-alt"></i></a>
            <a class="hint-top" aria-label="缩略图模式"><i class="text-link mx-1 ti-view-grid"></i></a>
          </div>
        </div>
      </div>
      <div class="sep mx-3 my-0"></div>
      <ul class="list-unstyled" ref="list">
        <li class="filerow flex-center visible px-3" v-for="item in page.items">
          <div class="col check">
            <label class="md-checkbox"><input type="checkbox" :value="item.id" name="files" @change="check"><i class="bg-primary"></i></label>
          </div>
          <div class="flex-center no-gutters w-100 py-2 pl-3 bb-eee" @click="fileClick(item)">
            <div class="col filename text-default">
              <span class="img img-sm" :style="{backgroundImage: 'url(' + item.url + ')'}" v-if="item.mimetype.startsWith('image/')"></span>
              <img class="img-sm" src="/static/img/file_ps.svg" v-else>
              <span class="ml-3">{{item.filename}}</span>
            </div>
            <div class="col size d-none d-md-block">{{item.size | filesize}}</div>
            <div class="col creator hidden-md-down">backflow</div>
            <div class="col hidden-md-down">{{item.updatedTime | timepast}}</div>
            <div class="col invisible d-none d-md-block">
              <a class="text-link mx-2 hint-top" aria-label="下载"><i class="ti-arrow-circle-down"></i></a>
              <a class="text-link mx-2 hint-top" aria-label="更新"><i class="ti-arrow-circle-up "></i></a>
              <a class="text-link mx-2 hint-top" aria-label="移动"><i class="ti-split-h"></i></a>
              <a class="text-link mx-2 hint-top" aria-label="重命名"><i class="ti-pencil"></i></a>
              <a class="text-link mx-2 hint-top" aria-label="更多操作"><i class="ti-angle-down"></i></a>
            </div>
          </div>
        </li>
      </ul>

      <pagination class="py-3 justify-content-center" :page="page" @paging="paging"></pagination>
    </div>

    <div class="upload-queue-wrapper" :class="{minimize}" v-show="showQueue">
      <div class="card">
        <div class="card-header text-right">
          <span class="float-left">上传完成</span>
          <a class="text-link ml-2" :class="[minimize ? 'ti-arrow-up' : 'ti-arrow-down']" @click="minimize = !minimize "></a>
          <a class="text-link ml-2 ti-close" @click="showQueue = false"></a>
        </div>
        <ul class="upload-queue">
          <li class="upload-item flex-center" v-for="(file, index) in queue">
            <span class="img img-sm" :style="{backgroundImage: 'url(' + file.url + ')'}" v-if="file.type.startsWith('image/')"></span>
            <img class="img-sm" src="/static/img/file_ps.svg" v-else>
            <div class="flex-fill ml-2">
              <div class="flex-center text-truncate">
                <span class="upload-name">{{file.name}}</span>
                <span class="upload-info">({{file.loaded | filesize}}/{{file.total | filesize}})</span>
              </div>
              <div class="progress progress-xxs">
                <span class="progress-bar bg-skyblue" :style="{width: file.progress + '%'}"></span>
              </div>
            </div>
            <i class="upload-operation text-link ti-check" v-if="file.id"></i>
            <a class="upload-operation text-link ti-close" v-else @click="abort(file, index)"></a>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
  import Pagination from '../../widgets/pagination.vue'
  import {FileMapping, baseURL} from '../../resources'
  import Uploader from '../../misc/uploader'
  import FileMenu from './files-menu.vue'

  var uploader = new Uploader(baseURL + '/filemapping/upload')

  export default {
    components: {FileMenu, Pagination},
    data: () => ({
      showQueue: false, // 显示上传队列
      minimize: true,   // 最小化上传队列
      loading: false,   // 加载指示
      checked: [],
      queue: [],
      params: {
        parent: 0       // 父文件 (当前目录)
      },
      page: {}
    }),
    beforeRouteEnter (to, from, next) {
      FileMapping.query().then(response => {
        next(vm => {
          vm.page = response.data
          vm.$nextTick(() => vm.$emit('loaded'))
        })
      })
    },
    beforeRouteUpdate (to, from, next) {
      next()
    },
    methods: {
      // 选中文件回调 (执行上传)
      change (e) {
        var options = {
          data: {
            parent: this.param.parent   // 父文件 (所属目录)
          }
        }
        Array.from(e.target.files).forEach(file => {
          // 如果为图片文件, 读取DataURL显示图片
          if (file.type.startsWith('image/')) {
            var reader = new FileReader()
            reader.readAsDataURL(file)
            return reader.onload = (e) => {
              file.url = e.target.result
              this.queue.unshift(uploader.upload(file, options))
            }
          }
          this.queue.unshift(uploader.upload(file, options))
        })
        this.showQueue = true
        this.minimize = false
      },
      // 取消上传
      abort (file, index) {
        this.queue.splice(index, 1)
        file.xhr.abort()
      },
      fileClick (file) {
        console.log(file)
      },
      check (e) {
        this.$refs.list.querySelectorAll('[name=files]').forEach(el => {
          this.checked.push(el.value)
        })
      },
      paging (pn) {
        FileMapping.paging(this, pn)
      }
    }
  }
</script>

<style>
  .col.filename, .upload-name {
    text-overflow: ellipsis;
    white-space: nowrap;
    overflow-x: hidden;
  }
  .col-header .fa { vertical-align: middle; color: #A6A6A6 }
  .col.check { max-width: 4rem; text-align: center }
  .col.filename { min-width: 45% }
  .col.size { max-width: 6rem }
  .col.creator { max-width: 9rem }
  .col.invisible { text-align: center }
  .filerow {
    transition: background-color 218ms;
    cursor: pointer;
    color: #808080;
  }
  .filerow:hover { background: #f5f5f5 }
  .filerow .col { white-space: nowrap }
  /* 上传队列 */
  .upload-queue-wrapper {
    box-shadow: 0 7px 21px rgba(0, 0, 0, 0.1);
    transition: bottom 218ms;
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
    margin-left: 1rem;
    font-size: 12px;
    color: #A6A6A6;
  }
  @media (max-width: 767px) {
    .col.filename {
      max-width: 16rem
    }
  }
</style>
