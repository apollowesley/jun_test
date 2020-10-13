<template>
  <div></div>
</template>
<!--
  对jstree的封装, 以内嵌或dropdown的形式显示树形
-->
<script>
  import $ from 'jquery'

  var $attach,     // attach target
    $parent,     // parent element
    $tree       // jstree instanse

  var setText = function (selected) {
    var text = selected.map(n => n.text).join(',')
    $attach.val(text)
  }

  var clickHander = function (e) {
    var target = e.target
    var className = target.className || target.parentNode.className
    if (target !== $attach[0] && !/^jstree/.test(className)) {
      $(document.body).off('click', clickHander)
      $parent.removeClass('show')
    }
    return false
  }

  var bindevent = function (context) {
    $tree
      .on('ready.jstree', () => { // select initial nodes when tree is ready
        $tree.jstree('select_node', context.value, false)
      })
      .on('changed.jstree', (event, data) => {  // set value/text to target/attach where selected nodes changed
        var selected = data.selected
        if (selected.length && !context.multiple) {
          selected = selected[0]
          if (typeof context.value === 'number') {
            selected = Number(selected)
          }
        }
        setText($tree.jstree('get_selected', true))
        context.$emit('input', selected)
      })
  }

  export default {
    name: 'jstree',
    props: {
      treenodes: {            // treenodes
        requried: true,
        type: Array
      },
      dropdown: String,       // wrap in a bootstrap dropdown-menu, it's value indicate the dropdown direction, could be `up` or `down`
      checkbox: Boolean,      //
      multiple: Boolean,      // allow multiselection
      attach: String,         // attach target
      value: [Number, Array, String]  // value from `v-model` directive
    },
    mounted () {
      $attach = $(this.attach)
      $tree = $(this.$el)

      // initialize as a dropdown-tree
      if (this.dropdown !== undefined) {
        $parent = $tree.parent().addClass('drop' + this.dropdown || 'down')
        $tree.addClass('dropdown-menu')
        $tree.css({'min-width': $attach.outerWidth(false), 'left': $parent.css('padding-left')})
        $attach.focus(function () {
          if (!$parent.hasClass('show')) {
            $(document.body).on('click', clickHander)
            $tree.css('min-width', $attach.outerWidth(false))
            $parent.addClass('show')
          }
        })
      }
      bindevent(this)
    },
    watch: {
      treenodes (val) {
        $tree.jstree({
          core: {
            data: val,
            multiple: this.multiple // allow multiselection
          },
          plugins: ['types', 'checkbox', 'wholerow'],
          types: {
            '#': {icon: 'fa fa-institution'},
            'default': {icon: 'fa fa-folder'},
            'dept': {icon: 'fa fa-home'},
            'perm': {icon: 'fa fa-eye'}
          },
          checkbox: {
            three_state: false,     // should checkboxes cascade down and have an undetermined state. Defaults to true
            cascade: ''             // up down undetermined
          }
        })
      },
      value: function (val) {
        var tree = $tree.jstree(true)
        tree.deselect_all(true)
        tree.select_node(val, true)
      }
    }
  }
</script>
