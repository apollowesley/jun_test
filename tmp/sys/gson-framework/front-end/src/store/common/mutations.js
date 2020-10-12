import {COMMON} from '../mutation-types'

export default {
  [COMMON.UPDATE_USER](state, data) {
    state.user = data
  },
  [COMMON.UPDATE_SLIDER_COLLAPSE](state, data) {
    state.sliderCollapsed = data
  }
}
