import store from '@/store'

/**
 * @param {Array} value
 * @returns {Boolean}
 * @example see @/views/permission/directive.vue
 */
export default function checkPermission(value) {
  if (value && value instanceof Array && value.length > 0) {
    const permissions = store.getters && store.getters.permissions
    const hasPermission = permissions.some(permission => {
      return value.includes(permission)
    })

    if (!hasPermission) {
      return false
    }
    return true
  } else {
    return false
  }
}
