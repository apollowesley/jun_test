<template>
  <ul class="treelist">
    <li :class="{'parent': root.children.length}" :data-id="root.id" v-for="root in treenodes" type="root">
      <span class="treeitem visible" @click.stop="toggle">
        <span class="indicator"></span>
        <i v-if="root.icon" :class="root.icon"></i>
        <span class="mr-auto">{{root.name}}</span>
        <span class="column">{{root.code}}</span>
        <span class="column text-center invisible">
          <a class="text-primary">编辑</a>
          <a class="text-danger">删除</a>
        </span>
      </span>
      <children v-if="root.children.length" :parent="root"></children>
    </li>
  </ul>
</template>
<!--
  权限树形列表
-->
<script>
  /* 展开/收缩子元素 */
  function toggle (e) {
    e.currentTarget.parentNode.classList.toggle('open')
  }

  function go (item, ctx) {
    console.log(ctx)
  }

  /*
  var sortableOptions = {
    animation: 150,
    onMove (e, original) { // when item moves in the list or between lists, return false for cancel
      return e.related.parentNode === e.dragged.parentNode
    }
  }
  */

  export default {
    name: 'treeview',
    props: ['treenodes'],
    methods: {toggle, go},
    components: {
      children: {
        name: 'children',
        props: ['parent', 'checked'],
        template: `<ul>
          <li :class="{'parent': child.children.length}" :data-id="child.id" v-for="child in parent.children">
            <span class="treeitem visible" @click.stop="toggle">
              <span class="indicator"></span>
              <i v-if="child.icon" :class="child.icon"></i>
              <span class="mr-auto">{{child.name}}</span>
              <span class="column">{{child.code}}</span>
              <span class="column text-center invisible">
                <a class="text-primary">编辑</a>
                <a class="text-danger">删除</a>
              </span>
            </span>
            <children v-if="child.children.length" :parent="child"></children>
          </li>
        </ul>`,
        methods: {toggle, go}
      }
    }
  }
</script>
