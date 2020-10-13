<script>
  import Nestable from '../../misc/nestable.esm'

  export default {
    props: ['treenodes', 'maxDepth'],
    updated () {
      new Nestable(this.$el, {
        callback: function (root, el) {
          console.log(el)
        },
        maxDepth: this.maxDepth   // 最大层级
      })
    },
    render (h) {
      function contents (node) {
        var sub = []  // 节点(dd-content)内部元素数组
        // 拖拽点
        sub.push(h('i', {'class': 'dd-handle', domProps: {title: '拖动'}}))
        // 节点名称
        sub.push(h('div', {'class': 'text', domProps: {innerHTML: node.name}}))
        // 添加子分类按钮
        sub.push(h('a', {'class': 'text-link', domProps: {innerHTML: '添加子分类'}}))
        // 编辑按钮
        sub.push(h('a', {'class': 'text-link', domProps: {innerHTML: node.edit ? '保存' : '编辑'}}))
        // 删除按钮
        sub.push(h('a', {'class': 'text-danger', domProps: {innerHTML: '删除'}}))
        // 所有节点内容数组
        var arr = [h('div', {'class': ['dd-content', node.edit ? 'edit' : '']}, sub)]
        // 如果节点包含子节点, 生成子节点列表
        if (node.children && node.children.length) {
          arr.push(h('ol', {'class': 'dd-list parent'}, node.children.map(contents)))
        }
        return h('li', {'class': 'dd-item', attrs: {'data-id': node.id}}, arr)
      }

      return h('ol', {'class': 'dd-list'}, this.treenodes.map(contents))
    }
  }
</script>
