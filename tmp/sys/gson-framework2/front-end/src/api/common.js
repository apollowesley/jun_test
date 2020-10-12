/**
 *<p>****************************************************************************</p>
 * <p><b>Copyright © 2010-2019 soho team All Rights Reserved<b></p>
 * <ul style="margin:15px;">
 * <li>Description : </li>
 * <li>Version     : 1.0</li>
 * <li>Creation    : 2019年01月29日</li>
 * <li>@author     : ____′↘夏悸</li>
 * </ul>
 * <p>****************************************************************************</p>
 */
import request from '../kernel/request'

export default {
  init() {
    return request.get('init')
  },
  login(data) {
    return request.post('login', data)
  },
  logout() {
    return request.post('logout')
  }
}
