<template>
  <div>
    <a class="text-link" @click="addRoot"><span class="img-btn">┼</span> 新增分类</a>
    <nestable class="mt-3" :treenodes="treenodes" ref="tree"></nestable>
  </div>
</template>

<script>
  import Nestable from '../../misc/nestable.esm'
  import {closest} from '../../misc/utils'
  import {Category, http} from '../../resources'

  // 为所有节点添加 edit 属性
  function modify (node) {
    if (!node.children) {
      node.children = []
    }
    if (node.children.length)
      node.children.forEach(modify)
    node.edit = false
    return node
  }

  export default {
    name: 'categoryTree',
    props: ['maxDepth'],
    data: () => ({
      treenodes: []
    }),
    created () {
      var that = this
      Category.tree().then(response => {
        that.treenodes = response.data.data.map(modify)
        that.$nextTick(() => {
          new Nestable(that.$refs.tree.$el, {
            callback (root, el, pos) {
              var parent = el.parent().closest('li.dd-item', root)
              var next = el.next()
              var parentId = parent == root ? 0 : parent.data('id')
              that.move(el.data('id'), next.data('id'), parentId)
            },
            maxDepth: this.maxDepth   // 最大层级
          })
        })
      })
    },
    methods: {
      move (from, to, parent) {
        Category.move({from, to, parent}).then(response => {
          if (response.data.code == 0) {
            return this.$notice.success('排序操作成功')
          }
          this.$notice.error(response)
        })
      },
      addRoot () {
        this.treenodes.push({
          id: new Date().getTime(),
          categroyName: '新增分类',
          edit: true,
        })
      }
    },
    components: {
      nestable: {
        props: ['treenodes'],
        methods: {
          addChild (node, e) {
            node.children.push({
              id: new Date().getTime(),
              parentId: node.categroyId,
              categroyName: '新增分类',
              edit: true,
            })
          },
          edit (node, e) {
            if (node.edit) {
              var li = closest(e.target, 'li')
              var categroyId = li.dataset.id
              var categroyName = li.querySelector('.text').firstChild.value.trim()
              var parent = closest(li, 'li.dd-item', this.$el)
              var parentId = parent == this.$el ? 0 : parent.dataset.id
              if (node.categroyName !== categroyName) {
                Category.edit({categroyId, categroyName, parentId}).then(response => {
                  if (response.data.code == 0) {
                    node.categroyName = response.data.data.categroyName
                    node.categroyId = response.data.data.categroyId
                    return this.$notice.success((categroyId ? '编辑' : '创建') + '分类成功')
                  }
                  this.$notice.error(response.data.msg)
                })
              }
            }
            node.edit = !node.edit
          },
          del (node, index, parent) {
            Category.delete(node.categroyId).then(response => {
              if (response.data.code == 0) {
                parent.children.splice(index, 1)
                return this.$notice.success('删除成功')
              }
              this.$notice.error(response.data.msg)
            })
          }
        },
        render (h) {
          var that = this

          function _contents (node, index, pnode) {
            // 节点(dd-content)内部元素数组
            var sub = [h('i', {class: 'dd-handle', domProps: {title: '拖动'}})],
                parent = node.children && node.children.length

            // 节点名称/输入框
            sub.push(h('div', {
              class: 'text',
              domProps: {innerHTML: node.edit ? '<input value="' + node.categroyName + '">' : node.categroyName},
              on: {
                keyup (e) {
                  if (e.keyCode == 13) { // 按下Enter键, 执行修改
                    that.edit(node, e)
                  }
                  if (e.keyCode == 27) { // 按下Esc键, 撤消创建
                    node.edit = false
                  }
                }
              }
            }))
            // 添加子分类按钮 TODO 根据层级做判断
            if (!node.parentId) {
              sub.push(h('a', {
                class: 'text-link',
                domProps: {innerHTML: '添加子分类'},
                on: {click: that.addChild.bind(null, node)},
              }))
            }
            // 编辑按钮
            sub.push(h('a', {
              class: 'text-link',
              domProps: {innerHTML: node.edit ? '保存' : '编辑'},
              on: {click: that.edit.bind(null, node)}
            }))
            // 删除按钮
            sub.push(h('a', {
              class: 'text-danger',
              domProps: {innerHTML: '删除'},
              on: {
                click () {
                  if (node.edit && !node.categroyId) {
                    return pnode.children.splice(index, 1)
                  }
                  that.del(node, index, pnode)
                }
              }
            }))
            var arr = [h('div', {class: ['dd-content', node.edit ? 'edit' : '']}, sub)]
            // 如果节点包含子节点, 生成子节点列表
            if (parent) {
              arr.push(h('ol', {class: 'dd-list'}, node.children.map(function (n, i) {
                return _contents(n, i, node)
              })))
            }

            return h('li', {class: ['dd-item', parent ? 'parent' : ''], attrs: {'data-id': node.categroyId}}, arr)
          }

          return h('ol', {class: 'dd-list'}, this.treenodes.map(function (n, i) {
            return _contents(n, i, null)
          }))
        }
      }
    }
  }
</script>
