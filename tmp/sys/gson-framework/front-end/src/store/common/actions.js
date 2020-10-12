import {COMMON} from '../mutation-types'
import api from '../../api'

/**
 * 初始化系统数据
 * @param commit
 * @param data
 * @returns {Promise<any>}
 */
export function init({commit}) {
  return new Promise((resolve, reject) => {
    api.common.init().then(({success, data, msg}) => {
      if (success) {
        commit(COMMON.UPDATE_USER, data)
        resolve(data)
      } else {
        reject(msg)
      }
    }).catch(err => {
      reject(err)
    })
  })
}

/**
 *
 * @param commit
 * @param state
 */
export function toggleSlider({commit, state}) {
  commit(COMMON.UPDATE_SLIDER_COLLAPSE, !state.sliderCollapsed)
}
