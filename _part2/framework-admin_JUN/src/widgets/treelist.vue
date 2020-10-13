<!--
  树形列表, 目前用于权限树展示 (是否还有其它用途?)
-->
<script>
  import {closest} from '../misc/utils'

  /* 展开/收缩子元素 */
  function toggle (e) {
    e.currentTarget.parentNode.classList.toggle('open')
  }

  /* 选中状态改变回调 */
  function change (e) {
    var checked = e.currentTarget.checked
    var parent = closest(e.currentTarget, 'li')
    console.log(parent)
    parent.querySelectorAll('input').forEach(i => {
      i.checked = checked
    }) // 设置所有子节点为相同状态
    checkAncestors(parent, checked)
  }

  /* 递归设置祖先元素状态 */
  function checkAncestors (parent, checked) {
    if (parent.getAttribute('role') == 'root') { return } // 当前元素不为根元素时, 还需处理祖先节点的选中状态
    var siblings = parent.parentNode.childNodes
    var len = 0
    siblings.forEach(s => s.querySelector('input').checked && len++)
    parent = closest(parent, 'li')
    console.log(parent)
    parent.querySelector('input').checked = siblings.length === len
    // checkAncestors(parent, checked)
  }

  export default {
    name: 'permissions',
    props: {
      treenodes: Array,
      checked: {type: Array, 'default': () => []}
    },
    render (h) {
      var that = this

      // 渲染复选框
      function _checkbox (/* 节点对象 */ node, /* 额外样式 */ style, /* 选项文本 */ text) {
        var sub = [
          h('input', {
            domProps: {type: 'checkbox', name: 'items', value: node.id, checked: that.checked.includes(node.id)},
            on: {change}
          }),
          h('i', {'class': 'bg-themedark'})
        ]
        if (text) {
          sub.push(that._v(text))
        }
        return h('label', {'class': ['md-checkbox', style]}, sub)
      }

      // 渲染节点元素
      function contents (node) {
        var parent = node.children && node.children.length  // 是否为父节点
        var sub = []  // 节点(dd-content)内部元素数组

        if (parent) {
          sub.push(h('span', {'class': 'indicator'})) // 展开指示
        } else {
          sub.push(_checkbox(node, 'mr-1'))         // 复选框
        }
        // 图标
        if (node.icon) {
          sub.push(h('i', {'class': node.icon}))
        }
        // 名称
        sub.push(h('span', {domProps: {innerHTML: node.name}}))
        // 全选
        if (parent) {
          sub.push(_checkbox(node, 'ml-auto', ' 全部'))
        }
        //
        var arr = [h(parent ? 'span' : 'label', {'class': 'treeitem', on: {click: toggle}}, sub)]
        // 如果节点包含子节点, 生成子节点列表
        if (parent) {
          arr.push(h('ul', node.children.map(contents)))
        }
        return h('li', {
          'class': [parent ? 'parent' : ''],
          attrs: {role: node.parent == 0 ? 'root' : 'node'}
        }, arr)
      }

      return h('ul', {'class': 'treelist'}, this.treenodes.map(contents))
    }
  }
</script>
