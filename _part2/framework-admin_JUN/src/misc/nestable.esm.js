/*
 * Nestable jQuery Plugin - Copyright (c) 2014 Ramon Smit - https://github.com/RamonSmit/Nestable
 */
import $ from 'jquery'

/**
 * Detect CSS pointer-events property
 * events are normally disabled on the dragging element to avoid conflicts
 * https://github.com/ausi/Feature-detection-technique-for-pointer-events/blob/master/modernizr-pointerevents.js
 */
var hasPointerEvents = (function () {
  var el    = document.createElement('div'),
      docEl = document.documentElement
  if (!('pointerEvents' in el.style)) {
    return false
  }
  el.style.pointerEvents = 'auto'
  el.style.pointerEvents = 'x'
  docEl.appendChild(el)
  var supports = window.getComputedStyle && window.getComputedStyle(el, '').pointerEvents === 'auto'
  docEl.removeChild(el)
  return !!supports
})()

var hasTouch = 'ontouchstart' in window

var eStart  = hasTouch ? 'touchstart' : 'mousedown',
    eMove   = hasTouch ? 'touchmove' : 'mousemove',
    eEnd    = hasTouch ? 'touchend' : 'mouseup',
    eCancel = hasTouch ? 'touchcancel' : 'mouseup'

var $document = $(document)

var DEFAULTS = {

  rootClass: 'dd',
  listClass: 'dd-list',
  itemClass: 'dd-item',
  dragClass: 'dd-dragel',
  emptyClass: 'dd-empty',
  handleClass: 'dd-handle',
  placeClass: 'dd-placeholder',
  noDragClass: 'dd-nodrag',
  noChildrenClass: 'dd-nochildren',

  expandClass: 'dd-expand',

  listTag: 'ol',
  itemTag: 'li',

  group: 0,
  maxDepth: 5,
  threshold: 20,      // mouse move threshold
  fixedDepth: false,  // fixed item's depth
  fixed: false,

  callback: function (l, e, p) {},
  onDragStart: function (l, e, p) {}
}

function Plugin (element, options) {

  this.el = $(element)

  this.options = $.extend({}, DEFAULTS, options)

  this.init()
}

Plugin.prototype = {

  init: function () {
    var list = this

    list.reset()

    list.el.data('nestable-group', this.options.group)

    list.placeEl = $('<div class="' + list.options.placeClass + '"/>')

    list.el.find(list.options.itemTag).each(function () {
      var item = $(this), parent = item.parent()
      list.setParent(item)
      if (parent.hasClass(list.options.expandClass)) {
        parent.parent().addClass(list.options.expandClass)
      }
    })

    list.el.on('click', 'i', function (e) {
      if (list.dragEl || (!hasTouch && e.button !== 0)) {
        return
      }
      $(e.currentTarget)
        .parents(list.options.itemTag).eq(0)
        .toggleClass(list.options.expandClass)
    })

    var onStartEvent = function (e) {
      var handle = $(e.target)
      if (!handle.hasClass(list.options.handleClass)) {
        if (handle.closest('.' + list.options.noDragClass).length) {
          return
        }
        handle = handle.closest('.' + list.options.handleClass)
      }
      if (!handle.length || list.dragEl || (!hasTouch && e.which !== 1) || (hasTouch && e.touches.length !== 1)) {
        return
      }
      e.preventDefault()
      list.dragStart(hasTouch ? e.touches[0] : e)
    }

    var onMoveEvent = function (e) {
      if (list.dragEl) {
        e.preventDefault()
        list.dragMove(hasTouch ? e.touches[0] : e)
      }
    }

    var onEndEvent = function (e) {
      if (list.dragEl) {
        e.preventDefault()
        list.dragStop(hasTouch ? e.changedTouches[0] : e)
      }
    }

    if (hasTouch) {
      list.el[0].addEventListener(eStart, onStartEvent, false)
      window.addEventListener(eMove, onMoveEvent, false)
      window.addEventListener(eEnd, onEndEvent, false)
      window.addEventListener(eCancel, onEndEvent, false)
    }
    else {
      list.el.on(eStart, onStartEvent)
      $document.on(eMove, onMoveEvent)
      $document.on(eEnd, onEndEvent)
    }

    var destroyNestable = function () {
      if (hasTouch) {
        list.el[0].removeEventListener(eStart, onStartEvent, false)
        window.removeEventListener(eMove, onMoveEvent, false)
        window.removeEventListener(eEnd, onEndEvent, false)
        window.removeEventListener(eCancel, onEndEvent, false)
      }
      else {
        list.el.off(eStart, onStartEvent)
        $document.off(eMove, onMoveEvent)
        $document.off(eEnd, onEndEvent)
      }

      list.el.off('click')
      list.el.unbind('nestable-destroy')

      list.el.data('nestable', null)
    }

    list.el.bind('nestable-destroy', destroyNestable)
  },

  destroy: function () {
    this.el.trigger('nestable-destroy')
  },

  remove: function (itemId) {
    $(this.el)
      .children('.' + this.options.listClass)
      .find('[data-id="' + itemId + '"]')
      .remove()

    // remove empty children lists
    $(this.el).find('.' + this.options.listClass + ':empty').remove()
  },

  reset: function () {
    this.mouse = {
      offsetX: 0,
      offsetY: 0,
      startX: 0,
      startY: 0,
      lastX: 0,
      lastY: 0,
      nowX: 0,
      nowY: 0,
      distX: 0,
      distY: 0,
      dirAx: 0,
      dirX: 0,
      dirY: 0,
      lastDirX: 0,
      lastDirY: 0,
      distAxX: 0,
      distAxY: 0
    }
    this.moving = false
    this.dragEl = null
    this.dragRootEl = null
    this.dragDepth = 0
    this.hasNewRoot = false
    this.pointEl = null
  },

  expandAll: function () {
    this.el.find(this.options.itemTag).addClass(this.options.expandClass)
  },

  collapseAll: function () {
    this.el.find(this.options.itemTag).removeClass(this.options.expandClass)
  },

  setParent: function (li) {
    if (li.children(this.options.listTag).length) {
      li.addClass(this.options.expandClass)
    }
  },

  unsetParent: function (li) {
    li.removeClass(this.options.expandClass)
    li.children(this.options.listTag).remove()
  },

  setIndexOfItem: function (item, index) {
    if ((typeof index) === 'undefined') {
      index = []
    }

    index.unshift(item.index())

    if ($(item[0].parentNode)[0] !== this.dragRootEl[0]) {
      this.setIndexOfItem($(item[0].parentNode), index)
    }
    else {
      this.dragEl.data('indexOfItem', index)
    }
  },

  restoreItemAtIndex: function (dragElement) {
    var indexArray = this.dragEl.data('indexOfItem'),
        currentEl  = this.el

    for (var i = 0; i < indexArray.length; i++) {
      if ((indexArray.length - 1) === parseInt(i)) {
        placeElement(currentEl, dragElement)
        return
      }
      currentEl = currentEl[0].children[indexArray[i]]
    }

    function placeElement (currentEl, dragElement) {
      if (indexArray[indexArray.length - 1] === 0) {
        $(currentEl).prepend(dragElement.clone())
      }
      else {
        $(currentEl.children[indexArray[indexArray.length - 1] - 1]).after(dragElement.clone())
      }
    }
  },

  dragStart: function (e) {
    var mouse    = this.mouse,
        target   = $(e.target),
        dragItem = target.closest(this.options.itemTag)

    var position = {left: e.pageX, top: e.pageY}

    var continueExecution = this.options.onDragStart.call(this, this.el, dragItem, position)

    if (typeof continueExecution !== 'undefined' && continueExecution === false) {
      return
    }

    this.placeEl.css('height', dragItem.outerHeight())

    mouse.offsetX = e.pageX - dragItem.offset().left
    mouse.offsetY = e.pageY - dragItem.offset().top
    mouse.startX = mouse.lastX = e.pageX
    mouse.startY = mouse.lastY = e.pageY

    this.dragRootEl = this.el
    this.dragEl = $(document.createElement(this.options.listTag)).addClass(this.options.listClass + ' ' + this.options.dragClass)
    this.dragEl.css('width', dragItem.outerWidth())

    this.setIndexOfItem(dragItem)

    // fix for zepto.js
    // dragItem.after(this.placeEl).detach().appendTo(this.dragEl)
    dragItem.after(this.placeEl)
    dragItem[0].parentNode.removeChild(dragItem[0])
    dragItem.appendTo(this.dragEl)

    this.dragEl.css({
      'left': e.pageX - mouse.offsetX,
      'top': e.pageY - mouse.offsetY
    }).appendTo(document.body)

    // total depth of dragging item
    var i, depth, items = this.dragEl.find(this.options.itemTag)
    for (i = 0; i < items.length; i++) {
      depth = $(items[i]).parents(this.options.listTag).length
      if (depth > this.dragDepth) {
        this.dragDepth = depth
      }
    }
  },

  dragStop: function (e) {
    // fix for zepto.js
    // this.placeEl.replaceWith(this.dragEl.children(this.options.itemTag + ':first').detach())
    var el = this.dragEl.children(this.options.itemTag).first()
    el[0].parentNode.removeChild(el[0])
    this.placeEl.replaceWith(el)

    var position = {}
    position.top = e.pageY
    position.left = e.pageX

    if (this.hasNewRoot) {
      if (this.options.fixed === true) {
        this.restoreItemAtIndex(el)
      } else {
        this.el.trigger('lostItem')
      }
      this.dragRootEl.trigger('gainedItem')
    }
    else {
      this.dragRootEl.trigger('change')
    }

    this.dragEl.remove()
    this.options.callback.call(this, this.dragRootEl, el, position)

    this.reset()
  },

  dragMove: function (e) {
    var list, parent, prev, next, depth,
        opt   = this.options,
        mouse = this.mouse

    this.dragEl.css({
      'left': e.pageX - mouse.offsetX,
      'top': e.pageY - mouse.offsetY
    })

    // mouse position last events
    mouse.lastX = mouse.nowX
    mouse.lastY = mouse.nowY
    // mouse position this events
    mouse.nowX = e.pageX
    mouse.nowY = e.pageY
    // distance mouse moved between events
    mouse.distX = mouse.nowX - mouse.lastX
    mouse.distY = mouse.nowY - mouse.lastY
    // direction mouse was moving
    mouse.lastDirX = mouse.dirX
    mouse.lastDirY = mouse.dirY
    // direction mouse is now moving (on both axis)
    mouse.dirX = mouse.distX === 0 ? 0 : mouse.distX > 0 ? 1 : -1
    mouse.dirY = mouse.distY === 0 ? 0 : mouse.distY > 0 ? 1 : -1
    // axis mouse is now moving on
    var newAx = Math.abs(mouse.distX) > Math.abs(mouse.distY) ? 1 : 0

    // do nothing on first move
    if (!mouse.moving) {
      mouse.dirAx = newAx
      mouse.moving = true
      return
    }

    // calc distance moved on this axis (and direction)
    if (mouse.dirAx !== newAx) {
      mouse.distAxX = 0
      mouse.distAxY = 0
    } else {
      mouse.distAxX += Math.abs(mouse.distX)
      if (mouse.dirX !== 0 && mouse.dirX !== mouse.lastDirX) {
        mouse.distAxX = 0
      }
      mouse.distAxY += Math.abs(mouse.distY)
      if (mouse.dirY !== 0 && mouse.dirY !== mouse.lastDirY) {
        mouse.distAxY = 0
      }
    }
    mouse.dirAx = newAx

    /**
     * move horizontal
     */
    if (mouse.dirAx && mouse.distAxX >= opt.threshold) {
      // reset move distance on x-axis for new phase
      mouse.distAxX = 0
      prev = this.placeEl.prev(opt.itemTag)
      // increase horizontal level if previous sibling exists, and can have children
      if (mouse.distX > 0 && prev.length && !prev.hasClass(opt.noChildrenClass)) {
        // cannot increase level when item above is collapsed
        list = prev.find(opt.listTag).last()
        // check if depth limit has reached
        depth = this.placeEl.parents(opt.listTag).length
        if (depth + this.dragDepth <= opt.maxDepth) {
          // create new sub-level if one doesn't exist
          if (!list.length) {
            list = $('<' + opt.listTag + '/>').addClass(opt.listClass)
            list.append(this.placeEl)
            prev.append(list)
            this.setParent(prev)
          } else {
            // else append to next level up
            list = prev.children(opt.listTag).last()
            list.append(this.placeEl)
          }
        }
      }
      // decrease horizontal level
      if (mouse.distX < 0) {
        // we can't decrease a level if an item preceeds the current one
        next = this.placeEl.next(opt.itemTag)
        if (!next.length) {
          parent = this.placeEl.parent()
          this.placeEl.closest(opt.itemTag).after(this.placeEl)
          if (!parent.children().length) {
            this.unsetParent(parent.parent())
          }
        }
      }
    }

    var isEmpty = false

    // find list item under cursor
    if (!hasPointerEvents) {
      this.dragEl[0].style.visibility = 'hidden'
    }
    this.pointEl = $(document.elementFromPoint(e.pageX - document.body.scrollLeft, e.pageY - (window.pageYOffset || document.documentElement.scrollTop)))
    if (!hasPointerEvents) {
      this.dragEl[0].style.visibility = 'visible'
    }
    if (this.pointEl.hasClass(opt.handleClass)) {
      this.pointEl = this.pointEl.closest(opt.itemTag)
    }
    if (this.pointEl.hasClass(opt.emptyClass)) {
      isEmpty = true
    } else if (!this.pointEl.length || !this.pointEl.hasClass(opt.itemClass)) {
      return
    }

    // find parent list of item under cursor
    var pointElRoot = this.el
    var isNewRoot = this.dragRootEl.data('nestable-id') !== pointElRoot.data('nestable-id')

    /**
     * move vertical
     */
    if (!mouse.dirAx || isNewRoot || isEmpty) {
      // check if groups match if dragging over new root
      if (isNewRoot && opt.group !== pointElRoot.data('nestable-group')) {
        return
      }

      // fixed item's depth, use for some list has specific type, eg:'Volume, Section, Chapter ...'
      if (this.options.fixedDepth && this.dragDepth + 1 !== this.pointEl.parents(opt.listTag).length) {
        return
      }

      // check depth limit
      depth = this.dragDepth - 1 + this.pointEl.parents(opt.listTag).length
      if (depth > opt.maxDepth) {
        return
      }
      var before = e.pageY < (this.pointEl.offset().top + this.pointEl.height() / 2)
      parent = this.placeEl.parent()
      // if empty create new list to replace empty placeholder
      if (isEmpty) {
        list = $(document.createElement(opt.listTag)).addClass(opt.listClass)
        list.append(this.placeEl)
        this.pointEl.replaceWith(list)
      } else if (before) {
        this.pointEl.before(this.placeEl)
      } else {
        this.pointEl.after(this.placeEl)
      }
      if (!parent.children().length) {
        this.unsetParent(parent.parent())
      }
      if (!this.dragRootEl.find(opt.itemTag).length) {
        this.dragRootEl.append('<div class="' + opt.emptyClass + '"/>')
      }
      // parent root list has changed
      this.dragRootEl = pointElRoot
      if (isNewRoot) {
        this.hasNewRoot = this.el[0] !== this.dragRootEl[0]
      }
    }
  }
}

export default Plugin
