/**
 *<p>****************************************************************************</p>
 * <p><b>Copyright © 2010-2019 soho team All Rights Reserved<b></p>
 * <ul style="margin:15px;">
 * <li>Description : </li>
 * <li>Version     : 1.0</li>
 * <li>Creation    : 2019年05月06日</li>
 * <li>@author     : ____′↘夏悸</li>
 * </ul>
 * <p>****************************************************************************</p>
 */
export default new Model({
  id: null,
  'masterDepartmentId': null,
  'jobNumber': null,
  'type': {
    type: Number,
    default: 1
  },
  'telephone': null,
  'idCode': null,
  'email': null,
  'address': null,
  'entryDate': null,
  'gender': {
    type: Number,
    default: 0
  },
  'userName': {
    type: String
  },
  'status': {
    type: String,
    default: '试用期'
  },
})