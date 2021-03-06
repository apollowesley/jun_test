/**
 *<p>****************************************************************************</p>
 * <p><b>Copyright © 2010-2019 soho team All Rights Reserved<b></p>
 * <ul style="margin:15px;">
 * <li>Description : </li>
 * <li>Version     : 1.0</li>
 * <li>Creation    : 2019年01月30日</li>
 * <li>@author     : ____′↘夏悸</li>
 * </ul>
 * <p>****************************************************************************</p>
 */
import request from '../kernel/request'

export default {
  account: {
    list(params) {
      return request.get('system/account', params)
    },
    save(data) {
      return request.post(`system/account`, data)
    },
    update(data) {
      return request.put(`system/account`, data)
    },
    load(id) {
      return request.get('system/account/' + id)
    },
    delete(id) {
      return request.delete('system/account/' + id)
    }
  },
  role: {
    list(params) {
      return request.get('system/role', params)
    },
    save(data) {
      return request.post(`system/role`, data)
    },
    update(data) {
      return request.put(`system/role`, data)
    },
    load(id) {
      return request.get('system/role/' + id)
    },
    delete(id) {
      return request.delete('system/role/' + id)
    }
  }
}
