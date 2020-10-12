export function user(state) {
  return state.user
}

export function sliderCollapsed(state) {
  return state.sliderCollapsed
}

export function userName(state) {
  let {employeesName, account} = state.user
  return employeesName ? employeesName : account
}
