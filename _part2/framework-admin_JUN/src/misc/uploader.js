import toastr from './toastr.esm'

/*
 AJAX 文件上传
 */
export default class Uploader {

  static DEFAULTS = {
    callback (e) {
      console.log(e)
    }
  }

  constructor (url, options) {
    if (typeof url !== 'string') {
      throw new Error('[Uploader]: url is must be an string!')
    }
    this.options = Object.assign({}, Uploader.DEFAULTS, options)
    this.url = url
  }

  upload (file, options) {
    // 优先使用options里的上传url
    var url = options.url || this.url
    var xhr = new XMLHttpRequest()
    var formdata = new FormData()
    // 添加文件
    formdata.append('file', file)
    // 添加额外参数
    Object.keys(options.data).forEach(k => {
      formdata.append(k, options.data[k])
    })

    // 构建上传文件对象
    var fileitem = {
      name: file.name,
      size: file.size,
      type: file.type,
      url: file.url,
      progress: 0,
      loaded: 0,
      total: 0,
      id: 0,
      xhr
    }
    xhr.open('POST', url, true)
    xhr.responseType = 'json'
    xhr.upload.addEventListener('progress', function (e) {
      fileitem.loaded = e.loaded
      fileitem.total = e.total
      if (e.lengthComputable) {
        fileitem.progress = e.loaded / e.total * 100
      }
    })
    xhr.addEventListener('abort', function (e) {
      console.log('abort', e)
    })
    xhr.addEventListener('error', function (e) {
      console.log('error', e)
      toastr.error(e)
    })
    xhr.addEventListener('load', function (e) {
      Object.assign(fileitem, xhr.response.file)
    })
    xhr.send(formdata)
    return fileitem
  }
}
