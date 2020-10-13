<template>
  <ul class="treeview row">
    <li class='parent open col-md-6 col-lg-4' v-if="root.children.length" v-for="root in treenodes" type="root">
      <span class="treeitem" title='展开/折叠' @click.stop="toggle">
        <i :class='root.icon'></i>
        <span>{{root.name}}</span>
      </span>
      <label class="md-checkbox ml-1">
        <input type="checkbox" name="items" :value="root.id" :checked="checked.includes(root.id)" @change="change">
        <i class="bg-warning"></i>
        <span class="text-link font-xs">全部</span>
      </label>
      <children :parent="root" :checked="checked"></children>
    </li>
    <li v-else type="root">
      <span class="treeitem">
        <label class="md-checkbox">
          <input type="checkbox" name="items" :value="root.id" :checked="checked.includes(root.id)" @change="change">
          <i class="bg-warning"></i>
          <em :class='root.icon'></em>
          {{root.name}}
        </label>
      </span>
    </li>
  </ul>
</template>
<!--
  树形视图, 目前用于角色的权限展示 (是否还有其它用途?)
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
    parent.querySelectorAll('input').forEach(i => {
      i.checked = checked
    }) // 设置所有子节点为相同状态
    checkAncestors(parent, checked)
  }
  /* 递归设置祖先元素状态 */
  function checkAncestors (parent, checked) {
    if (parent.type === 'root') return  // 当前元素不为根元素时, 还需处理祖先节点的选中状态
    var siblings = parent.parentNode.childNodes
    var len = 0
    siblings.forEach(s => s.querySelector('input').checked && len++)
    parent = closest(parent, 'li')
    parent.querySelector('input').checked = siblings.length === len
    checkAncestors(parent, checked)
  }
  export default {
    name: 'treeview',
    props: {
      treenodes: Array,
      checked: {
        type: Array,
        default: () => []
      }
    },
    methods: {toggle, change},
    components: {
      children: {
        name: 'children',
        props: ['parent', 'checked'],
        template: `<ul>
          <li v-if="child.children.length" class="parent open" v-for="child in parent.children">
            <span class="treeitem" title='展开/折叠' @click.stop="toggle">
              <i :class='child.icon'></i>
              <span>{{child.name}}</span>
            </span>
            <label class="md-checkbox ml-1">
              <input type="checkbox" name="items" :value="child.id" :checked="checked.includes(child.id)" @change="change">
              <i class="bg-warning"></i>
              <span class="text-link font-xs">全部</span>
            </label>
            <children :parent="child" :checked="checked"></children>
          </li>
          <li v-else>
            <span class="treeitem">
              <label class="md-checkbox">
                <input type="checkbox" name="items" :value="child.id" :checked="checked.includes(child.id)" @change="change">
                <i class="bg-warning"></i>
                <em :class='child.icon'></em>
                {{child.name}}
              </label>
            </span>
          </li>
        </ul>`,
        methods: {toggle, change}
      }
    }
  }
</script>
<style>

  .treeview { list-style: none; padding-left: 1rem }
  .treeview ul { padding-left: 34px; padding-top: 10px; list-style: none }
  .treeview ul li:hover { background: rgba(0, 0, 0, .015) }
  .treeview > li:before, .treeview > li:after { display: none !important }
  .treeview li { padding: 5px; position: relative }
  .treeview li::after, .treeview li::before { position: absolute; content: ''; left: -16px }
  .treeview li::before { border-left: 1px solid #ccc; height: 100%; bottom: 50px; top: -10px; width: 1px }
  .treeview li::after { border-top: 1px solid #ccc; height: 20px; top: 20px; width: 22px }
  .treeview li:last-child::before { height: 30px }
  .treeview li::before, .treeview li::after, .treeview .treeitem { -webkit-transition: color, border-color, background-color, .25s ease; transition: color, border-color, background-color, .25s ease; }
  .treeview > ul > li::after, .treeview > ul > li::before { border: 0 }
  .treeview .treeitem { display: inline-block; align-items: center; white-space: nowrap; border: 1px dotted #999; border-radius: 3px; padding: 3px 8px; cursor: pointer }
  .treeview .treeitem > i, .treeview .treeitem em { text-align: center; min-width: 18px; }
  .treeview .parent > ul { display: none }
  .treeview .parent.open > ul { display: block }
  .treeview .parent > .treeitem::before { font-family: FontAwesome; content: '\F055'; display: inline-block; text-align: center; padding: 0 7px 0 3px }
  .treeview .parent.open > .treeitem::before { content: '\f056' }
  .treeview .treeitem:hover { background: #eee; border-color: #94A0B4; color: #222 }
  .treeview .treeitem:hover { background-color: #DF8505; border-color: #C67605; color: #fff }
  .treeview .treeitem:hover ~ ul li::before,
  .treeview .treeitem:hover ~ ul li::after { border-color: #F89406 }
  .treeview .treeitem:hover ~ ul .treeitem { background: #FDDFB3; border-color: #FAA937; color: #222 }
</style>
