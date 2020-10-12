import Vue from 'vue'

Vue.prototype.$formatDate = (num) => {
  if (!num) {
    return ''
  }
  var date = new Date(num)
  var y = date.getFullYear()
  var m = ('00' + (date.getMonth() + 1)).slice(-2)
  var d = ('00' + date.getDate()).slice(-2)
  return `${y}-${m}-${d}`
}
Vue.prototype.$formatDateTime = (num) => {
  if (!num) {
    return ''
  }
  var date = new Date(num)
  var y = date.getFullYear()
  var m = ('00' + (date.getMonth() + 1)).slice(-2)
  var d = ('00' + date.getDate()).slice(-2)
  var h = ('00' + date.getHours()).slice(-2)
  var min = ('00' + date.getMinutes()).slice(-2)
  return `${y}-${m}-${d} ${h}:${min}`
}