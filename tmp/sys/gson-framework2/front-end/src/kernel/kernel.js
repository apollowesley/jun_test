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
const TOKEN = 'jwt-token'

/**
 * 清除 token
 */
let clearToken = () => {
  localStorage.removeItem(TOKEN)
}

/**
 * 保存 token
 * @param token
 */
let saveToken = (token) => {
  localStorage.setItem(TOKEN, token)
}

/**
 * 获取 token
 * @returns {string}
 */
let token = () => {
  return localStorage.getItem(TOKEN)
}

export default {
  clearToken,
  saveToken,
  token
}
