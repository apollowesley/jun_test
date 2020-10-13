import Treetable from './treetable.esm'
import toastr from './toastr.esm'
import swal from 'sweetalert'

export function treeify (el, expanded) {
  return new Treetable(el, {
    clickableNodeNames: true,
    expandable: true,
    indent: 35,
    onInitialized () {
      Object.keys(expanded).forEach(n => {
        this.expandNode(n)
      })
    },
    onNodeCollapse () {
      delete expanded[this.id]
    },
    onNodeExpand () {
      expanded[this.id] = 1
    }
  })
}

export const showResponse = function (response, success, fail) {
  if (response.data.success) {
    return swal({type: 'success', title: '操作成功', text: '您的操作已经执行成功!'}, () => {
      success && success()
    })
  }
  if (response.data.errors) {
    toastr.warning(Object.keys(response.data.errors).map(k => response.data.errors[k]).join('<br>'))
  }
  if (response.data.msg) {
    swal('操作失败', response.data.msg, 'error')
  }
  fail && fail()
}

export const del = function (confirmed) {
  return swal({
    type: 'warning',
    title: '您确定?',
    text: '删除的记录将不能恢复!',
    showCancelButton: true,
    cancelButtonText: '取消',
    confirmButtonText: '是的, 我要删除!',
    confirmButtonColor: '#DD6B55',
    showLoaderOnConfirm: true,
    closeOnConfirm: false
  }, confirmed)
}

export const dropdownEvent = function () {
  // $(document).on('click.bs.dropdown.data-api', '.selectize .dropdown-menu', e => e.stopPropagation())
  document.documentElement.addEventListener('click', function (e) {
    if (e && e.which === 3) { return } // right button (assuming a right-handed mouse)
    var el = e.target, parent = closest(el, '.dropdown')
    Array.from(document.querySelectorAll('.dropdown')).concat(Array.from(document.querySelectorAll('.dropup'))).forEach(m => { // close all dropdown-menus
      if (m.contains(el)) {
        if (!m.classList.contains('selectize')) { // prevent auto hiding when click a dropdown-menu inside `.selectize` class
          var toggle = closest(el, '[data-toggle="dropdown"]', m)
          m.classList[toggle !== m ? 'add' : 'remove']('show')
        }
        return
      }
      m.classList.remove('show')
    })
    // if (el.getAttribute('data-toggle') != 'dropdown') return  // 只处理[data-toggle='dropdown']的元素
    if (!parent || parent.classList.contains('show') || !parent.className.match(/dropdown|dropup/) || el.disabled || el.classList.contains('disabled')) { return }
    parent.classList.add('show')
  })
}

/* 生成当前时间戳对应的62进制形式短链接 */
export const shorten = function () {
  var DIGITS = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'
  var seq = new Date().getTime()
  var len = DIGITS.length
  var arr = []
  do {
    arr.unshift(DIGITS.charAt(seq % len))
    seq = parseInt(seq / len)
  } while (seq !== 0)
  return arr.join('')
}

// 按size拆分数组
export const chunk = function (array, size) {
  var arr = []
  for (var i = 0; i < array.length; i += size) {
    arr.push(array.slice(i, i + size))
  }
  return arr
}

/* 格式化文件大小 */
export const filesize = function (bytes) {
  var unit = 'B'
  if (bytes > 1024) {
    bytes /= 1024
    unit = 'KB'
  }
  if (bytes > 1024) {
    bytes /= 1024
    unit = ' MB'
  }
  return Number(bytes.toFixed(1)) + ' ' + unit
}

/* returns duration from millisecends in hh:mm:ss format */
export const format = function (secends, millis) {
  if (!secends) return ''
  secends = millis ? Math.floor(secends / 1000) : secends
  var hou = Math.floor(secends / 3600),
      min = Math.floor(secends % 3600 / 60),
      sec = Math.floor(secends % 60)

  hou = hou ? hou + ':' : ''
  return hou + hou ? pad(min) : min + ':' + pad(sec)
}

/* 个位前补零 */
export const pad = function (n) {
  n = n.toString()
  return n[1] ? n : '0' + n
}

/* 计算已过去的时间 */
export const timepast = function (timestamp) {
  var uminute = 60,
      uhour   = uminute * 60,
      uday    = uhour * 24,
      umonth  = uday * 30,
      uyear   = umonth * 12,
      diff    = (new Date().getTime() - timestamp) / 1000
  if (diff < 60) return Math.abs(Math.ceil(diff)) + '秒前'
  var year = parseInt(diff / uyear)
  if (year > 0) return year + '年前'
  var month = parseInt(diff / umonth)
  if (month > 0) return month + '个月前'
  var week = parseInt(diff / (uday * 7))
  if (week > 0) return week + '周前'
  var days = parseInt(diff / uday)
  if (days > 0) return days + '天前'
  var hours = parseInt(diff / uhour)
  if (hours > 0) return hours + '小时前'
  var minites = parseInt(diff / uminute)
  if (minites > 0) return minites + '分钟前'
}

export const elementIndex = function (el) {
  var i = 0
  while ((el = el.previousSibling) !== null) {
    i++
  }
  return i
}

/* 触发DOM事件 */
export const triggerEvent = function (el, name, data) {
  var evt = document.createEvent('Event')
  evt.initEvent(name, true, true)
  data && Object.assign(evt, data)
  el.dispatchEvent(evt)
}

/* 匹配最近的祖先节点 (使用原生方法) */
export const closest = function (el, selector, ctx = document) {
  var matchesSelector = el.matches || el.webkitMatchesSelector || el.mozMatchesSelector || el.msMatchesSelector
  if (!matchesSelector) {
    return _closest(el, selector, ctx)
  }
  while (el = el.parentElement) {
    if (el === ctx || matchesSelector.call(el, selector)) {
      return el
    }
  }
}

/* 匹配最近的祖先节点 */
export const _closest = function (el, selector, ctx = document) {
  if (!el) return
  while (el = el.parentNode) {
    if (el === ctx || matches(el, selector)) {
      return el
    }
  }
}

/* 判断元素是否匹配给定的CSS选择器 */
export const matches = function (el, selector) {
  if (!el) return false
  selector = selector.split('.')
  var tag = selector.shift().toUpperCase(),
      re  = new RegExp('\\s(' + selector.join('|') + ')(?=\\s)', 'g')
  return (
    (tag === '' || el.nodeName.toUpperCase() === tag) &&
    (!selector.length || ((' ' + el.className + ' ').match(re) || []).length === selector.length)
  )
}

// Returns a function, that, as long as it continues to be invoked, will not
// be triggered. The function will be called after it stops being called for
// N milliseconds. If `immediate` is passed, trigger the function on the
// leading edge, instead of the trailing.
export const debounce = function (func, wait, immediate) {
  var timeout, args, context, timestamp, result

  var later = function () {
    var last = Date.now() - timestamp

    if (last < wait && last >= 0) {
      timeout = setTimeout(later, wait - last)
    } else {
      timeout = null
      if (!immediate) {
        result = func.apply(context, args)
        if (!timeout) context = args = null
      }
    }
  }
  return function () {
    context = this
    args = arguments
    timestamp = Date.now()
    var callnow = immediate && !timeout
    if (!timeout) timeout = setTimeout(later, wait)
    if (callnow) {
      result = func.apply(context, args)
      context = args = null
    }
    return result
  }
}
