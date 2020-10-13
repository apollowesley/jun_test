// import Sortable from 'sortablejs'
// import {dispatch} from './emitter'
import {triggerEvent} from './utils'
import {Auth} from '../resources'
import $ from 'jquery'

/*
 钩子函数（可选）：
 bind: 只调用一次，指令第一次绑定到元素时调用，用这个钩子函数可以定义一个在绑定时执行一次的初始化动作。
 inserted: 被绑定元素插入父节点时调用（父节点存在即可调用，不必存在于 document 中）。
 update: 被绑定元素所在的模板更新时调用，而不论绑定值是否变化。通过比较更新前后的绑定值，可以忽略不必要的模板更新（详细的钩子函数参数见下）。
 componentUpdated: 被绑定元素所在模板完成一次更新周期时调用。
 unbind: 只调用一次， 指令与元素解绑时调用。

 钩子函数被赋予了以下参数：
 el: 指令所绑定的元素，可以用来直接操作 DOM 。
 binding: 一个对象，包含以下属性：
 name: 指令名，不包括 v- 前缀。
 value: 指令的绑定值， 例如： v-my-directive="1 + 1", value 的值是 2。
 oldValue: 指令绑定的前一个值，仅在 update 和 componentUpdated 钩子中可用。无论值是否改变都可用。
 expression: 绑定值的字符串形式。 例如 v-my-directive="1 + 1" ， expression 的值是 "1 + 1"。
 arg: 传给指令的参数。例如 v-my-directive:foo， arg 的值是 "foo"。
 modifiers: 一个包含修饰符的对象。 例如： v-my-directive.foo.bar, 修饰符对象 modifiers 的值是 { foo: true, bar: true }。
 vnode: Vue 编译生成的虚拟节点，查阅 VNode API 了解更多详情。
 oldVnode: 上一个虚拟节点，仅在 update 和 componentUpdated 钩子中可用。

 除了 el 之外，其它参数都应该是只读的，尽量不要修改他们。如果需要在钩子之间共享数据，建议通过元素的 dataset 来进行。
 */
export default {

  // permission check
  permission: {
    bind (el, binding, vnode) {
      if (!Auth.has(binding.value)) {
        el.parentNode.removeChild(el)
      }
    }
  },

  datepicker: {
    bind (el, binding, vnode) {
      var options = Object.assign({
        onSelect (formattedDate, date, instance) {
          triggerEvent(el, 'input', date)
        }
      }, binding.value)
      $(el).datepicker(options)
    }
  },

  /* Simditor WYSIWYG
  simditor: {
    inserted (el, binding) {
      var editor = new Simditor({
        textarea: $(el),
        toolbar: ['title', 'bold', 'italic', 'color', '|', 'ol', 'ul', 'hr', 'blockquote', 'table', 'link', 'image']
      })
      editor.on('blur', () => {
        triggerEvent(el, 'input', editor.getValue())
      })
      el.editor = editor
    },
    update (el, binding) {
      if (el.value.trim()) {
        el.editor.setValue(el.value)
      }
    }
  },
  */

  /* Sortable with sortable.js
  sortable: {
    inserted (el, binding, vnode) {
      var key = binding.arg
      var vm = vnode.context

      var options = Object.assign({
        onUpdate (e) {
          dispatch.call(vm, key, 'sort', e)
        }
      }, binding.value)

      Sortable.create(el, options)
    }
  },
  */

  modal: {
    inserted (el, binding, vnode) {
      vnode.context[binding.value || 'modal'] = new Modal(el, binding.modifiers)
    }
  },

  /* jQuery MaterialRipple Plugin */
  /* 2014 Dominik Biedebach */
  ripple: {
    bind (el, binding) {
      var defaults = {
        rippleClass: binding.arg || 'ripple-element',
        effectClass: binding.expression || 'ripple-effect',
        rippleColor: Object.keys(binding.modifiers).join(' ')
      }

      $('body').on('animationend webkitAnimationEnd oAnimationEnd', '.' + defaults.rippleClass, function () {
        this.remove()
      })

      var addRippleElement = function (element, e) {
        var $element = $(element)
        var offset = $element.offset()
        var d = Math.max($element.width(), $element.height())
        var x = e.pageX - offset.left - d / 2
        var y = e.pageY - offset.top - d / 2
        var style = Object.keys(defaults).map(k => defaults[k]).join(' ')
        $('<span class="' + style + '"></span>')
          .css({width: d, height: d, top: y + 'px', left: x + 'px'})
          .prependTo(element)
      }

      // add Ripple-Wrapper to all Elements
      $(el).addClass('ripple-wrapper').click(function (e) {
        addRippleElement(this, e)
        if (binding.modifiers.stop) {
          e.stopPropagation()
        }
      })
    }
  }
}

/* 模态框 */
export class Modal {
  constructor (el, options) {
    Object.assign(this, options)
    this.visible = false
    this.el = el

    var array = el.querySelectorAll('[data-dismiss="modal"]'),
        that  = this
    Array.from(array).forEach(function (element) {
      element.addEventListener('click', function (e) {
        e.preventDefault()
        that.hide()
      })
    })
  }

  show () {
    document.body.classList.add('modal-open')
    this.el.style.display = 'block'
    this.visible = true
    setTimeout(() => { this.el.classList.add('show') }, 0)
    this.el.addEventListener('click', (e) => {
      if (e.target !== this.el || this.static) return
      this.hide()
    })
  }

  hide () {
    this.el.classList.remove('show')
    this.visible = false
    setTimeout(() => {
      document.body.classList.remove('modal-open')
      this.el.style.display = 'none'
    }, 300)
  }
}
