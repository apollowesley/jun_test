/*
 * 事件广播 (向下)
 */
export function broadcast (componentName, eventName, params) {
  this.$children.forEach(child => {
    const name = child.$options.name

    if (name === componentName) {
      child.$emit.apply(child, [eventName].concat(params))
    } else {
      // TODO 如果 params 是空数组，接收到的会是 undefined
      broadcast.apply(child, [componentName, eventName].concat([params]))
    }
  })
}

/*
 * 事件分发 (向上)
 */
export function dispatch (componentName, eventName, params) {
  let parent = this
  let name = this.$options.name

  while (parent && componentName !== name) {
    parent = parent.$parent

    if (parent) {
      name = parent.$options.name
    }
  }
  if (parent) {
    parent.$emit.apply(parent, [eventName].concat(params))
  }
}

export default {
  methods: {broadcast, dispatch}
}