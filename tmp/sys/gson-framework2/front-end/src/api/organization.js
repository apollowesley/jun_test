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
  company: {
    list(params) {
      return request.get('organization/company', params)
    },
    public(params) {
      return request.get('organization/company/public', params)
    },
    save(data) {
      return request.post(`organization/company`, data)
    },
    update(data) {
      return request.put(`organization/company`, data)
    },
    load(id) {
      return request.get('organization/company/' + id)
    },
    delete(id) {
      return request.delete('organization/company/' + id)
    }
  },
  dept: {
    list(params) {
      return request.get('organization/dept', params)
    },
    fullTree() {
      return request.get('organization/dept/full/tree')
    },
    save(data) {
      return request.post(`organization/dept`, data)
    },
    update(data) {
      return request.put(`organization/dept`, data)
    },
    load(id) {
      return request.get('organization/dept/' + id)
    },
    delete(id) {
      return request.delete('organization/dept/' + id)
    }
  },
  employees: {
    list(params) {
      return request.get('organization/employees', params)
    },
    save(data) {
      return request.post(`organization/employees`, data)
    },
    update(data) {
      return request.put(`organization/employees`, data)
    },
    load(id) {
      return request.get('organization/employees/' + id)
    },
    delete(id) {
      return request.delete('organization/employees/' + id)
    }
  },
  position: {
    list(params) {
      return request.get('organization/position', params)
    },
    save(data) {
      return request.post(`organization/position`, data)
    },
    update(data) {
      return request.put(`organization/position`, data)
    },
    load(id) {
      return request.get('organization/position/' + id)
    },
    delete(id) {
      return request.delete('organization/position/' + id)
    }
  }
}
